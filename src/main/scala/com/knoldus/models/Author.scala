package com.knoldus.models

import play.api.libs.json.Json

case class Author(id: Int,
                  firstName: String,
                  lastName: String,
                  gender: String,
                  emailId: String)

object Author {
  implicit val authorParser = Json.format[Author]
}