package escudo.api;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EscudoService {

    private final BuyerRepository buyerRepo;
    private final ProductRepository productRepo;
    private final PurchaseRepository purchaseRepo;

    public EscudoService(BuyerRepository buyerRepo, ProductRepository productRepo, PurchaseRepository purchaseRepo) {
        this.buyerRepo = buyerRepo;
        this.productRepo = productRepo;
        this.purchaseRepo = purchaseRepo;
    }

    public List<Product> getProducts(String username) {
        return productRepo.findProductsByBuyerUsername(username);
    }

    public Product addNewProduct(Product product, String username) throws DuplicateProductException {
        Buyer buyer = buyerRepo.findByUsername(username);
        product.setBuyer(buyer);
        try {
            return productRepo.save(product);
        } catch (DataIntegrityViolationException e) {
            throw new DuplicateProductException("Product with this name already exists!", e);
        }
    }

    public Purchase addNewPurchase(String username, Long productId, Purchase purchase)
            throws ProductNotFoundException, IllegalAccessException {

        Product product = productRepo.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException("No product with this id!"));

        if (!username.equals(product.getBuyer().getUsername())) {
            throw new IllegalAccessException("You don't own this product!");
        }
        purchase.setProduct(product);
        return purchaseRepo.save(purchase);
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
}
