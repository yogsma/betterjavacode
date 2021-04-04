package com.betterjavacode.modelmapperdemo.controllers;

import com.betterjavacode.modelmapperdemo.dtos.OrderDTO;
import com.betterjavacode.modelmapperdemo.models.Order;
import com.betterjavacode.modelmapperdemo.service.IOrderService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/v1/betterjavacode/orders")
public class OrderController
{
    private static final Logger LOGGER = LoggerFactory.getLogger(OrderController.class);

    @Autowired
    private IOrderService orderService;

    @Autowired
    private ModelMapper modelMapper;

    @PostMapping
    public OrderDTO createOrder(@RequestBody OrderDTO orderDTO)
    {
        Order order = convertToEntity(orderDTO);
        Order orderCreated = orderService.createOrder(order);

        return convertToDTO(orderCreated);
    }

    @GetMapping("/{customerId}")
    public List<OrderDTO> getAllOrders(@PathVariable("customerId") long customerId)
    {
        List<Order> orderList = orderService.getAllOrdersForCustomer(customerId);
        List<OrderDTO> orderDTOs = new ArrayList<>();
        for(Order order : orderList)
        {
            orderDTOs.add(convertToDTO(order));
        }
        return orderDTOs;
    }


    private Order convertToEntity (OrderDTO orderDTO)
    {
        LOGGER.info("DTO Object = {} ", orderDTO);

        Order order = modelMapper.map(orderDTO, Order.class);

        return order;
    }

    private OrderDTO convertToDTO (Order order)
    {
        OrderDTO orderDTO = modelMapper.map(order, OrderDTO.class);
        return orderDTO;
    }
}
