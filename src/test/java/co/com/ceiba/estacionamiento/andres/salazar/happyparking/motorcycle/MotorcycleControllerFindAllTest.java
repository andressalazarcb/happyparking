package co.com.ceiba.estacionamiento.andres.salazar.happyparking.motorcycle;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import org.junit.After;
import org.junit.Before;
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

import co.com.ceiba.estacionamiento.andres.salazar.happyparking.domain.HappyParkingResponse;
import co.com.ceiba.estacionamiento.andres.salazar.happyparking.domain.Motorcycle;
import co.com.ceiba.estacionamiento.andres.salazar.happyparking.domain.ParkingOrder;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class MotorcycleControllerFindAllTest {
	
	@Autowired
	private TestRestTemplate restTemplate;
	
	@MockBean
	private MotorcycleRepositoryMongo motorcycleRepository;

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testFindAllVehiclesParkingEmpty() {
		String url = "/parkinglot/motorcycles/";
		when(motorcycleRepository.findCountMotorcycleByIsParking(true)).thenReturn(0L);
		when(motorcycleRepository.findAllMotorcyclesByIsParkingTrueAndStream()).thenReturn(null);
		
		ResponseEntity<HappyParkingResponse> entity = restTemplate.getForEntity(url, HappyParkingResponse.class);
		
        assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
        assertThat(entity.getBody()).isEqualTo(null);
	}
	
	@Test
	public void testFindAllVehiclesParking() {
		String url = "/parkinglot/motorcycles/";
		long size = 2L;
		when(motorcycleRepository.findCountMotorcycleByIsParking(true)).thenReturn(size);
		when(motorcycleRepository.findAllMotorcyclesByIsParkingTrueAndStream()).thenReturn(setupDatabase(size));
		
		ResponseEntity<HappyParkingResponse> entity = restTemplate.getForEntity(url, HappyParkingResponse.class);
		
        assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(entity.getBody()).isInstanceOf(HappyParkingResponse.class);
        List<?> list = (List<?>) entity.getBody().getContent();
        for (Object object : list) {
			assertThat(object).extracting("plate").isNotEmpty();
			assertThat(object).extracting("type").contains("Moto");
			assertThat(object).extracting("parking").isNotEmpty();
			assertThat(object).extracting("parkingOrders").isNotEmpty();
		}
	}
	
	private Stream<Motorcycle> setupDatabase(long size) {
		List<Motorcycle> motorcycles = new ArrayList<>();
		for (int i = 0; i < size; i++) {
			Motorcycle motorcycleToSave = new Motorcycle();
			motorcycleToSave.setParking(true);
			motorcycleToSave.setType("Moto");
			ParkingOrder parkingOrder = new ParkingOrder();
			parkingOrder.setActive(true);
			parkingOrder.setStartDate(System.currentTimeMillis());
			motorcycleToSave.setParkingOrders(Arrays.asList(parkingOrder));
			motorcycles.add(motorcycleToSave);
		}
		return motorcycles.stream();
	}
	
}
