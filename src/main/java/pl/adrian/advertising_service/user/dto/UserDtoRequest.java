package pl.adrian.advertising_service.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.adrian.advertising_service.role.Role;

import java.util.List;
import java.util.Set;

@Setter
@Getter
@AllArgsConstructor
public class UserDtoRequest {
    private Long id;
    private String username;
    private String password;
    private Boolean enabled;
    private String email;
    private Set<Role> roles;

    public UserDtoRequest(String username, String password, String email, Set<Role> roles) {
        this.username = username;
        this.password = password;
        this.enabled = true;
        this.email = email;
        this.roles = roles;
    }
}
