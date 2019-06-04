package com.mochu.controller;

import com.mochu.po.UserPo;
import com.mochu.po.UserSecurityPo;
import com.mochu.service.IUserService;
import com.mochu.util.StrUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpServletRequest;

@Controller
public class BaseController {

    @Autowired
    private IUserService userService;

    protected String getRequestIp(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }

        return ip;
    }

    protected String apiName() {
        String name = "";

        StackTraceElement[] elements = (new Exception()).getStackTrace();
        if (elements.length >= 2) {
            StackTraceElement traceElement = elements[1];

            String[] arr = traceElement.getClassName().split("\\.");
            String classname = arr[arr.length - 1];
            name = classname + "-" + traceElement.getMethodName();
        }

        return name;
    }

    /**
     * 从权限系统获取用户信息
     *
     * @return
     */
    protected UserPo getLoginUser() {
        try {

            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            Object principal = auth.getPrincipal();
            if (!(principal instanceof UserSecurityPo)) {
                return null;
            }

            UserSecurityPo usersSecurityPo = (UserSecurityPo) principal;

            UserPo usersPo = userService.getUserByUsername(usersSecurityPo.getUsername());

            return usersPo;
        } catch (Exception e) {

            return null;
        }
    }

    /**
     * 根据seesion获取用户信息，可能为空
     *
     * @return
     */
    protected UserPo getLoginUserBySession(HttpServletRequest request) {
        try {

            String authorization = request.getHeader("Authorization");
            if (StrUtil.isNullStr(authorization)) {
                return null;
            }

            String sessionbase64 = authorization.replace("session_", "");
            String session = StrUtil.base64decode(sessionbase64);
            if (StrUtil.isNullStr(session)) {
                return null;
            }

            UserPo usersPo = userService.getUserBySession(session);

            return usersPo;
        } catch (Exception e) {

            return null;
        }
    }

    /*protected static int ExprotMaxLine = 60000;

    protected boolean export(String filename, String[][] arr, HttpServletRequest request,
                             HttpServletResponse response) {
        try {
            OutputStream os = response.getOutputStream();

            response.setCharacterEncoding("UTF-8");
            response.reset();// 清空输出流

            filename = URLEncoder.encode(filename, "UTF-8");
            filename = new String(filename.getBytes("UTF-8"), "GBK");
            filename = "attachment;filename=" + filename + ".xls";

            response.setHeader("Content-Disposition", filename);
            response.setContentType("application/msexcel");// 定义输出类型

            // 创建工作薄
            WritableWorkbook workbook = Workbook.createWorkbook(os);
            // 创建新的一页
            WritableSheet sheet = workbook.createSheet("工作表1", 0);

            // 创建要显示的内容,创建一个单元格，第一个参数为列坐标，第二个参数为行坐标，第三个参数为内容

            for (int i = 0; i < arr.length; i++) {
                String[] row = arr[i];
                for (int j = 0; j < row.length; j++) {

                    Label label = new Label(j, i, row[j]);
                    sheet.addCell(label);
                }
            }

            // 把创建的内容写入到输出流中，并关闭输出流
            workbook.write();
            workbook.close();

            os.close();

            return true;
        } catch (Exception e) {
            e.printStackTrace();

            return false;
        }
    }*/

}
