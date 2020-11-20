package io.github.zyngjaku.tmsbackend.services;

import com.google.gson.Gson;
import io.github.zyngjaku.tmsbackend.dao.CompanyRepo;
import io.github.zyngjaku.tmsbackend.dao.UserRepo;
import io.github.zyngjaku.tmsbackend.dao.entity.Company;
import io.github.zyngjaku.tmsbackend.dao.entity.User;
import io.github.zyngjaku.tmsbackend.request.CreateCompanyRequest;
import io.github.zyngjaku.tmsbackend.response.JwtResponse;
import io.github.zyngjaku.tmsbackend.security.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.security.Principal;

@Service
public class CompanyService {
    private final CompanyRepo companyRepo;
    private final UserService userService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    public CompanyService(CompanyRepo companyRepo, UserService userService) {
        this.companyRepo = companyRepo;
        this.userService = userService;
    }

    public ResponseEntity createCompany(CreateCompanyRequest createCompanyRequest) {
        if (companyRepo.findCompanyByName(createCompanyRequest.getName()) != null) {
            return ResponseEntity.status(HttpStatus.FOUND).body("Already exists company with this name");
        }

        Company company = new Company(createCompanyRequest.getName(), createCompanyRequest.getStreet(), createCompanyRequest.getCity(),
                createCompanyRequest.getZipCode(), createCompanyRequest.getCountry());
        companyRepo.save(company);

        return userService.createUser(new User(createCompanyRequest.getMail(), passwordEncoder.encode(createCompanyRequest.getPassword()), createCompanyRequest.getFirstName(),
                createCompanyRequest.getLastName(), company, User.Role.OWNER));
    }

    public Company getCompanyDetails(String userName) {
        return userService.getUserDetails(userName).getCompany();
    }
}
