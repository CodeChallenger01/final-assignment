package com.knoldus.implement.book

import com.knoldus.models.Book
import com.knoldus.validator.BookValidator

import scala.collection.mutable.ListBuffer

class BookImplementation(titleValidate: BookValidator) extends BookRepo {
  private val listOfBook: ListBuffer[Book] = ListBuffer[Book]().empty

  override def create(book: Book): Option[List[Book]] = {
    if (titleValidate.isBookValidated(book)) {
      val check = listOfBook.find(list => list.id == book.id)
      check match {
        case Some(_) => None
        case None => Some((listOfBook += book).toList)
      }
    }
    else None

  }

  override def get(id: Int): Option[Book] = {
    listOfBook.find(list => list.id == id)
  }

  def getAll(): Option[List[Book]] = {
    Some(listOfBook.toList)
  }

  override def put(book: Book): Either[Error, Unit] = {
    val check = listOfBook.find(list => list.id == book.id)
    check match {
      case Some(_) =>
        listOfBook.map { list =>
          if (list.id == book.id) book
          else list
        }
        Right(println("Updated"))
      case None => Left(throw new Exception("Error"))
    }
  }

  override def delete(id: Int): Option[List[Book]] = {
    val check = listOfBook.find(list => list.id == id)
    check match {
      case Some(value) => Some((listOfBook -= value).toList)
      case None => None
    }
  }
}

//object MainApplication extends App {
//  val book = Book(1212, "Scala Fundamentals", 230, 21431232, 101)
//  val newBook = book.copy(1212, "Java", 5000)
//  val createBook = new BookImplementation(new BookValidator)
//
//  val result = createBook.create(newBook)
//  println(result)
//
//}