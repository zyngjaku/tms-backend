package io.github.zyngjaku.tmsbackend.controller;

import io.github.zyngjaku.tmsbackend.dao.entity.Order;
import io.github.zyngjaku.tmsbackend.dao.entity.Vehicle;
import io.github.zyngjaku.tmsbackend.services.OrderService;
import io.github.zyngjaku.tmsbackend.services.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping(value = "/api")
public class OrderController {
    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PreAuthorize("hasAnyRole('OWNER', 'FORWARDER')")
    @RequestMapping(value = "/orders", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Order> getOrders(Principal principal) {
        return orderService.getOrders(principal.getName());
    }

    @PreAuthorize("hasAnyRole('OWNER', 'FORWARDER')")
    @RequestMapping(value = "/orders/future", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Order> getFutureOrders(Principal principal) {
        return orderService.getFutureOrders(principal.getName());
    }
}
