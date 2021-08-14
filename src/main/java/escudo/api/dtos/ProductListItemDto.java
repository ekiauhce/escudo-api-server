package escudo.api.dtos;

import java.util.List;
import java.util.stream.Collectors;

import escudo.api.entities.Product;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ProductListItemDto {

    private Long id;
    private String name;
    private List<PurchaseListItemDto> purchases;

    public ProductListItemDto(Product product) {
        this.id = product.getId();
        this.name = product.getName();
        this.purchases = product.getPurchases().stream()
            .map(PurchaseListItemDto::new)
            .collect(Collectors.toList());
    }
}
