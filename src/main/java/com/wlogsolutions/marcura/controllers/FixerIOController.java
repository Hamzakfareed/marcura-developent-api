package com.wlogsolutions.marcura.controllers;

import java.text.ParseException;

import org.codehaus.jettison.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.wlogsolutions.marcura.services.FixerIOService;

@CrossOrigin(origins = "http://localhost:8080", maxAge = 3600)
@RestController
public class FixerIOController {

	private static final String SUCCESS_MESSAGE = "SUCCESS";
	@Autowired
	private FixerIOService fixerIOService;

	@GetMapping("/fixer")
	public ResponseEntity<String> fixer() throws JsonMappingException, JsonProcessingException, JSONException, ParseException {

		fixerIOService.run();
		return new ResponseEntity<String>(SUCCESS_MESSAGE, HttpStatus.OK);
	}

}
