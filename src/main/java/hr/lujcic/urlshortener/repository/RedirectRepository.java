package hr.lujcic.urlshortener.repository;

import hr.lujcic.urlshortener.model.Redirect;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * Repository used in accessing the redirect table
 */
public interface RedirectRepository extends JpaRepository<Redirect, Integer> {

    Optional<Redirect> findOneByOriginalURL(String originalUrl);

    Optional<Redirect> findOneByShortURL(String shortUrl);

    List<Redirect> findAllByAccountId(int accountId);
}
