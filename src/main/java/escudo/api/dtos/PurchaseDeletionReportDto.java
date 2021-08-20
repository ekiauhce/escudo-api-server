package escudo.api.dtos;

import escudo.api.entities.Product;
import escudo.api.entities.ProductSummary;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PurchaseDeletionReportDto {
    
    private ProductSummary summary;

    public PurchaseDeletionReportDto(Product product) {
        this.summary = product.getSummary();
    }
}
