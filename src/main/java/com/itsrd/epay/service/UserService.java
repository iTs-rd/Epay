package com.itsrd.epay.service;

import com.itsrd.epay.model.User;
import com.itsrd.epay.request.UserRequest;

public interface UserService {

    User getUser(Long id);

    User saveUser(UserRequest userRequest);

}
