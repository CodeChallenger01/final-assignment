package com.knoldus.request

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.Directives.{complete, path, pathPrefix}
import akka.http.scaladsl.server.{Directives, Route}
import com.knoldus.implement.author.AuthorImplementation
import com.knoldus.models.Author
import com.knoldus.validator.AuthorValidator
import play.api.libs.json.Json

import scala.util.{Failure, Success}

object AuthorRequest extends App {
  private val host = "localhost"
  private val port = 8082
  implicit val system = ActorSystem("HTTP_SERVER")

  import system.dispatcher

  val authorImplementation = new AuthorImplementation(new AuthorValidator)

  val listOfAuthors = List(
    Json.parse {
      """
        |{
        | "id" : 101,
        | "firstName" : "Manish",
        | "lastName" : "Mishra",
        | "gender" : "Male",
        | "emailId" : "mm0255275@gmail.com"
        |}
        |""".stripMargin
    }.as[Author],
    Json.parse {
      """
        |{
        | "id" : 102,
        | "firstName" : "Ajit",
        | "lastName" : "Kumar",
        | "gender" : "Male",
        | "emailId" : "ajit@knoldus.com"
        |}
        |""".stripMargin
    }.as[Author]
  )
  val route: Route = {
    Directives.get {
      pathPrefix("api-author") {
        Directives.concat(
          path("create-author") {
            complete(StatusCodes.OK, s"${listOfAuthors.map(author => authorImplementation.create(author))}")
          },
          path("view-authors") {
            complete(StatusCodes.OK, s"${authorImplementation.getAll()}")
          },
          path("view-author") {
            complete(StatusCodes.OK, s"${authorImplementation.get(101)}")
          },
          path("delete-author") {
            complete(StatusCodes.OK, s"${authorImplementation.delete(102)}")
          },
          path("update-author") {
            complete(StatusCodes.OK, s"${authorImplementation.put(Author(103, "Ravi", "Kumar", "Male", "ravi@gmail.com"))}")
          }
        )
      }
    }
  }

  private val bindingFuture = Http().newServerAt(host, port).bindFlow(route)
  bindingFuture.onComplete {
    case Success(_) =>
      println(s"Server is listening on http://$host:$port/api-author")
    case Failure(exception) =>
      println(s"Failure :$exception")
      system.terminate()
  }
}
