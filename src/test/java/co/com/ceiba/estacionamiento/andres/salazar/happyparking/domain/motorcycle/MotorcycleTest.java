package co.com.ceiba.estacionamiento.andres.salazar.happyparking.domain.motorcycle;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

public class MotorcycleTest {

	@Test
	public void test() {
		Motorcycle motorcycle = new Motorcycle();
		assertThat(motorcycle).hasFieldOrProperty("plate");
		assertThat(motorcycle).hasFieldOrProperty("type");
		assertThat(motorcycle).hasFieldOrProperty("parkingOrders");
		assertThat(motorcycle).hasFieldOrProperty("isParking");
		assertThat(motorcycle).hasFieldOrProperty("cc");
	}

}
