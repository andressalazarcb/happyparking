package co.com.ceiba.estacionamiento.andres.salazar.happyparking.application.api;

import co.com.ceiba.estacionamiento.andres.salazar.happyparking.domain.parkinglot.AbstractVehicle;
import co.com.ceiba.estacionamiento.andres.salazar.happyparking.domain.parkinglot.ParkingLot;

public interface VehicleParking<T extends AbstractVehicle> {
	
	public T getInVehicle(ParkingLot<T> parkingLot);
	
	public T getOutVehicle(ParkingLot<T> parkingLot);
	
}
