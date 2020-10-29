package mk.ukim.finki.emt.consultations.usermanagement.config.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import mk.ukim.finki.emt.consultations.usermanagement.domain.application.service.impl.UserDetailsServiceImpl;
import mk.ukim.finki.emt.consultations.usermanagement.domain.model.User;
import org.jasig.cas.client.session.SingleSignOutFilter;
import org.jasig.cas.client.validation.Cas30ServiceTicketValidator;
import org.jasig.cas.client.validation.TicketValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.cas.ServiceProperties;
import org.springframework.security.cas.authentication.CasAuthenticationProvider;
import org.springframework.security.cas.web.CasAuthenticationFilter;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

import static mk.ukim.finki.emt.consultations.sharedkernel.config.security.SecurityConstants.*;
import static mk.ukim.finki.emt.consultations.sharedkernel.config.security.SecurityConstants.IDENTIFIER_HEADER;

@Configuration
public class CasConfig {

    private final UserDetailsServiceImpl userDetailsService;

    public CasConfig(UserDetailsServiceImpl userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public CasAuthenticationFilter casAuthenticationFilter(AuthenticationManager authenticationManager,
                                                           ServiceProperties serviceProperties) throws Exception {
        CasAuthenticationFilter filter = new CasAuthenticationFilter();
        filter.setAuthenticationManager(authenticationManager);
        filter.setServiceProperties(serviceProperties);
        filter.setAuthenticationSuccessHandler(authenticationSuccessHandler());
        return filter;
    }

    @Bean
    public ServiceProperties serviceProperties() {
        ServiceProperties serviceProperties = new ServiceProperties();
        serviceProperties.setService("http://localhost:8083/login/cas");
        serviceProperties.setSendRenew(false);
        return serviceProperties;
    }

    @Bean
    public TicketValidator ticketValidator() {
        return new Cas30ServiceTicketValidator("https://localhost:8443");
    }

    @Bean
    public CasAuthenticationProvider casAuthenticationProvider(TicketValidator ticketValidator,
                                                               ServiceProperties serviceProperties) {
        CasAuthenticationProvider provider = new CasAuthenticationProvider();
        provider.setServiceProperties(serviceProperties);
        provider.setTicketValidator(ticketValidator);
        provider.setUserDetailsService(userDetailsService);
        provider.setKey("CAS_PROVIDER_LOCALHOST_8083");
        return provider;
    }

    @Bean
    public AuthenticationSuccessHandler authenticationSuccessHandler() {
        return new AuthenticationSuccessHandler() {
            @SuppressWarnings("OptionalGetWithoutIsPresent")
            @Override
            public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                                Authentication authentication) throws IOException, ServletException {
                request.getSession().invalidate();
                User user = (User) authentication.getPrincipal();
                String token = JWT.create()
                        .withSubject(user.getUsername())
                        .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                        .sign(Algorithm.HMAC512(SECRET.getBytes()));
                token = TOKEN_PREFIX + token;
                String identifier = user.getUsername();
                String role = user.getAuthorities().stream().findFirst().get().toString();
                response.setHeader("Location",
                        "http://localhost:3000/#/casLogin/" + token + "/" + identifier + "/" + role);
                response.setStatus(302);
            }
        };
    }

    @Bean
    public SecurityContextLogoutHandler securityContextLogoutHandler() {
        return new SecurityContextLogoutHandler();
    }

    @Bean
    public LogoutFilter logoutFilter() {
        LogoutFilter logoutFilter = new LogoutFilter("https://localhost:8443/logout",
                securityContextLogoutHandler());
        logoutFilter.setFilterProcessesUrl("/logout/cas");
        return logoutFilter;
    }

    @Bean
    public SingleSignOutFilter singleSignOutFilter() {
        SingleSignOutFilter singleSignOutFilter = new SingleSignOutFilter();
//        singleSignOutFilter.setCasServerUrlPrefix("https://localhost:8443");
        singleSignOutFilter.setLogoutCallbackPath("/exit/cas");
        singleSignOutFilter.setIgnoreInitConfiguration(true);
        return singleSignOutFilter;
    }

}
