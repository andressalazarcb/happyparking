package co.com.ceiba.estacionamiento.andres.salazar.happyparking.infraestructure.jersey.motorcycle;

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
public class MotorcycleControllerJerseyGetInIntegrationTest {
	
	private String url = "/parkinglot/motorcycles/";
	private String plateField = "plate";
	private String parkingField = "parking";
	
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
		String plate = "AAA123";
		String requestJson = getJson(plate);
		
		//Act
		ResponseEntity<HappyParkingResponse> entity = restTemplate.postForEntity(url, getRequest(requestJson), HappyParkingResponse.class);
		
		//Assert
        assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(entity.getBody()).isInstanceOf(HappyParkingResponse.class);
        assertThat(entity.getBody().getStatus()).isEqualTo(Status.CREATED.getStatusCode());
        assertThat(entity.getBody().getContent()).extracting(plateField).contains(plate);
        assertThat(entity.getBody().getContent()).extracting(parkingField).contains(true);
	}
	
	@Test
	public void testGetInPlateEmpty() {
		//Arrange
		String requestJson = getJson("");
		
		//Act
		ResponseEntity<HappyParkingResponse> entity = restTemplate.postForEntity(url, getRequest(requestJson), HappyParkingResponse.class);
		
		//Assert
        assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(entity.getBody()).isInstanceOf(HappyParkingResponse.class);
        assertThat(entity.getBody().getStatus()).isEqualTo(Status.NOT_FOUND.getStatusCode());
        assertThat(entity.getBody().getContent()).isEqualTo("The vehicle plate is required");
	}
	
	@Test
	public void testGetInNullRequest() {
		//Arrange
		String requestJson = null;
		
		//Act
		ResponseEntity<HappyParkingResponse> entity = restTemplate.postForEntity(url, getRequest(requestJson), HappyParkingResponse.class);
		
		//Assert
        assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
        assertThat(entity.getBody()).isInstanceOf(HappyParkingResponse.class);
        assertThat(entity.getBody().getStatus()).isEqualTo(Status.INTERNAL_SERVER_ERROR.getStatusCode());
        assertThat(entity.getBody().getContent()).isEqualTo("Something goes wrong, we are fixing soon as posible");
	}
	
	@Test
	public void testGetInThereAreNotSpaceToParking() {		
		//Arrange
		String plate = "POA123";
		setupDatabase(10);
		String requestJson = getJson(plate);
		
		//Act
		ResponseEntity<HappyParkingResponse> entity = restTemplate.postForEntity(url, getRequest(requestJson), HappyParkingResponse.class);
		
		//Assert
        assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(entity.getBody()).isInstanceOf(HappyParkingResponse.class);
        assertThat(entity.getBody().getStatus()).isEqualTo(Status.NOT_FOUND.getStatusCode());
        assertThat(entity.getBody().getContent()).isEqualTo("There are not space for motorcycles");
		
	}
	
	@Test
	public void testGetInExistSameMotorcycleInParking() {		
		//Arrange
		String plate = "WAA123";
		setupDatabase(plate);
		String requestJson = getJson(plate);
		
		//Act
		ResponseEntity<HappyParkingResponse> entity = restTemplate.postForEntity(url, getRequest(requestJson), HappyParkingResponse.class);
				
		//Assert
        assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(entity.getBody()).isInstanceOf(HappyParkingResponse.class);
        assertThat(entity.getBody().getStatus()).isEqualTo(Status.NOT_FOUND.getStatusCode());
        assertThat(entity.getBody().getContent()).isEqualTo("There is a vehicle already in parking lot");
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
	
	private String getJson(String plate) {
		return "{\"plate\":\"" + plate + "\"}";
	}

}
