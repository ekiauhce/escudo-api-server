package escudo.api.dtos;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

import escudo.api.entities.Purchase;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
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
