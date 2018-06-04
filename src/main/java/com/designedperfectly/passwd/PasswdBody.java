package com.designedperfectly.passwd;

public class PasswdBody {

	private String passwd;

	public PasswdBody() {
	}

	public PasswdBody(String passwd) {
		this.passwd = passwd;
	}

	public String getPasswd() {
		return passwd;
	}

	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}
	
	@Override
	public String toString() {
		return passwd;
	}
}
