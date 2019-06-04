package com.mochu.service.impl;

import com.mochu.dao.MobileDao;
import com.mochu.po.MobilePo;
import com.mochu.service.IMobileService;
import com.mochu.util.DateUtil;
import com.mochu.util.SMSUtil;
import com.mochu.util.StrUtil;
import com.mochu.vo.ResultVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Slf4j
@Service
public class MobileServiceImpl implements IMobileService {

    @Autowired
    private MobileDao mobileDao;


    @Override
    public ResultVo sendLoginCode(String mobile) {

        if (!StrUtil.isMobileNO(mobile)) {
            return ResultVo.failed("手机号格式不正确");
        }

        mobile = mobile.trim().replace("-", "");

        MobilePo mobilePo = getValidMobileByMobile(mobile);

        ResultVo checkReuslt = checkMobile(mobilePo);
        if (checkReuslt.isFailed()) {
            return checkReuslt;
        }

        String code = SMSUtil.createCode();

        String msg = "您的验证码是：" + code + "，请在30分钟内填写，如非本人操作，请忽略此短信。";

        ResultVo sendResult = SMSUtil.sendMsg(mobile, msg);
        if (sendResult.isFailed()) {
            mobilePo.setSendresult(sendResult.getMsg());
            mobileDao.updateMobile(mobilePo);

            return sendResult;
        }

        // 标记短信发送结果
        int todayCount = 0;

        Date sendDate = DateUtil.dateForText(mobilePo.getSendtime());
        if (sendDate != null) {
            // 发送时间不为空
            String sendDay = mobilePo.getSendtime().split(" ")[0];
            String today = DateUtil.dayTextWithDay(0);
            if (today.equals(sendDay)) {
                // 上次发送为当日
                todayCount = mobilePo.getTodaycount();
            }
        }

        todayCount++;
        mobilePo.setSendtime(DateUtil.currentDateText());
        mobilePo.setCode(code);
        mobilePo.setValidtime(DateUtil.dateTextWithMinute(30));

        mobilePo.setVerifytime(0);
        mobilePo.setTodaycount(todayCount);
        mobilePo.setSendresult("发送成功");
        mobileDao.updateMobile(mobilePo);

        return ResultVo.successMsg("发送成功！");
    }

    public MobilePo getValidMobileByMobile(String mobile) {
        mobile = mobile.trim();

        MobilePo po = getMobileByMobile(mobile);
        if (po == null) {
            mobileDao.insertMobile(mobile);
            po = new MobilePo();
            po.setMobile(mobile);
        }

        return po;
    }

    public MobilePo getMobileByMobile(String mobile) {
        MobilePo po = mobileDao.selectMobileByMobile(mobile);
        return po;
    }


    /**
     * 检查手机
     *
     * @param mobilePo
     * @return
     */
    private ResultVo checkMobile(MobilePo mobilePo) {

        if (mobilePo.getStatus() == -1) {
            return ResultVo.failed("该号码（" + mobilePo.getMobile() + ")已被封禁，请联系客服。");
        }

        Date sendDate = DateUtil.dateForText(mobilePo.getSendtime());
        if (sendDate != null) {
            // 发送时间不为空
            String sendDay = mobilePo.getSendtime().split(" ")[0];
            String today = DateUtil.dayTextWithDay(0);
            if (today.equals(sendDay)) {
                // 上次发送为当日
                if (mobilePo.getTodaycount() >= 6) {
                    return ResultVo.failed("该手机号码（" + mobilePo.getMobile() + ")的验证码当天发送次数已超过6次！请联系客服。");
                }
            }
            long interval = System.currentTimeMillis() - sendDate.getTime();
            long second = interval / 1000;
            if (second < 60) {
                return ResultVo.failed("请稍后重试!");
            }
        }

        return ResultVo.success();
    }
}
