package com.betterjavacode.sss.todolist.repositories;

import com.betterjavacode.sss.todolist.models.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsersRepository extends JpaRepository<Users, Integer>
{
    Users findUserByEmail (String email);
}
