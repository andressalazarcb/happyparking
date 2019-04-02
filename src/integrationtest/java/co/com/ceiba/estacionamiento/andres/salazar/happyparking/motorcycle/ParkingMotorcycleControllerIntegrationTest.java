package co.com.ceiba.estacionamiento.andres.salazar.happyparking.motorcycle;

import static org.assertj.core.api.Assertions.*;

import javax.ws.rs.core.Response.Status;

import org.junit.After;
import org.junit.Before;
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
import org.springframework.web.client.RestClientException;

import co.com.ceiba.estacionamiento.andres.salazar.happyparking.domain.HappyParkingResponse;
import co.com.ceiba.estacionamiento.andres.salazar.happyparking.domain.Motorcycle;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class ParkingMotorcycleControllerIntegrationTest {
	
	@Autowired
    private TestRestTemplate restTemplate;
	
	@Autowired
	private MotorcycleRepository motorcycleRepository;

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
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
        assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(entity.getBody()).isInstanceOf(HappyParkingResponse.class);
        assertThat(entity.getBody().getStatus()).isEqualTo(Status.CREATED.getStatusCode());
        assertThat(entity.getBody().getContent()).extracting("plate").contains("AAA123");
        assertThat(entity.getBody().getContent()).extracting("parking").contains(true);
	}
	
	@Test(expected = RestClientException.class)
	public void testGetInThereAreNotSpaceToParking() {		
		//Arrange
		setupDatabase(10);
		
		String url = "/parkinglot/motorcycles/";
		String requestJson = "{\"plate\":\"WAA123\"}";
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> request = new HttpEntity<>(requestJson, headers);
		
		//Act
		restTemplate.postForEntity(url, request, HappyParkingResponse.class);
	}
	
	@Test(expected = RestClientException.class)
	public void testGetInExistSameMotorcycleInParking() {		
		//Arrange
		setupDatabase("WAA123");
		
		String url = "/parkinglot/motorcycles/";
		String requestJson = "{\"plate\":\"WAA123\"}";
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> request = new HttpEntity<>(requestJson, headers);
		
		//Act
		restTemplate.postForEntity(url, request, HappyParkingResponse.class);
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
