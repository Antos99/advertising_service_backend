package pl.adrian.advertising_service.user;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import pl.adrian.advertising_service.user.dto.UserDtoRequest;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/users")
    public List<User> getUsers(){
        return userService.getUsers();
    }

    @PostMapping("/users")
    public User saveUser(@RequestBody UserDtoRequest userDtoRequest){
        return userService.addUser(userDtoRequest);
    }

    @PutMapping("/users")
    public User editUser(@RequestBody UserDtoRequest userDtoRequest){
        return userService.editUser(userDtoRequest);
    }
}
