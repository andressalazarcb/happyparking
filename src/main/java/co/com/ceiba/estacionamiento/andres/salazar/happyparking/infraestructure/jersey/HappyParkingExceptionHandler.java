package co.com.ceiba.estacionamiento.andres.salazar.happyparking.infraestructure.jersey;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;

import co.com.ceiba.estacionamiento.andres.salazar.happyparking.domain.HappyParkingException;

public class HappyParkingExceptionHandler extends RuntimeException implements ExceptionMapper<HappyParkingException> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1600086802826808705L;

	@Override
	public Response toResponse(HappyParkingException exception) {
		HappyParkingResponse happyParkingResponse = new HappyParkingResponse();
		happyParkingResponse.setStatus(Status.NOT_FOUND.getStatusCode());
		happyParkingResponse.setContent(exception.getMessage());
		return Response.status(Status.NOT_FOUND).entity(happyParkingResponse).type(MediaType.APPLICATION_JSON).build();
	}

}