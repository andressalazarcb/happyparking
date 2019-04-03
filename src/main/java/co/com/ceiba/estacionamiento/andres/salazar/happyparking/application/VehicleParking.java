package co.com.ceiba.estacionamiento.andres.salazar.happyparking.application;

import java.util.stream.Stream;

public interface VehicleParking<T> {
	
	public T getInVehicleToParkingLot(T vehicle);
	
	public T getOutVehicleOfParkingLot(String plate);
	
	public Stream<T> findVehiclesThatAreParking();
	
	public T findVehicleByPlate(String plate);

}
