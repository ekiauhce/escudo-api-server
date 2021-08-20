package escudo.api.dtos;

import java.util.List;

import escudo.api.entities.Product;
import escudo.api.entities.ProductSummary;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ProductDto {

    private String name;
    private List<?> purchases = null;
    private ProductSummary summary;


    public ProductDto(Product product) {
        this.name = product.getName();
        this.summary = product.getSummary(); 
    }
}
