package com.betterjavacode.retrydemo.daos;

import com.betterjavacode.retrydemo.models.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface CompanyRepository extends JpaRepository<Company, Long>
{

    List<Company> findAllByName (String name);
}
