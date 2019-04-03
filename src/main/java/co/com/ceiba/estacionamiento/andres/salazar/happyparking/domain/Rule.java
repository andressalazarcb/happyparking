package co.com.ceiba.estacionamiento.andres.salazar.happyparking.domain;

public interface Rule<T> {
	
	HappyParkingException excecute(T vehicle);

}
