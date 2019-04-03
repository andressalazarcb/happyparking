package co.com.ceiba.estacionamiento.andres.salazar.happyparking.car;

import javax.ws.rs.core.Response;

public interface CarController {

	public Response getIn(CarRequestJersey requestJersey);

	public Response findAllVehiclesParking();
	
	public Response getOut(CarRequestJersey requestJersey);
}
