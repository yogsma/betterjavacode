package com.betterjavacode.modelmapperdemo.service;

import com.betterjavacode.modelmapperdemo.models.Order;

import java.util.List;

public interface IOrderService
{
    Order createOrder(Order order);

    List<Order> getAllOrdersForCustomer(long customerId);
}
