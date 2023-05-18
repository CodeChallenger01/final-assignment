package com.knoldus.implement.author

import com.knoldus.models.{Author, Error}

trait AuthorRepo {
  def create(author: Author): Either[Error, List[Author]]

  def get(id: Int): Option[Author]

  def getAll(): Either[Error, List[Author]]

  def put(author: Author): Either[Error, Unit]

  def delete(id: Int): Option[List[Author]]
}
