package co.com.ceiba.estacionamiento.andres.salazar.happyparking.motorcycle;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

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

import co.com.ceiba.estacionamiento.andres.salazar.happyparking.domain.Motorcycle;
import co.com.ceiba.estacionamiento.andres.salazar.happyparking.domain.HappyParkingResponse;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class ParkingMotorcycleControllerTest {

	@Autowired
	private TestRestTemplate restTemplate;

	@MockBean
	private MotorcycleRepository motorcycleRepository;

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
		String url = "/parkinglot/motorcycles/";
		String requestJson = "{\"plate\":\"AAA123\"}";
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> request = new HttpEntity<>(requestJson, headers);
		Motorcycle motorcycleEntity = new Motorcycle();
		motorcycleEntity.setPlate(plate);
		motorcycleEntity.setParking(true);
		
		when(motorcycleRepository.save(any(Motorcycle.class))).thenReturn(motorcycleEntity);
		

		// Act
		ResponseEntity<HappyParkingResponse> entity = restTemplate.postForEntity(url, request,
				HappyParkingResponse.class);

		// Assert
		assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(entity.getBody()).isInstanceOf(HappyParkingResponse.class);
		assertThat(entity.getBody().getHttpStatus()).isEqualTo(HttpStatus.CREATED);
		assertThat(entity.getBody().getContent()).extracting("plate").contains("AAA123");
		assertThat(entity.getBody().getContent()).extracting("parking").contains(true);
	}
	
	@Test(expected = RestClientException.class)
	public void testGetInExistSameMotorcycleInParking() {
		// Arange
		String plate = "AAA123";
		String url = "/parkinglot/motorcycles/";
		String requestJson = "{\"plate\":\"AAA123\"}";
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> request = new HttpEntity<>(requestJson, headers);

		Motorcycle motorEntity = new Motorcycle();
		motorEntity.setPlate(plate);
		motorEntity.setParking(true);

		when(motorcycleRepository.findMotorcycleByPlateAndIsParking(plate, true)).thenReturn(new Motorcycle());

		// Act
		restTemplate.postForEntity(url, request, HappyParkingResponse.class);

	}
	
	@Test(expected = RestClientException.class)
	public void testGetInThereAreNotSpaceToParking() {
		// Arange
		String url = "/parkinglot/motorcycles/";
		String requestJson = "{\"plate\":\"AAA123\"}";
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> request = new HttpEntity<>(requestJson, headers);
		when(motorcycleRepository.findCountMotorcycleByIsParking(true)).thenReturn(10L);

		// Act
		restTemplate.postForEntity(url, request, HappyParkingResponse.class);
	}

}
