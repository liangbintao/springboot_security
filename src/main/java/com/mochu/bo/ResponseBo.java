package com.mochu.bo;

import com.alibaba.fastjson.JSON;

public class ResponseBo {
	private int code;
	private String msg;
	private Object data;

	public static final int CodeSuccess = 0;
	public static final int CodeFailed = -1;

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	static public String response(int code, String msg, Object data) {
		ResponseBo bo = new ResponseBo();
		bo.setCode(code);
		bo.setMsg(msg);
		bo.setData(data);

		return JSON.toJSONString(bo);
	}

	static public String successMsg(String msg) {

		return response(CodeSuccess, msg, null);
	}

	static public String success(String msg, Object data) {

		return response(CodeSuccess, msg, data);
	}

	static public String success() {

		return response(CodeSuccess, "success!", null);
	}

	static public String successData(Object data) {

		return success("success!", data);
	}

	static public String failed(String msg) {

		return response(CodeFailed, msg, null);
	}

	static public String systemError() {

		return failed("系统错误！");
	}

}
