package escudo.api.repositories;

import escudo.api.entities.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PurchaseRepository extends JpaRepository<Purchase, Long> {
    List<Purchase> findByProductBuyerUsernameAndProductId(String username, Long productId);
    Purchase findFirstByProductBuyerUsernameAndProductIdOrderByMadeAtDesc(String username, Long productId);
}
