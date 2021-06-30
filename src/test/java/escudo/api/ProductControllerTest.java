package escudo.api;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProductController.class)
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductRepository productRepo;

    @MockBean
    private BuyerRepository buyerRepo;

    @MockBean
    private UserDetailsService userDetailsService;

    @Test
    @WithMockUser(username = "user", password = "pass")
    public void postProduct_newProduct_statusCreated() throws Exception {
        Buyer buyerToReturn = new Buyer("user", "pass");
        when(buyerRepo.findByUsername("user")).thenReturn(buyerToReturn);
        Product productToReturn = new Product(1L, "t-shirt", null, null);
        when(productRepo.save(Mockito.any(Product.class))).thenReturn(productToReturn);
        when(userDetailsService.loadUserByUsername("user"))
                .thenReturn(new User(
                        buyerToReturn.getUsername(),
                        buyerToReturn.getPassword(),
                        Collections.singleton(new SimpleGrantedAuthority("ROLE_USER"))));


        mockMvc.perform(post("/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\": \"t-shirt\"}"))
                .andExpect(status().isCreated())
                .andExpect(content().json("{\"id\": 1, \"name\": \"t-shirt\", \"purchases\": null}"));
    }


}