package com.wlogsolutions.marcura.controllers;

import java.text.ParseException;
import java.util.Date;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.wlogsolutions.marcura.exceptions.RatesNotFoundException;
import com.wlogsolutions.marcura.models.dto.RatesDTO;
import com.wlogsolutions.marcura.request.RatesRequest;
import com.wlogsolutions.marcura.response.CurrencyExchangeResponse;
import com.wlogsolutions.marcura.response.RatesResponse;
import com.wlogsolutions.marcura.services.CurrencyExchangeService;
import com.wlogsolutions.marcura.utils.DateUtils;

import io.swagger.annotations.ApiOperation;

@CrossOrigin(origins = "http://localhost:8080", maxAge = 3600)
@RestController
public class CurrencyExchangeController {

	@Autowired
	private CurrencyExchangeService currencyExchangeService;

	private ModelMapper mapper = new ModelMapper();

	@ApiOperation(value = "Currency exchange by from , to and date", notes = "return currency exchange response ")
	@GetMapping("/exchange")
	public ResponseEntity<String> currencyConversion(@RequestParam(name = "from") String from,
			@RequestParam(name = "to") String to,
			@RequestParam(name = "date", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date date)
			throws ParseException, JsonProcessingException {
		CurrencyExchangeResponse currencyExchangeResponse = currencyExchangeService.currencyExchange(from, to,
				DateUtils.isoDateFromDate(date));

		if (currencyExchangeResponse == null) {
			return ResponseEntity.notFound().build();
		}
		return new ResponseEntity<String>(convertCurrencyExchangeResponseToJson(currencyExchangeResponse), HttpStatus.OK);
	}

	private String convertCurrencyExchangeResponseToJson(CurrencyExchangeResponse currencyExchangeResponse)
			throws JsonProcessingException {
		ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
		String json = ow.writeValueAsString(currencyExchangeResponse);
		return json;
	}

	@ApiOperation(value = "Update currency exchange rates without update the request counter", notes = "return rate response  ")
	@PutMapping("/exchange")
	public ResponseEntity<String> updateRates(@RequestBody RatesRequest ratesRequest)
			throws RatesNotFoundException, ParseException, JsonProcessingException {

		RatesDTO requestDTO = mapRatesRequestToRatesDTO(ratesRequest);
		RatesDTO responseDTO = currencyExchangeService.updateRates(requestDTO);
		RatesResponse ratesResponse = mapRatesDtoToRatesResponse(responseDTO);
		return new ResponseEntity<String>(convertRatesResponseToJson(ratesResponse), HttpStatus.OK);
	}

	private String convertRatesResponseToJson(RatesResponse ratesResponse) throws JsonProcessingException {
		ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
		String json = ow.writeValueAsString(ratesResponse);
		return json;
	}

	private RatesResponse mapRatesDtoToRatesResponse(RatesDTO responseDTO) {
		return mapper.map(responseDTO, RatesResponse.class);
	}

	private RatesDTO mapRatesRequestToRatesDTO(RatesRequest ratesRequest) {
		return mapper.map(ratesRequest, RatesDTO.class);
	}

}
