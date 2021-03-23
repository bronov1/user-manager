package com.wamisoftware.usermanager.service;

import com.wamisoftware.usermanager.exception.UserNotFoundException;
import com.wamisoftware.usermanager.model.User;
import com.wamisoftware.usermanager.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private static final Logger logger = LoggerFactory.getLogger("UserService");

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public void create(User user) {
        userRepository.save(user);
        logger.info("Saved new user");
    }

    @Override
    @Transactional
    public List<User> getAll() {
        List<User> users = userRepository.findAll();
        logger.info("Got all users");
        return users;
    }

    @Override
    @Transactional
    public User get(long id) {
        logger.info("Got user with id: {}", id);
        return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
    }

    @Override
    @Transactional
    public User update(User user, long id) {
        return userRepository.findById(id)
                .map(u -> {
                    u = user;
                    u.setId(id);
                    logger.info("Replaced user with id: {}", id);
                    return userRepository.save(u);
                })
                .orElseGet(() -> {
                    user.setId(id);
                    logger.info("Saved new user");
                    return userRepository.save(user);
                });
    }

    @Override
    @Transactional
    public boolean delete(long id) {
        if (userRepository.existsById(id)) {
            userRepository.findById(id)
                    .map(user -> {
                        userRepository.delete(user);
                        logger.info("Deleted user with id: {}", id);
                        return true;
                    });
            return true;
        } else return false;
    }

}
