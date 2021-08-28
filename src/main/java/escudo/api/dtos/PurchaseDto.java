package escudo.api.dtos;

import escudo.api.entities.Purchase;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseDto {
    
    private Long id;
    private Double price;
    private Long madeAt;

    public PurchaseDto(Purchase purchase) {
        this.id = purchase.getId();
        this.price = purchase.getPrice();
        this.madeAt = purchase.getMadeAt();
    }
}
