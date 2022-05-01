package com.cyiplus.scarlett.common.lang;

import java.io.Serializable;
import lombok.Data;

@Data
public class Result implements Serializable {
    
    private int code;
	private String msg;
	private Object data;

	public static Result succ(Object data) {
		return succ(200, "操作成功", data);
	}

	public static Result succ(String msg) {
		return succ(200, msg, "");
	}

	public static Result succ() {
		return succ(200, "操作成功！", "");
	}

	public static Result succ(String msg, Object data) {
		return succ(200, msg, data);
	}

	public static Result nodata(String msg) {
		return nodata(401, msg, "");
	}

	public static Result nodata(int code, String msg, Object data) {
		Result r = new Result();
		r.setCode(code);
		r.setMsg(msg);
		r.setData(data);
		return r;
	}

	public static Result succ(int code, String msg, Object data) {
		Result r = new Result();
		r.setCode(code);
		r.setMsg(msg);
		r.setData(data);
		return r;
	}

	public static Result fail(String msg) {
		return fail(400, msg, null);
	}

	public static Result fail(int code, String msg, Object data) {
		Result r = new Result();
		r.setCode(code);
		r.setMsg(msg);
		r.setData(data);
		return r;
	}
}
