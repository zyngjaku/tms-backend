package io.github.zyngjaku.tmsbackend.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.github.zyngjaku.tmsbackend.dao.entity.Order;
import io.github.zyngjaku.tmsbackend.dao.entity.Vehicle;
import io.github.zyngjaku.tmsbackend.services.OrderService;
import io.github.zyngjaku.tmsbackend.services.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping(value = "/api")
public class OrderController {
    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PreAuthorize("hasAnyRole('OWNER', 'FORWARDER', 'DRIVER')")
    @GetMapping(value = "/orders", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Order> getOrders(Principal principal, @RequestParam(name = "from", required = false) String from) throws ParseException {
        SimpleDateFormat DateFor = new SimpleDateFormat("HH:mm dd-MM-yyyy");

        return (from == null)? orderService.getOrders(principal.getName()) : orderService.getOrders(principal.getName(), DateFor.parse(from));
    }

    @PreAuthorize("hasAnyRole('OWNER', 'FORWARDER')")
    @PostMapping(value = "/orders")
    public ResponseEntity createOrder(Principal principal, @RequestBody Order order) {

        return orderService.createOrder(principal.getName(), order);
    }

    @PreAuthorize("hasAnyRole('OWNER', 'FORWARDER')")
    @PutMapping(value = "/orders/{orderId}")
    public ResponseEntity updateOrder(Principal principal, @PathVariable Long orderId) {
        return null;
        //return orderService.updateOrder(principal.getName(), orderId);
    }

    @PreAuthorize("hasAnyRole('OWNER', 'FORWARDER')")
    @DeleteMapping(value = "/orders/{orderId}")
    public ResponseEntity deleteOrder(Principal principal, @PathVariable Long orderId) {
        return null;
        //return orderService.deleteOrder(principal.getName(), orderId);
    }
}
