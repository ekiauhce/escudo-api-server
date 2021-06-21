package escudo.api;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BuyerRepository extends JpaRepository<Buyer, Long> {

    Buyer findByUsername(String username);

    Boolean existsByUsername(String username);
}
