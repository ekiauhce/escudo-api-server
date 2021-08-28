package escudo.api.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PurchaseCreationReportDto {

    @JsonProperty("purchase")
    private PurchaseDto purchaseDto;

    @JsonProperty("summary")
    private PurchasesSummaryDto summaryDto;

    private Long latestPurchaseMadeAt;
}
