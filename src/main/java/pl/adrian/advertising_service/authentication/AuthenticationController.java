package pl.adrian.advertising_service.authentication;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pl.adrian.advertising_service.authentication.dto.AuthenticationDtoLogin;

@RestController
public class AuthenticationController {

    @PostMapping("/login")
    public void login(@RequestBody AuthenticationDtoLogin authenticationDtoLogin){
    }
}
