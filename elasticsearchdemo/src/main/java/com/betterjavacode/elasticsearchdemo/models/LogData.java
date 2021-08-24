package com.betterjavacode.elasticsearchdemo.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.Date;

@Document(indexName = "logdataindex")
public class LogData
{
    @Id
    private String id;

    @Field(type = FieldType.Text, name = "host")
    private String host;

    @Field(type = FieldType.Date, name = "date")
    private Date date;

    @Field(type = FieldType.Text, name = "message")
    private String message;

    @Field(type = FieldType.Double, name = "size")
    private double size;

    @Field(type = FieldType.Text, name = "status")
    private String status;

    public String getId ()
    {
        return id;
    }

    public void setId (String id)
    {
        this.id = id;
    }

    public String getHost ()
    {
        return host;
    }

    public void setHost (String host)
    {
        this.host = host;
    }

    public Date getDate ()
    {
        return date;
    }

    public void setDate (Date date)
    {
        this.date = date;
    }

    public String getMessage ()
    {
        return message;
    }

    public void setMessage (String message)
    {
        this.message = message;
    }

    public double getSize ()
    {
        return size;
    }

    public void setSize (double size)
    {
        this.size = size;
    }

    public String getStatus ()
    {
        return status;
    }

    public void setStatus (String status)
    {
        this.status = status;
    }
}
