package com.epam.testProject.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

import com.epam.testProject.model.Book;
import com.epam.testProject.model.Section;
import com.epam.testProject.model.Type;
import com.epam.testProject.model.dto.BookDto;
import com.epam.testProject.repository.BookRepository;
import com.epam.testProject.repository.SectionRepository;
import com.epam.testProject.service.impl.BookServiceImpl;
import com.epam.testProject.util.impl.DocumentParserImpl;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
public class BookServiceImplTest {

  BookServiceImpl bookService;
  @MockBean
  private SectionRepository repository;
  @MockBean
  private BookRepository bookRepository;
  @MockBean
  private DocumentParserImpl parser;
  @MockBean
  private ModelMapper modelMapper;
  private List<Section> list;
  private Book book;
  private BookDto bookDto;

  @BeforeEach
  public void setUp() {
    bookService = Mockito.spy(
        new BookServiceImpl(parser, repository, bookRepository, modelMapper));
    list = new ArrayList<>();
    list.add(new Section(Type.SECTION, "The European Languages"));
    list.add(new Section(Type.SECTION, "The European Languages2"));
    book = new Book("5b9cfc7c", list);
    bookDto = new BookDto("5b9cfc7c", list);
  }

  @Test
  public void should_return_all_sections() {
    given(repository.findAll()).willReturn(list);

    List<Section> sections = bookService.getAllSections();

    assertEquals(list, sections);
    verify(repository).findAll();
    verifyNoMoreInteractions(repository);
  }

  @Test
  public void should_return_book_sections() {
    String bookId = "5b9cfc7c";
    given(bookRepository.findById(anyString())).willReturn(book);

    List<Section> sections = bookService.getBookSelections(bookId);

    assertEquals(list, sections);
    verify(bookRepository).findById(anyString());
    verifyNoMoreInteractions(bookRepository);
  }

  @Test
  public void should_create_book() {
    String path = "resources/endpoint/book.xml";
    given(parser.parseToBook(path)).willReturn(book);
    given(bookRepository.save(any())).willReturn(book);
    given(modelMapper.map(book, BookDto.class)).willReturn(bookDto);

    BookDto createdBookDto = bookService.createBook(path);

    assertEquals(bookDto, createdBookDto);
    verify(bookRepository).save(any());
    verifyNoMoreInteractions(bookRepository);
  }
}
