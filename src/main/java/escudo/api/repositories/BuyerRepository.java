package escudo.api.repositories;

import escudo.api.entities.Buyer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BuyerRepository extends JpaRepository<Buyer, Long> {

    Buyer findByUsername(String username);
}
