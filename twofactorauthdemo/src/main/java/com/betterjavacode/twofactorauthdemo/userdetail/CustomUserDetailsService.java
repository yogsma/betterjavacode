package com.betterjavacode.twofactorauthdemo.userdetail;


import com.betterjavacode.twofactorauthdemo.config.authentication.CustomAuthenticationProvider;
import com.betterjavacode.twofactorauthdemo.models.UserEntity;
import com.betterjavacode.twofactorauthdemo.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

@Service("userDetailsService")
@Transactional
public class CustomUserDetailsService implements UserDetailsService
{

    private static final Logger LOG = LoggerFactory.getLogger(CustomUserDetailsService.class);
    @Resource
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername (String email) throws UsernameNotFoundException
    {
        final UserEntity customer = userRepository.findByEmail(email);
        if (customer == null) {
            throw new UsernameNotFoundException(email);
        }
        LOG.info("Getting User", customer);
        boolean enabled = !customer.isAccountVerified(); // we can use this in case we want to activate account after customer verified the account
        CustomUser user = CustomUser.CustomUserBuilder.aCustomUser().
                withUsername(customer.getEmail())
                .withPassword(customer.getPassword())
                .withAuthorities(getAuthorities(customer))
                .withSecret(customer.getSecret())
                .withAccountNonLocked(false)
                .build();

        return user;
    }

    private Collection<GrantedAuthority> getAuthorities(UserEntity user){
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ADMIN"));
        return authorities;
    }

}
