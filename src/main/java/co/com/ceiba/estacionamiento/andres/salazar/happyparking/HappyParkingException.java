package co.com.ceiba.estacionamiento.andres.salazar.happyparking;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

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

	public HappyParkingException(Throwable arg0) {
		super(arg0);
	}

	public HappyParkingException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public HappyParkingException(String arg0, Throwable arg1, boolean arg2, boolean arg3) {
		super(arg0, arg1, arg2, arg3);
	}

	@Override
	public Response toResponse(HappyParkingException exception) {
		return Response.status(Status.NOT_FOUND).entity(exception.getMessage()).type(MediaType.APPLICATION_JSON).build();
	}

}
