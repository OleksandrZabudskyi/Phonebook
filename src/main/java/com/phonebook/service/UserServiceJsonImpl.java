package com.phonebook.service;

import com.phonebook.model.User;
import com.phonebook.repository.UserJsonFileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * <code>UserServiceJsonImpl</code> class is the <code>UserService</code> interface
 * implementation.
 *
 * @author Zabudskyi Oleksandr zabudskyioleksandr@gmail.com
 * @see UserService
 */
@Service
public class UserServiceJsonImpl implements UserService {

    private UserJsonFileRepository userJsonFileRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserServiceJsonImpl(UserJsonFileRepository userJsonFileRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userJsonFileRepository = userJsonFileRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public User findUserByUsername(String username) {
        return userJsonFileRepository.findByUsername(username);
    }

    @Override
    public void saveUser(User user) {
        if (user.getId() == null) {
            user.setId((long) user.hashCode());
            user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        }
        userJsonFileRepository.saveUser(user);
    }
}
