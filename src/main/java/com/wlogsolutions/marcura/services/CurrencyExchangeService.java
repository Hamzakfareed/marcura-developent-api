package com.wlogsolutions.marcura.services;

import java.text.ParseException;

import com.wlogsolutions.marcura.exceptions.RatesNotFoundException;
import com.wlogsolutions.marcura.models.dto.RatesDTO;
import com.wlogsolutions.marcura.response.CurrencyExchangeResponse;

public interface CurrencyExchangeService {
	public CurrencyExchangeResponse currencyExchange(String from , String to , String date) throws ParseException;

	public RatesDTO updateRates(RatesDTO ratesDTO) throws RatesNotFoundException, ParseException;

}
