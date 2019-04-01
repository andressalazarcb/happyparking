package co.com.ceiba.estacionamiento.andres.salazar.happyparking.car;

import static org.assertj.core.api.Assertions.*;


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

import co.com.ceiba.estacionamiento.andres.salazar.happyparking.domain.Car;
import co.com.ceiba.estacionamiento.andres.salazar.happyparking.domain.HappyParkingResponse;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class ParkingCarControllerJerseyIntegrationTest {
	
	@Autowired
    private TestRestTemplate restTemplate;
	
	@Autowired
	private CarRepository carRepository;
	
	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testGetIn() {
		String url = "/parkinglot/cars/";
		String requestJson = "{\"plate\":\"BAA123\"}";
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<String> request = new HttpEntity<>(requestJson, headers);
		ResponseEntity<HappyParkingResponse> entity = restTemplate.postForEntity(url, request, HappyParkingResponse.class);
		
        assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(entity.getBody()).isInstanceOf(HappyParkingResponse.class);
        assertThat(entity.getBody().getHttpStatus()).isEqualTo(HttpStatus.CREATED);
        assertThat(entity.getBody().getContent()).extracting("plate").contains("BAA123");
        assertThat(entity.getBody().getContent()).extracting("parking").contains(true);
	}
	
	@Test(expected = RestClientException.class)
	public void testGetInThereAreNotSpaceToParking() {		
		//Arrange
		setupDatabase(20);
		
		String url = "/parkinglot/cars/";
		String requestJson = "{\"plate\":\"WAA123\"}";
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> request = new HttpEntity<>(requestJson, headers);
		
		//Act
		restTemplate.postForEntity(url, request, HappyParkingResponse.class);
	}
	
	@Test(expected = RestClientException.class)
	public void testGetInExistSameCarInParking() {		
		//Arrange
		setupDatabase("WAA123");
		
		String url = "/parkinglot/cars/";
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
		Car car = new Car();
		car.setPlate(plate);
		car.setParking(true);
		carRepository.save(car);
	}
	
}
