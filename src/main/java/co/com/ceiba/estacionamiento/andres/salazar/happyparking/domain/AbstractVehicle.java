package co.com.ceiba.estacionamiento.andres.salazar.happyparking.domain;

import java.util.List;

import org.springframework.data.annotation.Id;

public abstract class AbstractVehicle {

	@Id
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

}
