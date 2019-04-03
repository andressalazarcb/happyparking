package co.com.ceiba.estacionamiento.andres.salazar.happyparking.domain.motorcycle.integration.copy;

import static org.assertj.core.api.Assertions.*;

import javax.ws.rs.core.Response.Status;

import org.junit.After;
import org.junit.Ignore;
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

import co.com.ceiba.estacionamiento.andres.salazar.happyparking.domain.motorcycle.Motorcycle;
import co.com.ceiba.estacionamiento.andres.salazar.happyparking.infraestructure.jersey.HappyParkingResponse;
import co.com.ceiba.estacionamiento.andres.salazar.happyparking.infraestructure.repository.MotorcycleRepositoryMongo;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class MotorcycleControllerGetInIntegrationTest {
	
	@Autowired
    private TestRestTemplate restTemplate;
	
	@Autowired
	private MotorcycleRepositoryMongo motorcycleRepository;

	@After
	public void tearDown(){
		motorcycleRepository.deleteAll();
	}

	@Test
	@Ignore
	public void testGetIn() {
		//Arrange
		String url = "/parkinglot/motorcycles/";
		String requestJson = "{\"plate\":\"AAA123\"}";
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> request = new HttpEntity<>(requestJson, headers);
		
		//Act
		ResponseEntity<HappyParkingResponse> entity = restTemplate.postForEntity(url, request, HappyParkingResponse.class);
		
		//Assert
        assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(entity.getBody()).isInstanceOf(HappyParkingResponse.class);
        assertThat(entity.getBody().getStatus()).isEqualTo(Status.CREATED.getStatusCode());
        assertThat(entity.getBody().getContent()).extracting("plate").contains("AAA123");
        assertThat(entity.getBody().getContent()).extracting("parking").contains(true);
	}
	
	@Test
	public void testGetInPlateEmpty() {
		//Arrange
		String url = "/parkinglot/motorcycles/";
		String requestJson = "{\"plate\":\"\"}";
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> request = new HttpEntity<>(requestJson, headers);
		
		//Act
		ResponseEntity<HappyParkingResponse> entity = restTemplate.postForEntity(url, request, HappyParkingResponse.class);
		
		//Assert
        assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(entity.getBody()).isInstanceOf(HappyParkingResponse.class);
        assertThat(entity.getBody().getStatus()).isEqualTo(Status.NOT_FOUND.getStatusCode());
        assertThat(entity.getBody().getContent()).isEqualTo("La placa es requerida");
	}
	
	@Test
	public void testGetInNullRequest() {
		//Arrange
		String url = "/parkinglot/motorcycles/";
		String requestJson = null;
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> request = new HttpEntity<>(requestJson, headers);
		
		//Act
		ResponseEntity<HappyParkingResponse> entity = restTemplate.postForEntity(url, request, HappyParkingResponse.class);
		
		//Assert
        assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
        assertThat(entity.getBody()).isInstanceOf(HappyParkingResponse.class);
        assertThat(entity.getBody().getStatus()).isEqualTo(Status.INTERNAL_SERVER_ERROR.getStatusCode());
        assertThat(entity.getBody().getContent()).isEqualTo("ha ocurrido un error, pronto lo solucionaremos");
	}
	
	@Test
	@Ignore
	public void testGetInThereAreNotSpaceToParking() {		
		//Arrange
		setupDatabase(10);
		
		String url = "/parkinglot/motorcycles/";
		String requestJson = "{\"plate\":\"POA123\"}";
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> request = new HttpEntity<>(requestJson, headers);
		
		//Act
		ResponseEntity<HappyParkingResponse> entity = restTemplate.postForEntity(url, request, HappyParkingResponse.class);
		
		//Assert
        assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(entity.getBody()).isInstanceOf(HappyParkingResponse.class);
        assertThat(entity.getBody().getStatus()).isEqualTo(Status.NOT_FOUND.getStatusCode());
        assertThat(entity.getBody().getContent()).isEqualTo("no hay espacio para motos");
		
	}
	
	@Test
	@Ignore
	public void testGetInExistSameMotorcycleInParking() {		
		//Arrange
		setupDatabase("WAA123");
		
		String url = "/parkinglot/motorcycles/";
		String requestJson = "{\"plate\":\"WAA123\"}";
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> request = new HttpEntity<>(requestJson, headers);
		
		//Act
		ResponseEntity<HappyParkingResponse> entity = restTemplate.postForEntity(url, request, HappyParkingResponse.class);
				
		//Assert
        assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(entity.getBody()).isInstanceOf(HappyParkingResponse.class);
        assertThat(entity.getBody().getStatus()).isEqualTo(Status.NOT_FOUND.getStatusCode());
        assertThat(entity.getBody().getContent()).isEqualTo("hay una moto parqueada");
	}
	
	private void setupDatabase(int size) {
		for (int i = 0; i < size; i++) {
			setupDatabase("DFG1"+i);
		}
	}
	
	private void setupDatabase(String plate) {
		Motorcycle motorcycle = new Motorcycle();
		motorcycle.setPlate(plate);
		motorcycle.setParking(true);
		motorcycleRepository.save(motorcycle);
	}

}
