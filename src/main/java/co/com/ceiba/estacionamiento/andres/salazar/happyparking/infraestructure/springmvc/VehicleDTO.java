package co.com.ceiba.estacionamiento.andres.salazar.happyparking.infraestructure.springmvc;

import co.com.ceiba.estacionamiento.andres.salazar.happyparking.domain.exception.HappyParkingException;

public class VehicleDTO {
	
	private String plate;
	
	private int cc;
	
	public String getPlate() {
		if(this.plate == null || this.plate.trim().isEmpty()) {
			throw new HappyParkingException("messages.vehicle.exception.noplate.value");
		}
		return plate;
	}

	public void setPlate(String plate) {
		this.plate = plate;
	}

	public int getCc() {
		return cc;
	}

	public void setCc(int cc) {
		this.cc = cc;
	}

}
