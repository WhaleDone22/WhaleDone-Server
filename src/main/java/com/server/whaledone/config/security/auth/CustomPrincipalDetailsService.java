package com.server.whaledone.config.security.auth;

import com.server.whaledone.config.Entity.Status;
import com.server.whaledone.user.UserRepository;
import com.server.whaledone.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CustomPrincipalDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> optionalUser = userRepository.findByEmailAndStatus(email, Status.ACTIVE);
        return new CustomPrincipalDetails(
                optionalUser.orElseThrow(() -> new UsernameNotFoundException(email)));
    }
}
