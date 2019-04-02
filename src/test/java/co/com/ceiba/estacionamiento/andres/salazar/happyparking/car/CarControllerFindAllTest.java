package co.com.ceiba.estacionamiento.andres.salazar.happyparking.car;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import co.com.ceiba.estacionamiento.andres.salazar.happyparking.domain.Car;
import co.com.ceiba.estacionamiento.andres.salazar.happyparking.domain.HappyParkingResponse;
import co.com.ceiba.estacionamiento.andres.salazar.happyparking.domain.ParkingOrder;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class CarControllerFindAllTest {

	@Autowired
	private TestRestTemplate restTemplate;
	
	@MockBean
	private CarRepositoryMongo carRepository;

	@Test
	public void testFindAllVehiclesParkingEmpty() {
		String url = "/parkinglot/cars/";
		when(carRepository.findCountCarsByIsParking(true)).thenReturn(0L);
		when(carRepository.findAllCarsByIsParkingTrueAndStream()).thenReturn(null);
		
		ResponseEntity<HappyParkingResponse> entity = restTemplate.getForEntity(url, HappyParkingResponse.class);
		
        assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
        assertThat(entity.getBody()).isEqualTo(null);
	}
	
	@Test
	public void testFindAllVehiclesParking() {
		String url = "/parkinglot/cars/";
		long size = 2L;
		when(carRepository.findCountCarsByIsParking(true)).thenReturn(size);
		when(carRepository.findAllCarsByIsParkingTrueAndStream()).thenReturn(setupDatabase(size));
		
		ResponseEntity<HappyParkingResponse> entity = restTemplate.getForEntity(url, HappyParkingResponse.class);
		
        assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(entity.getBody()).isInstanceOf(HappyParkingResponse.class);
        List<?> list = (List<?>) entity.getBody().getContent();
        for (Object object : list) {
			assertThat(object).extracting("plate").isNotEmpty();
			assertThat(object).extracting("type").contains("Carro");
			assertThat(object).extracting("parking").isNotEmpty();
			assertThat(object).extracting("parkingOrders").isNotEmpty();
		}
	}
	
	private Stream<Car> setupDatabase(long size) {
		List<Car> cars = new ArrayList<>();
		for (int i = 0; i < size; i++) {
			Car carToSave = new Car();
			carToSave.setParking(true);
			carToSave.setType("Carro");
			ParkingOrder parkingOrder = new ParkingOrder();
			parkingOrder.setActive(true);
			parkingOrder.setStartDate(System.currentTimeMillis());
			carToSave.setParkingOrders(Arrays.asList(parkingOrder));
			cars.add(carToSave);
		}
		return cars.stream();
	}

}
