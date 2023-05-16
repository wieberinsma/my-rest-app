package nl.han.rwd.srd.domain.user.impl.service;

import nl.han.rwd.srd.database.model.UserEntity;
import nl.han.rwd.srd.domain.user.spec.repository.UserRepository;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.HashSet;

/**
 * The Spring-default username/password authentication requires a custom-defined user details implementation to be able
 * to provide your own authorisation (authorities of type String) and a Spring
 * {@link org.springframework.security.core.userdetails.User} to persist authorities between requests.
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService
{
    @Inject
    private UserRepository userRepository;

    @Inject
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException
    {
        UserEntity user = userRepository.findByUsername(username);
        if (user == null)
        {
            throw new BadCredentialsException("Invalid username or password.");
        }

        // TODO: Fetch user authorities
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
                new HashSet<>());
    }

}
