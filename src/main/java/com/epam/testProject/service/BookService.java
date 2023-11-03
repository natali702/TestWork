package com.epam.testProject.service;


import com.epam.testProject.model.Section;
import com.epam.testProject.model.dto.BookDto;
import java.util.List;

public interface BookService {

  /**
   * Returns selections for all books
   *
   * @return list of selections
   */
  List<Section> getAllSections();

  /**
   * Returns selections of book by book id
   *
   * @return list of selections
   */
  List<Section> getBookSelections(String id);

  /**
   * Create book using page url
   *
   * @param bookUrl String page url
   * @return book
   */
  BookDto createBook(String bookUrl);

}
