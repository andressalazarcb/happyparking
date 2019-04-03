package co.com.ceiba.estacionamiento.andres.salazar.happyparking.domain.car;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CarFactoryTest {

	@Autowired
	private CarFactory carFactory;
	
	@Test
	public void testGetObject() throws Exception {
		Car car = carFactory.getObject();
		assertThat(car).isNotNull();
	}

	@Test
	public void testGetObjectType() {
		Class<?> classType = carFactory.getObjectType();
		assertThat(classType).isEqualTo(Car.class);
	}

	@Test
	public void testIsSingleton() {
		boolean singleton = carFactory.isSingleton();
		assertThat(singleton).isEqualTo(false);
	}

}
