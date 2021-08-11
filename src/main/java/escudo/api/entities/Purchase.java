package escudo.api.entities;


import escudo.api.dtos.NewPurchaseDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "purchases")
@AllArgsConstructor
@NoArgsConstructor
public class Purchase {

    public Purchase(NewPurchaseDto dto) {
        this.price = dto.getPrice();
    }
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double price;

    private LocalDateTime madeAt;

    @ManyToOne(optional = false)
    @JoinColumn(name = "product_id", referencedColumnName = "id")
    private Product product;

    @PrePersist
    public void madeAt() {
        madeAt = LocalDateTime.now();
    }
}
