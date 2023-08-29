package com.itsrd.epay.service;

import com.itsrd.epay.model.User;
import com.itsrd.epay.request.UserRequest;

public interface UserService {

    User getUser(Long id);

    User saveUser(UserRequest userRequest);

    User updateUser(Long id, UserRequest userRequest);

    String deleteUser(Long id);

    Long getWalletIdFromUserId(Long user_id);
}
