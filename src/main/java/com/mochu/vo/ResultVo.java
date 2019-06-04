package com.mochu.vo;

public class ResultVo {

	public static int CodeSuccess = 0;
	public static int CodeFailed = -1;

	private int code;
	private String msg;
	private Object data;

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

	static public ResultVo result(int code, String msg) {
		ResultVo vo = new ResultVo();
		vo.setCode(code);
		vo.setMsg(msg);
		return vo;
	}

	static public ResultVo success() {
		return result(CodeSuccess, "success");
	}

	static public ResultVo successMsg(String msg) {
		return result(CodeSuccess, msg);
	}

	static public ResultVo successData(Object data) {
		ResultVo vo = result(CodeSuccess, null);
		vo.setData(data);

		return vo;
	}

	static public ResultVo failed(String msg) {
		return result(CodeFailed, msg);
	}

	public boolean isSuccess() {
		return code == CodeSuccess;
	}

	public boolean isFailed() {
		return code == CodeFailed;
	}

}
