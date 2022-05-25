package io.jdevelop.restcontrollers;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.servlet.ModelAndView;

import io.jdevelop.beans.UserActivities;
import io.jdevelop.keys.UserActivitiesPrimaryKey;
import io.jdevelop.repository.UserActivitiesRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class UserActivitiesController {

    @Autowired
    private UserActivitiesRepository userActivitiesRepository;

    @PostMapping("/addUserBook")
    public ModelAndView addBookForUser(@RequestBody MultiValueMap<String, String> formData, @AuthenticationPrincipal OAuth2User principal) {
        log.info("{}", formData);

        if (principal == null) {
            return null;
        }

        UserActivities userActivities = new UserActivities();
        // create a mapping here instead
        UserActivitiesPrimaryKey key = new UserActivitiesPrimaryKey();
        key.setUserId(principal.getAttribute("login"));
        String bookId = formData.getFirst("bookId");
        key.setBookId(bookId);
        userActivities.setId(key);

        userActivities.setStartedDate(LocalDate.parse(formData.getFirst("startDate")));
        userActivities.setCompletedDate(LocalDate.parse(formData.getFirst("completedDate")));
        userActivities.setRating(Integer.parseInt(formData.getFirst("rating")));
        userActivities.setCompletionStatus(formData.getFirst("readingStatus"));

        userActivitiesRepository.save(userActivities);

        return new ModelAndView("redirect:/books/" + bookId);
    }
}
