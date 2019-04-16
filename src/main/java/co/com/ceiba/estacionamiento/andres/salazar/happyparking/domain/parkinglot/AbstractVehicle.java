package co.com.ceiba.estacionamiento.andres.salazar.happyparking.domain.parkinglot;

import java.util.List;

import co.com.ceiba.estacionamiento.andres.salazar.happyparking.domain.parkingorder.ParkingOrder;

public abstract class AbstractVehicle {

	private String plate;
	private String type;
	private List<ParkingOrder> parkingOrders;
	private boolean isParking;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

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
	
	public boolean isPlateStartWith(String letter) {
		if(plate != null && !plate.isEmpty())
			return plate.trim().startsWith(letter);
		return false;
	}
	
	public void addParkingOrders(List<ParkingOrder> parkingOrders) {
		if(this.parkingOrders == null || this.parkingOrders.isEmpty()) {
			this.parkingOrders = parkingOrders;
		}else if(!this.parkingOrders.isEmpty()) {
			this.parkingOrders.addAll(parkingOrders);
		}
	}

}
