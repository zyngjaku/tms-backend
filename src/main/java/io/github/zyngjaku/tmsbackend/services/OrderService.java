package io.github.zyngjaku.tmsbackend.services;

import io.github.zyngjaku.tmsbackend.dao.OrderRepo;
import io.github.zyngjaku.tmsbackend.dao.VehicleRepo;
import io.github.zyngjaku.tmsbackend.dao.entity.Order;
import io.github.zyngjaku.tmsbackend.dao.entity.User;
import io.github.zyngjaku.tmsbackend.dao.entity.Vehicle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class OrderService {
    private final OrderRepo orderRepo;
    private final UserService userService;

    @Autowired
    public OrderService(OrderRepo orderRepo, UserService userService) {
        this.orderRepo = orderRepo;
        this.userService = userService;
    }

    public List<Order> getOrders(String userMail) {
        User user = userService.getUserDetails(userMail);

        return orderRepo.findOrdersByCompany(user.getCompany());
    }

    public List<Order> getFutureOrders(String userMail) {
        User user = userService.getUserDetails(userMail);

        return orderRepo.findOrdersByCompanyAndMaxArrivalTimeIsGreaterThan(user.getCompany(), new Date());
    }
}
