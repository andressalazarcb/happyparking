package co.com.ceiba.estacionamiento.andres.salazar.happyparking.infraestructure.jersey.motorcycle;

import co.com.ceiba.estacionamiento.andres.salazar.happyparking.infraestructure.jersey.AbstractVehicleRequestJersey;

public class MotorcycleRequestJersey extends AbstractVehicleRequestJersey{

	private int cc;

	public int getCc() {
		return cc;
	}

	public void setCc(int cc) {
		this.cc = cc;
	}

}
