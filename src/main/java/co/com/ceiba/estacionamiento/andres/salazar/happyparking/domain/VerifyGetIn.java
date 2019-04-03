package co.com.ceiba.estacionamiento.andres.salazar.happyparking.domain;

public interface VerifyGetIn<T> {
	
	public HappyParkingException check(T vehicle);

}
