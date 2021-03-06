package io.github.zyngjaku.tmsbackend.services;

import io.github.zyngjaku.tmsbackend.dao.CompanyRepo;
import io.github.zyngjaku.tmsbackend.dao.VehicleRepo;
import io.github.zyngjaku.tmsbackend.dao.entity.Company;
import io.github.zyngjaku.tmsbackend.dao.entity.User;
import io.github.zyngjaku.tmsbackend.dao.entity.Vehicle;
import io.github.zyngjaku.tmsbackend.request.VehicleRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.transaction.Transactional;
import java.security.Principal;
import java.util.List;

@Service
public class VehicleService {
    private final VehicleRepo vehicleRepo;
    private final UserService userService;
    private final CompanyRepo companyRepo;

    @Autowired
    public VehicleService(VehicleRepo vehicleRepo, UserService userService, CompanyRepo companyRepo) {
        this.vehicleRepo = vehicleRepo;
        this.userService = userService;
        this.companyRepo = companyRepo;
    }

    public List<Vehicle> getVehicle(String userMail) {
        User user = userService.getUserDetails(userMail);

        return vehicleRepo.findVehiclesByCompany(user.getCompany());
    }

    @Transactional
    public ResponseEntity createVehicle(VehicleRequest vehicleRequest) {
        Company company = companyRepo.findCompanyByName(vehicleRequest.getCompanyName());
        if (company == null) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }

        Vehicle vehicle = new Vehicle(vehicleRequest, company);
        vehicleRepo.save(vehicle);

        return new ResponseEntity(HttpStatus.CREATED);
    }

    @Transactional
    public ResponseEntity updateVehicle(Long vehicleId, VehicleRequest vehicleRequest) {
        Vehicle vehicle = vehicleRepo.findVehiclesById(vehicleId);
        if (vehicle == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Not found vehicle with provided id");
        }

        vehicle.setFields(vehicleRequest);
        vehicleRepo.save(vehicle);

        return new ResponseEntity(HttpStatus.OK);
    }

    @Transactional
    public ResponseEntity deleteVehicle(Long vehicleId) {
        if (vehicleRepo.findVehiclesById(vehicleId) == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Not found vehicle with provided id");
        }

        vehicleRepo.deleteById(vehicleId);

        return new ResponseEntity(HttpStatus.OK);
    }
}
