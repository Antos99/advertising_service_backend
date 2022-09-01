package pl.adrian.advertising_service.user.dto;

import org.springframework.stereotype.Component;
import pl.adrian.advertising_service.user.User;

import java.util.List;

@Component
public class UserMapper {

    public List<UserDtoResponse> mapToUserDtoResponses(List<User> users){
        return users.stream().map(this::mapToUserDtoResponse).toList();
    }

    public UserDtoResponse mapToUserDtoResponse(User user){
        return new UserDtoResponse(user);
    }
}
