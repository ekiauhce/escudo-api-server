package escudo.api.services;

import escudo.api.dtos.NewProductDto;
import escudo.api.dtos.NewPurchaseDto;
import escudo.api.dtos.ProductListItemDto;
import escudo.api.dtos.ProductPatchDto;
import escudo.api.entities.Buyer;
import escudo.api.entities.Product;
import escudo.api.entities.Purchase;
import escudo.api.exceptions.DuplicateProductException;
import escudo.api.exceptions.ProductNotFoundException;
import escudo.api.exceptions.PurchaseNotFoundException;
import escudo.api.repositories.BuyerRepository;
import escudo.api.repositories.ProductRepository;
import escudo.api.repositories.PurchaseRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;


@Service
@Transactional(rollbackFor = {
    DuplicateProductException.class,
    ProductNotFoundException.class,
    PurchaseNotFoundException.class,
    IllegalAccessException.class,
})
public class EscudoService {

    private final BuyerRepository buyerRepo;
    private final ProductRepository productRepo;
    private final PurchaseRepository purchaseRepo;

    public EscudoService(BuyerRepository buyerRepo, ProductRepository productRepo, PurchaseRepository purchaseRepo) {
        this.buyerRepo = buyerRepo;
        this.productRepo = productRepo;
        this.purchaseRepo = purchaseRepo;
    }

    public List<ProductListItemDto> getProducts(String username) {
        return productRepo.findProductsByBuyerUsername(username).stream()
            .map(ProductListItemDto::new)
            .collect(Collectors.toList());
    }


    public NewProductDto addNewProduct(NewProductDto productDto, String username) throws DuplicateProductException {
        Buyer buyer = buyerRepo.findByUsername(username);
        Product product = new Product(productDto);
        product.setBuyer(buyer);
        
        try {
            product = productRepo.save(product);
        } catch (DataIntegrityViolationException e) {
            throw new DuplicateProductException("Product with this name already exists!", e);
        }

        return new NewProductDto(product);
    }

    public NewPurchaseDto addNewPurchase(String username, Long productId, NewPurchaseDto purchaseDto)
        throws ProductNotFoundException, IllegalAccessException {

        Product product = productRepo.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException("No product with this id!"));

        if (!username.equals(product.getBuyer().getUsername())) {
            throw new IllegalAccessException("You don't own this product!");
        }

        Purchase purchase = new Purchase(purchaseDto);
        purchase.setProduct(product);
        purchase = purchaseRepo.save(purchase);

        return new NewPurchaseDto(purchase);
    }


    public void deletePurchase(String username, Long productId, Long purchaseId)
        throws ProductNotFoundException, IllegalAccessException, PurchaseNotFoundException {

        Product product = productRepo.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException("No product with this id!"));

        if (!username.equals(product.getBuyer().getUsername())) {
            throw new IllegalAccessException("You don't own this product!");
        }

        try {
            purchaseRepo.deleteById(purchaseId);
        } catch (EmptyResultDataAccessException e) {
            throw new PurchaseNotFoundException("No purchase with this id!", e);
        }
    }

    public void updateProduct(String username, Long productId, ProductPatchDto productDto)
        throws ProductNotFoundException, IllegalAccessException, DuplicateProductException {

        Product product = productRepo.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException("No product with this id!"));

        if (!username.equals(product.getBuyer().getUsername())) {
            throw new IllegalAccessException("You don't own this product!");
        }
        try {
            product.setName(productDto.getName());
        } catch (DataIntegrityViolationException e) {
            throw new DuplicateProductException("Product with this name already exists!", e);
        }
    }

    public void deleteProduct(String username, Long productId) 
        throws ProductNotFoundException, IllegalAccessException {
        
        Product product = productRepo.findById(productId)
            .orElseThrow(() -> new ProductNotFoundException("No product with this id"));

        if (!username.equals(product.getBuyer().getUsername())) {
            throw new IllegalAccessException("You don't own this product");
        }

        productRepo.delete(product);
    }
}
