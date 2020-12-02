package io.github.zyngjaku.tmsbackend.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.zyngjaku.tmsbackend.dao.OrderRepo;
import io.github.zyngjaku.tmsbackend.dao.TransshipmentTerminalRepo;
import io.github.zyngjaku.tmsbackend.dao.VehicleRepo;
import io.github.zyngjaku.tmsbackend.dao.entity.*;
import io.github.zyngjaku.tmsbackend.request.VehicleRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

@Service
public class OrderService {
    private final OrderRepo orderRepo;
    private final TransshipmentTerminalRepo transshipmentTerminalRepo;
    private final UserService userService;

    @Autowired
    public OrderService(OrderRepo orderRepo, TransshipmentTerminalRepo transshipmentTerminalRepo, UserService userService) {
        this.orderRepo = orderRepo;
        this.transshipmentTerminalRepo = transshipmentTerminalRepo;
        this.userService = userService;
    }

    public List<Order> getOrders(String userMail) {
        User user = userService.getUserDetails(userMail);

        return orderRepo.findOrdersByCompany(user.getCompany());
    }

    public List<Order> getOrders(String userMail, Date from) {
        User user = userService.getUserDetails(userMail);

        return orderRepo.findOrdersByCompanyAndMaxArrivalTimeIsGreaterThan(user.getCompany(), from);
    }

    @Transactional
    public ResponseEntity createOrder(String userMail, Order order) {
        order.setCompany(userService.getUserDetails(userMail).getCompany());

        for (int i=0; i<order.getTransshipmentTerminalList().size(); i++) {
            order.getTransshipmentTerminalList().set(i, transshipmentTerminalRepo.save(order.getTransshipmentTerminalList().get(i)));
        }

        orderRepo.save(order);

        return new ResponseEntity(HttpStatus.CREATED);
    }
}
