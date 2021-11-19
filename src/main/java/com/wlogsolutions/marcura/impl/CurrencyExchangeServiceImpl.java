package com.wlogsolutions.marcura.impl;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Date;

import javax.transaction.Transactional;

import org.hibernate.annotations.Synchronize;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wlogsolutions.marcura.exceptions.RatesNotFoundException;
import com.wlogsolutions.marcura.models.Rates;
import com.wlogsolutions.marcura.models.Spread;
import com.wlogsolutions.marcura.models.dto.RatesDTO;
import com.wlogsolutions.marcura.repositories.RatesRepository;
import com.wlogsolutions.marcura.repositories.SpreadRepository;
import com.wlogsolutions.marcura.response.CurrencyExchangeResponse;
import com.wlogsolutions.marcura.services.CurrencyExchangeService;
import com.wlogsolutions.marcura.utils.DateUtils;

@Service
public class CurrencyExchangeServiceImpl implements CurrencyExchangeService {
	@Autowired
	private RatesRepository ratesRepository;

	@Autowired
	private SpreadRepository spreadRepository;

	private ModelMapper mapper = new ModelMapper();

	@Override
	public CurrencyExchangeResponse currencyExchange(String from, String to, String date) throws ParseException {
		Rates fromCurrencyExchange = retreiveRateByNameAndDate(from, date);
		Rates toCurrencyExchange = retreiveRateByNameAndDate(to, date);

		updateCurrencyRequestCounter(fromCurrencyExchange);
		updateCurrencyRequestCounter(toCurrencyExchange);

		Spread fromCurrencySpread = retreiveCurrencySpreadByName(from);
		Spread toCurrencySpread = retreiveCurrencySpreadByName(to);

		BigDecimal exchange = currencyExchangeUsingFrom_to_fromSpread_andToSpread(fromCurrencyExchange,
				toCurrencyExchange, fromCurrencySpread, toCurrencySpread);

		return createCurrencyExchangeResponse(from, to, exchange);
	}
	@Transactional
	private void updateCurrencyRequestCounter(Rates currencyExchange) {
		if (currencyExchange != null) {
			currencyExchange.setCounter(currencyExchange.getCounter() + 1);
			ratesRepository.save(currencyExchange);
		}
	}

	private CurrencyExchangeResponse createCurrencyExchangeResponse(String from, String to, BigDecimal exchange) {
		CurrencyExchangeResponse response = new CurrencyExchangeResponse(from, to, exchange);
		return response;
	}

	private BigDecimal currencyExchangeUsingFrom_to_fromSpread_andToSpread(Rates fromCurrencyExchange,
			Rates toCurrencyExchange, Spread fromCurrencySpread, Spread toCurrencySpread) {

		return BigDecimal.valueOf(toCurrencyExchange.getValue())
				.multiply(BigDecimal.valueOf(fromCurrencyExchange.getValue())).multiply(BigDecimal.valueOf(
						(100 - Math.max(fromCurrencySpread.getPercentage(), toCurrencySpread.getPercentage())) / 100));

	}

	private Spread retreiveCurrencySpreadByName(String fromSpread) {
		Spread spread = spreadRepository.findByCurrencyLike(fromSpread);
		if (spread == null) {
			spread = spreadRepository.findByCurrencyLike("ELSE");
		}
		return spread;
	}

	private Rates retreiveRateByNameAndDate(String from, String date) throws ParseException {
		if (date == null) {
			return ratesRepository.findFirstByNameOrderByIdDesc(from);
		}
		return ratesRepository.findByNameAndDate(from, date);
	}

	@Override
	public RatesDTO updateRates(RatesDTO ratesDTO) throws RatesNotFoundException, ParseException {

		if (ratesDTO == null)
			throw new NullPointerException("Rates DTO is null");

		Rates rates = mapRatesDtoToRates(ratesDTO);
		Rates responseRates = updateRatesValueAndDateWithoutEffectingCounter(rates);

		return mapper.map(responseRates, RatesDTO.class);
	}

	private Rates mapRatesDtoToRates(RatesDTO ratesDTO) {
		return mapper.map(ratesDTO, Rates.class);
	}

	@Transactional
	private Rates updateRatesValueAndDateWithoutEffectingCounter(Rates rates)
			throws RatesNotFoundException, ParseException {
		Rates retrievedRates = ratesRepository.findByName(rates.getName());

		if (retrievedRates == null) {
			throw new RatesNotFoundException("Rates not found for " + rates.getName());
		}
		retrievedRates.setValue(rates.getValue());
		return ratesRepository.save(retrievedRates);
	}

}
