package com.phonebook.service;

import com.phonebook.model.User;
import com.phonebook.repository.UserMySqlRepository;
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
public class UserServiceMySqlImpl implements UserService {

    private UserMySqlRepository userRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserServiceMySqlImpl(UserMySqlRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public void saveUser(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    @Override
    public User findUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}
