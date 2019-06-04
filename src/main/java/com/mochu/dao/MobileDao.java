package com.mochu.dao;

import com.mochu.mapper.IMobileMapper;
import com.mochu.po.MobilePo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class MobileDao {

    @Autowired
    private IMobileMapper mobileMapper;

    public void insertMobile(String mobile) {

        MobilePo po = new MobilePo();
        po.setMobile(mobile);

        mobileMapper.insertSelective(po);
    }

    public void updateMobile(MobilePo po) {
        mobileMapper.updateByPrimaryKey(po);
    }

    public MobilePo selectMobileByMobile(String mobile) {
        return mobileMapper.selectMobileByMobile(mobile);
    }
}
