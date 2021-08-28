package escudo.api.repositories;

import escudo.api.entities.Purchase;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PurchaseRepository extends JpaRepository<Purchase, Long> {
    Purchase findFirstByProductIdOrderByMadeAtDesc(Long productId);

    @Query(value = 
    "select coalesce(avg(price / (cast((next_made_at - made_at) as double precision) / (1000*60*60*24))), 0) " +
    "from (" +
        "select price, made_at, lead(made_at, 1) over (order by made_at) as next_made_at " +
        "from purchases " +
        "where product_id = ?1" +
    ") as sub", nativeQuery = true)
    Double findAvgCpdByProductId(Long productId);

    @Query(value = 
    "select coalesce(avg(cast((next_made_at - made_at) as double precision) / (1000*60*60*24)), 0) " +
    "from (" +
        "select made_at, lead(made_at, 1) over (order by made_at) as next_made_at " +
        "from purchases " +
        "where product_id = ?1" +
    ") as sub", nativeQuery = true)
    Double findAvgLifespanByProductId(Long productId);
}
