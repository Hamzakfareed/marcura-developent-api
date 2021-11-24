package marcura.development.api.controllers;

import java.text.ParseException;
import java.util.Date;

import org.codehaus.jettison.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import marcura.development.api.models.dto.CurrencyExchangeResponse;
import marcura.development.api.services.CurrencyExchangeService;
import marcura.development.api.utils.DateUtils;

@CrossOrigin(origins = "http://localhost:8080", maxAge = 3600)
@RestController
public class CurrencyExchangeController {

	private static final String SUCCESS_MESSAGE = "SUCCESS";

	@Autowired
	private CurrencyExchangeService currencyExchangeService;

	@ApiOperation(value = "${get.currency.exchange.description}", notes = "${get.currency.exchange.note}")
	@GetMapping("/exchange")
	public ResponseEntity<CurrencyExchangeResponse> currencyConversion(@ApiParam("${get.currency.exchange.param.from}")@RequestParam(name = "from") String from,
			@ApiParam("${get.currency.exchange.param.to}")@RequestParam(name = "to") String to,
			@ApiParam("${get.currency.exchange.param.date}")@RequestParam(name = "date", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date date)
			throws ParseException, JsonProcessingException {
		CurrencyExchangeResponse currencyExchangeResponse = currencyExchangeService.currencyExchange(from, to,
				DateUtils.isoDateFromDate(date));

		if (currencyExchangeResponse == null) {
			return ResponseEntity.notFound().build();
		}
		return new ResponseEntity<CurrencyExchangeResponse>(currencyExchangeResponse, HttpStatus.OK);
	}

	@ApiOperation(value = "Create or update currecny exchange from fixerio", notes = "return success if no exception occur.")
	@PutMapping("/exchange")
	public ResponseEntity<String> fixer()
			throws JsonMappingException, JsonProcessingException, JSONException, ParseException {
		currencyExchangeService.saveOrUpdateCurrencyExchange();
		return new ResponseEntity<String>(SUCCESS_MESSAGE, HttpStatus.OK);
	}

}
