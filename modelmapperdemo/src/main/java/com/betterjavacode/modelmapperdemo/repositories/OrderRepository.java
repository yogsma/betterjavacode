package com.betterjavacode.modelmapperdemo.repositories;

import com.betterjavacode.modelmapperdemo.models.Customer;
import com.betterjavacode.modelmapperdemo.models.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long>
{
    List<Order> findAllByCustomer (Customer customer);
}
