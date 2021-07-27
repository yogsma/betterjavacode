package com.betterjavacode.sss.todolist.models;

import javax.persistence.*;
import java.io.Serializable;

@Entity(name="Users")
@Table(name="users")
public class Users implements Serializable
{

    private static final long serialVersionUID = 1477353835642681020L;

    public Users()
    {

    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="user_id", nullable=false)
    private int id;

    @Column(name="firstname", length=100, nullable=false)
    private String firstName;

    @Column(name="lastname", length=100, nullable=false)
    private String lastName;

    @Column(name="email", length=255, nullable=false)
    private String email;

    @Column(name="password", nullable=false)
    private String password;

    @Column(name="role", length=45)
    private String role;

    @Column(name="enabled")
    private boolean enabled;

    public int getId ()
    {
        return id;
    }

    public void setId (int id)
    {
        this.id = id;
    }

    public String getFirstName ()
    {
        return firstName;
    }

    public void setFirstName (String firstName)
    {
        this.firstName = firstName;
    }

    public String getLastName ()
    {
        return lastName;
    }

    public void setLastName (String lastName)
    {
        this.lastName = lastName;
    }

    public String getEmail ()
    {
        return email;
    }

    public void setEmail (String email)
    {
        this.email = email;
    }

    public String getPassword ()
    {
        return password;
    }

    public void setPassword (String password)
    {
        this.password = password;
    }

    public String getRole ()
    {
        return role;
    }

    public void setRole (String role)
    {
        this.role = role;
    }

    public boolean isEnabled ()
    {
        return enabled;
    }

    public void setEnabled (boolean enabled)
    {
        this.enabled = enabled;
    }
}
