package io.jdevelop.restcontrollers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.cassandra.core.query.CassandraPageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import io.jdevelop.beans.UserBooks;
import io.jdevelop.repository.UserBooksRepository;

@Controller
public class DashBoardController {

    @Autowired
    private UserBooksRepository userActivitiesRepository;

    @GetMapping(value = "/")
    public String home(@AuthenticationPrincipal OAuth2User principal, Model model) {
        if (principal == null) {
            return "index";
        }

        String userId = principal.getAttribute("login");
        Slice<UserBooks> bookSlice = userActivitiesRepository.findAllById(userId, CassandraPageRequest.of(0, 100));
        List<UserBooks> booksByUser = bookSlice.getContent();

        model.addAttribute("booksByUser", booksByUser);
        return "home";
    }
}
