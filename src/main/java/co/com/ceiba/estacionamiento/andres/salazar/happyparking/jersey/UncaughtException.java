package co.com.ceiba.estacionamiento.andres.salazar.happyparking.jersey;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class UncaughtException extends Throwable implements ExceptionMapper<Throwable>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1831119523166829731L;

	@Override
	public Response toResponse(Throwable exception) {
		HappyParkingResponse happyParkingResponse = new HappyParkingResponse();
		happyParkingResponse.setStatus(Status.INTERNAL_SERVER_ERROR.getStatusCode());
		happyParkingResponse.setContent("ha ocurrido un error, pronto lo solucionaremos");
		return Response.status(Status.INTERNAL_SERVER_ERROR).entity(happyParkingResponse).type(MediaType.APPLICATION_JSON).build();
	}

}
