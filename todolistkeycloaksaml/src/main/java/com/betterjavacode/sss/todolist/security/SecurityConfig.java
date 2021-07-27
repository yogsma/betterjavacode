package com.betterjavacode.sss.todolist.security;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.saml2.credentials.Saml2X509Credential;
import org.springframework.security.saml2.provider.service.registration.InMemoryRelyingPartyRegistrationRepository;
import org.springframework.security.saml2.provider.service.registration.RelyingPartyRegistration;
import org.springframework.security.saml2.provider.service.registration.RelyingPartyRegistrationRepository;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;

import java.io.InputStream;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

import static org.springframework.security.saml2.credentials.Saml2X509Credential.Saml2X509CredentialType.VERIFICATION;


@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter
{
    @Autowired
    public CustomLogoutSuccessHandler logoutSuccessHandler;

    @Bean
    public RelyingPartyRegistrationRepository relyingPartyRegistrationRepository() throws CertificateException
    {
        final String idpEntityId = "http://localhost:8180/auth/realms/ToDoListSAMLApp";
        final String webSSOEndpoint = "http://localhost:8180/auth/realms/ToDoListSAMLApp/protocol/saml";
        final String registrationId = "keycloak";
        final String localEntityIdTemplate = "{baseUrl}/saml2/service-provider-metadata" +
                "/{registrationId}";
        final String acsUrlTemplate = "{baseUrl}/login/saml2/sso/{registrationId}";


        Saml2X509Credential idpVerificationCertificate;
        try (InputStream pub = new ClassPathResource("credentials/idp.cer").getInputStream())
        {
            X509Certificate c = (X509Certificate) CertificateFactory.getInstance("X.509").generateCertificate(pub);
            idpVerificationCertificate = new Saml2X509Credential(c, VERIFICATION);
        }
        catch (Exception e)
        {
            throw new RuntimeException(e);
        }

        RelyingPartyRegistration relyingPartyRegistration = RelyingPartyRegistration
                .withRegistrationId(registrationId)
                .providerDetails(config -> config.entityId(idpEntityId))
                .providerDetails(config -> config.webSsoUrl(webSSOEndpoint))
                .providerDetails(config -> config.signAuthNRequest(false))
                .credentials(c -> c.add(idpVerificationCertificate))
                .assertionConsumerServiceUrlTemplate(acsUrlTemplate)
                .build();

        return new InMemoryRelyingPartyRegistrationRepository(relyingPartyRegistration);
    }


    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception
    {
        httpSecurity.authorizeRequests()
                .antMatchers("/js/**","/css/**","/img/**").permitAll()
                .antMatchers("/signup","/forgotpassword").permitAll()
                .antMatchers("/saml/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login").permitAll()
                .and()
                .saml2Login(Customizer.withDefaults()).exceptionHandling(exception -> exception.authenticationEntryPoint(entryPoint()))
                .logout()
                .logoutUrl("/logout")
                .logoutSuccessHandler(logoutSuccessHandler)
                .deleteCookies("JSESSIONID")
                .permitAll();
    }

    public AuthenticationEntryPoint entryPoint() {
        AuthenticationEntryPoint entryPoint = new LoginUrlAuthenticationEntryPoint("/");
        return (request, response, exception) -> {
            String registrationId = "keycloak";
            response.sendRedirect("/saml2/authenticate/" + registrationId);
        };
    }

    @Bean
    public static PasswordEncoder passwordEncoder()
    {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

}
