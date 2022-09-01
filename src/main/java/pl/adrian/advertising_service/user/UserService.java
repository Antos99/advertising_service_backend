package pl.adrian.advertising_service.user;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import pl.adrian.advertising_service.role.Role;
import pl.adrian.advertising_service.role.RoleRepository;
import pl.adrian.advertising_service.user.dto.UserDtoRequest;
import pl.adrian.advertising_service.user.dto.UserDtoResponse;
import pl.adrian.advertising_service.user.dto.UserMapper;

import javax.transaction.Transactional;
import java.util.*;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findUserByUsername(username);
        if(user == null){
            throw new UsernameNotFoundException("User not found in database");
        }
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        user.getRoles().forEach(role -> authorities.add(new SimpleGrantedAuthority(role.getName())));
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(), user.getPassword(), authorities);
    }

    public UserDtoResponse addUser(UserDtoRequest userDtoRequest){
        if (userDtoRequest.getUsername() == null){
            throw new IllegalArgumentException("username cannot be null");
        }
        if (userDtoRequest.getPassword() == null){
            throw new IllegalArgumentException("password cannot be null");
        }
        if (userDtoRequest.getRoles() == null){
            throw new IllegalArgumentException("role cannot be null");
        }

        Set<Role> userRoles = new HashSet<>();

        for(Role r : userDtoRequest.getRoles()){
            Role roleToCheck = roleRepository.findRoleByName(r.getName());
            if(roleToCheck == null){
                throw new IllegalArgumentException("incorrect role name: " + r.getName());
            }else{
                userRoles.add(roleToCheck);
            }
        }

        User user = new User(
                userDtoRequest.getUsername(),
                new BCryptPasswordEncoder().encode(userDtoRequest.getPassword()),
                userDtoRequest.getEmail(),
                userRoles
        );
        return userMapper.mapToUserDtoResponse(userRepository.save(user));
    }

    public UserDtoResponse getUser(String username){
        User user = userRepository.findUserByUsername(username);
        if (user == null){
            throw new IllegalArgumentException("User with username: " + username + " does not exist");
        }
        return userMapper.mapToUserDtoResponse(user);
    }

    public List<UserDtoResponse> getUsers(){
        return userMapper.mapToUserDtoResponses(userRepository.findAll());
    }

    @Transactional
    public UserDtoResponse editUserUsername(String username, String newUsername) {
        User user = userRepository.findUserByUsername(username);
        if (user == null){
            throw new IllegalArgumentException("User with username: " + username + " does not exist");
        }
        if(userRepository.findUserByUsername(newUsername) != null){
            throw new IllegalArgumentException("Username: " + newUsername + " is already taken");
        }

        user.setUsername(newUsername);
        return userMapper.mapToUserDtoResponse(user);
    }

    @Transactional
    public UserDtoResponse editUserEmail(String username, String newEmail) {
        User user = userRepository.findUserByUsername(username);
        if (user == null){
            throw new IllegalArgumentException("User with username: " + username + " does not exist");
        }
        if (userRepository.findUserByEmail(newEmail) != null){
            throw new IllegalArgumentException("Email: " + newEmail + " is already taken");
        }
        user.setEmail(newEmail);
        return userMapper.mapToUserDtoResponse(user);
    }

    @Transactional
    public UserDtoResponse editUserPassword(String username, String newPassword) {
        User user = userRepository.findUserByUsername(username);
        if (user == null){
            throw new IllegalArgumentException("User with username: " + username + " does not exist");
        }
        if (newPassword == null){
            throw new IllegalArgumentException("password cannot be null");
        }
        user.setPassword(new BCryptPasswordEncoder().encode(newPassword));
        return userMapper.mapToUserDtoResponse(user);
    }

    @Transactional
    public UserDtoResponse editUserEnabled(String username, Boolean newEnabled) {
        User user = userRepository.findUserByUsername(username);
        if (user == null){
            throw new IllegalArgumentException("User with username: " + username + " does not exist");
        }
        user.setEnabled(newEnabled);
        return userMapper.mapToUserDtoResponse(user);
    }

    @Transactional
    public UserDtoResponse editUserRoles(String username, List<Role> newRoles) {
        User user = userRepository.findUserByUsername(username);
        if (user == null){
            throw new IllegalArgumentException("User with username: " + username + " does not exist");
        }
        Set<Role> newUserRoles = new HashSet<>();
        for(Role r : newRoles){
            Role roleToCheck = roleRepository.findRoleByName(r.getName());
            if(roleToCheck == null){
                throw new IllegalArgumentException("incorrect role name: " + r.getName());
            }else{
                newUserRoles.add(roleToCheck);
            }
        }
        user.setRoles(newUserRoles);
        return userMapper.mapToUserDtoResponse(user);
    }

    public void deleteUser(String username) {
        userRepository.deleteUserByUsername(username);
    }
}
