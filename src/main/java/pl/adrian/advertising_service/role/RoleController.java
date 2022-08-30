package pl.adrian.advertising_service.role;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


import java.util.List;

@RestController
@RequiredArgsConstructor
public class RoleController {
    private final RoleService roleService;

    @GetMapping("/roles")
    public List<Role> getRoles(){
        return roleService.getRoles();
    }

    @PostMapping("/roles")
    public Role saveRole(@RequestBody Role role){
        return roleService.addRole(role);
    }
}
