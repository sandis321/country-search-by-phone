package com.countrysearch.entity;

public class ResponseBody {

	public ResponseBody(String msg, String result) {
		this.msg = msg;
		this.result = result;
	}

	public ResponseBody() {
	}

	private String msg;
	private String result;

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

}
