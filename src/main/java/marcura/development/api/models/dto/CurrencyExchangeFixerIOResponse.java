package marcura.development.api.models.dto;

import java.math.BigDecimal;
import java.util.Map;

public class CurrencyExchangeFixerIOResponse {
	public boolean success;
	public long timestamp;
	public String base;
	public String date;
	private Map<String,BigDecimal> rates;
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
	public Map<String, BigDecimal> getRates() {
		return rates;
	}
	public void setRates(Map<String, BigDecimal> rates) {
		this.rates = rates;
	}
	@Override
	public String toString() {
		return "CurrencyExchangeFixerIOResponse [success=" + success + ", timestamp=" + timestamp + ", base=" + base
				+ ", date=" + date + ", rates=" + rates + "]";
	}
	
	
}
