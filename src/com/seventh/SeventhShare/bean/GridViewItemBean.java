package com.seventh.SeventhShare.bean;

public class GridViewItemBean {
	private int gvitemid;
	private String itemname;
	private int imageid;
	private Boolean selected;
	public GridViewItemBean(){
		
	}
	public GridViewItemBean(int id,String name,int iid,Boolean bl){
		this.gvitemid=id;
		this.itemname=name;
		this.imageid=iid;
		this.selected=bl;
	}
	public int getGvitemid() {
		return gvitemid;
	}
	public void setGvitemid(int gvitemid) {
		this.gvitemid = gvitemid;
	}
	public String getItemname() {
		return itemname;
	}
	public void setItemname(String itemname) {
		this.itemname = itemname;
	}
	public int getImageid() {
		return imageid;
	}
	public void setImageid(int imageid) {
		this.imageid = imageid;
	}
	public Boolean getSelected() {
		return selected;
	}
	public void setSelected(Boolean selected) {
		this.selected = selected;
	}
	
}
