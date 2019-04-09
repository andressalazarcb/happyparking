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

import co.com.ceiba.estacionamiento.andres.salazar.happyparking.domain.motorcycle.Motorcycle;
import co.com.ceiba.estacionamiento.andres.salazar.happyparking.domain.motorcycle.MotorcycleTestBuilder;
import co.com.ceiba.estacionamiento.andres.salazar.happyparking.infraestructure.jersey.HappyParkingResponse;
import co.com.ceiba.estacionamiento.andres.salazar.happyparking.infraestructure.repository.MotorcycleRepositoryMongo;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class MotorcycleControllerGetInIntegrationTest {
	
	private String url = "/parkinglot/motorcycles/";
	
	@Autowired
    private TestRestTemplate restTemplate;
	
	@Autowired
	private MotorcycleRepositoryMongo motorcycleRepository;

	@After
	public void tearDown(){
		motorcycleRepository.deleteAll();
	}

	@Test
	public void testGetIn() {
		//Arrange
		String requestJson = "{\"plate\":\"AAA123\"}";
		
		//Act
		ResponseEntity<HappyParkingResponse> entity = restTemplate.postForEntity(url, getRequest(requestJson), HappyParkingResponse.class);
		
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
		String requestJson = "{\"plate\":\"\"}";
		
		//Act
		ResponseEntity<HappyParkingResponse> entity = restTemplate.postForEntity(url, getRequest(requestJson), HappyParkingResponse.class);
		
		//Assert
        assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(entity.getBody()).isInstanceOf(HappyParkingResponse.class);
        assertThat(entity.getBody().getStatus()).isEqualTo(Status.NOT_FOUND.getStatusCode());
        assertThat(entity.getBody().getContent()).isEqualTo("La placa es requerida");
	}
	
	@Test
	public void testGetInNullRequest() {
		//Arrange
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
	public void testGetInThereAreNotSpaceToParking() {		
		//Arrange
		setupDatabase(10);
		
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
	public void testGetInExistSameMotorcycleInParking() {		
		//Arrange
		String plate = "WAA123";
		setupDatabase(plate);
		String requestJson = "{\"plate\":\""+plate+"\"}";
		
		//Act
		ResponseEntity<HappyParkingResponse> entity = restTemplate.postForEntity(url, getRequest(requestJson), HappyParkingResponse.class);
				
		//Assert
        assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(entity.getBody()).isInstanceOf(HappyParkingResponse.class);
        assertThat(entity.getBody().getStatus()).isEqualTo(Status.NOT_FOUND.getStatusCode());
        assertThat(entity.getBody().getContent()).isEqualTo("hay un vehiculo parqueado");
	}
	
	private void setupDatabase(int size) {
		for (int i = 0; i < size; i++) {
			setupDatabase("DFG1"+i);
		}
	}
	
	private void setupDatabase(String plate) {
		Motorcycle motorcycle = MotorcycleTestBuilder.create()
				.withIsParking()
				.withPlate(plate)
				.build();
		motorcycleRepository.save(motorcycle);
	}
	
	private HttpEntity<String> getRequest(String json){
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		return new HttpEntity<>(json, headers);
	}

}
