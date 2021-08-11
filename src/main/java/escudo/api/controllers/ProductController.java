package escudo.api.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import escudo.api.dtos.NewProductDto;
import escudo.api.exceptions.DuplicateProductException;
import escudo.api.services.EscudoService;


@RestController
@RequestMapping("products")
public class ProductController {

    private final EscudoService escudoService;

    public ProductController(EscudoService escudoService) {
        this.escudoService = escudoService;
    }

    @GetMapping
    public List<NewProductDto> getProducts(@AuthenticationPrincipal UserDetails details) {
        return escudoService.getProducts(details.getUsername());
    }
    
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public NewProductDto postProduct(@AuthenticationPrincipal UserDetails details, 
                                  @RequestBody NewProductDto productDto) {
        try {
            return escudoService.addNewProduct(productDto, details.getUsername());
        } catch (DuplicateProductException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage(), e);
        }
    }    
}
