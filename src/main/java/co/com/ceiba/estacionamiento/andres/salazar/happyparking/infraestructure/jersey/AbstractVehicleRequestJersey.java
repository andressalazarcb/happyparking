package co.com.ceiba.estacionamiento.andres.salazar.happyparking.infraestructure.jersey;

import co.com.ceiba.estacionamiento.andres.salazar.happyparking.domain.HappyParkingException;

public abstract class AbstractVehicleRequestJersey {
	
	private String plate;
	
	public String getPlate() {
		if(this.plate == null || this.plate.trim().isEmpty()) {
			throw new HappyParkingException("messages.vehicle.exception.noplate.value");
		}
		return plate;
	}

	public void setPlate(String plate) {
		this.plate = plate;
	}

}
