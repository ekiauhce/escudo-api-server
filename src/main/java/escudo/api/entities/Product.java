package escudo.api.entities;


import escudo.api.dtos.NewProductDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Table(name = "products",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"name", "buyer_id"})})
@AllArgsConstructor
@NoArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToOne(optional = false)
    @JoinColumn(name = "buyer_id", referencedColumnName = "id")
    private Buyer buyer;

    @OneToMany(mappedBy = "product", cascade = CascadeType.REMOVE)
    private List<Purchase> purchases = new ArrayList<>();

    /**
     * Construct entity from dto sended by web client
     * dto includes name only
     * @param dto
     */
    public Product(NewProductDto dto) {
        this.name = dto.getName();
    }
}
