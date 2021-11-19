package com.wlogsolutions.marcura.Marcura;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.wlogsolutions.marcura.response.CurrencyExchangeResponse;
import com.wlogsolutions.marcura.services.CurrencyExchangeService;

@SpringBootTest
@AutoConfigureMockMvc
public class CurrencyExchangeRepositoryIntegrationTest {

	private static final String TO_CURRENCY = "USD";
	private static final String FROM_CURRENCY = "EUR";
	private static final double EXCHANGE_RATE = 1.45;
	private static final String URL_WITH_DATE = "/exchange?from=EUR&to=pln&date=2021-11-17";
	@Autowired
	protected MockMvc mvc;
	@Autowired
	WebApplicationContext webApplicationContext;

	@Mock
	private CurrencyExchangeService currencyExchangeService;

	@Test
	public void test() throws Exception {
		CurrencyExchangeResponse expected = new CurrencyExchangeResponse(FROM_CURRENCY, TO_CURRENCY,
				BigDecimal.valueOf(EXCHANGE_RATE));
		when(currencyExchangeService.currencyExchange(FROM_CURRENCY, TO_CURRENCY, URL_WITH_DATE)).thenReturn(expected);
		MvcResult actual = mvc.perform(MockMvcRequestBuilders.get(URL_WITH_DATE)
				.contentType(MediaType.APPLICATION_JSON_VALUE).content(expected.toString())).andReturn();
		assertCurrencyExchangeResponse(expected.toString(), actual.getResponse().getContentAsString());
	}

	private void assertCurrencyExchangeResponse(String expected, String actual) throws UnsupportedEncodingException {
		assertEquals(expected, actual);
	}

}
