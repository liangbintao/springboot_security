package com.mochu.service;

import com.mochu.po.UserPo;

public interface IUserService {

    UserPo getUserByUsername(String username);

    String getUsernameBySession(String session);

    UserPo getUserBySession(String session);
}
