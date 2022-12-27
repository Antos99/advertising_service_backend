package pl.adrian.advertising_service.role;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}")
public class RoleController {
    private final RoleService roleService;

    @GetMapping("/roles")
    @Operation(
            security = { @SecurityRequirement(name = "bearer-key") },
            summary = "Get all roles. Only ADMIN can get list of all roles"
    )
    public ResponseEntity<List<Role>> getRoles(){
        return new ResponseEntity<>(roleService.getRoles(), HttpStatus.OK);
    }
}
