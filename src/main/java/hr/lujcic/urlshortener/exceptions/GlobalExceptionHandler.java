package hr.lujcic.urlshortener.exceptions;

import hr.lujcic.urlshortener.dto.AccountResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * Global exception handler
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handles {@link AccountExistsException}
     * @param exc exception caught
     * @return bad request with {@link AccountResponse} as body
     */
    @ExceptionHandler(AccountExistsException.class)
    public ResponseEntity<AccountResponse> handleAccountExistsException(AccountExistsException exc){
        return ResponseEntity.badRequest().body(new AccountResponse(false, exc.getMessage(), null));
    }

    /**
     * Handles {@link UnauthorizedException}
     * @param exc exception caught
     * @return unauthorized with appropriate message
     */
    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<String> handleUnauthorizedException(UnauthorizedException exc) { return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(exc.getMessage()); }

    /**
     * Handles {@link InvalidStatusCodeException}
     * @param exc exception caught
     * @return bad request with appropriate message
     */
    @ExceptionHandler(InvalidStatusCodeException.class)
    public ResponseEntity<String> handleInvalidStatusCodeException(InvalidStatusCodeException exc) { return ResponseEntity.badRequest().body(exc.getMessage()); }

    /**
     * Handles {@link AccountNotFoundException}
     * @param exc exception caught
     * @return bad request with appropriate message
     */
    @ExceptionHandler(AccountNotFoundException.class)
    public ResponseEntity<String> handleAccountNotFoundException(AccountNotFoundException exc) { return ResponseEntity.badRequest().body(exc.getMessage()); }
}
