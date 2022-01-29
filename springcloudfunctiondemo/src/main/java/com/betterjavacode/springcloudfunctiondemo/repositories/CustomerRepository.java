package com.betterjavacode.springcloudfunctiondemo.repositories;

import com.betterjavacode.springcloudfunctiondemo.models.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long>
{
}
