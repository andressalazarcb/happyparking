package co.com.ceiba.estacionamiento.andres.salazar.happyparking.domain.car;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;

import co.com.ceiba.estacionamiento.andres.salazar.happyparking.domain.AbstractVehicle;
import co.com.ceiba.estacionamiento.andres.salazar.happyparking.domain.parkingorder.ParkingOrder;

@Document
public class Car extends AbstractVehicle{

	public Car copy(Car that) {
		Car carCopy = new Car();
		carCopy.setParking(that.isParking());
		carCopy.setPlate(that.getPlate());
		carCopy.setType(that.getType());
		if(that.getParkingOrders() != null && !that.getParkingOrders().isEmpty()) {
			List<ParkingOrder> parkingOrders = new ArrayList<>();
			Collections.copy(parkingOrders, that.getParkingOrders());
			carCopy.setParkingOrders(parkingOrders);
		}
		return carCopy;
	}

}
