package escudo.api.security;

import escudo.api.Buyer;
import escudo.api.BuyerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("register")
public class RegisterController {

    private final BuyerRepository repo;
    private final PasswordEncoder encoder;

    @Autowired
    public RegisterController(BuyerRepository repo, PasswordEncoder encoder) {
        this.repo = repo;
        this.encoder = encoder;
    }

    @PostMapping
    public ResponseEntity<Void> register(@RequestBody Credentials credentials) {
        try {
            Buyer buyer = new Buyer(credentials.getUsername(), encoder.encode(credentials.getPassword()));
            repo.save(buyer);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (DataIntegrityViolationException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "User with this username already exists!", e);
        }
    }
}
