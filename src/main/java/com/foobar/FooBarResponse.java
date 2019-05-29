package com.foobar;

public class FooBarResponse {

	private String message;
	private String status;
	private Object data;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}


	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public FooBarResponse(String message, String status, Object data) {
		super();
		this.message = message;
		this.status = status;
		this.data = data;
	}

}
