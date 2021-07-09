package escudo.api;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class EscudoApiServerApplicationTests {

    @Autowired
    ProductRepository productRepo;
    @Autowired
    BuyerRepository buyerRepository;

    @Test
    @Transactional
    void test() {
        Buyer buyer = new Buyer("user", "pass");
        buyerRepository.save(buyer);

        Buyer buyer1 = buyerRepository.findByUsername("user");
        Product product = new Product();
        product.setName("lentils");
        product.setBuyer(buyer1);
        productRepo.save(product);

        System.out.println(productRepo.findProductsByBuyerUsername("user"));
    }
}
