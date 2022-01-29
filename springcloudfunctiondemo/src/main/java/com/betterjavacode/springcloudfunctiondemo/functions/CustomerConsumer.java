package com.betterjavacode.springcloudfunctiondemo.functions;

import com.betterjavacode.springcloudfunctiondemo.models.Customer;
import com.betterjavacode.springcloudfunctiondemo.repositories.CustomerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.Map;
import java.util.function.Consumer;

@Component
public class CustomerConsumer implements Consumer<Map<String, String>>
{
    public static final Logger LOGGER = LoggerFactory.getLogger(CustomerConsumer.class);

    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public void accept (Map<String, String> map)
    {
        LOGGER.info("Creating the customer", map);
        Customer customer = new Customer(map.get("name"), Integer.parseInt(map.get(
                "customerIdentifier")), map.get("email"), map.get("contactPerson"));
        customerRepository.save(customer);
    }

}
