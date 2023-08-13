package com.betterjavacode.twofactorauthdemo.repositories;

import com.betterjavacode.twofactorauthdemo.models.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long>
{
    UserEntity findByEmail(String email);
}
