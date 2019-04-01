package co.com.ceiba.estacionamiento.andres.salazar.happyparking.car;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import co.com.ceiba.estacionamiento.andres.salazar.happyparking.domain.Car;
import co.com.ceiba.estacionamiento.andres.salazar.happyparking.domain.CarBuilder;
import co.com.ceiba.estacionamiento.andres.salazar.happyparking.domain.ParkingOrder;
import co.com.ceiba.estacionamiento.andres.salazar.happyparking.domain.ParkingOrderBuilder;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderParkingCarRepositoryTest {
	
	@MockBean
	private CarRepository repositoryParkingCar;
	
	@Autowired
	private ParkingOrderBuilder parkingOrderBuilder;
	
	@Autowired
	private CarBuilder carBuilder;

	@Before
	public void setUp() throws Exception {
		
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testFindById() {
		//Arrange
		String vehiclePlateParam = "AAA321";
		
		ParkingOrder parkingOrder = parkingOrderBuilder.getParkingOrder();
		
		Car carValue = carBuilder
				.buildPlate(vehiclePlateParam)
				.buildAddParkingOrder(parkingOrder)
				.getCar();
		
		when(repositoryParkingCar.findById(vehiclePlateParam)).thenReturn(Optional.of(carValue));
		
		//Act
		Optional<Car> carFound = repositoryParkingCar.findById(vehiclePlateParam);
		
		//Assert
		assertThat(carFound.isPresent()).isEqualTo(true);
		if(carFound.isPresent()) {
			Car car = carFound.get();
			assertThat(car).isInstanceOf(Car.class);
			assertThat(car).hasFieldOrPropertyWithValue("plate", vehiclePlateParam);
			assertThat(car).hasFieldOrPropertyWithValue("isParking", true);
		}
		
	}
	
}
