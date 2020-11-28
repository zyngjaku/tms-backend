package io.github.zyngjaku.tmsbackend.controller;

import io.github.zyngjaku.tmsbackend.dao.entity.User;
import io.github.zyngjaku.tmsbackend.request.AuthenticationRequest;
import io.github.zyngjaku.tmsbackend.request.CreateEmployeeRequest;
import io.github.zyngjaku.tmsbackend.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping(value = "/api")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PreAuthorize("hasAnyRole('OWNER', 'FORWARDER', 'DRIVER')")
    @GetMapping(value = "/users", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<User> getUsers(Principal principal,
                                @RequestParam(name = "role", required = false) User.Role role,
                                @RequestParam(name = "excludeMe", defaultValue = "true") Boolean excludeMe) {

        if (role == null) {
            return userService.getUsers(principal.getName(), excludeMe);
        }

        return userService.getUsers(principal.getName(), excludeMe, role);
    }

    @PreAuthorize("hasAnyRole('OWNER', 'FORWARDER', 'DRIVER')")
    @GetMapping(value = "/users/details", produces = MediaType.APPLICATION_JSON_VALUE)
    public User userDetails(Principal principal) {
        return userService.getUserDetails(principal.getName());
    }

    @PreAuthorize("hasRole('OWNER')")
    @PostMapping(value = "/users")
    public ResponseEntity employeeCreate(@RequestBody CreateEmployeeRequest registerRequest, Principal principal) {
        return userService.createEmployee(principal.getName(), registerRequest);
    }

    @PostMapping(value = "/authenticate")
    public ResponseEntity<?> userAuthenticate(@RequestBody AuthenticationRequest authenticationRequest) throws Exception {
        return userService.authenticateUser(authenticationRequest);
    }
}
