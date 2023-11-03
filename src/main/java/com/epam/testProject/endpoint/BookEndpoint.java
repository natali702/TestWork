package com.epam.testProject.endpoint;


import com.epam.testProject.model.Section;
import com.epam.testProject.model.dto.BookDto;
import com.epam.testProject.service.BookService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/book")
public class BookEndpoint {

  private final BookService bookService;

  @GetMapping("/")
  @ResponseStatus(HttpStatus.OK)
  public List<Section> getAllBooksSelections() {
    return bookService.getAllSections();
  }

  @GetMapping(path = "/", params = "id")
  @ResponseStatus(HttpStatus.OK)
  public List<Section> getBookSelections(@RequestParam String id) {
    return bookService.getBookSelections(id);
  }

  @PostMapping(path = "/", params = "bookUrl")
  @ResponseStatus(HttpStatus.CREATED)
  public BookDto createBook(@RequestParam String bookUrl) {
    return bookService.createBook(bookUrl);
  }
}

