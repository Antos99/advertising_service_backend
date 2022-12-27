package pl.adrian.advertising_service.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import pl.adrian.advertising_service.exceptions.BadRequestException;
import pl.adrian.advertising_service.exceptions.ConflictException;
import pl.adrian.advertising_service.exceptions.ForbiddenException;
import pl.adrian.advertising_service.exceptions.NotFoundException;
import pl.adrian.advertising_service.security.CustomUserDetails;
import pl.adrian.advertising_service.user.dto.UserDtoRequest;
import pl.adrian.advertising_service.user.dto.UserDtoResponse;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}")
public class UserController {
    private final UserService userService;

    @GetMapping("/users/{username}")
    @Operation(summary = "Get a user by its username")
    public ResponseEntity<UserDtoResponse> getUser(@PathVariable("username") String username) throws NotFoundException {
        return new ResponseEntity<>(userService.getUser(username), HttpStatus.OK);
    }

    @GetMapping("/users")
    @Operation(
            security = { @SecurityRequirement(name = "bearer-key") },
            summary = "Get all users. Only ADMIN can get list of all users"
    )
    public ResponseEntity<List<UserDtoResponse>> getUsers(){
        return new ResponseEntity<>(userService.getUsers(), HttpStatus.OK);
    }

    @PostMapping("/users")
    @Operation(
            security = { @SecurityRequirement(name = "bearer-key") },
            summary = "Add new user. Only ADMIN can add new user"
    )
    public ResponseEntity<UserDtoResponse> saveUser(@RequestBody UserDtoRequest userDtoRequest) throws BadRequestException, ConflictException, NotFoundException {
        return new ResponseEntity<>(userService.addUser(userDtoRequest), HttpStatus.CREATED);
    }

    @PutMapping("/users/{username}/edit/username")
    @Operation(
            security = { @SecurityRequirement(name = "bearer-key") },
            summary = "Edit user's username. Only USER with specific username and ADMIN can change username"
    )
    public ResponseEntity<UserDtoResponse> editUserUsername(
            @PathVariable("username") String username,
            @RequestBody
            @NotNull(message = "Category name cannot be null")
            @Size(min = 4, max = 20, message = "Username should contain from 4 to 20 characters")
            String newUsername,
            Authentication authentication
    )
            throws NotFoundException, ConflictException, ForbiddenException {
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        return new ResponseEntity<>(userService.editUserUsername(username, newUsername, userDetails), HttpStatus.OK);
    }

    @PutMapping("/users/{username}/edit/email")
    @Operation(
            security = { @SecurityRequirement(name = "bearer-key") },
            summary = "Edit user's email. Only USER with specific username and ADMIN can change email"
    )
    public ResponseEntity<UserDtoResponse> editUserEmail(
            @PathVariable("username") String username,
            @RequestBody
            @NotNull(message = "Email cannot be null")
            @Email(message = "Email should have correct syntax")
            String newEmail,
            Authentication authentication
            ) throws NotFoundException, ConflictException, ForbiddenException {
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

        return new ResponseEntity<>(userService.editUserEmail(username, newEmail, userDetails), HttpStatus.OK);
    }

    @PutMapping("/users/{username}/edit/password")
    @Operation(
            security = { @SecurityRequirement(name = "bearer-key") },
            summary = "Edit user's password. Only USER with specific username can change password"
    )
    public ResponseEntity<UserDtoResponse> editUserPassword(
            @PathVariable("username") String username,
            @RequestBody
            @NotNull(message = "Password cannot be null")
            @Size(min = 4, max = 20, message = "Password should contain from 4 to 20 characters")
            String newPassword,
            Authentication authentication
            )
            throws NotFoundException, ForbiddenException {
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        return new ResponseEntity<>(userService.editUserPassword(username, newPassword, userDetails), HttpStatus.OK);
    }

    @PutMapping("/users/{username}/edit/enabled")
    @Operation(
            security = { @SecurityRequirement(name = "bearer-key") },
            summary = "Edit user's enabled status. Only ADMIN can change enabled status"
    )
    public ResponseEntity<UserDtoResponse> editUserEnabled(
            @PathVariable("username") String username,
            @RequestBody
            @NotNull(message = "Enabled cannot be null")
            Boolean newEnabled)
            throws NotFoundException {
        return new ResponseEntity<>(userService.editUserEnabled(username, newEnabled), HttpStatus.OK);
    }

    @PutMapping("/users/{username}/edit/roles")
    @Operation(
            security = { @SecurityRequirement(name = "bearer-key") },
            summary = "Edit user's roles. Only ADMIN can change roles"
    )
    public ResponseEntity<UserDtoResponse> editUserRoles(
            @PathVariable("username") String username,
            @NotNull(message = "Roles cannot be null")
            @RequestBody List<String> newRoles) throws BadRequestException, NotFoundException {
        return new ResponseEntity<>(userService.editUserRoles(username, newRoles), HttpStatus.OK);
    }

    @DeleteMapping("/users/{username}")
    @Operation(
            security = { @SecurityRequirement(name = "bearer-key") },
            summary = "Delete user. Only USER with specific username and ADMIN can delete user"
    )
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable("username") String username, Authentication authentication) throws ForbiddenException, NotFoundException {
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        userService.deleteUser(username, userDetails);
    }
}
