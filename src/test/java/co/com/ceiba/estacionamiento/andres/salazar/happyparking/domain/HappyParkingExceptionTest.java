package co.com.ceiba.estacionamiento.andres.salazar.happyparking.domain;

import org.junit.Test;

import co.com.ceiba.estacionamiento.andres.salazar.happyparking.domain.exception.HappyParkingException;

public class HappyParkingExceptionTest {

	@Test(expected = HappyParkingException.class)
	public void testHappyParkingExceptionString() {
		throw new HappyParkingException("Hola mundo");
	}

}
