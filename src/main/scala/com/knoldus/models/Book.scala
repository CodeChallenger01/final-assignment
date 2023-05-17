package com.knoldus.models

import play.api.libs.json.Json

case class Book(id: Int,
                title: String,
                price: Int,
                isbn: BigInt,
                authorId: Int)

object Book {
  implicit val parser = Json.format[Book]
}
