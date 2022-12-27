package pl.adrian.advertising_service.security.dto;

import lombok.Getter;

import javax.validation.constraints.NotNull;

@Getter
public class AuthenticationDtoLoginRequest {
    @NotNull(message = "Username cannot be null")
    private String username;
    @NotNull(message = "Password cannot be null")
    private String password;
}
