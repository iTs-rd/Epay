package com.itsrd.epay.service;

import com.itsrd.epay.model.User;

public interface UserService {

    User getUser(Long id);

    User saveUser(User user);

}
