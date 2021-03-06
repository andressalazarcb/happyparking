package co.com.ceiba.estacionamiento.andres.salazar.happyparking.domain;

import org.junit.Test;

import co.com.ceiba.estacionamiento.andres.salazar.happyparking.domain.exception.HappyParkingSystemException;

public class HappyParkingSystemExceptionTest {

	@Test(expected = HappyParkingSystemException.class)
	public void test() {
		throw new HappyParkingSystemException();
	}
	
	@Test(expected = HappyParkingSystemException.class)
	public void testNestedException() {
		throw new HappyParkingSystemException(new NullPointerException());
	}

}
