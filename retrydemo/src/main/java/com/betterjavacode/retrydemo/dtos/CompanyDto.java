package com.betterjavacode.retrydemo.dtos;

import com.betterjavacode.retrydemo.models.Company;

public class CompanyDto
{
    private String name;
    private String type;
    private String city;
    private String state;
    private String description;

    public CompanyDto(String name, String type, String city, String state, String description)
    {
        this.name = name;
        this.type = type;
        this.city = city;
        this.state = state;
        this.description = description;
    }

    public Company convertToCompany(CompanyDto companyDto)
    {
        Company company = new Company();
        company.setName(companyDto.name);
        company.setType(companyDto.type);
        company.setCity(companyDto.city);
        company.setState(companyDto.state);
        company.setDescription(companyDto.description);

        return company;
    }

    public String getName ()
    {
        return name;
    }

    public void setName (String name)
    {
        this.name = name;
    }

    public String getType ()
    {
        return type;
    }

    public void setType (String type)
    {
        this.type = type;
    }

    public String getCity ()
    {
        return city;
    }

    public void setCity (String city)
    {
        this.city = city;
    }

    public String getState ()
    {
        return state;
    }

    public void setState (String state)
    {
        this.state = state;
    }

    public String getDescription ()
    {
        return description;
    }

    public void setDescription (String description)
    {
        this.description = description;
    }
}
