package co.com.ceiba.estacionamiento.andres.salazar.happyparking.domain.car;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

public class CarTest {

	@Test
	public void test() {
		Car car = new Car();
		assertFields(car);
	}

	@Test
	public void testCopyOne() {
		Car currentCar = CarTestBuilder.create().build();
		Car car = new Car();
		car.copy(currentCar);
		assertFields(car);
	}

	@Test
	public void testCopyTwo() {
		Car currentCar = CarTestBuilder.create().withParkingOrders(null).build();
		Car car = new Car();
		car.copy(currentCar);
		assertFields(car);
	}

	private void assertFields(Car car) {
		assertThat(car).hasFieldOrProperty("plate");
		assertThat(car).hasFieldOrProperty("type");
		assertThat(car).hasFieldOrProperty("parkingOrders");
		assertThat(car).hasFieldOrProperty("isParking");
	}

}
