package escudo.api.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PurchaseDeletionReportDto {

    private PurchasesSummaryDto summary;
    private Long latestPurchaseMadeAt;
}
