package co.com.ceiba.estacionamiento.andres.salazar.happyparking.car;

import javax.ws.rs.core.Response;

import co.com.ceiba.estacionamiento.andres.salazar.happyparking.domain.Car;

public interface CarController {

	public Response getIn(Car car);

	public Response findAllVehiclesParking();
}
