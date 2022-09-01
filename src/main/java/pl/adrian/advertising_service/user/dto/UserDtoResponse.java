package pl.adrian.advertising_service.user.dto;

import lombok.Getter;
import lombok.Setter;
import pl.adrian.advertising_service.role.Role;
import pl.adrian.advertising_service.user.User;

import java.util.Set;

@Setter
@Getter
public class UserDtoResponse {
    private Long id;
    private String username;
    private Boolean enabled;
    private String email;
    private Set<Role> roles;

    public UserDtoResponse(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.enabled = user.getEnabled();
        this.email = user.getEmail();
        this.roles = user.getRoles();
    }
}
