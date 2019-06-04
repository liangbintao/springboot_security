package com.mochu.dao;

import com.mochu.mapper.IAuthorityMapper;
import com.mochu.po.AuthorityPo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class AuthorityDao {

    @Autowired
    private IAuthorityMapper authorityMapper;

    public List<AuthorityPo> selectAuthoritiesByUsername(String username) {

        return authorityMapper.selectAuthoritiesByUsername(username);
    }
}
