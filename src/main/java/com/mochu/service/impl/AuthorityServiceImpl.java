package com.mochu.service.impl;

import com.mochu.dao.AuthorityDao;
import com.mochu.po.AuthorityPo;
import com.mochu.service.IAuthorityService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class AuthorityServiceImpl implements IAuthorityService {

    @Autowired
    private AuthorityDao authorityDao;

    @Override
    public List<AuthorityPo> getAuthoritiesByUsername(String username) {

        List<AuthorityPo> list = authorityDao.selectAuthoritiesByUsername(username);

        return list;
    }
}
