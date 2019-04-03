package co.com.ceiba.estacionamiento.andres.salazar.happyparking.domain.parkingorder;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

public class ParkingOrderTest {

	@Test
	public void test() {
		ParkingOrder parkingOrder = new ParkingOrder();
		assertThat(parkingOrder).hasFieldOrProperty("parkingOrderId");
		assertThat(parkingOrder).hasFieldOrProperty("startDate");
		assertThat(parkingOrder).hasFieldOrProperty("endDate");
		assertThat(parkingOrder).hasFieldOrProperty("active");
		assertThat(parkingOrder).hasFieldOrProperty("price");
	}

}
