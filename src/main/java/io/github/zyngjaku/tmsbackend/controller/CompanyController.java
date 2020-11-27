package io.github.zyngjaku.tmsbackend.controller;

import io.github.zyngjaku.tmsbackend.dao.entity.Company;
import io.github.zyngjaku.tmsbackend.request.CreateCompanyRequest;
import io.github.zyngjaku.tmsbackend.services.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping(value = "/api")
public class CompanyController {
    private final CompanyService companyService;

    @Autowired
    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    @RequestMapping(value = "/companies", method = RequestMethod.POST)
    public ResponseEntity<?> companyCreate(@RequestBody CreateCompanyRequest createCompanyRequest) {
        return companyService.createCompany(createCompanyRequest);
    }

    @RequestMapping(value = "/companies/details", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Company companyGet(Principal principal) {
        return companyService.getCompanyDetails(principal.getName());
    }
}
