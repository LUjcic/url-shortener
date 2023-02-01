package hr.lujcic.urlshortener.controllers;

import hr.lujcic.urlshortener.dto.RegisterRequest;
import hr.lujcic.urlshortener.dto.RegisterResponse;
import hr.lujcic.urlshortener.exceptions.AccountNotFoundException;
import hr.lujcic.urlshortener.model.Redirect;
import hr.lujcic.urlshortener.exceptions.InvalidStatusCodeException;
import hr.lujcic.urlshortener.service.RedirectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Handles requests to /register
 */
@RestController
@RequestMapping("/register")
public class RegisterController {

    private final RedirectService redirectService;

    @Autowired
    public RegisterController(RedirectService redirectService) {
        this.redirectService = redirectService;
    }

    /**
     * Handles POST requests to /register
     * @param authentication info from JWT token
     * @param redirectDto http body object that contains url to be shortened
     * @return ok with shortened url in response body
     */
    @PostMapping
    public ResponseEntity<RegisterResponse> saveRedirect(Authentication authentication, @RequestBody RegisterRequest redirectDto) throws InvalidStatusCodeException, AccountNotFoundException {
        if (redirectDto.getUrl() == null) return ResponseEntity.badRequest().body(new RegisterResponse(""));
        Redirect redirect = redirectService.saveNew(redirectDto, ((User) authentication.getPrincipal()).getUsername());
        return ResponseEntity.ok(new RegisterResponse("http://localhost:8080/short/" + redirect.getShortURL()));
    }
}
