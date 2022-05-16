package io.jdevelop.restcontrollers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
// import org.springframework.web.bind.annotation.RestController;

import io.jdevelop.DTO.Book;
import io.jdevelop.services.BookService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class BookController {
    
    @Autowired
    private BookService bookService;

    @Value("${external.url.bookCover}")
    private String COVER_SEARCH_URL;

    @GetMapping(value = "/books/{bookId}")
    public String getBook(@PathVariable String bookId, Model model) {
        Optional<Book> bookIdOpt = bookService.findById(bookId);

        if (bookIdOpt.isPresent()) {
            Book book = bookIdOpt.get();
            model.addAttribute("book", book);
            
            constructImageSearchUrl(book, model);

            log.info("{}", book);
            return "book";
        }

        return "book-not-found";
    }

    private void constructImageSearchUrl(Book book, Model model) {
        String coverImgUrl = "/images/img_not_found.webp";
        if(book.getCoverIds() != null && book.getCoverIds().size() > 0) {
            // construct 
            coverImgUrl = COVER_SEARCH_URL + book.getCoverIds().get(0) + "-L.jpg";
        } 
        model.addAttribute("coverImgUrl", coverImgUrl);
    }
}
