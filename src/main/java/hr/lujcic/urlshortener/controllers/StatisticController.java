package hr.lujcic.urlshortener.controllers;

import hr.lujcic.urlshortener.exceptions.AccountNotFoundException;
import hr.lujcic.urlshortener.exceptions.UnauthorizedException;
import hr.lujcic.urlshortener.model.Account;
import hr.lujcic.urlshortener.model.Redirect;
import hr.lujcic.urlshortener.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Handles requests to /statistic
 */
@RestController
@RequestMapping("/statistic")
public class StatisticController {

    private final AccountService accountService;

    @Autowired
    public StatisticController(AccountService accountService) {
        this.accountService = accountService;
    }

    /**
     * Handles GET requests to statistics with account id in path
     * @param authentication info from JWT token
     * @param accountId of account whos statistics should be returned
     * @return ok with map of urls and number of visits in the response body
     * @throws UnauthorizedException if user requesting the statistic is not the same as the given accountId
     */
    @GetMapping("/{accountId}")
    public ResponseEntity<Map<String, Integer>> getStatistics(Authentication authentication, @PathVariable String accountId) throws UnauthorizedException, AccountNotFoundException {
        String username = ((User) authentication.getPrincipal()).getUsername();
        if (!username.equals(accountId)) throw new UnauthorizedException("You are not authorized to get other users statistics!");
        Account account = accountService.getByUsername(accountId);
        List<Redirect> redirectList = account.getRedirects();
        Map<String, Integer> statistics = redirectList.stream().collect(Collectors.toMap(Redirect::getOriginalURL, Redirect::getVisits));
        return ResponseEntity.ok().body(statistics);
    }
}
