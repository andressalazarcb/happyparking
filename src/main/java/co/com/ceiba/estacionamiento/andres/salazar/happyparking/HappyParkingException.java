package co.com.ceiba.estacionamiento.andres.salazar.happyparking;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import co.com.ceiba.estacionamiento.andres.salazar.happyparking.domain.HappyParkingResponse;

@Provider
public class HappyParkingException extends RuntimeException implements ExceptionMapper<HappyParkingException> {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1232007252240090307L;
	
	public HappyParkingException() {
	}
	
	public HappyParkingException(String arg0) {
		super(arg0);
	}

	@Override
	public Response toResponse(HappyParkingException exception) {
		HappyParkingResponse happyParkingResponse = new HappyParkingResponse();
		happyParkingResponse.setStatus(Status.NOT_FOUND.getStatusCode());
		happyParkingResponse.setContent(exception.getMessage());
		return Response.status(Status.NOT_FOUND).entity(happyParkingResponse).type(MediaType.APPLICATION_JSON).build();
	}

}
