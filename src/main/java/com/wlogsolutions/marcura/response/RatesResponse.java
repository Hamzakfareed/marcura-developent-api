package com.wlogsolutions.marcura.response;

public class RatesResponse {

	private String name;
	private String value;
	private String date;

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
		return "RatesResponse [name=" + name + ", value=" + value + ", date=" + date + "]";
	}

}
