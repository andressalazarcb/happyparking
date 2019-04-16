package co.com.ceiba.estacionamiento.andres.salazar.happyparking.infraestructure.repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "vehicles")
@TypeAlias("car")
public class CarEntity extends AbstractVehicle {

	public void copy(CarEntity that) {
		this.setParking(that.isParking());
		this.setPlate(that.getPlate());
		this.setType(that.getType());
		if (that.getParkingOrders() != null && !that.getParkingOrders().isEmpty()) {
			List<ParkingOrderEntity> parkingOrders = new ArrayList<>();
			Collections.copy(parkingOrders, that.getParkingOrders());
			this.setParkingOrders(parkingOrders);
		}
	}

	@Override
	public String getType() {
		return VehicleType.CAR.getValue();
	}

}
