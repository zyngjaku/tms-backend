package io.github.zyngjaku.tmsbackend.controller;

import io.github.zyngjaku.tmsbackend.dao.entity.Company;
import io.github.zyngjaku.tmsbackend.request.CompanyRequest;
import io.github.zyngjaku.tmsbackend.services.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping(value = "/api")
public class CompanyController {
    private final CompanyService companyService;

    @Autowired
    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    @GetMapping(value = "/companies/details", produces = MediaType.APPLICATION_JSON_VALUE)
    public Company getCompany(Principal principal) {
        return companyService.getCompanyDetails(principal.getName());
    }

    @PostMapping(value = "/companies")
    public ResponseEntity<?> createCompany(@RequestBody CompanyRequest companyRequest) {
        return companyService.createCompany(companyRequest);
    }
}
