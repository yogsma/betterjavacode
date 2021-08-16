package com.betterjavacode.dynamodbdemo.services;

import com.betterjavacode.dynamodbdemo.models.Company;
import com.betterjavacode.dynamodbdemo.repositories.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CompanyService
{
    @Autowired
    private CompanyRepository companyRepository;

    public Company createCompany(final Company company)
    {
        Company createdCompany = companyRepository.save(company);
        return createdCompany;
    }

    public List<Company> getAllCompanies()
    {
        return (List<Company>) companyRepository.findAll();
    }

    public Company getCompany(String companyId)
    {
        Optional<Company> companyOptional = companyRepository.findById(companyId);

        if(companyOptional.isPresent())
        {
            return companyOptional.get();
        }
        else
        {
            return null;
        }
    }
}
