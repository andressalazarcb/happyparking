package co.com.ceiba.estacionamiento.andres.salazar.happyparking.domain;

import java.util.List;

public interface CarBuilder {
	
	public CarBuilder buildPlate(String plate);
	public CarBuilder buildisParking(boolean isParking);
	public CarBuilder buildParkingOrders(List<ParkingOrder> parkingOrders);
	public CarBuilder buildAddParkingOrder(ParkingOrder parkingOrder);
	public Car getCar();

}
