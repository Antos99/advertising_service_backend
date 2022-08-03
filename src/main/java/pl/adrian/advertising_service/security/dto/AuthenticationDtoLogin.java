package pl.adrian.advertising_service.security.dto;

import lombok.Getter;

@Getter
public class AuthenticationDtoLogin {
    private String username;
    private String password;
}
