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

import javax.transaction.Transactional;
import java.util.*;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

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

    public User addUser(UserDtoRequest userDtoRequest){
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
        return userRepository.save(user);
    }

    public User getUser(String username){
        return userRepository.findUserByUsername(username);
    }

    public List<User> getUsers(){
        return userRepository.findAll();
    }

    @Transactional
    public User editUser(UserDtoRequest userDtoRequest){
        if (userDtoRequest.getEmail() == null){
            throw new IllegalArgumentException("email cannot be null");
        }
        if (userDtoRequest.getRoles() == null){
            throw new IllegalArgumentException("roles cannot be null");
        }

        User userEdited = userRepository.findById(userDtoRequest.getId()).orElseThrow();

        userEdited.setEmail(userDtoRequest.getEmail());
        userEdited.setRoles(userDtoRequest.getRoles());

        return userEdited;
    }
}
