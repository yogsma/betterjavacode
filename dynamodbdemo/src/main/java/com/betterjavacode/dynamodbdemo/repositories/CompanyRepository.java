package com.betterjavacode.dynamodbdemo.repositories;

import com.betterjavacode.dynamodbdemo.models.Company;
import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;

@EnableScan
public interface CompanyRepository extends CrudRepository<Company, String>
{
}
