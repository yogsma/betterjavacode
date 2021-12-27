package com.betterjavacode.loggingdemo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/v1/companies")
public class CompanyController
{
    private static final Logger LOGGER = LoggerFactory.getLogger(CompanyController.class);
    @GetMapping
    public List<String> getAllCompanies()
    {
        LOGGER.debug("Getting all companies");

        List<String> result = new ArrayList<>();

        result.add("Google");
        result.add("Alphabet");
        result.add("SpaceX");

        LOGGER.debug("Got all companies - {}", result);

        return result;
    }
}
