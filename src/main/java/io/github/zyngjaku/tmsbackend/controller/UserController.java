package io.github.zyngjaku.tmsbackend.controller;

import io.github.zyngjaku.tmsbackend.dao.entity.User;
import io.github.zyngjaku.tmsbackend.request.AuthenticationRequest;
import io.github.zyngjaku.tmsbackend.request.UserRequest;
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
    public User getUserDetails(Principal principal) {
        return userService.getUserDetails(principal.getName());
    }

    @PreAuthorize("hasRole('OWNER')")
    @PostMapping(value = "/users")
    public ResponseEntity createUser(Principal principal, @RequestBody UserRequest userRequest) {
        return userService.createUser(principal.getName(), userRequest);
    }

    @PreAuthorize("hasAnyRole('OWNER', 'FORWARDER', 'DRIVER')")
    @PutMapping(value = "/users")
    public ResponseEntity updateUser(Principal principal, @RequestBody UserRequest userRequest) {
        return userService.updateUser(principal.getName(), userRequest);
    }

    @PreAuthorize("hasRole('OWNER')")
    @PutMapping(value = "/users/{userId}/hire")
    public ResponseEntity hireUser(Principal principal, @PathVariable Long userId) {
        return userService.hireUser(principal.getName(), userId);
    }

    @PreAuthorize("hasRole('OWNER')")
    @PutMapping(value = "/users/{userId}/fire")
    public ResponseEntity fireUser(Principal principal, @PathVariable Long userId) {
        return userService.fireUser(principal.getName(), userId);
    }

    @PostMapping(value = "/authenticate")
    public ResponseEntity<?> userAuthenticate(@RequestBody AuthenticationRequest authenticationRequest) throws Exception {
        return userService.authenticateUser(authenticationRequest);
    }
}
