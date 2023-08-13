package com.betterjavacode.twofactorauthdemo.services.impl;

import com.betterjavacode.twofactorauthdemo.context.AccountVerificationEmailContext;
import com.betterjavacode.twofactorauthdemo.dtos.MfaTokenDto;
import com.betterjavacode.twofactorauthdemo.dtos.UserDto;
import com.betterjavacode.twofactorauthdemo.exceptions.InvalidTokenException;
import com.betterjavacode.twofactorauthdemo.exceptions.UnknownIdentifierException;
import com.betterjavacode.twofactorauthdemo.exceptions.UserAlreadyExistsException;
import com.betterjavacode.twofactorauthdemo.managers.MfaTokenManager;
import com.betterjavacode.twofactorauthdemo.models.SecureToken;
import com.betterjavacode.twofactorauthdemo.models.UserEntity;
import com.betterjavacode.twofactorauthdemo.repositories.SecureTokenRepository;
import com.betterjavacode.twofactorauthdemo.repositories.UserRepository;
import com.betterjavacode.twofactorauthdemo.services.EmailService;
import com.betterjavacode.twofactorauthdemo.services.SecureTokenService;
import com.betterjavacode.twofactorauthdemo.services.UserService;
import dev.samstevens.totp.exceptions.QrGenerationException;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Objects;

@Service("userService")
public class UserServiceImpl implements UserService
{
    @Resource
    private UserRepository userRepository;

    @Resource
    private PasswordEncoder passwordEncoder;

    @Resource
    private EmailService emailService;

    @Resource
    private SecureTokenService secureTokenService;

    @Resource
    SecureTokenRepository secureTokenRepository;


    @Value("${site.base.url.http}")
    private String baseURL;

    @Resource
    private MfaTokenManager mfaTokenManager;

    @Override
    public void register (UserDto user) throws UserAlreadyExistsException
    {
        if(checkIfUserExist(user.getEmail())){
            throw new UserAlreadyExistsException("User already exists for this email");
        }
        UserEntity userEntity = new UserEntity();
        BeanUtils.copyProperties(user, userEntity);
        encodePassword(user, userEntity);
        userEntity.setSecret(mfaTokenManager.generateSecretKey());
        userEntity.setMfaEnabled(true);
        userRepository.save(userEntity);
        sendRegistrationConfirmationEmail(userEntity);
    }

    @Override
    public boolean checkIfUserExist (String email)
    {
        return userRepository.findByEmail(email)!=null ? true : false;
    }

    @Override
    public void sendRegistrationConfirmationEmail (UserEntity user)
    {
        SecureToken secureToken= secureTokenService.createSecureToken();
        secureToken.setUser(user);
        secureTokenRepository.save(secureToken);
        AccountVerificationEmailContext emailContext = new AccountVerificationEmailContext();
        emailContext.init(user);
        emailContext.setToken(secureToken.getToken());
        emailContext.buildVerificationUrl(baseURL, secureToken.getToken());
        try {
            emailService.sendMail(emailContext);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean verifyUser (String token) throws InvalidTokenException
    {
        SecureToken secureToken = secureTokenService.findByToken(token);
        if(Objects.isNull(secureToken) || !StringUtils.equals(token, secureToken.getToken()) || secureToken.isExpired()){
            throw new InvalidTokenException("Token is not valid");
        }
        UserEntity user = userRepository.getReferenceById(secureToken.getUser().getId());
        if(Objects.isNull(user)){
            return false;
        }
        user.setAccountVerified(true);
        userRepository.save(user); // let's same user details

        // we don't need invalid password now
        secureTokenService.removeToken(secureToken);
        return true;
    }

    @Override
    public UserEntity getUserById (String id) throws UnknownIdentifierException
    {
        UserEntity user= userRepository.findByEmail(id);
        if(user == null || BooleanUtils.isFalse(user.isAccountVerified())){
            // we will ignore in case account is not verified or account does not exists
            throw new UnknownIdentifierException("unable to find account or account is not active");
        }
        return user;
    }

    @Override
    public MfaTokenDto mfaSetup (String email) throws UnknownIdentifierException,
            QrGenerationException
    {
        UserEntity user= userRepository.findByEmail(email);
        if(user == null ){
            throw new UnknownIdentifierException("unable to find account or account is not active");
        }
        return new MfaTokenDto(mfaTokenManager.getQRCode( user.getSecret()), user.getSecret());
    }

    private void encodePassword(UserDto source, UserEntity target){
        target.setPassword(passwordEncoder.encode(source.getPassword()));
    }
}
