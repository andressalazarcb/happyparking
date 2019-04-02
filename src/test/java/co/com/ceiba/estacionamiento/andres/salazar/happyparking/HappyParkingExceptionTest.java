package co.com.ceiba.estacionamiento.andres.salazar.happyparking;

import org.junit.Test;

public class HappyParkingExceptionTest {

	@Test(expected = HappyParkingException.class)
	public void testExceptionWithMessage() {
		throw new HappyParkingException("Hello World");
	}

}
