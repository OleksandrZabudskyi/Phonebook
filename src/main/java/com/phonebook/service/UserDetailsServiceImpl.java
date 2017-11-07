package com.phonebook.service;

import com.phonebook.model.User;
import com.phonebook.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;

/**
 * @author Zabudskyi Oleksandr zabudskyioleksandr@gmail.com
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    @Qualifier("userRepoType")
    private UserRepository userRepository;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);

        return new org.springframework.security.core.userdetails.User(user.getUsername(),
                user.getPassword(), Arrays.asList(new SimpleGrantedAuthority("USER")));
    }
}
