package com.betterjavacode.springcloudfunctiondemo.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name= "customer")
public class Customer
{
    @Id
    @GeneratedValue(generator = "UUID")
    private Long id;

    private String name;

    private int customerIdentifier;

    private String email;

    private String contactPerson;

    public Customer(String name, int customerIdentifier, String email, String contactPerson)
    {
        this.name = name;
        this.customerIdentifier = customerIdentifier;
        this.email = email;
        this.contactPerson = contactPerson;
    }

    public String getName ()
    {
        return name;
    }

    public void setName (String name)
    {
        this.name = name;
    }

    public int getCustomerIdentifier ()
    {
        return customerIdentifier;
    }

    public void setCustomerIdentifier (int customerIdentifier)
    {
        this.customerIdentifier = customerIdentifier;
    }

    public String getEmail ()
    {
        return email;
    }

    public void setEmail (String email)
    {
        this.email = email;
    }

    public String getContactPerson ()
    {
        return contactPerson;
    }

    public void setContactPerson (String contactPerson)
    {
        this.contactPerson = contactPerson;
    }

    public Long getId ()
    {
        return id;
    }

    public void setId (Long id)
    {
        this.id = id;
    }
}
