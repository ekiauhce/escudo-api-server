package escudo.api.repositories;

import escudo.api.entities.Product;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {

    @EntityGraph(attributePaths = "purchases")
    List<Product> findProductsByBuyerUsername(String username);

    Optional<Product> findByBuyerUsernameAndName(String username, String name);
}
