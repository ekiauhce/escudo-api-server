package escudo.api.security;

import escudo.api.Buyer;
import escudo.api.BuyerRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
public class SecurityController {

    private final BuyerRepository repo;
    private final PasswordEncoder encoder;

    public SecurityController(BuyerRepository repo, PasswordEncoder encoder) {
        this.repo = repo;
        this.encoder = encoder;
    }

    @PostMapping("register")
    public ResponseEntity<?> register(@RequestBody Credentials credentials) {
        try {
            Buyer buyer = new Buyer(credentials.getUsername(), encoder.encode(credentials.getPassword()));
            repo.save(buyer);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (DataIntegrityViolationException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "This username already exists!", e);
        }
    }

    @PostMapping("login")
    public ResponseEntity<?> login(@RequestBody Credentials credentials) {
        Buyer buyer = repo.findByUsername(credentials.getUsername());
        if (buyer == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "This username doesn't exist!");
        }
        else if (!encoder.matches(credentials.getPassword(), buyer.getPassword())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Password incorrect");
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
