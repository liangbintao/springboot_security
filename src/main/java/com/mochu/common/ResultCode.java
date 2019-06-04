package com.mochu.common;

/**
 * 结果码
 **/
public enum ResultCode {
    /**
     * 结果码
     */
    SUCCESS(200, "操作成功"),

    SUCCESS_COLLECTION(200000, "收藏成功"),
    SUCCESS_CANCEL_COLLECTION(200000, "取消收藏成功"),

    FAIL_GET_IMG(500009, "获取图片失败"),

    FAIL(500, "服务器内部错误"),

    FAIL_NOT_LOGIN(400001, "未登录"),

    FAIL_PARAMS(500010, "请求参数错误"),

    FAIL_ICON_CLASS_EXISTS(500001, "图标class已存在"),

    FAIL_PHONE_EXISTS(500002, "手机号已存在"),

    FAIL_EMAIL_EXISTS(500003, "邮箱已存在"),

    FAIL_PHONE_OR_EMAIL_EXISTS(500004, "手机号或邮箱已存在"),

    FAIL_INCORRECT_PASSWORD(500005, "密码错误"),

    FAIL_IMG_TOO_LARGE(500006, "上传图片过大"),

    FAIL_INCORRECT_UPLOAD_IMG(500007, "上传图片失败"),

    FAIL_DO_NOT_REPEAT_UPLOAD(500008, "请勿重复上传图片"),

    ERROR_VERI_CODE(404001, "验证码错误"),

    ERROR_INVALID_PHONE(404002, "手机号格式不正确");

    private final int code;
    private final String msg;

    ResultCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public static String getMsg(int code) {
        for (ResultCode resultCode : ResultCode.values()) {
            if (resultCode.getCode() == code) {
                return resultCode.getMsg();
            }
        }
        return null;
    }

    public static boolean codeExists(int code) {
        for (ResultCode resultCode : ResultCode.values()) {
            if (resultCode.getCode() == code) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return "ResultCode{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                '}';
    }
}
