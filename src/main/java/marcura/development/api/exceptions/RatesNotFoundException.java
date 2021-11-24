package marcura.development.api.exceptions;

public class RatesNotFoundException extends Exception {
	
	private String message;
	
	public RatesNotFoundException(String message) {
		this.message = message;
	}

}
