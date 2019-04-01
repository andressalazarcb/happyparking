package co.com.ceiba.estacionamiento.andres.salazar.happyparking.domain;

import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Car extends AbstractVehicle{

	public Car copy() {
		Car carCopy = new Car();
		carCopy.setParking(this.isParking());
		carCopy.setParkingOrders(this.getParkingOrders());
		carCopy.setPlate(this.getPlate());
		return carCopy;
	}

}
