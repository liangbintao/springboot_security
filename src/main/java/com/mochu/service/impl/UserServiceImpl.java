package com.mochu.service.impl;

import com.mochu.dao.SessionDao;
import com.mochu.dao.UserDao;
import com.mochu.po.SessionPo;
import com.mochu.po.UserPo;
import com.mochu.service.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private SessionDao sessionDao;

    @Override
    public UserPo getUserByUsername(String username) {

        return userDao.selectUserByUsername(username);
    }

    @Override
    public String getUsernameBySession(String session) {
        SessionPo sessionPo = sessionDao.selectSessionById(session);
        if (sessionPo == null) {
            return null;
        }

        UserPo userPo = getUserById(sessionPo.getUserid());
        if (userPo == null) {
            return null;
        }

        return userPo.getUsername();
    }

    @Override
    public UserPo getUserBySession(String session) {

        try {

            SessionPo sessionPo = sessionDao.selectSessionById(session);
            if (sessionPo == null) {
                return null;
            }

            UserPo userPo = userDao.selectUserById(sessionPo.getUserid());

            return userPo;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public UserPo getUserById(String id) {
        if (id == null || id.length() == 0) {
            return null;
        }

        try {
            UserPo userPo = userDao.selectUserById(id);

            return userPo;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
