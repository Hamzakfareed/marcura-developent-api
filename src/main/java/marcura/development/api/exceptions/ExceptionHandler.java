package marcura.development.api.exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ExceptionHandler extends ResponseEntityExceptionHandler {
	Logger logger = LoggerFactory.getLogger(ExceptionHandler.class);

	@org.springframework.web.bind.annotation.ExceptionHandler
	@ResponseStatus(value = HttpStatus.NOT_FOUND)
	@ResponseBody
	public ResponseEntity<String> exceptionsHandler(Exception e, WebRequest request) {
		logger.error(e.getMessage());
		logger.error(e.getStackTrace().toString());
		return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);

	}
}
