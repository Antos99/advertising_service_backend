package pl.adrian.advertising_service.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.adrian.advertising_service.role.EnumRole;
import pl.adrian.advertising_service.role.Role;
import pl.adrian.advertising_service.security.dto.AuthenticationDtoRegisterRequest;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserDtoRequest {
    private String username;
    private String password;
    private String email;
    private Set<Role> roles;

    public UserDtoRequest(AuthenticationDtoRegisterRequest authenticationDtoRegisterRequest){
        this.username = authenticationDtoRegisterRequest.getUsername();
        this.password = authenticationDtoRegisterRequest.getPassword();
        this.email = authenticationDtoRegisterRequest.getEmail();
        this.roles = new HashSet<>(List.of(new Role(EnumRole.ROLE_USER)));
    }
}
