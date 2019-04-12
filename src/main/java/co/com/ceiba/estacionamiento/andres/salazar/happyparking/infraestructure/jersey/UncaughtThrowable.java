package co.com.ceiba.estacionamiento.andres.salazar.happyparking.infraestructure.jersey;

import java.util.ResourceBundle;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.springframework.beans.factory.annotation.Autowired;

@Provider
public class UncaughtThrowable extends Throwable implements ExceptionMapper<Throwable>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1831119523166829731L;
	
	private final transient ResourceBundle bundle;
	
	@Autowired
	public UncaughtThrowable(ResourceBundle bundle) {
		this.bundle = bundle;
	}

	@Override
	public Response toResponse(Throwable exception) {
		HappyParkingResponse happyParkingResponse = new HappyParkingResponse();
		happyParkingResponse.setStatus(Status.INTERNAL_SERVER_ERROR.getStatusCode());
		happyParkingResponse.setContent(bundle.getObject("messages.system.exception.unknown.value"));
		return Response.status(Status.INTERNAL_SERVER_ERROR).entity(happyParkingResponse).type(MediaType.APPLICATION_JSON).build();
	}

}
