package co.com.ceiba.estacionamiento.andres.salazar.happyparking.car;

import static org.assertj.core.api.Assertions.*;

import javax.ws.rs.core.Response.Status;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import co.com.ceiba.estacionamiento.andres.salazar.happyparking.domain.Car;
import co.com.ceiba.estacionamiento.andres.salazar.happyparking.jersey.HappyParkingResponse;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class CarControllerGetInIntegrationTest {
	
	@Autowired
    private TestRestTemplate restTemplate;
	
	@Autowired
	private CarRepositoryMongo carRepository;
	
	@After
	public void tearDown(){
		carRepository.deleteAll();
	}

	@Test
	public void testGetIn() {
		String url = "/parkinglot/cars/";
		String requestJson = "{\"plate\":\"KLA123\"}";
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> request = new HttpEntity<>(requestJson, headers);
		
		ResponseEntity<HappyParkingResponse> entity = restTemplate.postForEntity(url, request, HappyParkingResponse.class);
		
        assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(entity.getBody()).isInstanceOf(HappyParkingResponse.class);
        assertThat(entity.getBody().getStatus()).isEqualTo(Status.CREATED.getStatusCode());
        assertThat(entity.getBody().getContent()).extracting("plate").contains("KLA123");
        assertThat(entity.getBody().getContent()).extracting("parking").contains(true);
	}
	
	@Test
	public void testGetInEmptyPlate() {
		String url = "/parkinglot/cars/";
		String requestJson = "{\"plate\":\"\"}";
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> request = new HttpEntity<>(requestJson, headers);
		
		ResponseEntity<HappyParkingResponse> entity = restTemplate.postForEntity(url, request, HappyParkingResponse.class);
		
        assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(entity.getBody()).isInstanceOf(HappyParkingResponse.class);
        assertThat(entity.getBody().getStatus()).isEqualTo(Status.NOT_FOUND.getStatusCode());
        assertThat(entity.getBody().getContent()).isEqualTo("La placa es requerida");
	}
	
	@Test
	public void testGetInNullRequest() {
		String url = "/parkinglot/cars/";
		String requestJson = null;
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> request = new HttpEntity<>(requestJson, headers);
		
		ResponseEntity<HappyParkingResponse> entity = restTemplate.postForEntity(url, request, HappyParkingResponse.class);
		
        assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
        assertThat(entity.getBody()).isInstanceOf(HappyParkingResponse.class);
        assertThat(entity.getBody().getStatus()).isEqualTo(Status.INTERNAL_SERVER_ERROR.getStatusCode());
        assertThat(entity.getBody().getContent()).isEqualTo("ha ocurrido un error, pronto lo solucionaremos");
	}
	
	@Test
	public void testGetInThereAreNotSpaceToParking() {		
		//Arrange
		setupDatabase(20);
		
		String url = "/parkinglot/cars/";
		String requestJson = "{\"plate\":\"PYA123\"}";
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> request = new HttpEntity<>(requestJson, headers);
		
		// Act
		ResponseEntity<HappyParkingResponse> entity = restTemplate.postForEntity(url, request, HappyParkingResponse.class);
		
		// Assert
		assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
		assertThat(entity.getBody()).isInstanceOf(HappyParkingResponse.class);
		assertThat(entity.getBody().getStatus()).isEqualTo(Status.NOT_FOUND.getStatusCode());
		assertThat(entity.getBody().getContent()).isEqualTo("no hay espacio para carros");
	}
	
	@Test
	public void testGetInExistSameCarInParking() {		
		//Arrange
		setupDatabase("WAA123");
		
		String url = "/parkinglot/cars/";
		String requestJson = "{\"plate\":\"WAA123\"}";
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> request = new HttpEntity<>(requestJson, headers);
		
		// Act
		ResponseEntity<HappyParkingResponse> entity = restTemplate.postForEntity(url, request, HappyParkingResponse.class);

		// Assert
		assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
		assertThat(entity.getBody()).isInstanceOf(HappyParkingResponse.class);
		assertThat(entity.getBody().getStatus()).isEqualTo(Status.NOT_FOUND.getStatusCode());
		assertThat(entity.getBody().getContent()).isEqualTo("hay un carro parqueado");	}
	
	private void setupDatabase(int size) {
		for (int i = 0; i < size; i++) {
			setupDatabase("DFG1"+i);
		}
	}
	
	private void setupDatabase(String plate) {
		Car car = new Car();
		car.setPlate(plate);
		car.setParking(true);
		carRepository.save(car);
	}
	
}
