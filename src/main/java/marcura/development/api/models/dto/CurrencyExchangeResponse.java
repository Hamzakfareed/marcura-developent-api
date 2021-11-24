package marcura.development.api.models.dto;

import java.math.BigDecimal;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import marcura.development.api.serializers.CurrencyExchangeSerializer;

@ApiModel(value = "All about currency exchange response", description = "Currency Exchange Response contains from currency , to currency and exchange rate")
public class CurrencyExchangeResponse {
	@ApiModelProperty("${currency.exchange.from}")
	private String from;
	@ApiModelProperty("${currency.exchange.to}")
	private String to;
	@ApiModelProperty("${currency.exchange.value}")
	@JsonSerialize(using = CurrencyExchangeSerializer.class)
	private BigDecimal exchange;

	public CurrencyExchangeResponse(String from, String to, BigDecimal exchange) {
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
