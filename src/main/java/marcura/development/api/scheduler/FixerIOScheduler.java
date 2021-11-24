package marcura.development.api.scheduler;

import java.text.ParseException;

import org.codehaus.jettison.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

import marcura.development.api.services.CurrencyExchangeService;

@Component
public class FixerIOScheduler {
	
	@Autowired
	private CurrencyExchangeService currencyExchangeService;
	
	@Scheduled(cron = "${fixerio.cron}")
	public void runJob() throws JsonMappingException, JsonProcessingException, JSONException, ParseException {
		currencyExchangeService.saveOrUpdateCurrencyExchange();
	}

}