package co.com.ceiba.estacionamiento.andres.salazar.happyparking.infraestructure.repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;


@Document(collection = "vehicles")
@TypeAlias("motorcycle")
public class MotorcycleEntity extends AbstractVehicle{
	
	private int cc;
	
	public void copy(MotorcycleEntity that) {
		this.setParking(that.isParking());
		this.setPlate(that.getPlate());
		this.setType(that.getType());
		this.setCc(that.getCc());
		if(that.getParkingOrders() != null && !that.getParkingOrders().isEmpty()) {
			List<ParkingOrderEntity> parkingOrders = new ArrayList<>();
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
