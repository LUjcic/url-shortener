package hr.lujcic.urlshortener.repository;

import hr.lujcic.urlshortener.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Repository used to access account table
 */
public interface AccountRepository extends JpaRepository<Account, Integer> {

    Optional<Account> findOneByUsername(String username);

    boolean existsByUsername(String username);
}
