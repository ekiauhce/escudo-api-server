package escudo.api.entities;

import javax.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductSummary {

    private Double avgCPD = 0.0;
    private Double avgLifespan = 0.0;
}
