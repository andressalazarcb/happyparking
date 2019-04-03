package co.com.ceiba.estacionamiento.andres.salazar.happyparking.motorcycle;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.List;

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
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import com.fasterxml.jackson.databind.ObjectMapper;

import co.com.ceiba.estacionamiento.andres.salazar.happyparking.domain.Motorcycle;
import co.com.ceiba.estacionamiento.andres.salazar.happyparking.domain.ParkingOrder;
import co.com.ceiba.estacionamiento.andres.salazar.happyparking.jersey.HappyParkingResponse;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class MotorcycleControllerGetOutIntegrationTest {

	@Autowired
	private TestRestTemplate restTemplate;
	
	@Autowired
	private MotorcycleRepositoryMongo motorcycleRepository;
	
	@After
	public void tearDown(){
		motorcycleRepository.deleteAll();
	}

	@Test
	public void testGetOut() throws IOException {
		// Arrange
		setupDatabase(8L, "AAA123");
				
		String url = "/parkinglot/motorcycles/out";
		String requestJson = "{\"plate\":\"AAA123\"}";
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> request = new HttpEntity<>(requestJson, headers);
		

		// Act
		ResponseEntity<HappyParkingResponse> entity = restTemplate.exchange(url, HttpMethod.PUT, request, HappyParkingResponse.class);

		// Assert
		assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(entity.getBody()).isInstanceOf(HappyParkingResponse.class);
		assertThat(entity.getBody().getStatus()).isEqualTo(Status.OK.getStatusCode());
		ObjectMapper mapper = new ObjectMapper();
		String motorcycleJson = mapper.writeValueAsString(entity.getBody().getContent());
		Motorcycle motorcycle = mapper.readValue(motorcycleJson, Motorcycle.class);
		assertThat(motorcycle).hasFieldOrPropertyWithValue("plate", "AAA123");
		assertThat(motorcycle.getParkingOrders()).isNotEmpty();
		List<ParkingOrder> parkingOrders = motorcycle.getParkingOrders();
		for (ParkingOrder parkingOrder : parkingOrders) {
			if(parkingOrder.isActive()) {
				assertThat(parkingOrder).hasFieldOrPropertyWithValue("price", BigDecimal.valueOf(4000L));
			}
		}
		
	}
	
	@Test
	public void testGetOutDay() throws IOException {
		// Arrange
		setupDatabase(12L, "AAA123");
				
		String url = "/parkinglot/motorcycles/out";
		String requestJson = "{\"plate\":\"AAA123\"}";
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> request = new HttpEntity<>(requestJson, headers);
		

		// Act
		ResponseEntity<HappyParkingResponse> entity = restTemplate.exchange(url, HttpMethod.PUT, request, HappyParkingResponse.class);

		// Assert
		assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(entity.getBody()).isInstanceOf(HappyParkingResponse.class);
		assertThat(entity.getBody().getStatus()).isEqualTo(Status.OK.getStatusCode());
		ObjectMapper mapper = new ObjectMapper();
		String motorcycleJson = mapper.writeValueAsString(entity.getBody().getContent());
		Motorcycle motorcycle = mapper.readValue(motorcycleJson, Motorcycle.class);
		assertThat(motorcycle).hasFieldOrPropertyWithValue("plate", "AAA123");
		assertThat(motorcycle.getParkingOrders()).isNotEmpty();
		List<ParkingOrder> parkingOrders = motorcycle.getParkingOrders();
		for (ParkingOrder parkingOrder : parkingOrders) {
			if(parkingOrder.isActive()) {
				assertThat(parkingOrder).hasFieldOrPropertyWithValue("price", BigDecimal.valueOf(4000L));
			}
		}
		
	}
	
	@Test
	public void testGetOutMoreThanTwoDays() throws IOException {
		// Arrange
		setupDatabase(55L, "AAA123");
				
		String url = "/parkinglot/motorcycles/out";
		String requestJson = "{\"plate\":\"AAA123\"}";
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> request = new HttpEntity<>(requestJson, headers);
		

		// Act
		ResponseEntity<HappyParkingResponse> entity = restTemplate.exchange(url, HttpMethod.PUT, request, HappyParkingResponse.class);

		// Assert
		assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(entity.getBody()).isInstanceOf(HappyParkingResponse.class);
		assertThat(entity.getBody().getStatus()).isEqualTo(Status.OK.getStatusCode());
		ObjectMapper mapper = new ObjectMapper();
		String motorcycleJson = mapper.writeValueAsString(entity.getBody().getContent());
		Motorcycle motorcycle = mapper.readValue(motorcycleJson, Motorcycle.class);
		assertThat(motorcycle).hasFieldOrPropertyWithValue("plate", "AAA123");
		assertThat(motorcycle.getParkingOrders()).isNotEmpty();
		List<ParkingOrder> parkingOrders = motorcycle.getParkingOrders();
		for (ParkingOrder parkingOrder : parkingOrders) {
			if(parkingOrder.isActive()) {
				assertThat(parkingOrder).hasFieldOrPropertyWithValue("price", BigDecimal.valueOf(11500L));
			}
		}
		
	}
	
	@Test
	public void testGetOutImmediately() throws IOException {
		// Arrange
		setupDatabase(0L, "AAA123");
				
		String url = "/parkinglot/motorcycles/out";
		String requestJson = "{\"plate\":\"AAA123\"}";
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> request = new HttpEntity<>(requestJson, headers);
		

		// Act
		ResponseEntity<HappyParkingResponse> entity = restTemplate.exchange(url, HttpMethod.PUT, request, HappyParkingResponse.class);

		// Assert
		assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(entity.getBody()).isInstanceOf(HappyParkingResponse.class);
		assertThat(entity.getBody().getStatus()).isEqualTo(Status.OK.getStatusCode());
		ObjectMapper mapper = new ObjectMapper();
		String motorcycleJson = mapper.writeValueAsString(entity.getBody().getContent());
		Motorcycle motorcycle = mapper.readValue(motorcycleJson, Motorcycle.class);
		assertThat(motorcycle).hasFieldOrPropertyWithValue("plate", "AAA123");
		assertThat(motorcycle.getParkingOrders()).isNotEmpty();
		List<ParkingOrder> parkingOrders = motorcycle.getParkingOrders();
		for (ParkingOrder parkingOrder : parkingOrders) {
			if(parkingOrder.isActive()) {
				assertThat(parkingOrder).hasFieldOrPropertyWithValue("price", BigDecimal.ZERO);
			}
		}
		
	}
	
	private void setupDatabase(long hoursBefore, String plate) {
		motorcycleRepository.save(getMotorcycle(hoursBefore, plate));
	}
	
	private Motorcycle getMotorcycle(long hoursBefore, String plate) {
		Motorcycle motorcycleToSave = new Motorcycle();
		motorcycleToSave.setPlate(plate);
		motorcycleToSave.setParking(true);
		motorcycleToSave.setType("Moto");
		ParkingOrder parkingOrder = new ParkingOrder();
		parkingOrder.setActive(true);
		parkingOrder.setStartDate(LocalDateTime.now().minusHours(hoursBefore).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli());
		motorcycleToSave.setParkingOrders(Arrays.asList(parkingOrder));
		return motorcycleToSave;
	}
	
	

}
