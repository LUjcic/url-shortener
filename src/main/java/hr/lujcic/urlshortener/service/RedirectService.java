package hr.lujcic.urlshortener.service;

import hr.lujcic.urlshortener.dto.RegisterRequest;
import hr.lujcic.urlshortener.exceptions.AccountNotFoundException;
import hr.lujcic.urlshortener.model.Redirect;
import hr.lujcic.urlshortener.repository.RedirectRepository;
import hr.lujcic.urlshortener.exceptions.InvalidStatusCodeException;
import hr.lujcic.urlshortener.utils.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Service used to handle logic and access to redirect repository
 */
@Service
public class RedirectService {

    private final RedirectRepository redirectRepository;
    private final AccountService accountService;

    @Autowired
    public RedirectService(RedirectRepository redirectRepository, AccountService accountService) {
        this.accountService = accountService;
        this.redirectRepository = redirectRepository;
    }

    /**
     * Saves a new redirect from given url, status code and username and generates a random short url
     * @param redirect contains url and status code
     * @param username username/account id to attach the redirect to
     * @return created redirect
     * @throws InvalidStatusCodeException if given status code is not 302 or 301
     * @throws AccountNotFoundException if account with given username could not be found
     */
    public Redirect saveNew(RegisterRequest redirect, String username) throws InvalidStatusCodeException, AccountNotFoundException {
        if (redirect.getRedirectType() != 302 && redirect.getRedirectType() != 301) throw new InvalidStatusCodeException("Invalid redirect status code! Status code can only be 302 or 301!");
        return redirectRepository.save(
            new Redirect(
                redirect.getUrl(),
                RandomUtils.generateString(58, 122, 6),
                redirect.getRedirectType(),
                accountService.getByUsername(username)
            )
        );
    }

    /**
     * Updates given a redirect in the repository with the one given
     * @param redirect that should be saved
     */
    public void update(Redirect redirect) {
        redirectRepository.save(redirect);
    }

    /**
     * Gets a redirect object from the given short url code
     * @param shortUrlCode to be searched in the repository
     * @return redirect matching the code
     */
    public Optional<Redirect> getRedirectByShortCode(String shortUrlCode) {
        return redirectRepository.findOneByShortURL(shortUrlCode);
    }
}
