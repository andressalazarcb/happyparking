package co.com.ceiba.estacionamiento.andres.salazar.happyparking.domain;

public interface VerifyGetIn<T> {

	HappyParkingException check(T vehicle);

}
