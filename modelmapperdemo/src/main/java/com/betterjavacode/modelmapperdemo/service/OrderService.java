package com.betterjavacode.modelmapperdemo.service;


import com.betterjavacode.modelmapperdemo.models.Address;
import com.betterjavacode.modelmapperdemo.models.Customer;
import com.betterjavacode.modelmapperdemo.models.Order;
import com.betterjavacode.modelmapperdemo.repositories.AddressRepository;
import com.betterjavacode.modelmapperdemo.repositories.CustomerRepository;
import com.betterjavacode.modelmapperdemo.repositories.OrderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderService implements IOrderService
{
    private static final Logger LOGGER = LoggerFactory.getLogger(OrderService.class);

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    AddressRepository addressRepository;

    @Override
    public Order createOrder (Order order)
    {
        // Validate Order Details
        Customer customer = order.getCustomer();
        Customer customerCreated = null;
        if(customer.getId() == 0)
        {
            Customer customerExists = customerRepository.findCustomerByEmail(customer.getEmail());
            if(customerExists != null)
            {
                order.setCustomer(customerExists);
            }
            else
            {
                customerCreated = customerRepository.save(customer);
                order.setCustomer(customerCreated);
            }
        }

        Address address = order.getAddress();
        Address addressCreated = null;
        if(address.getId() == 0)
        {
            Address addressExists =
                    addressRepository.findByStreetAndCityAndState(address.getStreet(),
                            address.getCity(), address.getState());
            if(addressExists != null)
            {
                order.setAddress(addressExists);
            }
            else
            {
                addressCreated = addressRepository.save(address);
                order.setAddress(addressCreated);
            }
        }

        return orderRepository.save(order);
    }

    @Override
    public List<Order> getAllOrdersForCustomer (long customerId)
    {
        Optional<Customer> customerOptional = customerRepository.findById(customerId);
        if(customerOptional.isPresent())
        {
            Customer customer = customerOptional.get();
            List<Order> orders = orderRepository.findAllByCustomer(customer);
            return orders;
        }
        else
        {
            LOGGER.info("Customer with this id = {} not found", customerId);
            return null;
        }
    }
}
