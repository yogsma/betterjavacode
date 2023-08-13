package com.betterjavacode.twofactorauthdemo.config;

import com.betterjavacode.twofactorauthdemo.config.authentication.CustomAuthenticationProvider;
import com.betterjavacode.twofactorauthdemo.config.handlers.CustomAccessDeniedHandler;
import com.betterjavacode.twofactorauthdemo.config.handlers.CustomSuccessHandler;
import com.betterjavacode.twofactorauthdemo.config.handlers.LoginAuthenticationFailureHandler;
import com.betterjavacode.twofactorauthdemo.config.web.CustomWebAuthenticationDetailsSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.annotation.Resource;
import javax.sql.DataSource;


@Configuration
public class SecurityConfiguration
{

    @Resource
    UserDetailsService userDetailsService;

    @Resource
    PasswordEncoder passwordEncoder;

    @Resource
    private DataSource dataSource;

    @Resource
    private CustomAuthenticationProvider customAuthenticationProvider;

    @Resource
    private CustomWebAuthenticationDetailsSource authenticationDetailsSource;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/login", "/register")
                .permitAll()
                .antMatchers("/home/**") .hasAnyAuthority("CUSTOMER", "ADMIN")
                .and()
                .exceptionHandling().accessDeniedHandler(accessDeniedHandler())
                .and()
                //Setting HTTPS for my account
                //.requiresChannel().anyRequest().requiresSecure()
                //.and()
                // Remember me configurations
                .rememberMe().tokenRepository(persistentTokenRepository())
                .rememberMeCookieDomain("domain")
                .rememberMeCookieName("custom-remember-me-cookie")
                .userDetailsService(this.userDetailsService)
                .tokenValiditySeconds(2000)
                .useSecureCookie(true)

                //Login configurations
                .and()
                .formLogin()
                .defaultSuccessUrl("/home")
                .loginPage("/login")
                .failureUrl("/login?error=true")
                .successHandler(successHandler())
                .failureHandler(failureHandler())
                .authenticationDetailsSource(authenticationDetailsSource)
                //logout configurations
                .and()
                .logout().deleteCookies("dummyCookie")
                .logoutSuccessUrl("/login");

        http.authorizeRequests().antMatchers("/admin/**").hasAuthority("ADMIN");

        return http.build();

    }

    @Bean
    public CustomSuccessHandler successHandler(){
        return new CustomSuccessHandler();
    }

    @Bean
    public AccessDeniedHandler accessDeniedHandler(){
        return new CustomAccessDeniedHandler();
    }

    @Bean
    public LoginAuthenticationFailureHandler failureHandler(){
        LoginAuthenticationFailureHandler failureHandler = new LoginAuthenticationFailureHandler();
        failureHandler.setDefaultFailureUrl("/login?error=true");
        return failureHandler;
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web
                .ignoring().antMatchers("/resources/**", "/static/**");
    }

    /* @Bean
    protected void configure(AuthenticationManagerBuilder auth){
        auth.authenticationProvider(customAuthenticationProvider);
    } */

    @Bean
    public PersistentTokenRepository persistentTokenRepository() {
        JdbcTokenRepositoryImpl db = new JdbcTokenRepositoryImpl();
        db.setDataSource(dataSource);
        return db;
    }
}
