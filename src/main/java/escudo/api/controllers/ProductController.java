package escudo.api.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import escudo.api.dtos.ProductDto;
import escudo.api.dtos.ProductPatchDto;
import escudo.api.exceptions.DuplicateProductException;
import escudo.api.exceptions.ProductNotFoundException;
import escudo.api.services.EscudoService;


@RestController
@RequestMapping("products")
public class ProductController {

    private final EscudoService escudoService;

    public ProductController(EscudoService escudoService) {
        this.escudoService = escudoService;
    }

    @GetMapping
    public List<ProductDto> getProducts(@AuthenticationPrincipal UserDetails details) {
        return escudoService.getProducts(details.getUsername());
    }
    
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProductDto postProduct(@AuthenticationPrincipal UserDetails details, 
                                  @RequestBody ProductDto productDto) {
        try {
            return escudoService.addNewProduct(productDto, details.getUsername());
        } catch (DuplicateProductException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage(), e);
        }
    }

    @PatchMapping("/{productName}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void patchProduct(@AuthenticationPrincipal UserDetails details,
                                          @PathVariable String productName,
                                          @RequestBody ProductPatchDto productDto) {
        try {
            escudoService.updateProduct(details.getUsername(), productName, productDto);
        } catch (ProductNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        } catch (DuplicateProductException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage(), e);
        }
    }

    @DeleteMapping("/{productName}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProduct(@AuthenticationPrincipal UserDetails details, @PathVariable String productName) {
        try {
            escudoService.deleteProduct(details.getUsername(), productName);
        } catch (ProductNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
    }
}
