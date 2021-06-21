package escudo.api.security;

import escudo.api.Buyer;
import escudo.api.BuyerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class JpaUserDetailsService implements UserDetailsService {

    private final BuyerRepository repo;

    @Autowired
    public JpaUserDetailsService(BuyerRepository repo) {
        this.repo = repo;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        Buyer buyer = repo.findByUsername(s);

        if (buyer != null) {
            return new User(
                    buyer.getUsername(),
                    buyer.getPassword(),
                    Collections.singleton(new SimpleGrantedAuthority("ROLE_USER"))
            );
        } else {
            throw new UsernameNotFoundException("No buyer with username: " + s);
        }
    }
}
