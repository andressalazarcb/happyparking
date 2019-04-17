package co.com.ceiba.estacionamiento.andres.salazar.happyparking.domain.motorcycle;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import co.com.ceiba.estacionamiento.andres.salazar.happyparking.domain.parkinglot.AbstractVehicle;
import co.com.ceiba.estacionamiento.andres.salazar.happyparking.domain.parkinglot.VehicleType;
import co.com.ceiba.estacionamiento.andres.salazar.happyparking.domain.parkingorder.ParkingOrder;

public class Motorcycle extends AbstractVehicle{
	
	private int cc;
	
	public void copy(Motorcycle that) {
		this.setParking(that.isParking());
		this.setPlate(that.getPlate());
		this.setType(that.getType());
		this.setCc(that.getCc());
		if(that.getParkingOrders() != null && !that.getParkingOrders().isEmpty()) {
			List<ParkingOrder> parkingOrders = new ArrayList<>(that.getParkingOrders().size());
			Collections.copy(parkingOrders, that.getParkingOrders());
			this.setParkingOrders(parkingOrders);
		}
	}
	
	public int getCc() {
		return cc;
	}

	public void setCc(int cc) {
		this.cc = cc;
	}

	@Override
	public String getType() {
		return VehicleType.MOTORCYCLE.getValue();
	}

}
