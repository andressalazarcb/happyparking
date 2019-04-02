package co.com.ceiba.estacionamiento.andres.salazar.happyparking.car;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

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

import co.com.ceiba.estacionamiento.andres.salazar.happyparking.domain.Car;
import co.com.ceiba.estacionamiento.andres.salazar.happyparking.domain.HappyParkingResponse;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class CarControllerGitInTest {

	@Autowired
	private TestRestTemplate restTemplate;

	@MockBean
	private CarRepositoryMongo carRepository;

	@MockBean
	private HappyParkingTime happyParkingTime;

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
		assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.CREATED);
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
		assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.CREATED);
		assertThat(entity.getBody()).isInstanceOf(HappyParkingResponse.class);
		assertThat(entity.getBody().getStatus()).isEqualTo(Status.CREATED.getStatusCode());
		assertThat(entity.getBody().getContent()).extracting("plate").contains("AAA123");
		assertThat(entity.getBody().getContent()).extracting("parking").contains(true);
	}

	@Test
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
		ResponseEntity<HappyParkingResponse> entity = restTemplate.postForEntity(url, request, HappyParkingResponse.class);
		
		// Assert
		assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
		assertThat(entity.getBody()).isInstanceOf(HappyParkingResponse.class);
		assertThat(entity.getBody().getStatus()).isEqualTo(Status.NOT_FOUND.getStatusCode());
		assertThat(entity.getBody().getContent()).isEqualTo("no puede ingresar porque no esta en un dia habil");
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
		assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.CREATED);
		assertThat(entity.getBody()).isInstanceOf(HappyParkingResponse.class);
		assertThat(entity.getBody().getStatus()).isEqualTo(Status.CREATED.getStatusCode());
		assertThat(entity.getBody().getContent()).extracting("plate").contains("BAA123");
		assertThat(entity.getBody().getContent()).extracting("parking").contains(true);
	}
	
	
	@Test
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
		ResponseEntity<HappyParkingResponse> entity = restTemplate.postForEntity(url, request, HappyParkingResponse.class);

		// Assert
		assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
		assertThat(entity.getBody()).isInstanceOf(HappyParkingResponse.class);
		assertThat(entity.getBody().getStatus()).isEqualTo(Status.NOT_FOUND.getStatusCode());
		assertThat(entity.getBody().getContent()).isEqualTo("hay un carro parqueado");
	}
	
	@Test
	public void testGetInThereAreNotSpaceToParking() {
		// Arange
		String url = "/parkinglot/cars/";
		String requestJson = "{\"plate\":\"AAA123\"}";
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> request = new HttpEntity<>(requestJson, headers);
		when(carRepository.findCountCarsByIsParking(true)).thenReturn(20L);

		// Act
		ResponseEntity<HappyParkingResponse> entity = restTemplate.postForEntity(url, request, HappyParkingResponse.class);
		
		// Assert
		assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
		assertThat(entity.getBody()).isInstanceOf(HappyParkingResponse.class);
		assertThat(entity.getBody().getStatus()).isEqualTo(Status.NOT_FOUND.getStatusCode());
		assertThat(entity.getBody().getContent()).isEqualTo("no hay espacio para carros");
	}


}
