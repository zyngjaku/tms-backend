package io.github.zyngjaku.tmsbackend.services;

import io.github.zyngjaku.tmsbackend.dao.UserRepo;
import io.github.zyngjaku.tmsbackend.dao.entity.User;
import io.github.zyngjaku.tmsbackend.request.AuthenticationRequest;
import io.github.zyngjaku.tmsbackend.request.UserRequest;
import io.github.zyngjaku.tmsbackend.response.JwtResponse;
import io.github.zyngjaku.tmsbackend.security.JwtTokenUtil;
import io.github.zyngjaku.tmsbackend.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
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

    public List<User> getUsers(String companyOwnerMail, boolean excludeMe) {
        User user = userRepo.findUserByMail(companyOwnerMail);

        if (excludeMe) {
            return userRepo.findUsersByCompanyAndMailNotContains(user.getCompany(), user.getMail());
        }

        return userRepo.findUsersByCompany(user.getCompany());
    }

    public List<User> getUsers(String companyOwnerMail, boolean excludeMe, User.Role role) {
        User user = userRepo.findUserByMail(companyOwnerMail);

        if (excludeMe) {
            return userRepo.findUsersByCompanyAndMailNotContainsAndRoleIs(user.getCompany(), user.getMail(), role);
        }

        return userRepo.findUsersByCompanyAndRoleIs(user.getCompany(), role);
    }

    public User getUserDetails(String userMail) {
        return userRepo.findUserByMail(userMail);
    }

    @Transactional
    public ResponseEntity createUser(String ownerMail, UserRequest userRequest) {
        User user = userRepo.findUserByMail(ownerMail);
        if (userRepo.findUserByMail(userRequest.getMail()) != null) {
            return ResponseEntity.status(HttpStatus.FOUND).body("Already exists user with this mail");
        }

        String pass = Utils.generatePassword(6);
        Utils.sendMail("zyngi98@gmail.com", "New account has been created",
                "Hello!\n\nNew account has been created for this mail.\nLogin: " + userRequest.getMail() + "\nPassword: " + pass);
        User newUser = new User(userRequest.getMail(), pass, userRequest.getFirstName(),
                userRequest.getLastName(), user.getCompany(), userRequest.getRole());
        userRepo.save(newUser);

        return new ResponseEntity(HttpStatus.CREATED);
    }

    @Transactional
    public ResponseEntity createUser(User user) {
        if (userRepo.findUserByMail(user.getMail()) != null) {
            return ResponseEntity.status(HttpStatus.FOUND).body("Already exists user with this mail");
        }

        user.setToken(jwtTokenUtil.generateToken(user));
        userRepo.save(user);

        return ResponseEntity.ok(new JwtResponse(user.getToken()));
    }

    @Transactional
    public ResponseEntity updateUser(String providerMail, UserRequest userRequest) {
        User user = userRepo.findUserByMail(providerMail);

        user.copyFromRequest(userRequest);
        userRepo.save(user);

        return new ResponseEntity(HttpStatus.OK);
    }

    @Transactional
    public ResponseEntity hireUser(String companyOwnerMail, Long userId) {
        User user = userRepo.findUserById(userId);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Not found user with provided id");
        }
        if (user.getCompany() != null) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("User is already hired in other company");
        }

        user.setCompany(userRepo.findUserByMail(companyOwnerMail).getCompany());
        userRepo.save(user);

        return new ResponseEntity(HttpStatus.OK);
    }

    @Transactional
    public ResponseEntity fireUser(String companyOwnerMail, Long userId) {
        User user = userRepo.findUserById(userId);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Not found user with provided id");
        }
        if (userRepo.findUserByMail(companyOwnerMail).getCompany() != user.getCompany()) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("User is not hire in your company");
        }

        user.setCompany(null);
        userRepo.save(user);

        return new ResponseEntity(HttpStatus.OK);
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
