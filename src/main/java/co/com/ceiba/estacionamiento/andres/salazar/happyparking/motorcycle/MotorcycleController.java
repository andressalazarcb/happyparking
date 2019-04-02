package co.com.ceiba.estacionamiento.andres.salazar.happyparking.motorcycle;

import javax.ws.rs.core.Response;

import co.com.ceiba.estacionamiento.andres.salazar.happyparking.domain.Motorcycle;

public interface MotorcycleController {
	
	public Response getIn(Motorcycle motorcycle);
	public Response findAllVehiclesParking();

}
