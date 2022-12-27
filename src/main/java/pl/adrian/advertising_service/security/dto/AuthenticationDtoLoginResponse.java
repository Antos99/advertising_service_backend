package pl.adrian.advertising_service.security.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@Setter
@Getter
public class AuthenticationDtoLoginResponse {
    private String token;
    private final String type = "Bearer";
    private Long userId;
    private String username;
    private List<String> roles;

}
