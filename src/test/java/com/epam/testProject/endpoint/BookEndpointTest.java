package com.epam.testProject.endpoint;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.epam.testProject.model.Section;
import com.epam.testProject.model.Type;
import com.epam.testProject.model.dto.BookDto;
import com.epam.testProject.service.BookService;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@ExtendWith(SpringExtension.class)
@WebMvcTest(BookEndpoint.class)
@ContextConfiguration(classes = BookEndpoint.class)
public class BookEndpointTest {

  @MockBean
  private BookService bookService;
  @MockBean
  private ModelMapper modelMapper;
  private MockMvc mockMvc;
  BookDto book;
  private List<Section> list;
  @Autowired
  private WebApplicationContext webApplicationContext;

  @BeforeEach
  public void setUp() {
    mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
        .build();
    list = new ArrayList<>();
    list.add(new Section(Type.SECTION, "The European Languages"));
    list.add(new Section(Type.SECTION, "The European Languages2"));
    book = new BookDto("5b9cfc7c", list);
  }

  @Test
  public void should_get_all_books_sections() throws Exception {
    // Given
    given(bookService.getAllSections()).willReturn(list);

    // When
    mockMvc.perform(get("/book/"))

        // Then
        .andExpect(status().isOk());

    verify(bookService).getAllSections();
  }

  @Test
  public void should_create_book() throws Exception {
    // Given
    BookDto bookDto = new BookDto("5b9cfc7c", list);
    String path =
        "http://ec2-52-91-150-126.compute-1.amazonaws.com/content/books/far-far-away/section-1.xml";
    //   String path = "resources/endpoint/book.xml";
    given(bookService.createBook(path)).willReturn(book);
    given(modelMapper.map(book, BookDto.class)).willReturn(bookDto);

    // When
    mockMvc.perform(
        post("/book/?bookUrl=" + path))

        // Then
        .andExpect(status().isCreated());

    verify(bookService).createBook(path);
  }

  @Test
  public void should_get_book_sections_by_id() throws Exception {
    // Given
    String bookId = "5b9cfc7c";
    String path = "resources/endpoint/book.xml";
    given(bookService.getBookSelections(bookId)).willReturn(book.getSections());

    // When
    mockMvc.perform(
        get("/book/?id=" + bookId))

        // Then
        .andExpect(status().isOk());

    verify(bookService).getBookSelections(bookId);
  }
}
