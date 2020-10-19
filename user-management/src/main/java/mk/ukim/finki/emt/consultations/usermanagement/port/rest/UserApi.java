package mk.ukim.finki.emt.consultations.usermanagement.port.rest;

import mk.ukim.finki.emt.consultations.sharedkernel.domain.security.User;
import mk.ukim.finki.emt.consultations.usermanagement.domain.application.service.impl.UserDetailsServiceImpl;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/users", produces = MimeTypeUtils.APPLICATION_JSON_VALUE)
public class UserApi {

    private final UserDetailsServiceImpl userDetailsService;

    public UserApi(UserDetailsServiceImpl userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @GetMapping("/{username}")
    public User getUser(@PathVariable String username) {
        var user = this.userDetailsService.loadUserByUsername(username);
        return new User(user.getUsername(), user.getPassword(), user.getAuthorities());
    }

}
