package com.mochu.mapper;

import com.mochu.po.UserPo;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

public interface IUserMapper extends Mapper<UserPo> {

    @Select("select * from user where username = #{username}")
    UserPo selectUserByUsername(String username);
}
