package co.com.ceiba.estacionamiento.andres.salazar.happyparking.infraestructure.springmvc;

import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import co.com.ceiba.estacionamiento.andres.salazar.happyparking.domain.exception.HappyParkingException;

@ControllerAdvice
@RestController
public class HappyParkingExceptionHandler extends ResponseEntityExceptionHandler {

	private final ResourceBundle bundle;

	@Autowired
	public HappyParkingExceptionHandler(ResourceBundle bundle) {
		this.bundle = bundle;
	}

	@ExceptionHandler(HappyParkingException.class)
	public final ResponseEntity<String> handleUserNotFoundException(HappyParkingException ex, WebRequest request) {
		String msg = ex.getMessage();
		msg = msg.replaceAll("co.com.ceiba.estacionamiento.andres.salazar.happyparking.domain.HappyParkingException: ","");
		return new ResponseEntity<>(bundle.getString(msg), HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(Exception.class)
	public final ResponseEntity<String> handleAllExceptions(Exception ex, WebRequest request) {
	  return new ResponseEntity<>(bundle.getString("messages.system.exception.unknown.value"), HttpStatus.INTERNAL_SERVER_ERROR);
	}


}
