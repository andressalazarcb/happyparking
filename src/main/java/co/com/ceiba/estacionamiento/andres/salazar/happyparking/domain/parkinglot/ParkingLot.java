package co.com.ceiba.estacionamiento.andres.salazar.happyparking.domain.parkinglot;

import co.com.ceiba.estacionamiento.andres.salazar.happyparking.domain.exception.HappyParkingException;

public class ParkingLot<T extends AbstractVehicle> {
	
	private String msgException = "messages.vehicle.exception.notexist.value";
	
	private T vehicleIn;
	
	private T vehicleOut;
	
	private int quantity;

	public T getVehicleIn() {
		return vehicleIn;
	}

	public void setVehicleIn(T vehicleIn) {
		this.vehicleIn = vehicleIn;
	}

	public T getVehicleOut() {
		if (vehicleOut == null) {
			throw new HappyParkingException(msgException);
		}
		return vehicleOut;
	}

	public void setVehicleOut(T vehicleOut) {
		this.vehicleOut = vehicleOut;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	

}
