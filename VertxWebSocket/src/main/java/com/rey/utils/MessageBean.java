package com.rey.utils;

public class MessageBean {
	private String header;
	private Object body;

	public MessageBean(String header, Object body) {
		super();
		this.header = header;
		this.body = body;
	}

	public String getHeader() {
		return header;
	}

	public void setHeader(String header) {
		this.header = header;
	}

	public Object getBody() {
		return body;
	}

	public void setBody(Object body) {
		this.body = body;
	}

}
