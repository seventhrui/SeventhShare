package com.seventh.SeventhShare.bean;

import java.io.Serializable;

public class UserInfoBean implements Serializable{
	/**
	 * UserInfoBean
	 */
	private static final long serialVersionUID = 1L;
	
	private String usernum;
	private String userphone;
	private String useremail;
	public String getUsernum() {
		return usernum;
	}
	public void setUsernum(String usernum) {
		this.usernum = usernum;
	}
	public String getUserphone() {
		return userphone;
	}
	public void setUserphone(String userphone) {
		this.userphone = userphone;
	}
	public String getUseremail() {
		return useremail;
	}
	public void setUseremail(String useremail) {
		this.useremail = useremail;
	}
	
}
