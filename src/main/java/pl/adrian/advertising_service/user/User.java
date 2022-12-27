package pl.adrian.advertising_service.user;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.adrian.advertising_service.advertisement.Advertisement;
import pl.adrian.advertising_service.role.Role;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
@Setter
@Getter
@NoArgsConstructor
@Table(
        name="users",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"username", "email"})
        }
)
public class User implements Serializable{
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    @Column(name="username")
    private String username;
    @Column(name="email")
    private String email;
    @Column(name="password")
    private String password;
    @Column(name="enabled")
    private Boolean enabled;
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private Set<Advertisement> advertisements;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name="users_roles",
            joinColumns=@JoinColumn(name="user_id"),
            inverseJoinColumns=@JoinColumn(name="role_id")
    )
    private Set<Role> roles;

    public User(String username, String password, String email,Set<Role> roles){
        this.username = username;
        this.password = password;
        this.email = email;
        this.enabled = true;
        this.roles = roles;
    }

    public User(Long id, String username, String email, String password, Boolean enabled, Set<Role> roles) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.enabled = enabled;
        this.roles = roles;
    }
}
