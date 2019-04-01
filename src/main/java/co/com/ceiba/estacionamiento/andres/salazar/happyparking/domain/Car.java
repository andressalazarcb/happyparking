package co.com.ceiba.estacionamiento.andres.salazar.happyparking.domain;

import java.util.List;

public class Car {
	
	private String plate;
	private boolean isParking;
	private List<ParkingOrder> parkingOrders;

	public boolean isParking() {
		return isParking;
	}

	public void setParking(boolean isParking) {
		this.isParking = isParking;
	}

	public List<ParkingOrder> getParkingOrders() {
		return parkingOrders;
	}

	public void setParkingOrders(List<ParkingOrder> parkingOrders) {
		this.parkingOrders = parkingOrders;
	}

	public String getPlate() {
		return plate;
	}

	public void setPlate(String plate) {
		this.plate = plate;
	}

}
