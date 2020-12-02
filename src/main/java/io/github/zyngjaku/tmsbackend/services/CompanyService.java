package io.github.zyngjaku.tmsbackend.services;

import io.github.zyngjaku.tmsbackend.dao.CompanyRepo;
import io.github.zyngjaku.tmsbackend.dao.entity.Company;
import io.github.zyngjaku.tmsbackend.dao.entity.User;
import io.github.zyngjaku.tmsbackend.request.CompanyRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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

    public ResponseEntity createCompany(CompanyRequest companyRequest) {
        if (companyRepo.findCompanyByName(companyRequest.getName()) != null) {
            return ResponseEntity.status(HttpStatus.FOUND).body("Already exists company with this name");
        }

        Company company = new Company(companyRequest.getName(), companyRequest.getStreet(), companyRequest.getCity(),
                companyRequest.getZipCode(), companyRequest.getCountry());
        companyRepo.save(company);

        return userService.createUser(new User(companyRequest.getMail(), passwordEncoder.encode(companyRequest.getPassword()), companyRequest.getFirstName(),
                companyRequest.getLastName(), company, User.Role.OWNER));
    }

    public Company getCompanyDetails(String userName) {
        return userService.getUserDetails(userName).getCompany();
    }
}
