package escudo.api.security;

import escudo.api.Buyer;
import escudo.api.BuyerRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(SecurityController.class)
class SecurityControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BuyerRepository buyerRepo;

    @MockBean
    private PasswordEncoder encoder;

    @Test
    public void register_newUser_statusNoContent() throws Exception {
        mockMvc.perform(post("/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"username\": \"user\", \"password\": \"pass\"}"))
                .andExpect(status().isNoContent());
    }

    @Test
    public void register_userExists_statusConflict() throws Exception {
        when(buyerRepo.save(Mockito.any(Buyer.class)))
                .thenThrow(new DataIntegrityViolationException(""));

        mockMvc.perform(post("/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"username\": \"user\", \"password\": \"pass\"}"))
                .andExpect(status().isConflict());
    }

}