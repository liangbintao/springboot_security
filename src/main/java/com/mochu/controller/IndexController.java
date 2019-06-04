package com.mochu.controller;

import com.mochu.bo.ResponseBo;
import com.mochu.common.ResultCode;
import com.mochu.common.ResultMsg;
import com.mochu.util.LogUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
public class IndexController extends BaseController {

    @RequestMapping("/")
    @ResponseBody
    public Object index(HttpServletRequest request, Model model) {

        LogUtil.log(request.getRequestURL().toString());

        return "index";
    }

    // 根据id查询用户信息
    @RequestMapping("/test")
    @ResponseBody
    public Object test(String type) {
        try {

            return ResponseBo.successData("");

        } catch (Exception e) {
            return ResponseBo.systemError();
        }
    }

    // 根据id查询用户信息
    @RequestMapping("/status")
    @ResponseBody
    public String status() {
        return "1";
    }

    @RequestMapping("/403")
    @ResponseBody
    public Object page_403(Model model) {

        return "您没有该功能权限！";
    }

    @RequestMapping("/404")
    @ResponseBody
    public Object page_404(Model model) {

        return "找不到该页面！";
    }

    @RequestMapping("/401")
    @ResponseBody
    public ResultMsg page_401(Model model) {

        return ResultMsg.fail(ResultCode.FAIL_NOT_LOGIN);
    }
}
