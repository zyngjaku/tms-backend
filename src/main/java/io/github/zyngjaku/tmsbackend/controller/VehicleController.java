package io.github.zyngjaku.tmsbackend.controller;

import io.github.zyngjaku.tmsbackend.dao.entity.Vehicle;
import io.github.zyngjaku.tmsbackend.services.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping(value = "/vehicle")
public class VehicleController {
    private final VehicleService vehicleService;

    @Autowired
    public VehicleController(VehicleService vehicleService) {
        this.vehicleService = vehicleService;
    }


    @PreAuthorize("hasRole('OWNER')")
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ResponseEntity createVehicle() {
        return vehicleService.createVehicle();
    }

    @PreAuthorize("hasAnyRole('OWNER', 'FORWARDER')")
    @RequestMapping(value = "/all", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Vehicle> getVehicle(Principal principal) {
        return vehicleService.getVehicle(principal.getName());
    }
}
