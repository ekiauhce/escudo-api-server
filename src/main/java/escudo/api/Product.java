package escudo.api;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String name;

    private Date boughtAt;

    @ManyToOne(optional = false)
    @JoinColumn(name = "buyer_id", referencedColumnName = "id")
    @JsonIgnore
    private Buyer buyer;

    @PrePersist
    public void boughtAt() {
        boughtAt = new Date();
    }


}
