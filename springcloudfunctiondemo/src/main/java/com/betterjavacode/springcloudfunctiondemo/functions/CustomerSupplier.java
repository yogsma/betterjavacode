package com.betterjavacode.springcloudfunctiondemo.functions;

import com.betterjavacode.springcloudfunctiondemo.models.Customer;
import com.betterjavacode.springcloudfunctiondemo.repositories.CustomerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Supplier;

@Component
public class CustomerSupplier implements Supplier<Customer>
{
    public static final Logger LOGGER = LoggerFactory.getLogger(CustomerSupplier.class);

    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public Customer get ()
    {
        List<Customer> customers = customerRepository.findAll();
        LOGGER.info("Getting the customer of our choice - ", customers);
        return customers.get(0);
    }
}
