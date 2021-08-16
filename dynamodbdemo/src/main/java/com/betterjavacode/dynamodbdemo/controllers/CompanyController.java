package com.betterjavacode.dynamodbdemo.controllers;

import com.betterjavacode.dynamodbdemo.models.Company;
import com.betterjavacode.dynamodbdemo.services.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("v1/betterjavacode/companies")
public class CompanyController
{
    @Autowired
    private CompanyService companyService;

    @GetMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<Company> getCompany(@PathVariable("id") String id)
    {

        Company company = companyService.getCompany(id);

        if(company == null)
        {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        else
        {
            return new ResponseEntity<>(company, HttpStatus.OK);
        }
    }

    @PostMapping()
    public Company createCompany(@RequestBody Company company)
    {
        Company companyCreated = companyService.createCompany(company);

        return company;
    }
}


