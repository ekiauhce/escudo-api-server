package escudo.api.dtos;

import escudo.api.entities.Product;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class NewProductDto {

    private String name;
    private Long id;
    
    
    public NewProductDto(Product product) {
        this.name = product.getName();
        this.id = product.getId();
    }
}
