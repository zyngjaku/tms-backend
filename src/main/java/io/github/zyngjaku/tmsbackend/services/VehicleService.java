package io.github.zyngjaku.tmsbackend.services;

import io.github.zyngjaku.tmsbackend.dao.VehicleRepo;
import io.github.zyngjaku.tmsbackend.dao.entity.User;
import io.github.zyngjaku.tmsbackend.dao.entity.Vehicle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.security.Principal;
import java.util.List;

@Service
public class VehicleService {
    private final VehicleRepo vehicleRepo;
    private final UserService userService;

    @Autowired
    public VehicleService(VehicleRepo vehicleRepo, UserService userService) {
        this.vehicleRepo = vehicleRepo;
        this.userService = userService;
    }

    public ResponseEntity createVehicle() {
        //TODO: Implement!

        return new ResponseEntity(HttpStatus.CREATED);
    }

    public List<Vehicle> getVehicle(String userMail) {
        User user = userService.getUserDetails(userMail);

        return vehicleRepo.findVehiclesByCompany(user.getCompany());
    }
}
