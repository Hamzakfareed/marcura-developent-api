 package marcura.development.api;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.text.ParseException;

import javax.transaction.Transactional;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import marcura.development.api.models.Rates;
import marcura.development.api.models.Spread;
import marcura.development.api.models.dto.CurrencyExchangeResponse;
import marcura.development.api.repositories.CurrencyExchangeRepository;
import marcura.development.api.repositories.RatesRepository;
import marcura.development.api.services.CurrencyExchangeService;
import marcura.development.api.services.implementations.CurrencyExchangeServiceImpl;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@Transactional
public class CurrencyExchangeRepositoryTest {

	private static final String DATE = "2021-11-23";
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
	void testPrivateMethod() throws NoSuchMethodException, SecurityException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException {

		Rates fromRates = createRates("EUR", new BigDecimal(1.0));
		Rates toRates = createRates("USD", new BigDecimal(1.13));
		Spread fromSpread = createSpread("EUR", new BigDecimal(0.00));
		Spread toSpread = createSpread("USD", new BigDecimal(2.75));
		BigDecimal actual = callToCurrencyExchangeUsingFrom_to_fromSpread_andToSpread(fromRates, toRates, fromSpread,
				toSpread);

		BigDecimal expected = BigDecimal.valueOf(1.0989);
		assertBigDecimalValues(expected, actual);

	}

	private void assertBigDecimalValues(BigDecimal expected, BigDecimal actual) {
		assertEquals(expected, actual);
	}

	private BigDecimal callToCurrencyExchangeUsingFrom_to_fromSpread_andToSpread(Rates fromRates, Rates toRates,
			Spread fromSpread, Spread toSpread)
			throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
		CurrencyExchangeServiceImpl service = new CurrencyExchangeServiceImpl();
		Method method = CurrencyExchangeServiceImpl.class.getDeclaredMethod(
				"currencyExchangeUsingFrom_to_fromSpread_andToSpread", Rates.class, Rates.class, Spread.class,
				Spread.class);
		method.setAccessible(true);
		BigDecimal result = (BigDecimal) method.invoke(service, fromRates, toRates, fromSpread, toSpread);
		return result;
	}

	private Spread createSpread(String currency, BigDecimal percentage) {
		Spread spread = new Spread();
		spread.setCurrency(currency);
		spread.setPercentage(percentage);
		return spread;
	}

	private Rates createRates(String currency, BigDecimal value) {
		Rates rates = new Rates(currency, value);

		return rates;
	}

}
