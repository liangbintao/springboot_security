package com.mochu.service;

import com.mochu.po.AuthorityPo;

import java.util.List;

public interface IAuthorityService {

    List<AuthorityPo> getAuthoritiesByUsername(String username);
}
