package com.moutii.book_community.user.impl;

import com.moutii.book_community.user.UserRepository;
import com.moutii.book_community.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepo.findByEmailIgnoreCase(username)
                .orElseThrow(()->new UsernameNotFoundException("Username not found"));
    }
}
