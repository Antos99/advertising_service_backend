package pl.adrian.advertising_service.role;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoleService {
    private final RoleRepository roleRepository;

    public Role addRole(Role role){
        if (role.getName() == null){
            throw new IllegalArgumentException("name cannot be null");
        }
        Role roleToSave = new Role(role.getName());
        return roleRepository.save(roleToSave);
    }

    public List<Role> getRoles(){
        return roleRepository.findAll();
    }

}
