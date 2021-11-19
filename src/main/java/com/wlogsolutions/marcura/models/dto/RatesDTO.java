package com.wlogsolutions.marcura.models.dto;

public class RatesDTO {
	
	private long id;
	private String name;
	private String value;
	private String date;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	@Override
	public String toString() {
		return "RatesDTO [id=" + id + ", name=" + name + ", value=" + value + ", date=" + date + "]";
	}
	
	
	

}
