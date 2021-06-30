package escudo.api;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@Table(name = "buyers")
public class Buyer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String username;

    private String password;

    @OneToMany(mappedBy = "buyer", cascade = CascadeType.REMOVE)
    private List<Product> products = new ArrayList<>();

    public Buyer(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
