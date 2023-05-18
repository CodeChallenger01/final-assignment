package com.knoldus.request

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.Directives.{complete, path, pathPrefix}
import akka.http.scaladsl.server.{Directives, Route}
import com.knoldus.implement.book.BookImplementation
import com.knoldus.models.Book
import com.knoldus.validator.BookValidator
import play.api.libs.json.Json

import scala.util.{Failure, Success}

object BookRequest extends App {
  private val host = "localhost"
  private val port = 8084
  implicit val system = ActorSystem("HTTP_SERVER")

  import system.dispatcher

  val bookImplementation = new BookImplementation(new BookValidator)

  val listOfBooks = List(
    Json.parse {
      """
        |{
        | "id" : 2001,
        | "title" : "Scala Basics",
        | "price" : 200,
        | "isbn" : 213221,
        | "authorId": 101
        |}
        |""".stripMargin
    }.as[Book],
    Json.parse {
      """
        |{
        | "id" : 2002,
        | "title" : "Scala Fundamental",
        | "price" : 400,
        | "isbn" : 21321121,
        | "authorId": 102
        |}
        |""".stripMargin
    }.as[Book]
  )
  val route: Route = {
    Directives.get {
      pathPrefix("api-book") {
        Directives.concat(
          path("create-book") {
            complete(StatusCodes.OK, s"${listOfBooks.map(book => bookImplementation.create(book))}")
          },
          path("view-books") {
            complete(StatusCodes.OK, s"${bookImplementation.getAll()}")
          },
          path("view-book") {
            complete(StatusCodes.OK, s"${bookImplementation.get(2001)}")
          },
          path("delete-book") {
            complete(StatusCodes.OK, s"${bookImplementation.delete(2002)}")
          },
          path("update-book") {
            complete(StatusCodes.OK, s"${bookImplementation.put(Book(2001, "scala advance", 600, 32213221, 102))}")
          }
        )
      }
    }
  }

  private val bindingFuture = Http().newServerAt(host, port).bindFlow(route)
  bindingFuture.onComplete {
    case Success(_) =>
      println(s"Server is listening on http://$host:$port/api-book")
    case Failure(exception) =>
      println(s"Failure :$exception")
      system.terminate()
  }
}
