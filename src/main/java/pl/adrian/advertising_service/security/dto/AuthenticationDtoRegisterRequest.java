package pl.adrian.advertising_service.security.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@AllArgsConstructor
@Getter
public class AuthenticationDtoRegisterRequest {
    @NotNull(message = "Username cannot be null")
    @Size(min = 2, max = 20, message = "Username should be between {min} and {max} characters")
    private String username;
    @NotNull(message = "Password cannot be null")
    @Size(min = 2, max = 20, message = "Password should be between {min} and {max} characters")
    private String password;
    @NotNull(message = "Email cannot be null")
    @Email(message = "Email should have correct syntax")
    private String email;
}
