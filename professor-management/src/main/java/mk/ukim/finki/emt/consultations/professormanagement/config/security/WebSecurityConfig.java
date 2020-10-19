package mk.ukim.finki.emt.consultations.professormanagement.config.security;

import mk.ukim.finki.emt.consultations.professormanagement.application.service.impl.UserDetailsServiceImpl;
import mk.ukim.finki.emt.consultations.sharedkernel.config.security.JWTAuthorizationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import static mk.ukim.finki.emt.consultations.sharedkernel.config.security.SecurityConstants.*;

@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserDetailsServiceImpl userDetailsService;

    public WebSecurityConfig(UserDetailsServiceImpl userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable().authorizeRequests()
                .antMatchers(HttpMethod.POST, "/api/professors/regularConsultationSlots")
                .hasAuthority("PROFESSOR")
                .antMatchers(HttpMethod.PATCH, "/api/professors/regularConsultationSlots/**")
                .hasAuthority("PROFESSOR")
                .antMatchers(HttpMethod.DELETE, "/api/professors/regularConsultationSlots/**")
                .hasAuthority("PROFESSOR")
                .antMatchers(HttpMethod.PATCH, "/api/professors/regularConsultationSlots/cancel/**")
                .hasAuthority("PROFESSOR")
                .antMatchers(HttpMethod.PATCH, "/api/professors/regularConsultationSlots/uncancel/**")
                .hasAuthority("PROFESSOR")
                .antMatchers(HttpMethod.POST, "/api/professors/additionalConsultationSlots")
                .hasAuthority("PROFESSOR")
                .antMatchers(HttpMethod.PATCH, "/api/professors/additionalConsultationSlots/**")
                .hasAuthority("PROFESSOR")
                .antMatchers(HttpMethod.DELETE, "/api/professors/additionalConsultationSlots/**")
                .hasAuthority("PROFESSOR")
                .antMatchers(HttpMethod.GET).permitAll()
                .anyRequest().authenticated()
                .and()
                .addFilter(new JWTAuthorizationFilter(authenticationManager(), userDetailsService))
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration().applyPermitDefaultValues();

        config.addAllowedOrigin("*");
        config.addAllowedHeader("*");

        config.addExposedHeader(HEADER_STRING);
        config.addExposedHeader(IDENTIFIER_HEADER);
        config.addExposedHeader(ROLE_HEADER);

        config.addAllowedMethod("OPTIONS");
        config.addAllowedMethod("HEAD");
        config.addAllowedMethod("GET");
        config.addAllowedMethod("PUT");
        config.addAllowedMethod("POST");
        config.addAllowedMethod("DELETE");
        config.addAllowedMethod("PATCH");

        source.registerCorsConfiguration("/**", config);

        return source;
    }

}
