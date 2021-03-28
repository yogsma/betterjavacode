package com.betterjavacode.retrydemo.controllers;

import com.betterjavacode.retrydemo.dtos.CompanyDto;
import com.betterjavacode.retrydemo.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/betterjavacode/companies")
public class CompanyController
{
    @Autowired
    CompanyService companyService;

    @GetMapping
    public ResponseEntity<List<CompanyDto>> getAllCompanies()
    {
        List<CompanyDto> companyDtos = companyService.getAllCompanies();

        if(companyDtos.isEmpty())
        {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(companyDtos, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CompanyDto> getCompanyById(@PathVariable("id") long id)
    {
        CompanyDto companyDto = companyService.getCompany(id);
        if(companyDto == null)
        {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(companyDto, HttpStatus.OK);
    }

    @GetMapping("/")
    public ResponseEntity<List<CompanyDto>> searchCompanies(@RequestParam("name") String companyName)
    {
        List<CompanyDto> companyDtos = companyService.searchCompanyByName(companyName);
        if(companyDtos.isEmpty())
        {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(companyDtos, HttpStatus.OK);
    }
}
