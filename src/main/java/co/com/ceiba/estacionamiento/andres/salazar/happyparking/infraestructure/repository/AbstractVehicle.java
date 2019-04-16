package co.com.ceiba.estacionamiento.andres.salazar.happyparking.infraestructure.repository;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "vehicles")
abstract class AbstractVehicle {

	@Id
	private String plate;
	private String type;
	private List<ParkingOrderEntity> parkingOrders;
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

	public List<ParkingOrderEntity> getParkingOrders() {
		return parkingOrders;
	}

	public void setParkingOrders(List<ParkingOrderEntity> parkingOrders) {
		this.parkingOrders = parkingOrders;
	}

	public String getPlate() {
		return plate;
	}

	public void setPlate(String plate) {
		this.plate = plate;
	}

}
