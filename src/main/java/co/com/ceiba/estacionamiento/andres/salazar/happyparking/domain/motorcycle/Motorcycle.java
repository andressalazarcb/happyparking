package co.com.ceiba.estacionamiento.andres.salazar.happyparking.domain.motorcycle;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;

import co.com.ceiba.estacionamiento.andres.salazar.happyparking.domain.AbstractVehicle;
import co.com.ceiba.estacionamiento.andres.salazar.happyparking.domain.parkingorder.ParkingOrder;

@Document(collection = "vehicles")
@TypeAlias("motorcycle")
public class Motorcycle extends AbstractVehicle{
	
	private int cc;

	public int getCc() {
		return cc;
	}

	public void setCc(int cc) {
		this.cc = cc;
	}
	
	public void copy(Motorcycle that) {
		this.setParking(that.isParking());
		this.setPlate(that.getPlate());
		this.setType(that.getType());
		this.setCc(that.getCc());
		if(that.getParkingOrders() != null && !that.getParkingOrders().isEmpty()) {
			List<ParkingOrder> parkingOrders = new ArrayList<>();
			Collections.copy(parkingOrders, that.getParkingOrders());
			this.setParkingOrders(parkingOrders);
		}
	}
	

}
