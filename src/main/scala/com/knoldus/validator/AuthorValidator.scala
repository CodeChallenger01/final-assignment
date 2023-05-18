package com.knoldus.validator

import com.knoldus.models.Author

class AuthorValidator {
  def isAuthorValidated(author: Author): Boolean = {
    if ((author.firstName + author.lastName).length > 20) false
    else true
  }
}
