package com.betterjavacode.modelmapperdemo.models;

import javax.persistence.*;
import java.io.Serializable;

@Entity(name = "Order")
@Table(name = "orders")
public class Order implements Serializable
{
    private static final long serialVersionUID = 7385741327704693623L;

    public Order()
    {

    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private long id;

    @Column(name ="order_item")
    private String orderItem;

    @Column(name = "description")
    private String description;


    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;


    @ManyToOne
    @JoinColumn(name = "address_id")
    private Address address;

    public long getId ()
    {
        return id;
    }

    public void setId (long id)
    {
        this.id = id;
    }

    public String getOrderItem ()
    {
        return orderItem;
    }

    public void setOrderItem (String orderItem)
    {
        this.orderItem = orderItem;
    }

    public String getDescription ()
    {
        return description;
    }

    public void setDescription (String description)
    {
        this.description = description;
    }

    public Customer getCustomer ()
    {
        return customer;
    }

    public void setCustomer (Customer customer)
    {
        this.customer = customer;
    }

    public Address getAddress ()
    {
        return address;
    }

    public void setAddress (Address address)
    {
        this.address = address;
    }
}
