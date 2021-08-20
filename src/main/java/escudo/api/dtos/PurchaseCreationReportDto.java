package escudo.api.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;

import escudo.api.entities.Purchase;
import escudo.api.entities.ProductSummary;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PurchaseCreationReportDto {

    @JsonProperty("purchase")
    private PurchaseDto purchaseDto;
    private ProductSummary summary;
    

    public PurchaseCreationReportDto(Purchase purchase) {
        this.purchaseDto = new PurchaseDto(purchase);
        this.summary = purchase.getProduct().getSummary();
    }
}
