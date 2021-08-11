package escudo.api.dtos;

import java.time.LocalDateTime;

import escudo.api.entities.Purchase;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class NewPurchaseDto {
    
    private Long id;
    private Double price;
    private LocalDateTime madeAt;

    public NewPurchaseDto(Purchase purchase) {
        this.id = purchase.getId();
        this.price = purchase.getPrice();
        this.madeAt = purchase.getMadeAt();
    }
}
