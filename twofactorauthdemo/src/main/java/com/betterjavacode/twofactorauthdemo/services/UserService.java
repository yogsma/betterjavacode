package com.betterjavacode.twofactorauthdemo.services;

import com.betterjavacode.twofactorauthdemo.dtos.MfaTokenDto;
import com.betterjavacode.twofactorauthdemo.dtos.UserDto;
import com.betterjavacode.twofactorauthdemo.exceptions.InvalidTokenException;
import com.betterjavacode.twofactorauthdemo.exceptions.UnknownIdentifierException;
import com.betterjavacode.twofactorauthdemo.exceptions.UserAlreadyExistsException;
import com.betterjavacode.twofactorauthdemo.models.UserEntity;
import dev.samstevens.totp.exceptions.QrGenerationException;

public interface UserService
{
    void register(final UserDto user) throws UserAlreadyExistsException;
    boolean checkIfUserExist(final String email);
    void sendRegistrationConfirmationEmail(final UserEntity user);
    boolean verifyUser(final String token) throws InvalidTokenException;
    UserEntity getUserById(final String id) throws UnknownIdentifierException;
    MfaTokenDto mfaSetup(final String email) throws UnknownIdentifierException, QrGenerationException;
}
