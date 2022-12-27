package pl.adrian.advertising_service.user;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.adrian.advertising_service.exceptions.BadRequestException;
import pl.adrian.advertising_service.exceptions.ConflictException;
import pl.adrian.advertising_service.exceptions.ForbiddenException;
import pl.adrian.advertising_service.exceptions.NotFoundException;
import pl.adrian.advertising_service.role.EnumRole;
import pl.adrian.advertising_service.role.Role;
import pl.adrian.advertising_service.role.RoleRepository;
import pl.adrian.advertising_service.security.CustomUserDetails;
import pl.adrian.advertising_service.user.dto.UserDtoRequest;
import pl.adrian.advertising_service.user.dto.UserDtoResponse;
import pl.adrian.advertising_service.user.dto.UserMapper;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService{
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    public UserDtoResponse addUser(UserDtoRequest userDtoRequest) throws ConflictException, NotFoundException {
        if(userRepository.existsUserByUsername(userDtoRequest.getUsername()))
            throw new ConflictException("User with username: " + userDtoRequest.getUsername() + " already exists");
        if(userRepository.existsUserByEmail(userDtoRequest.getEmail()))
            throw new ConflictException("User with email: " + userDtoRequest.getEmail() + " already exists");

        Set<Role> userRoles = new HashSet<>();

        for(Role r : userDtoRequest.getRoles()){
            Role roleToCheck = roleRepository.findRoleByName(r.getName()).orElseThrow(
                    () -> new NotFoundException("Role with name: " + r.getName().name() + " does not exist")
            );
            userRoles.add(roleToCheck);
        }

        User user = new User(
                userDtoRequest.getUsername(),
                passwordEncoder.encode(userDtoRequest.getPassword()),
                userDtoRequest.getEmail(),
                userRoles
        );
        return userMapper.mapToUserDtoResponse(userRepository.save(user));
    }

    public UserDtoResponse getUser(String username) throws NotFoundException {
        User user = userRepository.findUserByUsername(username).orElseThrow(
                () -> new NotFoundException("User with username: " + username + " does not exist")
        );
        return userMapper.mapToUserDtoResponse(user);
    }

    public List<UserDtoResponse> getUsers(){
        return userMapper.mapToUserDtoResponses(userRepository.findAll());
    }

    public UserDtoResponse editUserUsername(String username, String newUsername, CustomUserDetails userDetails)
            throws NotFoundException, ConflictException, ForbiddenException {
        if (!username.equals(userDetails.getUsername()) &&
                !userDetails.getAuthorities().contains(new SimpleGrantedAuthority(EnumRole.ROLE_MODERATOR.name())))
            throw new ForbiddenException(
                    "User with username: " + userDetails.getUsername() +
                            " does not have access to edit username of user with username: " + username
            );
        User user = userRepository.findUserByUsername(username).orElseThrow(
                () -> new NotFoundException("User with username: " + username + " does not exist")
        );
        if(userRepository.existsUserByUsername(newUsername)){
            throw new ConflictException("Username: " + newUsername + " is already taken");
        }

        user.setUsername(newUsername);
        return userMapper.mapToUserDtoResponse(user);
    }

    public UserDtoResponse editUserEmail(String username, String newEmail, CustomUserDetails userDetails)
            throws NotFoundException, ConflictException, ForbiddenException {
        if (!username.equals(userDetails.getUsername()) &&
                !userDetails.getAuthorities().contains(new SimpleGrantedAuthority(EnumRole.ROLE_MODERATOR.name())))
            throw new ForbiddenException(
                    "User with username: " + userDetails.getUsername() +
                            " does not have access to edit email of user with username: " + username
            );
        User user = userRepository.findUserByUsername(username).orElseThrow(
                () -> new NotFoundException("User with username: " + username + " does not exist")
        );
        if (userRepository.existsUserByEmail(newEmail)){
            throw new ConflictException("Email: " + newEmail + " is already taken");
        }
        user.setEmail(newEmail);
        return userMapper.mapToUserDtoResponse(user);
    }

    public UserDtoResponse editUserPassword(String username, String newPassword, CustomUserDetails userDetails)
            throws NotFoundException, ForbiddenException {
        if (!username.equals(userDetails.getUsername()))
            throw new ForbiddenException(
                    "User with username: " + userDetails.getUsername() +
                            " does not have access to edit username of user with username: " + username
            );
        User user = userRepository.findUserByUsername(username).orElseThrow(
                () -> new NotFoundException("User with username: " + username + " does not exist")
        );

        user.setPassword(passwordEncoder.encode(newPassword));
        return userMapper.mapToUserDtoResponse(user);
    }

    public UserDtoResponse editUserEnabled(String username, Boolean newEnabled) throws NotFoundException {
        User user = userRepository.findUserByUsername(username).orElseThrow(
                () -> new NotFoundException("User with username: " + username + " does not exist")
        );
        user.setEnabled(newEnabled);
        return userMapper.mapToUserDtoResponse(user);
    }

    public UserDtoResponse editUserRoles(String username, List<String> newRoles) throws NotFoundException, BadRequestException {
        User user = userRepository.findUserByUsername(username).orElseThrow(
                () -> new NotFoundException("User with username: " + username + " does not exist")
        );
        Set<Role> newUserRoles = new HashSet<>();
        for(String r : newRoles){
            try {
                EnumRole.valueOf(r);
            } catch (IllegalArgumentException e) {
                throw new BadRequestException("Role with name: " + r + " does not exist");
            }
            Role roleToCheck = roleRepository.findRoleByName(EnumRole.valueOf(r)).orElseThrow(
                    () -> new BadRequestException("Role with name: " + r + " does not exist")
            );
            newUserRoles.add(roleToCheck);
        }
        user.setRoles(newUserRoles);
        return userMapper.mapToUserDtoResponse(user);
    }

    public void deleteUser(String username, CustomUserDetails userDetails) throws NotFoundException, ForbiddenException {
        User user = userRepository.findUserByUsername(username).orElseThrow(
                () -> new NotFoundException("User with username: " + username + " does not exist")
        );
        if (!username.equals(userDetails.getUsername()) &&
                !userDetails.getAuthorities().contains(new SimpleGrantedAuthority(EnumRole.ROLE_MODERATOR.name())))
            throw new ForbiddenException(
                    "User with username: " + userDetails.getUsername() +
                            " does not have access to delete user with username: " + username
            );
        userRepository.deleteUserByUsername(username);
    }
}
