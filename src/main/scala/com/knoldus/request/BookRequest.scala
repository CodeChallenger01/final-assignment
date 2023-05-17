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

object BookLet extends App {
  private val host = "localhost"
  private val port = 8083
  implicit val system = ActorSystem("HTTP_SERVER")

  import system.dispatcher

  val bookImplementation = new BookImplementation(new BookValidator)

  val listOfBooks = List(
    Json.parse {
      """
        |{
        | "id" : 2001,
        | "title" : "Scala Fundamentals",
        | "price" : 200,
        | "isbn" : 21341243,
        | "authorId" : 101
        |}
        |""".stripMargin
    }.as[Book],
    Json.parse {
      """
        |{
        | "id" : 2002,
        | "title" : "Scala Intermediate",
        | "price" : 400,
        | "isbn" : 21300243,
        | "authorId" : 101
        |}
        |""".stripMargin
    }.as[Book]
  )

  val route: Route = {
    Directives.get {
      pathPrefix("api-books") {
        Directives.concat(
          path("create") {
            complete(StatusCodes.OK, s"${listOfBooks.flatMap(book => bookImplementation.create(book)).flatten}")
          },
          path("books") {
            complete(StatusCodes.OK, s"${bookImplementation.getAll()}")
          },
          path("book") {
            complete(StatusCodes.OK, s"${bookImplementation.get(2002)}")
          },
          path("delete") {
            complete(StatusCodes.OK, s"${bookImplementation.delete(2001)}")
          },
          path("put") {
            complete(StatusCodes.OK, s"${bookImplementation.put(Book(2002, "Scala Advance", 400, 212121, 102))}")
          }
        )
      }
    }
  }

  private val bindingFuture = Http().newServerAt(host, port).bindFlow(route)
  bindingFuture.onComplete {
    case Success(_) =>
      println(s"Server is listening on http://$host:$port/api-books")
    case Failure(exception) =>
      println(s"Failure :$exception")
      system.terminate()
  }
}
