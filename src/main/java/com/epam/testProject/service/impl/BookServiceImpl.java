package com.epam.testProject.service.impl;


import static com.epam.testProject.exception.errorcode.CommonErrorCode.BOOK_NOT_EXIST;

import com.epam.testProject.exception.ServiceException;
import com.epam.testProject.model.Book;
import com.epam.testProject.model.Section;
import com.epam.testProject.model.dto.BookDto;
import com.epam.testProject.repository.BookRepository;
import com.epam.testProject.repository.SectionRepository;
import com.epam.testProject.service.BookService;
import com.epam.testProject.util.impl.DocumentParserImpl;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

  private final DocumentParserImpl parser;
  private final SectionRepository repository;
  private final BookRepository bookRepository;
  private final ModelMapper modelMapper;

  @Override
  public List<Section> getAllSections() {
    return repository.findAll();
  }

  @Override
  public List<Section> getBookSelections(String id) {
    Book book = bookRepository.findById(id);
    if (Objects.isNull(book)) {
      throw new ServiceException(BOOK_NOT_EXIST);
    }
    return book.getSections();
  }


  @Override
  public BookDto createBook(String bookUrl) {
    Book book = parser.parseToBook(bookUrl);
    List<Section> list = book.getSections().stream().map(repository::save).collect(Collectors.toList());
    return modelMapper.map(bookRepository.save(book), BookDto.class);
  }
}
