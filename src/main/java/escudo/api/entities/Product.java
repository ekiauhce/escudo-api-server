package escudo.api.entities;


import escudo.api.dtos.ProductDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import org.hibernate.annotations.Formula;

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

    @Formula(
    "(select purchases.made_at from purchases " +
    "where purchases.product_id = id " +
    "order by purchases.made_at desc " +
    "limit 1)")
    private Long latestPurchaseMadeAt;

    /**
     * Construct entity from dto sended by web client
     * dto includes name only
     * @param dto
     */
    public Product(ProductDto dto) {
        this.name = dto.getName();
    }
}
