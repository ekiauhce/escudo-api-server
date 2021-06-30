package escudo.api;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BuyerRepository extends JpaRepository<Buyer, Long> {

    Buyer findByUsername(String username);
}
