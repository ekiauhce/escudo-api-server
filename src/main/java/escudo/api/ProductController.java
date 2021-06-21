package escudo.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("products")
public class ProductController {

    private final ProductRepository productRepo;
    private final BuyerRepository buyerRepo;

    @Autowired
    public ProductController(ProductRepository productRepo, BuyerRepository buyerRepo) {
        this.productRepo = productRepo;
        this.buyerRepo = buyerRepo;
    }

    @GetMapping
    public List<Product> getProducts(@AuthenticationPrincipal UserDetails details) {
        return productRepo.findProductsByBuyerUsername(details.getUsername());
    }

    @PostMapping
    public ResponseEntity<Void> postProduct(@AuthenticationPrincipal UserDetails details,
                                            @RequestBody Product product) {
        Buyer buyer = buyerRepo.findByUsername(details.getUsername());
        product.setBuyer(buyer);
        try {
            productRepo.save(product);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (DataIntegrityViolationException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Product with this name already exists!", e);
        }
    }
}
