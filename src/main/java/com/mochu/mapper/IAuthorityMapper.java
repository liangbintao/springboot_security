package com.mochu.mapper;

import com.mochu.po.AuthorityPo;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface IAuthorityMapper extends Mapper<AuthorityPo> {

    @Select("select * from authorities where username = #{username}")
    List<AuthorityPo> selectAuthoritiesByUsername(String username);
}
