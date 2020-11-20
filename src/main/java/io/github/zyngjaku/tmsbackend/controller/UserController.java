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
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PreAuthorize("hasRole('OWNER')")
    @RequestMapping(value = "/employee/create", method = RequestMethod.POST)
    public ResponseEntity employeeCreate(@RequestBody CreateEmployeeRequest registerRequest, Principal principal) {
        return userService.createEmployee(principal.getName(), registerRequest);
    }

    @PreAuthorize("hasAnyRole('OWNER', 'FORWARDER')")
    @RequestMapping(value = "/employees", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<User> employeeAll(Principal principal) {
        return userService.getAllEmployees(principal.getName());
    }

    @PreAuthorize("hasAnyRole('OWNER', 'FORWARDER', 'DRIVER')")
    @RequestMapping(value = "/drivers", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<User> driverAll(Principal principal) {
        return userService.getAllDrivers(principal.getName());
    }

    @PreAuthorize("hasAnyRole('OWNER', 'FORWARDER', 'DRIVER')")
    @RequestMapping(value = "/user/details", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public User userDetails(Principal principal) {
        return userService.getUserDetails(principal.getName());
    }

    @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
    public ResponseEntity<?> userAuthenticate(@RequestBody AuthenticationRequest authenticationRequest) throws Exception {
        return userService.authenticateUser(authenticationRequest);
    }
}
