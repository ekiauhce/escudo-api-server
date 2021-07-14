package escudo.api.controllers;

import escudo.api.exceptions.DuplicateProductException;
import escudo.api.services.EscudoService;
import escudo.api.entities.Product;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("products")
public class ProductController {

    private final EscudoService escudoService;

    public ProductController(EscudoService escudoService) {
        this.escudoService = escudoService;
    }

    @GetMapping
    public List<Product> getProducts(@AuthenticationPrincipal UserDetails details) {
        return escudoService.getProducts(details.getUsername());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Product postProduct(@AuthenticationPrincipal UserDetails details,
                               @RequestBody Product product) {
        try {
            return escudoService.addNewProduct(product, details.getUsername());
        } catch (DuplicateProductException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage(), e);
        }
    }
}