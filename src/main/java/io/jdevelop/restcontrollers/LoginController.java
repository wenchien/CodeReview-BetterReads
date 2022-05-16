package io.jdevelop.restcontrollers;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class LoginController {

    @GetMapping("/getUser")
	public String user(@AuthenticationPrincipal OAuth2User principal) {
		log.info("{}", principal);
		return principal.getAttribute("name");
	}
	
}
