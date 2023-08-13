package com.betterjavacode.twofactorauthdemo.services.impl;

import com.betterjavacode.twofactorauthdemo.models.SecureToken;
import com.betterjavacode.twofactorauthdemo.repositories.SecureTokenRepository;
import com.betterjavacode.twofactorauthdemo.services.SecureTokenService;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.keygen.BytesKeyGenerator;
import org.springframework.security.crypto.keygen.KeyGenerators;
import org.springframework.stereotype.Service;

import java.nio.charset.Charset;
import java.time.LocalDateTime;

@Service
public class SecureTokenServiceImpl implements SecureTokenService
{
    private static final BytesKeyGenerator DEFAULT_TOKEN_GENERATOR = KeyGenerators.secureRandom(15);
    private static final Charset US_ASCII = Charset.forName("US-ASCII");

    @Value("${jdj.secure.token.validity}")
    private int tokenValidityInSeconds;

    @Autowired
    SecureTokenRepository secureTokenRepository;

    @Override
    public SecureToken createSecureToken ()
    {
        String tokenValue = new String(Base64.encodeBase64URLSafe(DEFAULT_TOKEN_GENERATOR.generateKey()), US_ASCII);
        SecureToken secureToken = new SecureToken();
        secureToken.setToken(tokenValue);
        secureToken.setExpireAt(LocalDateTime.now().plusSeconds(getTokenValidityInSeconds()));
        this.saveSecureToken(secureToken);
        return secureToken;
    }

    @Override
    public void saveSecureToken (SecureToken secureToken)
    {
        secureTokenRepository.save(secureToken);
    }

    @Override
    public SecureToken findByToken (String token)
    {
        return secureTokenRepository.findByToken(token);
    }

    @Override
    public void removeToken (SecureToken secureToken)
    {
        secureTokenRepository.delete(secureToken);
    }

    @Override
    public void removeTokenByToken (String token)
    {
        secureTokenRepository.removeByToken(token);
    }

    public int getTokenValidityInSeconds() {
        return tokenValidityInSeconds;
    }
}
