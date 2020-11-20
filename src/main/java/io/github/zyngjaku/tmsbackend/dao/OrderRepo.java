package io.github.zyngjaku.tmsbackend.dao;

import io.github.zyngjaku.tmsbackend.dao.entity.Company;
import io.github.zyngjaku.tmsbackend.dao.entity.Order;
import io.github.zyngjaku.tmsbackend.dao.entity.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface OrderRepo extends JpaRepository<Order, Long> {
    List<Order> findOrdersByCompany(Company company);
    List<Order> findOrdersByCompanyAndMaxArrivalTimeIsGreaterThan(Company company, Date date);
}
