package co.com.ceiba.estacionamiento.andres.salazar.happyparking.infraestructure.jersey;

import java.util.ResourceBundle;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import co.com.ceiba.estacionamiento.andres.salazar.happyparking.domain.HappyParkingException;

@Provider
@Component
public class HappyParkingExceptionHandler extends RuntimeException implements ExceptionMapper<HappyParkingException> {
	
	
	private final transient ResourceBundle bundle;
	
	@Autowired
	public HappyParkingExceptionHandler(ResourceBundle bundle) {
		this.bundle = bundle;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1600086802826808705L;

	@Override
	public Response toResponse(HappyParkingException exception) {
		HappyParkingResponse happyParkingResponse = new HappyParkingResponse();
		happyParkingResponse.setStatus(Status.NOT_FOUND.getStatusCode());
		String msg = exception.getMessage();
		msg = msg.replaceAll("co.com.ceiba.estacionamiento.andres.salazar.happyparking.domain.HappyParkingException: ", "");
		happyParkingResponse.setContent(bundle.getObject(msg));
		return Response.status(Status.NOT_FOUND).entity(happyParkingResponse).type(MediaType.APPLICATION_JSON).build();
	}

}
