package com.mochu.mapper;

import com.mochu.po.MobilePo;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

public interface IMobileMapper extends Mapper<MobilePo> {

    @Select("select * from mobile where mobile = #{mobile}")
    MobilePo selectMobileByMobile(String mobile);
}
