package mk.ukim.finki.emt.consultations.usermanagement.config.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import mk.ukim.finki.emt.consultations.usermanagement.domain.model.User;
import mk.ukim.finki.emt.consultations.usermanagement.domain.model.exception.PasswordNotTheSameException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

import static mk.ukim.finki.emt.consultations.sharedkernel.config.security.SecurityConstants.*;

//public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
//
//    private final AuthenticationManager authenticationManager;
//    private final UserDetailsService userDetailsService;
//    private final PasswordEncoder passwordEncoder;
//
//    public JWTAuthenticationFilter(AuthenticationManager authenticationManager, UserDetailsService userDetailsService,
//                                   PasswordEncoder passwordEncoder) {
//        this.authenticationManager = authenticationManager;
//        this.userDetailsService = userDetailsService;
//        this.passwordEncoder = passwordEncoder;
//    }
//
//    @Override
//    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
//            throws AuthenticationException {
//        try {
//            User cred = new ObjectMapper().readValue(request.getInputStream(), User.class);
//            UserDetails user = userDetailsService.loadUserByUsername(cred.getUsername());
//
//            if (!passwordEncoder.matches(cred.getPassword(), user.getPassword())) {
//                throw new PasswordNotTheSameException();
//            }
//            return authenticationManager.authenticate(
//                    new UsernamePasswordAuthenticationToken(
//                            user.getUsername(),
//                            cred.getPassword(),
//                            user.getAuthorities()
//                    )
//            );
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//    @SuppressWarnings("OptionalGetWithoutIsPresent")
//    @Override
//    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
//                                            FilterChain chain, Authentication authResult)
//            throws IOException, ServletException {
//        User user = (User) authResult.getPrincipal();
//        String token = JWT.create()
//                .withSubject(user.getUsername())
//                .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
//                .sign(Algorithm.HMAC512(SECRET.getBytes()));
//        response.addHeader(HEADER_STRING, TOKEN_PREFIX + token);
//        response.addHeader(ROLE_HEADER, user.getAuthorities().stream().findFirst().get().toString());
//        response.addHeader(IDENTIFIER_HEADER, user.getUsername());
//    }
//
//}
