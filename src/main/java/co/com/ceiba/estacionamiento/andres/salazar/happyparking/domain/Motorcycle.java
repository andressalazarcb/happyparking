package co.com.ceiba.estacionamiento.andres.salazar.happyparking.domain;

import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Motorcycle extends AbstractVehicle{
	
	private int cc;

	public int getCc() {
		return cc;
	}

	public void setCc(int cc) {
		this.cc = cc;
	}

}
