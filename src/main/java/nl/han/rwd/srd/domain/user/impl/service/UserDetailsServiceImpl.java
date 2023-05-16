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

        String password = "123123";
        System.out.println("password: " + passwordEncoder.encode(password));

        // TODO: Fetch user roles
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
                new HashSet<>());
    }

}
