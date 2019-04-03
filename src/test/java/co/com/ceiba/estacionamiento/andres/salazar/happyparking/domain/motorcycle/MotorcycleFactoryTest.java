package co.com.ceiba.estacionamiento.andres.salazar.happyparking.domain.motorcycle;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MotorcycleFactoryTest {
	
	@Autowired
	private MotorcycleFactory motorcycleFactory;

	@Test
	public void testGetObject() throws Exception {
		Motorcycle motorcycle = motorcycleFactory.getObject();
		assertThat(motorcycle).isNotNull();
	}

	@Test
	public void testGetObjectType() {
		Class<?> classType = motorcycleFactory.getObjectType();
		assertThat(classType).isEqualTo(Motorcycle.class);
	}

	@Test
	public void testIsSingleton() {
		boolean singleton = motorcycleFactory.isSingleton();
		assertThat(singleton).isEqualTo(false);
	}

}
