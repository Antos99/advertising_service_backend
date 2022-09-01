package pl.adrian.advertising_service.user;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import pl.adrian.advertising_service.role.Role;
import pl.adrian.advertising_service.user.dto.UserDtoRequest;
import pl.adrian.advertising_service.user.dto.UserDtoResponse;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/users/{username}")
    public UserDtoResponse getUser(@PathVariable("username") String username){
        return userService.getUser(username);
    }

    @GetMapping("/users")
    public List<UserDtoResponse> getUsers(){
        return userService.getUsers();
    }

    @PostMapping("/users")
    public UserDtoResponse saveUser(@RequestBody UserDtoRequest userDtoRequest){
        return userService.addUser(userDtoRequest);
    }

    @PutMapping("/users/{username}/edit/username")
    public UserDtoResponse editUserUsername(@PathVariable("username") String username, @RequestBody String newUsername) throws JsonProcessingException {
        JsonNode json = new ObjectMapper().readTree(newUsername);
        if(json.findValue("new username") == null){
            throw new IllegalArgumentException("Request body does not contain key: new username");
        }
        String parsedNewUsername = json.path("new username").asText();
        return userService.editUserUsername(username, parsedNewUsername);
    }

    @PutMapping("/users/{username}/edit/email")
    public UserDtoResponse editUserEmail(@PathVariable("username") String username, @RequestBody String newEmail) throws JsonProcessingException {
        JsonNode json = new ObjectMapper().readTree(newEmail);
        if(json.findValue("new email") == null){
            throw new IllegalArgumentException("Request body does not contain key: new email");
        }
        String parsedNewEmail = json.path("new email").asText();
        return userService.editUserEmail(username, parsedNewEmail);
    }

    @PutMapping("/users/{username}/edit/password")
    public UserDtoResponse editUserPassword(@PathVariable("username") String username, @RequestBody String newPassword) throws JsonProcessingException {
        JsonNode json = new ObjectMapper().readTree(newPassword);
        if(json.findValue("new password") == null){
            throw new IllegalArgumentException("Request body does not contain key: new password");
        }
        String parsedNewPassword = json.path("new password").asText();
        return userService.editUserPassword(username, parsedNewPassword);
    }

    @PutMapping("/users/{username}/edit/enabled")
    public UserDtoResponse editUserEnabled(@PathVariable("username") String username, @RequestBody String newEnabled) throws JsonProcessingException {
        JsonNode json = new ObjectMapper().readTree(newEnabled);
        if(json.findValue("new username") == null){
            throw new IllegalArgumentException("Request body does not contain key: new enabled");
        }
        Boolean parsedNewEnabled = json.path("new enabled").asBoolean();
        return userService.editUserEnabled(username, parsedNewEnabled);
    }

    @PutMapping("/users/{username}/edit/roles")
    public UserDtoResponse editUserRoles(@PathVariable("username") String username, @RequestBody List<Role> newRoles){
        return userService.editUserRoles(username, newRoles);
    }

    @DeleteMapping("/users/{username}")
    public void deleteUser(@PathVariable("username") String username){
        userService.deleteUser(username);
    }
}
