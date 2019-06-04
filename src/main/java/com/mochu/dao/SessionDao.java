package com.mochu.dao;

import com.mochu.mapper.ISessionMapper;
import com.mochu.po.SessionPo;
import com.mochu.util.StrUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class SessionDao {

    @Autowired
    private ISessionMapper sessionMapper;

    public SessionPo selectSessionById(String session) {

        if (StrUtil.isNullStr(session)) {
            return null;
        }
        return sessionMapper.selectByPrimaryKey(session);
    }
}
