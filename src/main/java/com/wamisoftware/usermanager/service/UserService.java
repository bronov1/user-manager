package com.wamisoftware.usermanager.service;

import com.wamisoftware.usermanager.model.User;

import java.util.List;

public interface UserService {

    void create(User user);

    List<User> getAll();

    User get(long id);

    User update(User user, long id);

    boolean delete(long id);

}
