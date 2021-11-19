package com.wlogsolutions.marcura.Marcura;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.text.ParseException;

import javax.transaction.Transactional;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.wlogsolutions.marcura.exceptions.RatesNotFoundException;
import com.wlogsolutions.marcura.models.CurrencyExchange;
import com.wlogsolutions.marcura.models.dto.RatesDTO;
import com.wlogsolutions.marcura.repositories.CurrencyExchangeRepository;
import com.wlogsolutions.marcura.repositories.RatesRepository;
import com.wlogsolutions.marcura.response.CurrencyExchangeResponse;
import com.wlogsolutions.marcura.services.CurrencyExchangeService;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@Transactional
public class CurrencyExchangeRepositoryTest {

	private static final boolean SUCCESS = true;
	private static final long ID = 1L;
	private static final String CURRENCY = "EUR";
	private static final String DATE = "2021-11-17";

	private static final String TO_CURRENCY = "USD";
	private static final String FROM_CURRENCY = "EUR";
	private static final double EXCHANGE_RATE = 1.45;

	@Mock
	private CurrencyExchangeRepository currencyExchangeRepository;

	@Mock
	private CurrencyExchangeService currencyExchangeService;

	@Mock
	private RatesRepository ratesRepository;

	@Test
	void test_currencyExchangeByDate() throws ParseException {
		CurrencyExchangeResponse expected = new CurrencyExchangeResponse(FROM_CURRENCY, TO_CURRENCY,
				BigDecimal.valueOf(EXCHANGE_RATE));
		when(currencyExchangeService.currencyExchange(FROM_CURRENCY, TO_CURRENCY, DATE)).thenReturn(expected);

		CurrencyExchangeResponse actual = currencyExchangeService.currencyExchange(FROM_CURRENCY, TO_CURRENCY, DATE);

		assertCurrencyExchangeResponse(expected, actual);

	}

	private void assertCurrencyExchangeResponse(CurrencyExchangeResponse expected, CurrencyExchangeResponse actual) {
		assertEquals(expected.getExchange(), actual.getExchange());
		assertEquals(expected.getFrom(), actual.getFrom());
		assertEquals(expected.getTo(), actual.getTo());
	}
	
	@Test
	void test_currencyRatesUpdateWithoutZero() throws RatesNotFoundException, ParseException {
		RatesDTO expected = createRatesDTO_for_Update();
		when(currencyExchangeService.updateRates(expected)).thenReturn(expected);
		
		RatesDTO actual =currencyExchangeService.updateRates(expected);
		assertRatesDTO(expected, actual);
	}

	private void assertRatesDTO(RatesDTO expected, RatesDTO actual) {
		assertEquals(expected.getName(), actual.getName());
		assertEquals(expected.getValue(),actual.getValue());
		assertEquals(expected.getDate(), actual.getDate());
	}

	private RatesDTO createRatesDTO_for_Update() {
		RatesDTO expected = new RatesDTO();
		expected.setName(FROM_CURRENCY);
		expected.setValue("1.1");
		expected.setDate(createCurrencyExchange().getDate());
		return expected;
	}

	private CurrencyExchange createCurrencyExchange() {
		CurrencyExchange currencyExchange = new CurrencyExchange();
		currencyExchange.setBase(CURRENCY);
		currencyExchange.setDate(DATE);
		currencyExchange.setId(ID);
		currencyExchange.setSuccess(SUCCESS);
		return currencyExchange;
	}

}
