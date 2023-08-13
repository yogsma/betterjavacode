package com.betterjavacode.twofactorauthdemo.config.authentication;

import com.betterjavacode.twofactorauthdemo.config.handlers.CustomAccessDeniedHandler;
import com.betterjavacode.twofactorauthdemo.config.web.CustomWebAuthenticationDetails;
import com.betterjavacode.twofactorauthdemo.managers.MfaTokenManager;
import com.betterjavacode.twofactorauthdemo.models.UserEntity;
import com.betterjavacode.twofactorauthdemo.userdetail.CustomUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class CustomAuthenticationProvider extends DaoAuthenticationProvider
{

    private static final Logger LOG = LoggerFactory.getLogger(CustomAuthenticationProvider.class);
    @Resource
    private MfaTokenManager mfaTokenManager;

    @Resource
    private PasswordEncoder passwordEncoder;

    @Autowired
    public CustomAuthenticationProvider(UserDetailsService userDetailsService) {
        super.setUserDetailsService(userDetailsService);
    }

    protected void additionalAuthenticationChecks(UserDetails userDetails,
                                                  UsernamePasswordAuthenticationToken authentication)
            throws AuthenticationException
    {

        super.additionalAuthenticationChecks(userDetails, authentication);

        //token check
        CustomWebAuthenticationDetails authenticationDetails = (CustomWebAuthenticationDetails) authentication.getDetails();
        CustomUser user = (CustomUser) userDetails;
        String mfaToken = authenticationDetails.getToken();

        LOG.info("tokan is ", mfaToken);

        if(!mfaTokenManager.verifyTotp(mfaToken,user.getSecret())){
            throw new BadCredentialsException(messages.getMessage(
                    "AbstractUserDetailsAuthenticationProvider.badCredentials",
                    "Bad credentials"));
        }
    }
}
