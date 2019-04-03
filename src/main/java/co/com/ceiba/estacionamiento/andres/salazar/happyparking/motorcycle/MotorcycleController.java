package co.com.ceiba.estacionamiento.andres.salazar.happyparking.motorcycle;

import javax.ws.rs.core.Response;

public interface MotorcycleController {
	
	public Response getIn(MotorcycleRequestJersey requestJersey);
	public Response findAllVehiclesParking();
	public Response getOut(MotorcycleRequestJersey requestJersey);

}
