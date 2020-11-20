package io.github.zyngjaku.tmsbackend.services;

import io.github.zyngjaku.tmsbackend.dao.UserRepo;
import io.github.zyngjaku.tmsbackend.dao.entity.User;
import io.github.zyngjaku.tmsbackend.request.AuthenticationRequest;
import io.github.zyngjaku.tmsbackend.request.CreateEmployeeRequest;
import io.github.zyngjaku.tmsbackend.response.CustomResponse;
import io.github.zyngjaku.tmsbackend.response.JwtResponse;
import io.github.zyngjaku.tmsbackend.security.JwtTokenUtil;
import io.github.zyngjaku.tmsbackend.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Service
public class UserService {
    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtil jwtTokenUtil;
    private final UserRepo userRepo;

    @Autowired
    public UserService(AuthenticationManager authenticationManager, JwtTokenUtil jwtTokenUtil, UserRepo userRepo) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenUtil = jwtTokenUtil;
        this.userRepo = userRepo;
    }

    public ResponseEntity createEmployee(String ownerMail, CreateEmployeeRequest createEmployeeRequest) {
        User user = userRepo.findUserByMail(ownerMail);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cannot find user with this mail!");
        } else if (userRepo.findUserByMail(createEmployeeRequest.getMail()) != null) {
            return ResponseEntity.status(HttpStatus.FOUND).body("Already exists user with this mail");
        }

        String pass = Utils.generatePassword(6);
        Utils.sendMail("zyngi98@gmail.com", "New account has been created",
                "Hello!\n\nNew account has been created for this mail.\nLogin: " + createEmployeeRequest.getMail() + "\nPassword: " + pass);
        User newUser = new User(createEmployeeRequest.getMail(), pass, createEmployeeRequest.getFirstName(),
                createEmployeeRequest.getLastName(), user.getCompany(), createEmployeeRequest.getRole());
        userRepo.save(newUser);

        return new ResponseEntity(HttpStatus.CREATED);
    }

    public ResponseEntity createUser(User user) {
        if (userRepo.findUserByMail(user.getMail()) != null) {
            return ResponseEntity.status(HttpStatus.FOUND).body("Already exists user with this mail");
        }

        user.setToken(jwtTokenUtil.generateToken(user));
        userRepo.save(user);

        return ResponseEntity.ok(new JwtResponse(user.getToken()));
    }

    public List<User> getAllEmployees(String companyOwnerMail) {
        User user = userRepo.findUserByMail(companyOwnerMail);

        return userRepo.findUsersByCompanyAndMailNotContains(user.getCompany(), user.getMail());
    }

    public List<User> getAllDrivers(String companyOwnerMail) {
        User user = userRepo.findUserByMail(companyOwnerMail);

        return userRepo.findUsersByCompanyAndLocalizationIsNotNullAndMailNotContains(user.getCompany(), user.getMail());
    }

    public User getUserDetails(String userMail) {
        return userRepo.findUserByMail(userMail);
    }

    public ResponseEntity authenticateUser(AuthenticationRequest authenticationRequest) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getMail(), authenticationRequest.getPassword()));

            User user = userRepo.findUserByMail(authenticationRequest.getMail());
            user.setToken(jwtTokenUtil.generateToken(user));
            userRepo.save(user);

            return ResponseEntity.ok(new JwtResponse(user.getToken()));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }
}
