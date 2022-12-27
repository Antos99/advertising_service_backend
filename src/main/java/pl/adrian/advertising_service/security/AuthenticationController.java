package pl.adrian.advertising_service.security;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.adrian.advertising_service.exceptions.ConflictException;
import pl.adrian.advertising_service.exceptions.NotFoundException;
import pl.adrian.advertising_service.security.dto.AuthenticationDtoLoginRequest;
import pl.adrian.advertising_service.security.dto.AuthenticationDtoLoginResponse;
import pl.adrian.advertising_service.security.dto.AuthenticationDtoRegisterRequest;
import pl.adrian.advertising_service.user.UserService;
import pl.adrian.advertising_service.user.dto.UserDtoRequest;
import pl.adrian.advertising_service.user.dto.UserDtoResponse;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("${api.prefix}")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private final UserService userService;

    @PostMapping("/login")
    @Operation(summary = "Login to the app to get JWT")
    public ResponseEntity<AuthenticationDtoLoginResponse> loginUser(
            @Valid @RequestBody AuthenticationDtoLoginRequest authenticationDtoLoginRequest){
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authenticationDtoLoginRequest.getUsername(),
                        authenticationDtoLoginRequest.getPassword()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());
        return ResponseEntity.ok(
                new AuthenticationDtoLoginResponse(
                        jwt,
                        userDetails.getId(),
                        userDetails.getUsername(),
                        roles
                )
        );
    }

    @PostMapping("/register")
    @Operation(summary = "Register to app")
    public ResponseEntity<UserDtoResponse> registerUser(
            @Valid @RequestBody AuthenticationDtoRegisterRequest authenticationDtoRegisterRequest)
            throws ConflictException, NotFoundException {
        UserDtoRequest userDtoRequest = new UserDtoRequest(authenticationDtoRegisterRequest);
        return new ResponseEntity<>(userService.addUser(userDtoRequest), HttpStatus.CREATED);
    }
}
