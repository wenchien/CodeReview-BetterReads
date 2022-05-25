package io.jdevelop.restcontrollers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
// import org.springframework.web.bind.annotation.RestController;

import io.jdevelop.beans.Book;
import io.jdevelop.beans.UserActivities;
import io.jdevelop.keys.UserActivitiesPrimaryKey;
import io.jdevelop.repository.UserActivitiesRepository;
import io.jdevelop.services.BookService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class BookController {

    @Autowired
    private BookService bookService;

    @Autowired
    private UserActivitiesRepository userActivitiesRepository;

    // Can probably be moved to a property class?
    @Value("${external.url.bookCover}")
    private String COVER_SEARCH_URL;

    @GetMapping(value = "/books/{bookId}")
    public String getBook(@PathVariable String bookId, Model model, @AuthenticationPrincipal OAuth2User principal) {
        Optional<Book> bookIdOpt = bookService.findById(bookId);

        if (bookIdOpt.isPresent()) {
            Book book = bookIdOpt.get();
            model.addAttribute("book", book);

            constructImageSearchUrl(book, model);

            log.info("{}", book);


            if (principal != null && principal.getAttribute("login") != null) {
                String userId = principal.getAttribute("login");
                model.addAttribute("loginId", userId);
                UserActivitiesPrimaryKey key = new UserActivitiesPrimaryKey();
                key.setBookId(book.getId());
                key.setUserId(userId);
                Optional<UserActivities> userActivitiesOpt = userActivitiesRepository.findById(key);
                if (userActivitiesOpt.isPresent()) {
                    model.addAttribute("userActivities", userActivitiesOpt.get());
                } else {
                    model.addAttribute("userActivities", new UserActivities());
                }
            }

            return "book";
        }

        return "book-not-found";
    }

    // Move this to utility since bookController and searchController both uses it
    private void constructImageSearchUrl(Book book, Model model) {
        String coverImgUrl = "/images/img_not_found.webp";
        if(book.getCoverIds() != null && book.getCoverIds().size() > 0) {
            // construct
            coverImgUrl = COVER_SEARCH_URL + book.getCoverIds().get(0) + "-L.jpg";
        }
        model.addAttribute("coverImgUrl", coverImgUrl);
    }
}
