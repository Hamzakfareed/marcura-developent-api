package marcura.development.api.services;

import marcura.development.api.models.dto.CurrencyExchangeResponse;

public interface CurrencyExchangeService {
	public CurrencyExchangeResponse currencyExchange(String from , String to , String date);

	public void saveOrUpdateCurrencyExchange();



}
