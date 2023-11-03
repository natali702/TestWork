package com.epam.testProject.util;

import com.epam.testProject.model.Book;

public interface DocumentParser {

  /**
   * Parse all pages to book
   *
   * @param bookUrl String source path
   * @return book
   */
  Book parseToBook(String bookUrl);

}
