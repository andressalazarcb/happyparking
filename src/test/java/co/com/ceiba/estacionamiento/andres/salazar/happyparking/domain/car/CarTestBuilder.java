package co.com.ceiba.estacionamiento.andres.salazar.happyparking.domain.car;

import java.util.ArrayList;
import java.util.List;

import co.com.ceiba.estacionamiento.andres.salazar.happyparking.domain.parkinglot.VehicleType;
import co.com.ceiba.estacionamiento.andres.salazar.happyparking.domain.parkingorder.ParkingOrder;

public class CarTestBuilder {

	private String plate = "HGF478";
	private String type = VehicleType.CAR.getValue();
	private List<ParkingOrder> parkingOrders;
	private boolean isParking;

	public CarTestBuilder() {
		this.parkingOrders = new ArrayList<>();
	}

	public static CarTestBuilder create() {
		return new CarTestBuilder();
	}

	public CarTestBuilder withPlate(String plate) {
		this.plate = plate;
		return this;
	}

	public CarTestBuilder withType(String type) {
		this.type = type;
		return this;
	}

	public CarTestBuilder withParkingOrders(List<ParkingOrder> parkingOrders) {
		this.parkingOrders = parkingOrders;
		return this;
	}

	public CarTestBuilder addParkingOrder(ParkingOrder parkingOrder) {
		this.parkingOrders.add(parkingOrder);
		return this;
	}

	public CarTestBuilder addParkingOrderWithEndDate(ParkingOrder parkingOrder, long endDate) {
		parkingOrder.setEndDate(endDate);
		this.parkingOrders.add(parkingOrder);
		return this;
	}

	public CarTestBuilder addParkingOrderWithSartDate(ParkingOrder parkingOrder, long startDate) {
		parkingOrder.setStartDate(startDate);
		this.parkingOrders.add(parkingOrder);
		return this;
	}

	public CarTestBuilder withIsParking() {
		this.isParking = true;
		return this;
	}

	public CarTestBuilder withNoParking() {
		this.isParking = false;
		return this;
	}

	public Car build() {
		Car car = new Car();
		car.setParking(this.isParking);
		car.setParkingOrders(this.parkingOrders);
		car.setPlate(this.plate);
		car.setType(this.type);
		return car;
	}

}
