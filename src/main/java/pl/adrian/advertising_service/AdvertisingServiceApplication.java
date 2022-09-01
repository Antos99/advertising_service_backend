package pl.adrian.advertising_service;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import pl.adrian.advertising_service.role.Role;
import pl.adrian.advertising_service.role.RoleRepository;
import pl.adrian.advertising_service.role.RoleService;
import pl.adrian.advertising_service.user.UserService;
import pl.adrian.advertising_service.user.dto.UserDtoRequest;

import java.util.HashSet;
import java.util.Set;

@SpringBootApplication
public class AdvertisingServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(AdvertisingServiceApplication.class, args);
    }

    @Bean
    CommandLineRunner run(UserService userService, RoleService roleService, RoleRepository roleRepository){
        return args -> {
            roleService.addRole(new Role("ROLE_USER"));
            roleService.addRole(new Role("ROLE_ADMIN"));
            roleService.addRole(new Role("ROLE_SUPER_ADMIN"));

            Set<Role> antonRoles= new HashSet<>();
            Set<Role> bobRoles= new HashSet<>();
            Set<Role> kenuRoles= new HashSet<>();

            antonRoles.add(roleRepository.findRoleByName("ROLE_USER"));
            bobRoles.add(roleRepository.findRoleByName("ROLE_USER"));
            bobRoles.add(roleRepository.findRoleByName("ROLE_ADMIN"));
            kenuRoles.add(roleRepository.findRoleByName("ROLE_USER"));
            kenuRoles.add(roleRepository.findRoleByName("ROLE_ADMIN"));
            kenuRoles.add(roleRepository.findRoleByName("ROLE_SUPER_ADMIN"));

            userService.addUser(new UserDtoRequest(
                    "anton", "haslo", "anton@gmail.com", antonRoles));
            userService.addUser(new UserDtoRequest(
                    "bob", "1234", "bob@gmail.com", bobRoles));
            userService.addUser(new UserDtoRequest(
                    "kenu", "password", "kenu@wp.pl", kenuRoles));


        };
    }

}
