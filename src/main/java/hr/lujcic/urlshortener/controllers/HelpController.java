package hr.lujcic.urlshortener.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Handles request to /help
 */
@RestController
@RequestMapping("/help")
public class HelpController {

    /**
     * Handles get requests to /help
     * @return ok with string in body describing how to use the api
     */
    @GetMapping
    public ResponseEntity<String> getHelp() {
        return ResponseEntity.ok().body("Post username to /account to make an account and you will get a password in response,\n" +
            "Post username and password to /login to get a signed JWT token, \n" +
            "Post URL to /register with authorization header to get a short link, \n" +
            "Get to /statistic/{your_account_id} to get visits counted for each URL registered to your account, \n" +
            "Get to /short/{code_to_url} to get redirected to the original url");
    }
}
