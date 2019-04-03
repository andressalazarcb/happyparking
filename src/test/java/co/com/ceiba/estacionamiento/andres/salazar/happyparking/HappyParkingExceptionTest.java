package co.com.ceiba.estacionamiento.andres.salazar.happyparking;

import org.junit.Test;

import co.com.ceiba.estacionamiento.andres.salazar.happyparking.jersey.HappyParkingException;

public class HappyParkingExceptionTest {

	@Test(expected = HappyParkingException.class)
	public void testExceptionWithMessage() {
		throw new HappyParkingException("Hello World");
	}

}
