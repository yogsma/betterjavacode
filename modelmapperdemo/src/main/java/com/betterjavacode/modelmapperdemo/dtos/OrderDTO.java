package com.betterjavacode.modelmapperdemo.dtos;

public class OrderDTO
{
    String orderItem;
    String orderDescription;
    String customerFirstName;
    String customerLastName;
    String customerEmail;
    String streetAddress;
    String cityAddress;
    String stateAddress;
    String countryAddress;
    int zipcodeAddress;

    public String getOrderItem ()
    {
        return orderItem;
    }

    public void setOrderItem (String orderItem)
    {
        this.orderItem = orderItem;
    }

    public String getOrderDescription ()
    {
        return orderDescription;
    }

    public void setOrderDescription (String orderDescription)
    {
        this.orderDescription = orderDescription;
    }

    public String getCustomerFirstName ()
    {
        return customerFirstName;
    }

    public void setCustomerFirstName (String customerFirstName)
    {
        this.customerFirstName = customerFirstName;
    }

    public String getCustomerLastName ()
    {
        return customerLastName;
    }

    public void setCustomerLastName (String customerLastName)
    {
        this.customerLastName = customerLastName;
    }

    public String getCustomerEmail ()
    {
        return customerEmail;
    }

    public void setCustomerEmail (String customerEmail)
    {
        this.customerEmail = customerEmail;
    }

    public String getStreetAddress ()
    {
        return streetAddress;
    }

    public void setStreetAddress (String streetAddress)
    {
        this.streetAddress = streetAddress;
    }

    public String getCityAddress ()
    {
        return cityAddress;
    }

    public void setCityAddress (String cityAddress)
    {
        this.cityAddress = cityAddress;
    }

    public String getStateAddress ()
    {
        return stateAddress;
    }

    public void setStateAddress (String stateAddress)
    {
        this.stateAddress = stateAddress;
    }

    public String getCountryAddress ()
    {
        return countryAddress;
    }

    public void setCountryAddress (String countryAddress)
    {
        this.countryAddress = countryAddress;
    }

    public int getZipcodeAddress ()
    {
        return zipcodeAddress;
    }

    public void setZipcodeAddress (int zipcodeAddress)
    {
        this.zipcodeAddress = zipcodeAddress;
    }

    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        return sb.append("orderItem").append("=").append(this.orderItem).append(",")
                .append("orderDescription").append("=").append(this.orderDescription).append(",")
                .append("customerFirstName").append("=").append(this.customerFirstName).append(",")
                .append("customerLastName").append("=").append(this.customerLastName).append(",")
                .append("customerEmail").append("=").append(this.customerEmail).append(",")
                .append("street").append("=").append(this.streetAddress).append(",")
                .append("city").append("=").append(this.cityAddress).append(",")
                .append("state").append("=").append(this.stateAddress).append(",")
                .append("country").append("=").append(this.countryAddress)
                .toString();

    }
}
