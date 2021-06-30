package escudo.api;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PurchaseRepository extends JpaRepository<Purchase, Long> {
    List<Purchase> findByProductBuyerUsernameAndProductId(String username, Long productId);
    Purchase findFirstByProductBuyerUsernameAndProductIdOrderByMadeAtDesc(String username, Long productId);
}
