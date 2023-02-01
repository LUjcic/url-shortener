package hr.lujcic.urlshortener.controllers;

import hr.lujcic.urlshortener.model.Redirect;
import hr.lujcic.urlshortener.service.RedirectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.Optional;

/**
 * Handles requests to /short
 */
@RestController
@RequestMapping("/short")
public class ShortUrlController {
    private final RedirectService redirectService;

    @Autowired
    public ShortUrlController(RedirectService redirectService) {
        this.redirectService = redirectService;
    }

    /**
     * Handles GET requests to /short with url code and redirects to original url
     * @param shortUrlCode code used to match the original url
     * @return not found if short code is not found or redirects to original url otherwise
     */
    @GetMapping("/{shortUrlCode}")
    public ResponseEntity<Void> redirect(@PathVariable String shortUrlCode) {
        Optional<Redirect> optionalRedirect = redirectService.getRedirectByShortCode(shortUrlCode);
        if (optionalRedirect.isPresent()) {
            Redirect redirect = optionalRedirect.get();
            redirect.setVisits(redirect.getVisits() + 1);
            redirectService.update(redirect);
            return ResponseEntity.status(redirect.getStatusCode()).location(URI.create(redirect.getOriginalURL())).build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
