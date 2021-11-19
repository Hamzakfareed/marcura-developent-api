package com.wlogsolutions.marcura.impl;

import java.util.Arrays;
import java.util.HashMap;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wlogsolutions.marcura.models.CurrencyExchange;
import com.wlogsolutions.marcura.models.Rates;
import com.wlogsolutions.marcura.repositories.CurrencyExchangeRepository;
import com.wlogsolutions.marcura.repositories.RatesRepository;
import com.wlogsolutions.marcura.services.FixerIOService;

@Service
public class FixerIOServiceImpl implements FixerIOService {

	@Value("${fixerio.url}")
	private String url;

	private static final String RATES = "rates";

	private static final String TIMESTAMP = "timestamp";

	private static final String DATE = "date";

	private static final String BASE = "base";

	@Autowired
	private CurrencyExchangeRepository currencyExchangeRepository;

	@Autowired
	private RatesRepository ratesRepository;

	@Override
	public void run() throws JsonMappingException, JsonProcessingException, JSONException {
		ResponseEntity<String> data = retreiveDataFromFixerIO();

		CurrencyExchange exchange = createCurrencyExchangeFromJson(new JSONObject(data.getBody()));

		if (exchange == null) {
			throw new NullPointerException("FixerIO limit crossed");
		}
		JSONObject ratesJson = createRatesFromJsonChildObject(data);

		retreiveDataFromRatesJsonAndStoreInDB(exchange, ratesJson);

	}

	private void retreiveDataFromRatesJsonAndStoreInDB(CurrencyExchange exchange, JSONObject ratesJson)
			throws JsonProcessingException, JsonMappingException {
		ObjectMapper mapper = new ObjectMapper();
		HashMap<String, Object> rates = mapper.readValue(ratesJson.toString(), HashMap.class);
		rates.entrySet().stream().forEach(element -> {
			Rates tempRates = new Rates(element.getKey(), Double.valueOf(element.getValue().toString()));
			tempRates.setDate(exchange.getDate());
			tempRates.setCurrencyExchange(exchange);
			ratesRepository.save(tempRates);

		});
	}

	private JSONObject createRatesFromJsonChildObject(ResponseEntity<String> data) throws JSONException {
		return (JSONObject) new JSONObject(data.getBody()).get(RATES);
	}

	private CurrencyExchange createCurrencyExchangeFromJson(JSONObject jsonObject) throws JSONException {
		CurrencyExchange exchange = new CurrencyExchange();
		if (jsonObject.toString().contains("error")) {
			return null;
		}
		exchange.setBase(jsonObject.getString(BASE));
		exchange.setDate(jsonObject.getString(DATE));
		exchange.setTimestamp(jsonObject.getLong(TIMESTAMP));
		currencyExchangeRepository.save(exchange);
		return exchange;
	}

	private ResponseEntity<String> retreiveDataFromFixerIO() {
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

		ResponseEntity<String> data = restTemplate.exchange(
				UriComponentsBuilder.fromHttpUrl(url).encode().toUriString(), HttpMethod.GET,
				new HttpEntity<String>(headers), String.class);
		return data;
	}


}
