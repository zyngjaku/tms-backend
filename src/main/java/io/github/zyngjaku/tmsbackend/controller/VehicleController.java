package io.github.zyngjaku.tmsbackend.controller;

import io.github.zyngjaku.tmsbackend.dao.entity.Vehicle;
import io.github.zyngjaku.tmsbackend.request.VehicleRequest;
import io.github.zyngjaku.tmsbackend.services.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping(value = "/api")
public class VehicleController {
    private final VehicleService vehicleService;

    @Autowired
    public VehicleController(VehicleService vehicleService) {
        this.vehicleService = vehicleService;
    }

    @PreAuthorize("hasAnyRole('OWNER', 'FORWARDER')")
    @GetMapping(value = "/vehicles", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Vehicle> getVehicle(Principal principal) {
        return vehicleService.getVehicle(principal.getName());
    }

    @PreAuthorize("hasRole('OWNER')")
    @PostMapping(value = "/vehicles")
    public ResponseEntity createVehicle(@RequestBody VehicleRequest vehicleRequest) {
        return vehicleService.createVehicle(vehicleRequest);
    }

    @PreAuthorize("hasRole('OWNER')")
    @PutMapping(value = "/vehicles/{vehicleId}")
    public ResponseEntity createVehicle(@PathVariable Long vehicleId, @RequestBody VehicleRequest vehicleRequest) {
        return vehicleService.updateVehicle(vehicleId, vehicleRequest);
    }

    @PreAuthorize("hasRole('OWNER')")
    @DeleteMapping(value = "/vehicles/{vehicleId}")
    public ResponseEntity deleteVehicle(@PathVariable Long vehicleId) {
        return vehicleService.deleteVehicle(vehicleId);
    }
}
