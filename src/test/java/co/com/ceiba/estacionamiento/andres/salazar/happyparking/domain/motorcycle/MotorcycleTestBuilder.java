package co.com.ceiba.estacionamiento.andres.salazar.happyparking.domain.motorcycle;

import java.util.ArrayList;
import java.util.List;

import co.com.ceiba.estacionamiento.andres.salazar.happyparking.domain.parkingorder.ParkingOrder;

public class MotorcycleTestBuilder {
	
	private String plate = "HGF478";
	private String type;
	private List<ParkingOrder> parkingOrders;
	private boolean isParking;
	private int cc;
	
	public MotorcycleTestBuilder() {
		this.parkingOrders = new ArrayList<>();
	}
	
	public static MotorcycleTestBuilder create() {
        return new MotorcycleTestBuilder();
    }
	
	public MotorcycleTestBuilder withPlate(String plate) {
        this.plate = plate;
        return this;
    }
	
	public MotorcycleTestBuilder withType(String type) {
        this.type = type;
        return this;
    }
	
	public MotorcycleTestBuilder withParkingOrders(List<ParkingOrder> parkingOrders) {
        this.parkingOrders = parkingOrders;
        return this;
    }
	
	public MotorcycleTestBuilder addParkingOrder(ParkingOrder parkingOrder) {
        this.parkingOrders.add(parkingOrder);
        return this;
    }
	
	public MotorcycleTestBuilder addParkingOrderWithEndDate(ParkingOrder parkingOrder, long endDate) {
		parkingOrder.setEndDate(endDate);
        this.parkingOrders.add(parkingOrder);
        return this;
    }
	
	public MotorcycleTestBuilder addParkingOrderWithSartDate(ParkingOrder parkingOrder, long startDate) {
		parkingOrder.setStartDate(startDate);
        this.parkingOrders.add(parkingOrder);
        return this;
    }
	
	public MotorcycleTestBuilder withIsParking() {
        this.isParking = true;
        return this;
    }
	
	public MotorcycleTestBuilder withNoParking() {
        this.isParking = false;
        return this;
    }
	
	public MotorcycleTestBuilder withCC(int cc) {
        this.cc = cc;
        return this;
    }
	
	public Motorcycle build() {
		Motorcycle motorcycle = new Motorcycle();
		motorcycle.setCc(this.cc);
		motorcycle.setParking(this.isParking);
		motorcycle.setParkingOrders(this.parkingOrders);
		motorcycle.setPlate(this.plate);
		motorcycle.setType(this.type);
        return motorcycle;
    }

}
