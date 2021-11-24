package marcura.development.api.services.implementations;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Map;

import javax.transaction.Transactional;

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

import marcura.development.api.models.CurrencyExchange;
import marcura.development.api.models.Rates;
import marcura.development.api.models.Spread;
import marcura.development.api.models.dto.CurrencyExchangeFixerIOResponse;
import marcura.development.api.models.dto.CurrencyExchangeResponse;
import marcura.development.api.repositories.CurrencyExchangeRepository;
import marcura.development.api.repositories.RatesRepository;
import marcura.development.api.repositories.SpreadRepository;
import marcura.development.api.services.CurrencyExchangeService;

@Service
public class CurrencyExchangeServiceImpl implements CurrencyExchangeService {
	@Autowired
	private RatesRepository ratesRepository;

	@Autowired
	private SpreadRepository spreadRepository;

	@Autowired
	private CurrencyExchangeRepository currencyExchangeRepository;

	@Value("${fixerio.url}")
	private String url;

	@Transactional
	@Override
	public CurrencyExchangeResponse currencyExchange(String from, String to, String date) {
		Rates fromCurrencyExchange = retrieveRatesByNameAndDate(from, date);
		Rates toCurrencyExchange = retrieveRatesByNameAndDate(to, date);

		updateCurrencyRequestCounter(fromCurrencyExchange);
		updateCurrencyRequestCounter(toCurrencyExchange);

		Spread fromCurrencySpread = retrieveSpreadByName(from);
		Spread toCurrencySpread = retrieveSpreadByName(to);

		BigDecimal exchange = currencyExchangeUsingFrom_to_fromSpread_andToSpread(fromCurrencyExchange,
				toCurrencyExchange, fromCurrencySpread, toCurrencySpread);

		return createCurrencyExchangeResponse(from, to, exchange);
	}

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

		return toCurrencyExchange.getValue().divide(fromCurrencyExchange.getValue())
				.multiply(BigDecimal.valueOf(100)
						.subtract(BigDecimal.valueOf(Math.max(fromCurrencySpread.getPercentage().intValue(),
								toCurrencySpread.getPercentage().intValue())))
						.divide(BigDecimal.valueOf(100)))
				.setScale(4, RoundingMode.HALF_UP);
	}

	private Spread retrieveSpreadByName(String fromSpread) {
		Spread spread = spreadRepository.findByCurrencyLike(fromSpread);
		if (spread == null) {
			spread = spreadRepository.findByCurrencyLike("ELSE");
		}
		return spread;
	}

	private Rates retrieveRatesByNameAndDate(String from, String date) {
		if (date == null) {
			return ratesRepository.findFirstByNameOrderByIdDesc(from);
		}
		return ratesRepository.findByNameAndDate(from, date);
	}

	@Transactional
	@Override
	public void saveOrUpdateCurrencyExchange() {
		ResponseEntity<CurrencyExchangeFixerIOResponse> data = currencyExchangesFromFixerIO();

		CurrencyExchange exchange = createCurrencyExchangeFromFixerIOResponse(data.getBody());

		if (exchange == null) {
			throw new NullPointerException("FixerIO limit crossed");
		}
		Map<String, BigDecimal> rates = data.getBody().getRates();

		retrieveCurrencyExchangeFromDbOrFixerIOResponse(exchange, rates);

	}

	private void retrieveCurrencyExchangeFromDbOrFixerIOResponse(CurrencyExchange exchange,
			Map<String, BigDecimal> rates) {

		ArrayList<Rates> retreiveFromDB = ratesRepository.findAllByCurrencyExchangeId(exchange.getId());

		if (retreiveFromDB != null && retreiveFromDB.size() > 0) {
			retreiveFromDB.forEach(element -> {
				if (rates.containsKey(element.getName())) {
					BigDecimal value = rates.get(element.getName());
					element.setValue(value);
					ratesRepository.save(element);
				}
			});
		} else {
			rates.entrySet().stream().forEach(element -> {
				Rates tempRates = new Rates(element.getKey(), element.getValue());
				tempRates.setDate(exchange.getDate());
				tempRates.setCurrencyExchange(exchange);
				ratesRepository.save(tempRates);

			});
		}
	}

	private CurrencyExchange createCurrencyExchangeFromFixerIOResponse(
			CurrencyExchangeFixerIOResponse currencyExchangeFixerIO) {
		CurrencyExchange exchange = createOrRetreiveCurrencyExchangeFromDatabase();
		exchange.setBase(currencyExchangeFixerIO.getBase());
		exchange.setDate(currencyExchangeFixerIO.getDate());
		exchange.setTimestamp(currencyExchangeFixerIO.getTimestamp());
		currencyExchangeRepository.save(exchange);
		return exchange;
	}

	private CurrencyExchange createOrRetreiveCurrencyExchangeFromDatabase() {

		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("YYYY-MM-dd");
		String currentDate = simpleDateFormat.format(new Date());
		CurrencyExchange existingCurrencyExchange = currencyExchangeRepository.findByDate(currentDate);
		if (existingCurrencyExchange != null) {
			return existingCurrencyExchange;
		}
		return new CurrencyExchange();
	}

	public ResponseEntity<CurrencyExchangeFixerIOResponse> currencyExchangesFromFixerIO() {
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

		ResponseEntity<CurrencyExchangeFixerIOResponse> data = restTemplate.exchange(
				UriComponentsBuilder.fromHttpUrl(url).encode().toUriString(), HttpMethod.GET,
				new HttpEntity<String>(headers), CurrencyExchangeFixerIOResponse.class);

		return data;
	}

}
