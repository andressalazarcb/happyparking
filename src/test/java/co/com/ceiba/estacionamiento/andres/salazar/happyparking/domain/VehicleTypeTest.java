package co.com.ceiba.estacionamiento.andres.salazar.happyparking.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

public class VehicleTypeTest {

	@Test
	public void testGetValueCar() {
		String value = VehicleType.CAR.getValue();
		assertThat(value).isEqualTo("Carro");
	}
	
	@Test
	public void testGetValueMotorcycle() {
		String value = VehicleType.MOTORCYCLE.getValue();
		assertThat(value).isEqualTo("Moto");
	}

}
