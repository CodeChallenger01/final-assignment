package com.knoldus.implement.book

import com.knoldus.models.Book

trait BookRepo {
  def create(book: Book): Option[List[Book]]

  def get(id: Int): Option[Book]

  def getAll(): Option[List[Book]]

  def put(book: Book): Either[Error, Unit]

  def delete(id: Int): Option[List[Book]]

}
