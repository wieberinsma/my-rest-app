package nl.han.resttest.domain.user.impl;

import nl.han.resttest.database.model.UserEntity;
import nl.han.resttest.domain.user.spec.repository.UserRepository;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.HashSet;

@Service
public class UserDetailsServiceImpl implements UserDetailsService
{
    @Inject
    private UserRepository userRepository;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException
    {
        UserEntity user = userRepository.findByUsername(username);
        if (user == null)
            throw new BadCredentialsException("Invalid username or password.");

        // TODO: Fetch user roles
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
                                                                      new HashSet<>());
    }

}
