package co.com.ceiba.estacionamiento.andres.salazar.happyparking.domain.car;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import co.com.ceiba.estacionamiento.andres.salazar.happyparking.domain.parkinglot.AbstractVehicle;
import co.com.ceiba.estacionamiento.andres.salazar.happyparking.domain.parkinglot.VehicleType;
import co.com.ceiba.estacionamiento.andres.salazar.happyparking.domain.parkingorder.ParkingOrder;

public class Car extends AbstractVehicle {

	public void copy(Car that) {
		this.setParking(that.isParking());
		this.setPlate(that.getPlate());
		this.setType(that.getType());
		if (that.getParkingOrders() != null && !that.getParkingOrders().isEmpty()) {
			List<ParkingOrder> parkingOrders = new ArrayList<>();
			Collections.copy(parkingOrders, that.getParkingOrders());
			this.setParkingOrders(parkingOrders);
		}
	}
	
	

	@Override
	public String getType() {
		return VehicleType.CAR.getValue();
	}

}
