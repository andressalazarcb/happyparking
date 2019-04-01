package co.com.ceiba.estacionamiento.andres.salazar.happyparking.domain;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class CarBuilderFactory implements CarBuilder {
	
	private Car car;

	public CarBuilderFactory() {
		this.car = new Car();
		this.car.setParking(true);
		this.car.setParkingOrders(new ArrayList<>());
		this.car.setPlate("Dummy");
	}

	@Override
	public CarBuilder buildPlate(String plate) {
		this.car.setPlate(plate);
		return this;
	}

	@Override
	public CarBuilder buildisParking(boolean isParking) {
		this.car.setParking(isParking);
		return this;
	}

	@Override
	public CarBuilder buildParkingOrders(List<ParkingOrder> parkingOrders) {
		this.car.setParkingOrders(parkingOrders);
		return this;
	}

	@Override
	public CarBuilder buildAddParkingOrder(ParkingOrder parkingOrder) {
		this.car.getParkingOrders().add(parkingOrder);
		return this;
	}

	@Override
	public Car getCar() {
		return this.car;
	}

}
