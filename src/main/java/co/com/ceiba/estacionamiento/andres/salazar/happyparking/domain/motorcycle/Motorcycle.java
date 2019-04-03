package co.com.ceiba.estacionamiento.andres.salazar.happyparking.domain.motorcycle;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;

import co.com.ceiba.estacionamiento.andres.salazar.happyparking.domain.AbstractVehicle;
import co.com.ceiba.estacionamiento.andres.salazar.happyparking.domain.parkingorder.ParkingOrder;
import lombok.Builder;

@Document
@Builder(toBuilder = true)
public class Motorcycle extends AbstractVehicle{
	
	private int cc;

	public int getCc() {
		return cc;
	}

	public void setCc(int cc) {
		this.cc = cc;
	}
	
	public Motorcycle copy(Motorcycle that) {
		Motorcycle motorcycleCopy = new Motorcycle();
		motorcycleCopy.setParking(that.isParking());
		motorcycleCopy.setPlate(that.getPlate());
		motorcycleCopy.setType(that.getType());
		motorcycleCopy.setCc(that.getCc());
		if(that.getParkingOrders() != null && !that.getParkingOrders().isEmpty()) {
			List<ParkingOrder> parkingOrders = new ArrayList<>();
			Collections.copy(parkingOrders, that.getParkingOrders());
			motorcycleCopy.setParkingOrders(parkingOrders);
		}
		return motorcycleCopy;
	}
	

}
