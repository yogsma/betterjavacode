package com.betterjavacode.twofactorauthdemo.services;

import com.betterjavacode.twofactorauthdemo.models.SecureToken;

public interface SecureTokenService
{
    SecureToken createSecureToken();
    void saveSecureToken(final SecureToken secureToken);
    SecureToken findByToken(final String token);
    void removeToken(final SecureToken secureToken);
    void removeTokenByToken(final String token);
}
