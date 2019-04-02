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
	
	public Motorcycle copy() {
		Motorcycle motorcycleCopy = new Motorcycle();
		motorcycleCopy.setParking(this.isParking());
		motorcycleCopy.setParkingOrders(this.getParkingOrders());
		motorcycleCopy.setPlate(this.getPlate());
		motorcycleCopy.setType(this.getType());
		motorcycleCopy.setCc(this.getCc());
		return motorcycleCopy;
	}

}
