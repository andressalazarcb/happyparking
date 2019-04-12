package co.com.ceiba.estacionamiento.andres.salazar.happyparking.domain.parkingorder;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ParkingOrderFactoryTest {

	@Autowired
	private ParkingOrderFactory parkingOrderFactory;

	@Test
	public void testGetObject() throws Exception {
		ParkingOrder parkingOrder = parkingOrderFactory.getObject();
		assertThat(parkingOrder).isNotNull();
	}

	@Test
	public void testGetObjectType() {
		Class<?> classType = parkingOrderFactory.getObjectType();
		assertThat(classType).isEqualTo(ParkingOrder.class);
	}

	@Test
	public void testIsSingleton() {
		boolean singleton = parkingOrderFactory.isSingleton();
		assertThat(singleton).isEqualTo(false);
	}

}
