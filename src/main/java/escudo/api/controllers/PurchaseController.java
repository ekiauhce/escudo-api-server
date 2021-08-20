package escudo.api.controllers;

import escudo.api.services.EscudoService;
import escudo.api.exceptions.ProductNotFoundException;
import escudo.api.dtos.PurchaseCreationReportDto;
import escudo.api.dtos.PurchaseDeletionReportDto;
import escudo.api.dtos.PurchaseDto;
import escudo.api.exceptions.PurchaseNotFoundException;

import java.util.List;

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

    @GetMapping("products/{productName}/purchases")
    @ResponseStatus(HttpStatus.OK)
    public List<PurchaseDto> getPurchases(@AuthenticationPrincipal UserDetails details, 
        @PathVariable String productName) {

        try {
            return escudoService.getPurchases(details.getUsername(), productName);
        } catch (ProductNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
        
    }

    @PostMapping("products/{productName}/purchases")
    @ResponseStatus(HttpStatus.CREATED)
    public PurchaseCreationReportDto postPurchase(@AuthenticationPrincipal UserDetails details,
        @PathVariable String productName, @RequestBody PurchaseDto purchaseDto) {
        
        try {
            return escudoService.addNewPurchase(details.getUsername(), productName, purchaseDto);
        } catch (ProductNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
    }
    
    @DeleteMapping("products/{productName}/purchases/{purchaseId}")
    @ResponseStatus(HttpStatus.OK)
    public PurchaseDeletionReportDto deletePurchase(@AuthenticationPrincipal UserDetails details,
        @PathVariable String productName, @PathVariable Long purchaseId) {

        try {
            return escudoService.deletePurchase(details.getUsername(), productName, purchaseId);
        } catch (ProductNotFoundException | PurchaseNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
    }
}
