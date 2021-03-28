package com.betterjavacode.retrydemo.service;

import com.betterjavacode.retrydemo.daos.CompanyRepository;
import com.betterjavacode.retrydemo.dtos.CompanyDto;
import com.betterjavacode.retrydemo.models.Company;
import io.github.resilience4j.retry.Retry;
import io.github.resilience4j.retry.RetryConfig;
import io.github.resilience4j.retry.RetryRegistry;
import org.hibernate.exception.JDBCConnectionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.stereotype.Service;

import javax.xml.ws.WebServiceException;
import java.io.IOException;
import java.sql.SQLException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeoutException;
import java.util.function.Supplier;

import static java.time.temporal.ChronoUnit.SECONDS;

@Service
public class CompanyService
{
    private static final Logger LOGGER = LoggerFactory.getLogger(CompanyService.class);

    @Autowired
    CompanyRepository companyRepository;

    @Autowired
    RetryTemplate retryTemplate;



    @Retryable(value = SQLException.class, maxAttempts = 2, backoff = @Backoff(delay = 100))
    public List<CompanyDto> getAllCompanies()
    {
        List<Company> companies =  companyRepository.findAll();
        List<CompanyDto> companyDtos = new ArrayList<>();
        for(Company company : companies)
        {
            CompanyDto companyDto = new CompanyDto(company.getName(), company.getType(),
                    company.getCity(), company.getState(), company.getDescription());
            companyDtos.add(companyDto);
        }

        return companyDtos;
    }

    public CompanyDto getCompany(long id)
    {
        CompanyDto companyDto = retryTemplate.execute(rt -> {
           Company company = companyRepository.findById(id).get();
           CompanyDto localCompanyDto = new CompanyDto(company.getName(), company.getType(),
                   company.getCity(),
                   company.getState(), company.getDescription());
           return localCompanyDto;
        });

        return companyDto;
    }

    public List<CompanyDto> searchCompanyByName(String name)
    {
        LOGGER.info("Search for company = {}", name);

        RetryConfig retryConfig =
                RetryConfig.custom().maxAttempts(4).waitDuration(Duration.of(2, SECONDS)).build();

        RetryRegistry retryRegistry = RetryRegistry.of(retryConfig);

        Retry retryConfiguration = retryRegistry.retry("companySearchService", retryConfig);

        Supplier<List<Company>> companiesSupplier = () -> companyRepository.findAllByName(name);

        Supplier<List<Company>> retryingCompaniesSearch =
                Retry.decorateSupplier(retryConfiguration, companiesSupplier);

        List<CompanyDto> companyDtos = new ArrayList<>();
        List<Company> companies = retryingCompaniesSearch.get();
        LOGGER.info("Retrying..");
        for(Company company : companies)
        {
            CompanyDto companyDto = new CompanyDto(company.getName(), company.getType(),
                    company.getCity(), company.getState(), company.getDescription());
            companyDtos.add(companyDto);
        }

        return companyDtos;
    }
}
