package com.betterjavacode.springcloudfunctiondemo.functions;

import com.betterjavacode.springcloudfunctiondemo.models.Customer;
import com.betterjavacode.springcloudfunctiondemo.repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.function.Function;

@Component
public class CustomerFunction implements Function<Long, Customer>
{
    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public Customer apply (Long s)
    {
        Optional<Customer> customerOptional = customerRepository.findById(s);
        if (customerOptional.isPresent()) {
            return customerOptional.get();
        }
        return null;
    }
}
