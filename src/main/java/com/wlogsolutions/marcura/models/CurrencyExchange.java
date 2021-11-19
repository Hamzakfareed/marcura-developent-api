package com.wlogsolutions.marcura.models;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.CacheConcurrencyStrategy;

import io.swagger.annotations.ApiModel;

@Entity
@Table(name = "currency_exchange")
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@ApiModel(description="All about currency exchange. ")
public class CurrencyExchange implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	public boolean success;
	public long timestamp;
	public String base;
	public String date;
	@OneToMany(cascade = CascadeType.ALL)
	public List<Rates> rates;

	public CurrencyExchange() {

	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

	public String getBase() {
		return base;
	}

	public void setBase(String base) {
		this.base = base;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public List<Rates> getRates() {
		return rates;
	}

	public void setRates(List<Rates> rates) {
		this.rates = rates;
	}

	@Override
	public String toString() {
		return "CurrencyConverter [success=" + success + ", timestamp=" + timestamp + ", base=" + base + ", date="
				+ date + ", rates=" + rates + "]";
	}

}