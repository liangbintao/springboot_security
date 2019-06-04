package com.mochu.util;

import com.mochu.vo.ResultVo;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class SMSUtil {

    public static void main(String[] args) {
        try {

            sendMsg("18668177827", "您的验证码是1234");

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            System.out.println("finally");
        }
    }

    // 内网
    // private static String host = "http://172.19.168.187/service-sms";

    // 公网
    private static String host = "http://120.55.22.53/service-sms";

    private static String sign = "【天天阅读】";

    public static ResultVo sendMsg(String mobile, String msg) {

        try {

            Map<String, String> parameters = new HashMap<>();
            parameters.put("type", "system");
            parameters.put("mobile", mobile);
            parameters.put("sign", sign);
            parameters.put("message", msg);

            String url = host + "/send/message";
            // String reuslt = HttpURLUtils.doPostHttp(url, parameters);
            String reuslt = "11111111";
            LogUtil.log(reuslt);

            return JsonUtil.parseObject(reuslt, ResultVo.class);

        } catch (Exception e) {
            e.printStackTrace();

            return ResultVo.failed("未知异常！");
        }

    }

    /**
     * 生成验证码
     *
     * @return
     */
    public static String createCode() {
        Random rd = new Random();
        String code = "";
        for (int i = 0; i < 4; i++) {
            code = code + rd.nextInt(10);
        }
        return code;
    }

}
