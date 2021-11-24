package marcura.development.api.models;

import java.math.BigDecimal;

import javax.persistence.Cacheable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.CacheConcurrencyStrategy;

import io.swagger.annotations.ApiModel;

@Table(name = "spread")
@Entity
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@ApiModel(description = "All about currency spreads. ")
public class Spread {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String currency;
	private BigDecimal percentage;

	public Spread() {

	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public BigDecimal getPercentage() {
		return percentage;
	}

	public void setPercentage(BigDecimal percentage) {
		this.percentage = percentage;
	}

	@Override
	public String toString() {
		return "Spread [currency=" + currency + ", percentage=" + percentage + "]";
	}

}
