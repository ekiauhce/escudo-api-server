package escudo.api.services;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import escudo.api.dtos.ProductDto;
import escudo.api.dtos.ProductPatchDto;
import escudo.api.dtos.PurchaseCreationReportDto;
import escudo.api.dtos.PurchaseDeletionReportDto;
import escudo.api.dtos.PurchaseDto;
import escudo.api.entities.Buyer;
import escudo.api.entities.Product;
import escudo.api.entities.ProductSummary;
import escudo.api.entities.Purchase;
import escudo.api.exceptions.DuplicateProductException;
import escudo.api.exceptions.ProductNotFoundException;
import escudo.api.exceptions.PurchaseNotFoundException;
import escudo.api.repositories.BuyerRepository;
import escudo.api.repositories.ProductRepository;
import escudo.api.repositories.PurchaseRepository;


@Service
@Transactional(rollbackFor = {
    DuplicateProductException.class,
    ProductNotFoundException.class,
    PurchaseNotFoundException.class
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

    public List<ProductDto> getProducts(String username) {
        return productRepo.findProductsByBuyerUsername(username).stream()
            .map(ProductDto::new)
            .collect(Collectors.toList());
    }


    public List<PurchaseDto> getPurchases(String username, String productName)
        throws ProductNotFoundException {
        
        Product product = productRepo.findByBuyerUsernameAndName(username, productName)
                .orElseThrow(() -> new ProductNotFoundException("No product with this name!"));

        return product.getPurchases().stream()
            .map(PurchaseDto::new)
            .collect(Collectors.toList());
    }

    public ProductDto addNewProduct(ProductDto productDto, String username) throws DuplicateProductException {
        Buyer buyer = buyerRepo.findByUsername(username);
        Product product = new Product(productDto);
        product.setBuyer(buyer);
        
        try {
            product = productRepo.save(product);
        } catch (DataIntegrityViolationException e) {
            throw new DuplicateProductException("Product with this name already exists!", e);
        }

        return new ProductDto(product);
    }

    public void updateProduct(String username, String productName, ProductPatchDto productDto)
        throws ProductNotFoundException, DuplicateProductException {

        Product product = productRepo.findByBuyerUsernameAndName(username, productName)
                .orElseThrow(() -> new ProductNotFoundException("No product with this name!"));
        
        try {
            product.setName(productDto.getName());
        } catch (DataIntegrityViolationException e) {
            throw new DuplicateProductException("Product with this name already exists!", e);
        }
    }

    public void deleteProduct(String username, String productName) throws ProductNotFoundException {
        Product product = productRepo.findByBuyerUsernameAndName(username, productName)
            .orElseThrow(() -> new ProductNotFoundException("No product with this name!"));

        productRepo.delete(product);
    }

    public PurchaseCreationReportDto addNewPurchase(String username, String productName, PurchaseDto purchaseDto)
        throws ProductNotFoundException {

        Product product = productRepo.findByBuyerUsernameAndName(username, productName)
                .orElseThrow(() -> new ProductNotFoundException("No product with this name!"));

        Purchase purchase = new Purchase(purchaseDto);
        purchase.setProduct(product);
        purchase = purchaseRepo.save(purchase);

        ProductSummary summary = new ProductSummary(avgCPD(product), avgLifespan(product));
        product.setSummary(summary);

        return new PurchaseCreationReportDto(purchase);
    }


    public PurchaseDeletionReportDto deletePurchase(String username, String productName, Long purchaseId)
        throws ProductNotFoundException, PurchaseNotFoundException {

        Product product = productRepo.findByBuyerUsernameAndName(username, productName)
                .orElseThrow(() -> new ProductNotFoundException("No product with this name!"));

        try {
            purchaseRepo.deleteById(purchaseId);
        } catch (EmptyResultDataAccessException e) {
            throw new PurchaseNotFoundException("No purchase with this id!", e);
        }

        ProductSummary summary = new ProductSummary(avgCPD(product), avgLifespan(product));
        product.setSummary(summary);

        return new PurchaseDeletionReportDto(product);
    }

    /**
     * Calculates average cost per day over all pairs of adjacent purchases
     * @param product
     * @return
     */
    private double avgCPD(Product product) {
        List<Purchase> purchases = product.getPurchases();
        
        if (purchases.size() < 2) {
            return 0.0;
        }

        double[] costs = new double[purchases.size() - 1];
        
        Purchase prev, next;
        for (int i = 0; i < purchases.size() - 1; i++) {
            prev = purchases.get(i);
            next = purchases.get(i + 1);
            costs[i] = prev.getPrice() / lifespan(prev, next);
        }

        return DoubleStream.of(costs).average()
            .orElse(0.0);
    }

    /**
     * Calculates average lifespan over all pairs of adjacent purchases
     * @param product
     * @return
     */
    private double avgLifespan(Product product) {
        List<Purchase> purchases = product.getPurchases();

        if (purchases.size() < 2) {
            return 0.0;
        }

        double[] lifespans = new double[purchases.size() -1];

        Purchase prev, next;
        for (int i = 0; i < purchases.size() - 1; i++) {
            prev = purchases.get(i);
            next = purchases.get(i + 1);
            lifespans[i] = lifespan(prev, next);
        }

        return DoubleStream.of(lifespans).average()
            .orElse(0.0);
    }

    // in days
    private double lifespan(Purchase prev, Purchase next) {
        double millis = next.getMadeAt() - prev.getMadeAt();
        return  millis / (1000 * 60 * 60 * 24);
    }
}
