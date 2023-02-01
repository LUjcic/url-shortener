package hr.lujcic.urlshortener.controllers;

import hr.lujcic.urlshortener.dto.AccountRequest;
import hr.lujcic.urlshortener.dto.AccountResponse;
import hr.lujcic.urlshortener.exceptions.AccountExistsException;
import hr.lujcic.urlshortener.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller that handles new account creation
 */
@RestController
@RequestMapping("/account")
public class AccountController {

    private final AccountService accountService;

    @Autowired
    public AccountController(AccountService accountService) {
        this.accountService = accountService;

    }

    /**
     * Handles POST request to /account with acount id in request body
     * @param account Json containing AccountId
     * @return ok response with info about account creation in the body
     * @throws AccountExistsException if account with same id already exists
     */
    @PostMapping
    public ResponseEntity<AccountResponse> createAccount(@RequestBody @Validated AccountRequest account) throws AccountExistsException {
        if (account.getAccountId() == null) return ResponseEntity.badRequest().body(new AccountResponse(false, "Missing account ID in request JSON!", null));
        String password = accountService.save(account.getAccountId());
        return ResponseEntity.ok(new AccountResponse(true, String.format("Account with id %s opened", account.getAccountId()), password));
    }
}
