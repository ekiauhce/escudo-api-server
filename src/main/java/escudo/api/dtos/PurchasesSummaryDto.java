package escudo.api.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PurchasesSummaryDto {
    private Double avgCpd;
    private Double avgLifespan;
}
