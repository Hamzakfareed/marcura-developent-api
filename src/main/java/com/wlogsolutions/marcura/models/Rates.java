package com.wlogsolutions.marcura.models;

import javax.persistence.Cacheable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.CacheConcurrencyStrategy;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@Entity
@Table(name="rates")
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@ApiModel(description="All about exchange rates. ")
public class Rates {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@ApiModelProperty(notes="Name should be minimum 3 characters")
	public String name;
	public double value;
	
	private String date;
	@OneToOne
	private CurrencyExchange currencyExchange;
	private long counter;
	public Rates() {
		
	}
	public Rates(String name , double value) {
		this.name = name;
		this.value = value;
	}
	public Long getId() {
		return id;
	}
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public double getValue() {
		return value;
	}
	public void setValue(double value) {
		this.value = value;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public CurrencyExchange getCurrencyExchange() {
		return currencyExchange;
	}
	public void setCurrencyExchange(CurrencyExchange currencyExchange) {
		this.currencyExchange = currencyExchange;
	}
	public void setValue(Double value) {
		this.value = value;
	}
	public long getCounter() {
		return counter;
	}
	public void setCounter(long counter) {
		this.counter = counter;
	}
	@Override
	public String toString() {
		return "Rates [id=" + id + ", name=" + name + ", value=" + value + ", date=" + date + ", currencyExchange="
				+ currencyExchange + ", counter=" + counter + "]";
	}
	
}