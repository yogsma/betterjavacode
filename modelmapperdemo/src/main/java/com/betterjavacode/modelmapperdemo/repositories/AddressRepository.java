package com.betterjavacode.modelmapperdemo.repositories;

import com.betterjavacode.modelmapperdemo.models.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long>
{
    Address findByStreetAndCityAndState(@Param("street") String street,
                                            @Param("city") String city,
                                            @Param("state") String state);

}
