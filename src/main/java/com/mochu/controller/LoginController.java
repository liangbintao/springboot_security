package com.mochu.controller;

import com.mochu.bo.ResponseBo;
import com.mochu.common.ResultMsg;
import com.mochu.service.IMobileService;
import com.mochu.service.IUserService;
import com.mochu.util.SignUtil;
import com.mochu.vo.ResultVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 *
 */
@RestController
public class LoginController extends BaseController {

    @Autowired
    private IUserService userService;

    @Autowired
    private IMobileService mobileService;

    /**
     * 登录验证码
     */
    @GetMapping(value = "/login/mobile/code")
    public Object login_mobile_code(String mobile, String machine, String t, String sign,
                                    HttpServletResponse response) {
        try {

            Map<String, String> params = new HashMap<String, String>();
            params.put("mobile", mobile);
            params.put("t", t);
            boolean checkSign = SignUtil.checkSign(params, sign);
            if (!checkSign) {
                return ResponseBo.failed("接口权限异常！");
            }

            ResultVo sendResult = mobileService.sendLoginCode(mobile);
            if (sendResult.isFailed()) {
                return ResponseBo.failed(sendResult.getMsg());
            }

            return ResponseBo.successMsg("发送成功！");
        } catch (Exception e) {
            e.printStackTrace();

            return ResponseBo.systemError();
        }
    }

    @GetMapping("/api/test")
    public ResultMsg test() {

        return ResultMsg.success();
    }

    /**
     * 手机验证码登录
     */
    /*@RequestMapping(value = "/login/mobile")
    @ResponseBody
    public Object login_mobile(String mobile, String code, String machine, HttpServletResponse response) {
        try {

            if (StrUtil.isNullStr(mobile)) {
                return ResponseBo.failed("手机号为空！");
            }

            if (!StrUtil.isMobileNO(mobile)) {
                return ResponseBo.failed("手机号格式不正确");
            }

            MobilePo mobilePo = mobileService.getValidMobileByMobile(mobile);
            if (!String.valueOf(code).equals(mobilePo.getCode())) {
                return ResponseBo.failed("验证码有误！");
            }

            if (!StrUtil.isNullStr(mobilePo.getValidtime())
                    && DateUtil.dateForText(mobilePo.getValidtime()).getTime() < System.currentTimeMillis()) {
                return ResponseBo.failed("验证码已过有效期！");
            }

            UserPo userPo = userService.getUserById(mobilePo.getUserid());
            boolean isNewUser = false;
            if (userPo == null) {
                isNewUser = true;
                // 手机号未绑定用户，查询之前是否用手机注册过帐号
                userPo = userService.getUserByUsername(mobile);

                if (userPo == null) {
                    String nickname = "用户" + mobile.substring(mobile.length() - 5);

                    // 默认女头像
                    userPo = userService.createUser(mobile, code, nickname, UserSex.girl);

                }

                // 更新手机表中的用户id
                mobilePo.setUserid(userPo.getId());
                mobileDao.updateMobileUserId(mobilePo);
            }

            machineService.login(machine, userPo.getId());

            articleService.checkCredit(machine, userPo.getId());

            Map<String, Object> dataMap = getLoginDataById(userPo.getId());
            dataMap.put("isNewUser", isNewUser);

            return ResponseBo.successData(dataMap);
        } catch (Exception e) {
            e.printStackTrace();

            return ResponseBo.systemError();
        }

    }

    private Map<String, Object> getLoginDataById(String id) {
        SessionPo sessionPo = sessionService.getValidSessionByUserid(id);

        Map<String, Object> dataMap = new HashMap<String, Object>();
        dataMap.put("session", sessionPo.getId());

        UserPo usersPo = userService.getUserById(id);

        Map<String, Object> userMap = new HashMap<>();

        userMap.put("id", id);
        userMap.put("nickname", usersPo.getNickname());
        userMap.put("avatar", "");

        dataMap.put("user", userMap);

        return dataMap;

    }*/

    /**
     * 后台管理登录
     *
     * @param username
     * @param password
     * @param response
     * @return
     */
    /*@RequestMapping(value = "/login/bos", method = RequestMethod.POST)
    @ResponseBody
    public Object login_bos(String username, String password, HttpServletResponse response) {
        try {

            UserPo po = userService.getUserByUsername(username);
            if (po == null) {
                return ResponseBo.failed("用户不存在！");
            }

            String mdPassward = Md5Util.getMD5Str(password, username);
            if (!mdPassward.equals(po.getPassword())) {
                return ResponseBo.failed("账号或密码错误");
            }

            // 用户session
            SessionPo sessionPo = sessionService.getValidSessionByUserid(po.getId());

            CookieUtil.setSession(response, sessionPo.getId());

            String title = po.getNickname();

            CookieUtil.addCookie(response, "title", title, CookieUtil.MaxAge);

            return ResponseBo.success();

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseBo.systemError();
        }
    }*/

}
