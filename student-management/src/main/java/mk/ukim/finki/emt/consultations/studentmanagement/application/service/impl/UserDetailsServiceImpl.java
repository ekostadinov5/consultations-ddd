package mk.ukim.finki.emt.consultations.studentmanagement.application.service.impl;

import mk.ukim.finki.emt.consultations.sharedkernel.domain.security.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return new RestTemplate().getForObject("http://localhost:8083/api/users/" + s, User.class);
    }

}
