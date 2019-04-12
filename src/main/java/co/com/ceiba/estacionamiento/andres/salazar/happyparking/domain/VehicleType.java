package co.com.ceiba.estacionamiento.andres.salazar.happyparking.domain;

public enum VehicleType {

	CAR("Carro"), MOTORCYCLE("Moto");

	private String value;

	private VehicleType(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

}
