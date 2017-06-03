package com.stone0.gobook;

import java.sql.Date;

public class Manager {

	//属性
	private String mid;
	private String mname;
	private String mpsw;
	private String mgender;
	private Date mbirth;
	private String mphone;
	private String midcard;
	private String maddress;
	private String hiredate;
	private int inuse;
	
	public Manager(String mid, String mpsw) {
		super();
		this.mid = mid;
		this.mpsw = mpsw;
	}
	public Manager() {
		super();
	}
	//方法
	public String getMid() {
		return mid;
	}
	public void setMid(String mid) {
		this.mid = mid;
	}
	public String getMname() {
		return mname;
	}
	public void setMname(String mname) {
		this.mname = mname;
	}
	public String getMpsw() {
		return mpsw;
	}
	public void setMpsw(String mpsw) {
		this.mpsw = mpsw;
	}
	public String getMgender() {
		return mgender;
	}
	public void setMgender(String mgender) {
		this.mgender = mgender;
	}
	public Date getMbirth() {
		return mbirth;
	}
	public void setMbirth(Date mbirth) {
		this.mbirth = mbirth;
	}
	public String getMphone() {
		return mphone;
	}
	public void setMphone(String mphone) {
		this.mphone = mphone;
	}
	public String getMidcard() {
		return midcard;
	}
	public void setMidcard(String midcard) {
		this.midcard = midcard;
	}
	public String getMaddress() {
		return maddress;
	}
	public void setMaddress(String maddress) {
		this.maddress = maddress;
	}
	public String getHiredate() {
		return hiredate;
	}
	public void setHiredate(String hiredate) {
		this.hiredate = hiredate;
	}
	public int getInuse() {
		return inuse;
	}
	public void setInuse(int inuse) {
		this.inuse = inuse;
	}
}
