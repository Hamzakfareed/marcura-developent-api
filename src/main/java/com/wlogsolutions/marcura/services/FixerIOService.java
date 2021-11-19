package com.wlogsolutions.marcura.services;

import java.text.ParseException;

import org.codehaus.jettison.json.JSONException;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

public interface FixerIOService {
	
	public void run() throws JsonMappingException, JsonProcessingException, JSONException, ParseException;
	
	
}
