package co.com.ceiba.estacionamiento.andres.salazar.happyparking.infraestructure.repository;

enum VehicleType {

	CAR("Carro"), MOTORCYCLE("Moto");

	private String value;

	private VehicleType(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

}
