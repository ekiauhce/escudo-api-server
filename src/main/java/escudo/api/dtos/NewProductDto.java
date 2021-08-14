package escudo.api.dtos;

import java.util.ArrayList;
import java.util.List;

import escudo.api.entities.Product;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class NewProductDto {

    private String name;
    private Long id;
    private List<?> purchases = new ArrayList<>();
    
    public NewProductDto(Product product) {
        this.name = product.getName();
        this.id = product.getId();
    }
}
