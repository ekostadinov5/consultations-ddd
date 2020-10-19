package mk.ukim.finki.emt.consultations.usermanagement.domain.application.service.impl;

import mk.ukim.finki.emt.consultations.usermanagement.domain.model.exception.InvalidUsernameException;
import mk.ukim.finki.emt.consultations.usermanagement.domain.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return this.userRepository.findByUsername(s).orElseThrow(InvalidUsernameException::new);
    }

}
