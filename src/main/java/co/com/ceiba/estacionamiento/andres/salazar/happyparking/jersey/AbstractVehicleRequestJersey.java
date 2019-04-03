package co.com.ceiba.estacionamiento.andres.salazar.happyparking.jersey;

public abstract class AbstractVehicleRequestJersey {
	
	private String plate;
	
	public String getPlate() {
		if(this.plate == null || this.plate.trim().isEmpty()) {
			throw new HappyParkingException("La placa es requerida");
		}
		return plate;
	}

	public void setPlate(String plate) {
		this.plate = plate;
	}

}
