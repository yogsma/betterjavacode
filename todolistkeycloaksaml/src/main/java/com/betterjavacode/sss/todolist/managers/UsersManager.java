package com.betterjavacode.sss.todolist.managers;

import com.betterjavacode.sss.todolist.dtos.UserDto;
import com.betterjavacode.sss.todolist.models.Users;
import com.betterjavacode.sss.todolist.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UsersManager
{

    @Autowired
    private UsersRepository usersRepository;

    public UserDto createUser(UserDto userDto)
    {
        if(userDto == null)
        {
            throw new RuntimeException("User can not be null");
        }
        Users user = new Users();
        user.setEmail(userDto.getEmail());
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setPassword(userDto.getPassword());
        user.setRole(userDto.getRole());
        user.setEnabled(userDto.isEnabled());
        Users savedUser = usersRepository.save(user);

        return userDto;
    }
}
