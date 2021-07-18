package com.betterjavacode.kafkademo.model;


public class Company
{
    private String name;
    private String type;
    private int noOfemployees;
    private String ceo;
    private String city;
    private String state;

    public Company()
    {}

    public Company(String name, String type, int employees, String ceo, String city, String state)
    {
        this();
        this.name = name;
        this.type = type;
        this.noOfemployees = employees;
        this.ceo = ceo;
        this.city = city;
        this.state = state;
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

    public int getNoOfemployees ()
    {
        return noOfemployees;
    }

    public void setNoOfemployees (int noOfemployees)
    {
        this.noOfemployees = noOfemployees;
    }

    public String getCeo ()
    {
        return ceo;
    }

    public void setCeo (String ceo)
    {
        this.ceo = ceo;
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

    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        sb.append("name=").append(this.name)
                .append(",").append("type=").append(this.type).append(",")
                .append("noOfemployees=").append(this.noOfemployees).append(",")
                .append("ceo=").append(this.ceo).append(",")
                .append("city=").append(this.city).append(",")
                .append("state=").append(this.state);

        return sb.toString();
    }
}
