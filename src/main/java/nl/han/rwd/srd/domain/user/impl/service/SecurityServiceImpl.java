package nl.han.rwd.srd.domain.user.impl.service;

import nl.han.rwd.srd.database.model.UserEntity;
import nl.han.rwd.srd.domain.user.AuthUser;
import nl.han.rwd.srd.domain.user.spec.repository.UserRepository;
import nl.han.rwd.srd.domain.user.spec.service.SecurityService;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class SecurityServiceImpl implements SecurityService
{
    @Inject
    private UserRepository userRepository;

    @Override
    public String getAuthUsername()
    {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof UserDetails)
        {
            return ((UserDetails) authentication.getPrincipal()).getUsername();
        }

        return null;
    }

    @Override
    public void updateAuthenticationAttributes(HttpSession session, String username)
    {
        if (session == null || !StringUtils.hasLength(username))
        {
            throw new BadCredentialsException("Unknown user.");
        }

        UserEntity userEntity = userRepository.findByUsername(username);
        if (userEntity != null)
        {
            AuthUser authUser = mapToAuthUser(userEntity);
            session.setAttribute("authUser", authUser);
        }
    }

    @Override
    public AuthUser mapToAuthUser(UserEntity userEntity)
    {
        AuthUser authUser = new AuthUser();
        authUser.setUsername(userEntity.getUsername());
        authUser.setRoles(getDefaultUserRoles());
        return authUser;
    }

    private Set<String> getDefaultUserRoles()
    {
        Set<String> defaultRoles = new HashSet<>();
        defaultRoles.add("USER");
        defaultRoles.add("ADMIN");
        return defaultRoles;
    }

    @Override
    public void clearAuthenticationAttributes(HttpSession session, List<String> attributes)
    {
        if (session == null)
        {
            return;
        }

        attributes.forEach(session::removeAttribute);
    }

    /**
     * Suggested implementation of modification of user authorisation. Modifies the current authentication in an
     * immutable way by replacing it in its entirety in the {@link SecurityContextHolder}.
     */
    @Override
    public void updateCurrentUserSession(@NonNull HttpSession session)
    {
        AuthUser authUser = (AuthUser) session.getAttribute("authUser");
        if (authUser != null)
        {
            Set<GrantedAuthority> newAuthorities = new HashSet<>();
            getUserAuthorities(authUser).forEach(userAuth ->
                    newAuthorities.add(new SimpleGrantedAuthority(userAuth)));
            Authentication oldAuth = SecurityContextHolder.getContext().getAuthentication();
            Authentication newAuth = new UsernamePasswordAuthenticationToken(oldAuth.getPrincipal(),
                    oldAuth.getCredentials(), newAuthorities);
            SecurityContextHolder.getContext().setAuthentication(newAuth);

            updateAuthenticationAttributes(session, authUser.getUsername());
        }
    }

    @Override
    public Set<String> getUserAuthorities(AuthUser authUser)
    {
        return getRoleAuthorities(getDefaultUserRoles());
    }

    //TODO: Define authorities from R(1):P(1-n) role-permission matrix table
    private Set<String> getRoleAuthorities(Set<String> roles)
    {
        return roles.stream()
                .map(role -> role + "_AUTH")
                .collect(Collectors.toSet());
    }
}
