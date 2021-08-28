package escudo.api.dtos;

import java.util.List;

import escudo.api.entities.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto {

    private String name;

    private Long latestPurchaseMadeAt;
    private List<?> purchases = null;
    private PurchasesSummaryDto summary = null;

    public ProductDto(Product product) {
        this.name = product.getName();
        this.latestPurchaseMadeAt = product.getLatestPurchaseMadeAt(); 
    }
}
