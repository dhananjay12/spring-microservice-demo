package com.mynotes.microservices.demo.reactive;

public class Greeting {
	
	private String msg;
	
	public Greeting() {}

	public Greeting(String msg) {
		this.msg = msg;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

}
