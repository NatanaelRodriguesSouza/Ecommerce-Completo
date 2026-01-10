package Ecommerce_Completo.service.excepetions;

@SuppressWarnings("serial")
public class EmailException extends RuntimeException {

	public EmailException(String msg) {
		super(msg);
	}
}
