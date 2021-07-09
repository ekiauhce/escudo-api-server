package escudo.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
public class PurchaseController {

    private final EscudoService escudoService;

    public PurchaseController(EscudoService escudoService) {
        this.escudoService = escudoService;
    }

    @PostMapping("products/{productId}/purchases")
    @ResponseStatus(HttpStatus.CREATED)
    public Purchase postPurchase(@AuthenticationPrincipal UserDetails details,
                                 @PathVariable Long productId, @RequestBody Purchase purchase) {
        try {
            return escudoService.addNewPurchase(details.getUsername(), productId, purchase);
        } catch (ProductNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        } catch (IllegalAccessException e) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, e.getMessage(), e);
        }
    }
    @DeleteMapping("products/{productId}/purchases/{purchaseId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePurchase(@AuthenticationPrincipal UserDetails details,
                               @PathVariable Long productId, @PathVariable Long purchaseId) {
        try {
            escudoService.deletePurchase(details.getUsername(), productId, purchaseId);
        } catch (ProductNotFoundException | PurchaseNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        } catch (IllegalAccessException e) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, e.getMessage(), e);
        }
    }
}
