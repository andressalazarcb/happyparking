package co.com.ceiba.estacionamiento.andres.salazar.happyparking.integration;

import static org.assertj.core.api.Assertions.assertThat;

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

import co.com.ceiba.estacionamiento.andres.salazar.happyparking.domain.car.Car;
import co.com.ceiba.estacionamiento.andres.salazar.happyparking.domain.car.CarTestBuilder;
import co.com.ceiba.estacionamiento.andres.salazar.happyparking.infraestructure.jersey.HappyParkingResponse;
import co.com.ceiba.estacionamiento.andres.salazar.happyparking.infraestructure.repository.CarRepositoryMongo;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class CarControllerGetInIntegrationTest {
	
	private String url = "/parkinglot/cars/";
	
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
		String requestJson = "{\"plate\":\"KLA123\"}";
		
		ResponseEntity<HappyParkingResponse> entity = restTemplate.postForEntity(url, getRequest(requestJson), HappyParkingResponse.class);
		
        assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(entity.getBody()).isInstanceOf(HappyParkingResponse.class);
        assertThat(entity.getBody().getStatus()).isEqualTo(Status.CREATED.getStatusCode());
        assertThat(entity.getBody().getContent()).extracting("plate").contains("KLA123");
        assertThat(entity.getBody().getContent()).extracting("parking").contains(true);
	}
	
	@Test
	public void testGetInEmptyPlate() {
		String requestJson = "{\"plate\":\"\"}";
		
		ResponseEntity<HappyParkingResponse> entity = restTemplate.postForEntity(url, getRequest(requestJson), HappyParkingResponse.class);
		
        assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(entity.getBody()).isInstanceOf(HappyParkingResponse.class);
        assertThat(entity.getBody().getStatus()).isEqualTo(Status.NOT_FOUND.getStatusCode());
        assertThat(entity.getBody().getContent()).isEqualTo("La placa es requerida");
	}
	
	@Test
	public void testGetInNullRequest() {
		String requestJson = null;
		
		ResponseEntity<HappyParkingResponse> entity = restTemplate.postForEntity(url, getRequest(requestJson), HappyParkingResponse.class);
		
        assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
        assertThat(entity.getBody()).isInstanceOf(HappyParkingResponse.class);
        assertThat(entity.getBody().getStatus()).isEqualTo(Status.INTERNAL_SERVER_ERROR.getStatusCode());
        assertThat(entity.getBody().getContent()).isEqualTo("ha ocurrido un error, pronto lo solucionaremos");
	}
	
	@Test
	public void testGetInThereAreNotSpaceToParking() {		
		//Arrange
		setupDatabase(20);
		String plate = "PYA123";
		String requestJson = "{\"plate\":\""+plate+"\"}";
		
		// Act
		ResponseEntity<HappyParkingResponse> entity = restTemplate.postForEntity(url, getRequest(requestJson), HappyParkingResponse.class);
		
		// Assert
		assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
		assertThat(entity.getBody()).isInstanceOf(HappyParkingResponse.class);
		assertThat(entity.getBody().getStatus()).isEqualTo(Status.NOT_FOUND.getStatusCode());
		assertThat(entity.getBody().getContent()).isEqualTo("no hay espacio para carros");
	}
	
	@Test
	public void testGetInExistSameCarInParking() {		
		//Arrange
		String plate = "WAA123";
		setupDatabase(plate);
		String requestJson = "{\"plate\":\""+plate+"\"}";
		
		// Act
		ResponseEntity<HappyParkingResponse> entity = restTemplate.postForEntity(url, getRequest(requestJson), HappyParkingResponse.class);

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
		
		Car car = CarTestBuilder.create()
				.withPlate(plate)
				.withIsParking()
				.build();
		carRepository.save(car);
	}
	
	private HttpEntity<String> getRequest(String json){
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		return new HttpEntity<>(json, headers);
	}
	
}
