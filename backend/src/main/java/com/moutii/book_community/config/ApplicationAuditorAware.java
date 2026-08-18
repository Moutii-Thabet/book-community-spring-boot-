package com.moutii.book_community.config;

import com.moutii.book_community.user.User;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

public class ApplicationAuditorAware implements AuditorAware<String> {
    @Override
    public Optional<String> getCurrentAuditor() {
        final Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if(auth == null ||!auth.isAuthenticated() || auth instanceof AnonymousAuthenticationToken) {
           return Optional.empty();
        }
        User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return Optional.of(user.get_id());
    }
}
