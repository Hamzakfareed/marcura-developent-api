package com.wlogsolutions.marcura.response;

import java.math.BigDecimal;

public class CurrencyExchangeResponse {
	
	private String from;
	private String to;
	private BigDecimal exchange;
	
	public CurrencyExchangeResponse(String from , String to , BigDecimal exchange) {
		// TODO Auto-generated constructor stub
		this.from = from;
		this.to = to;
		this.exchange = exchange;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public BigDecimal getExchange() {
		return exchange;
	}

	public void setExchange(BigDecimal exchange) {
		this.exchange = exchange;
	}

	@Override
	public String toString() {
		return "Response [from=" + from + ", to=" + to + ", exchange=" + exchange + "]";
	}
	
	
	

}
