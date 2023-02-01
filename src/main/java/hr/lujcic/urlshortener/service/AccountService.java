package hr.lujcic.urlshortener.service;

import hr.lujcic.urlshortener.exceptions.AccountExistsException;
import hr.lujcic.urlshortener.exceptions.AccountNotFoundException;
import hr.lujcic.urlshortener.model.Account;
import hr.lujcic.urlshortener.repository.AccountRepository;
import hr.lujcic.urlshortener.utils.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Service used in handling logic when accessing account repo
 */
@Service
public class AccountService {

    private final AccountRepository accountRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public AccountService(AccountRepository accountRepository, BCryptPasswordEncoder passwordEncoder) {
        this.accountRepository = accountRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Saves new account with given account name and generates password for it
     * @param accountName to save in the database
     * @return generated password for newly created account
     * @throws AccountExistsException if account name is already taken
     */
    public String save(String accountName) throws AccountExistsException {
        if (accountRepository.existsByUsername(accountName)) throw new AccountExistsException("Account with given id already exists!");
        String password = RandomUtils.generateString(48, 122, 8);
        accountRepository.save(new Account(
            accountName,
            passwordEncoder.encode(password)
        ));
        return password;
    }

    /**
     * Fetches account with given username/account id
     * @param username
     * @return account with given username
     */
    public Account getByUsername(String username) throws AccountNotFoundException {
        Optional<Account> account = this.accountRepository.findOneByUsername(username);
        if (account.isPresent()) return account.get();
        else throw new AccountNotFoundException(String.format("Failed to find account with username: %s", username));
    }
}
