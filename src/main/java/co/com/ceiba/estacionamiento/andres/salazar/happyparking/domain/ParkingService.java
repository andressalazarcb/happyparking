package co.com.ceiba.estacionamiento.andres.salazar.happyparking.domain;

import java.util.stream.Stream;

public interface ParkingService<T> {
	
	T getInVehicle(T vehicle);
	
	Stream<T> findAllVehiclesParking();

	T getOutVehicle(String plate);
	
	T findVehicle(String plate);

}
