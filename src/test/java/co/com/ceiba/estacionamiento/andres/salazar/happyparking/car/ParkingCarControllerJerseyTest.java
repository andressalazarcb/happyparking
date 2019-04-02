package co.com.ceiba.estacionamiento.andres.salazar.happyparking.car;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.Optional;

import javax.ws.rs.core.Response.Status;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestClientException;

import co.com.ceiba.estacionamiento.andres.salazar.happyparking.domain.Car;
import co.com.ceiba.estacionamiento.andres.salazar.happyparking.domain.CarBuilder;
import co.com.ceiba.estacionamiento.andres.salazar.happyparking.domain.HappyParkingResponse;
import co.com.ceiba.estacionamiento.andres.salazar.happyparking.domain.ParkingOrder;
import co.com.ceiba.estacionamiento.andres.salazar.happyparking.domain.ParkingOrderBuilder;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class ParkingCarControllerJerseyTest {

	@Autowired
	private TestRestTemplate restTemplate;

	@MockBean
	private CarRepository carRepository;

	@MockBean
	private HappyParkingTime happyParkingTime;

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
	public void testGetIn() {
		// Arange
		String plate = "AAA123";
		String url = "/parkinglot/cars/";
		String requestJson = "{\"plate\":\"AAA123\"}";
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> request = new HttpEntity<>(requestJson, headers);

		Car carEntity = new Car();
		carEntity.setPlate(plate);
		carEntity.setParking(true);

		when(happyParkingTime.getDay()).thenReturn("Sunday");
		when(carRepository.save(any(Car.class))).thenReturn(carEntity);

		// Act
		ResponseEntity<HappyParkingResponse> entity = restTemplate.postForEntity(url, request,
				HappyParkingResponse.class);

		// Assert
		assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(entity.getBody()).isInstanceOf(HappyParkingResponse.class);
		assertThat(entity.getBody().getStatus()).isEqualTo(Status.CREATED.getStatusCode());
		assertThat(entity.getBody().getContent()).extracting("plate").contains("AAA123");
		assertThat(entity.getBody().getContent()).extracting("parking").contains(true);
	}

	@Test
	public void testGetInPlateStartWithAAndSunday() {
		// Arange
		String plate = "AAA123";
		String url = "/parkinglot/cars/";
		String requestJson = "{\"plate\":\"AAA123\"}";
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> request = new HttpEntity<>(requestJson, headers);

		Car carEntity = new Car();
		carEntity.setPlate(plate);
		carEntity.setParking(true);

		when(carRepository.save(any(Car.class))).thenReturn(carEntity);
		when(happyParkingTime.getDay()).thenReturn("Sunday");

		// Act
		ResponseEntity<HappyParkingResponse> entity = restTemplate.postForEntity(url, request,
				HappyParkingResponse.class);

		// Assert
		assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(entity.getBody()).isInstanceOf(HappyParkingResponse.class);
		assertThat(entity.getBody().getStatus()).isEqualTo(Status.CREATED.getStatusCode());
		assertThat(entity.getBody().getContent()).extracting("plate").contains("AAA123");
		assertThat(entity.getBody().getContent()).extracting("parking").contains(true);
	}

	@Test(expected = RestClientException.class)
	public void testGetInPlateStartWithAAndWednesday() {
		// Arange
		String plate = "AAA123";
		String url = "/parkinglot/cars/";
		String requestJson = "{\"plate\":\"AAA123\"}";
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> request = new HttpEntity<>(requestJson, headers);

		Car carEntity = new Car();
		carEntity.setPlate(plate);
		carEntity.setParking(true);

		when(carRepository.save(any(Car.class))).thenReturn(carEntity);
		when(happyParkingTime.getDay()).thenReturn("Wednesday");

		// Act
		restTemplate.postForEntity(url, request, HappyParkingResponse.class);

	}

	@Test
	public void testGetInPlateStartWithBAndMonday() {
		// Arange
		String plate = "BAA123";
		String url = "/parkinglot/cars/";
		String requestJson = "{\"plate\":\"BAA123\"}";
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> request = new HttpEntity<>(requestJson, headers);

		Car carEntity = new Car();
		carEntity.setPlate(plate);
		carEntity.setParking(true);

		when(carRepository.save(any(Car.class))).thenReturn(carEntity);
		when(happyParkingTime.getDay()).thenReturn("Monday");

		// Act
		ResponseEntity<HappyParkingResponse> entity = restTemplate.postForEntity(url, request,
				HappyParkingResponse.class);

		// Assert
		assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(entity.getBody()).isInstanceOf(HappyParkingResponse.class);
		assertThat(entity.getBody().getStatus()).isEqualTo(Status.CREATED.getStatusCode());
		assertThat(entity.getBody().getContent()).extracting("plate").contains("BAA123");
		assertThat(entity.getBody().getContent()).extracting("parking").contains(true);
	}
	
	
	@Test(expected = RestClientException.class)
	public void testGetInExistSameCarInParking() {
		// Arange
		String plate = "AAA123";
		String url = "/parkinglot/cars/";
		String requestJson = "{\"plate\":\"AAA123\"}";
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> request = new HttpEntity<>(requestJson, headers);

		Car carEntity = new Car();
		carEntity.setPlate(plate);
		carEntity.setParking(true);

		when(carRepository.findCarByPlateAndIsParking(plate, true)).thenReturn(new Car());

		// Act
		restTemplate.postForEntity(url, request, HappyParkingResponse.class);

	}
	
	@Test(expected = RestClientException.class)
	public void testGetInThereAreNotSpaceToParking() {
		// Arange
		String url = "/parkinglot/cars/";
		String requestJson = "{\"plate\":\"AAA123\"}";
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> request = new HttpEntity<>(requestJson, headers);
		when(carRepository.findCountCarsByIsParking(true)).thenReturn(20L);

		// Act
		restTemplate.postForEntity(url, request, HappyParkingResponse.class);
	}
	

	@Test
	public void testFindVehicle() {
		String vehiclePlateParam = "AAA321";

		ParkingOrder parkingOrder = parkingOrderBuilder.getParkingOrder();

		Car carValue = carBuilder.buildPlate(vehiclePlateParam).buildAddParkingOrder(parkingOrder).getCar();

		when(carRepository.findById(vehiclePlateParam)).thenReturn(Optional.of(carValue));

		ResponseEntity<HappyParkingResponse> entity = this.restTemplate.getForEntity("/parkinglot/cars/AAA321",
				HappyParkingResponse.class);
		assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(entity.getBody()).isInstanceOf(HappyParkingResponse.class);
		assertThat(entity.getBody().getStatus()).isEqualTo(Status.OK.getStatusCode());
		assertThat(entity.getBody().getContent()).extracting("plate").contains(vehiclePlateParam);
		assertThat(entity.getBody().getContent()).extracting("parking").contains(true);
	}

}
