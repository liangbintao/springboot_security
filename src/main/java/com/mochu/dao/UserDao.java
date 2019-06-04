package com.mochu.dao;

import com.mochu.mapper.IUserMapper;
import com.mochu.po.UserPo;
import com.mochu.util.StrUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class UserDao {

    @Autowired
    private IUserMapper userMapper;

    public UserPo selectUserByUsername(String username) {

        return userMapper.selectUserByUsername(username);
    }

    public UserPo selectUserById(String id) {

        if (StrUtil.isNullStr(id)) {
            return null;
        }

        return userMapper.selectByPrimaryKey(id);
    }
}
