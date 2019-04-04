package co.com.ceiba.estacionamiento.andres.salazar.happyparking.integration;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;
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

import co.com.ceiba.estacionamiento.andres.salazar.happyparking.domain.motorcycle.Motorcycle;
import co.com.ceiba.estacionamiento.andres.salazar.happyparking.domain.motorcycle.MotorcycleTestBuilder;
import co.com.ceiba.estacionamiento.andres.salazar.happyparking.domain.parkingorder.ParkingOrder;
import co.com.ceiba.estacionamiento.andres.salazar.happyparking.domain.parkingorder.ParkingOrderFactory;
import co.com.ceiba.estacionamiento.andres.salazar.happyparking.infraestructure.jersey.HappyParkingResponse;
import co.com.ceiba.estacionamiento.andres.salazar.happyparking.infraestructure.repository.MotorcycleRepositoryMongo;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class MotorcycleControllerGetOutIntegrationTest {
	
	private String url = "/parkinglot/motorcycles/out";
	private String plateField = "plate";
	private String priceField = "price";

	@Autowired
	private TestRestTemplate restTemplate;
	
	@Autowired
	private MotorcycleRepositoryMongo motorcycleRepository;
	
	@Autowired
	private ParkingOrderFactory parkingOrderFactory;
	
	@After
	public void tearDown(){
		motorcycleRepository.deleteAll();
	}

	@Test
	public void testGetOut() throws Exception {
		// Arrange
		String plate = "BGH123";
		setupDatabase(8L, plate, false);
		String requestJson = getJson(plate);
		

		// Act
		ResponseEntity<HappyParkingResponse> entity = restTemplate.exchange(url, HttpMethod.PUT, getRequest(requestJson), HappyParkingResponse.class);

		// Assert
		assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(entity.getBody()).isInstanceOf(HappyParkingResponse.class);
		assertThat(entity.getBody().getStatus()).isEqualTo(Status.OK.getStatusCode());
		ObjectMapper mapper = new ObjectMapper();
		String motorcycleJson = mapper.writeValueAsString(entity.getBody().getContent());
		Motorcycle motorcycle = mapper.readValue(motorcycleJson, Motorcycle.class);
		assertThat(motorcycle).hasFieldOrPropertyWithValue(plateField, plate);
		assertThat(motorcycle.getParkingOrders()).isNotEmpty();
		List<ParkingOrder> parkingOrders = motorcycle.getParkingOrders();
		for (ParkingOrder parkingOrder : parkingOrders) {
			if(parkingOrder.isActive()) {
				assertThat(parkingOrder).hasFieldOrPropertyWithValue(priceField, BigDecimal.valueOf(4000L));
			}
		}
		
	}
	
	@Test
	public void testGetOutDay() throws Exception {
		// Arrange
		String plate = "LKJ345";
		setupDatabase(12L, plate, false);
		String requestJson = getJson(plate);
		

		// Act
		ResponseEntity<HappyParkingResponse> entity = restTemplate.exchange(url, HttpMethod.PUT, getRequest(requestJson), HappyParkingResponse.class);

		// Assert
		assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(entity.getBody()).isInstanceOf(HappyParkingResponse.class);
		assertThat(entity.getBody().getStatus()).isEqualTo(Status.OK.getStatusCode());
		ObjectMapper mapper = new ObjectMapper();
		String motorcycleJson = mapper.writeValueAsString(entity.getBody().getContent());
		Motorcycle motorcycle = mapper.readValue(motorcycleJson, Motorcycle.class);
		assertThat(motorcycle).hasFieldOrPropertyWithValue(plateField, plate);
		assertThat(motorcycle.getParkingOrders()).isNotEmpty();
		List<ParkingOrder> parkingOrders = motorcycle.getParkingOrders();
		for (ParkingOrder parkingOrder : parkingOrders) {
			if(parkingOrder.isActive()) {
				assertThat(parkingOrder).hasFieldOrPropertyWithValue(priceField, BigDecimal.valueOf(4000L));
			}
		}
		
	}
	
	@Test
	public void testGetOutMoreThanTwoDays() throws Exception {
		// Arrange
		String plate = "MNB123";
		setupDatabase(55L, plate, false);
		String requestJson = getJson(plate);

		// Act
		ResponseEntity<HappyParkingResponse> entity = restTemplate.exchange(url, HttpMethod.PUT, getRequest(requestJson), HappyParkingResponse.class);

		// Assert
		assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(entity.getBody()).isInstanceOf(HappyParkingResponse.class);
		assertThat(entity.getBody().getStatus()).isEqualTo(Status.OK.getStatusCode());
		ObjectMapper mapper = new ObjectMapper();
		String motorcycleJson = mapper.writeValueAsString(entity.getBody().getContent());
		Motorcycle motorcycle = mapper.readValue(motorcycleJson, Motorcycle.class);
		assertThat(motorcycle).hasFieldOrPropertyWithValue(plateField, plate);
		assertThat(motorcycle.getParkingOrders()).isNotEmpty();
		List<ParkingOrder> parkingOrders = motorcycle.getParkingOrders();
		for (ParkingOrder parkingOrder : parkingOrders) {
			if(parkingOrder.isActive()) {
				assertThat(parkingOrder).hasFieldOrPropertyWithValue(priceField, BigDecimal.valueOf(11500L));
			}
		}
		
	}
	
	@Test
	public void testGetOutImmediately() throws Exception {
		// Arrange
		String plate = "KJH123";
		setupDatabase(0L, plate, false);
		String requestJson = getJson(plate);

		// Act
		ResponseEntity<HappyParkingResponse> entity = restTemplate.exchange(url, HttpMethod.PUT, getRequest(requestJson), HappyParkingResponse.class);

		// Assert
		assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(entity.getBody()).isInstanceOf(HappyParkingResponse.class);
		assertThat(entity.getBody().getStatus()).isEqualTo(Status.OK.getStatusCode());
		ObjectMapper mapper = new ObjectMapper();
		String motorcycleJson = mapper.writeValueAsString(entity.getBody().getContent());
		Motorcycle motorcycle = mapper.readValue(motorcycleJson, Motorcycle.class);
		assertThat(motorcycle).hasFieldOrPropertyWithValue(plateField, plate);
		assertThat(motorcycle.getParkingOrders()).isNotEmpty();
		List<ParkingOrder> parkingOrders = motorcycle.getParkingOrders();
		for (ParkingOrder parkingOrder : parkingOrders) {
			assertThat(parkingOrder).hasFieldOrPropertyWithValue(priceField, BigDecimal.ZERO);
		}
		
	}
	
	@Test
	public void testGetOutHighCylinder() throws Exception {
		// Arrange
		String plate = "HRA123";
		setupDatabase(0L, plate, true);
		String requestJson = getJson(plate);

		// Act
		ResponseEntity<HappyParkingResponse> entity = restTemplate.exchange(url, HttpMethod.PUT, getRequest(requestJson), HappyParkingResponse.class);

		// Assert
		assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(entity.getBody()).isInstanceOf(HappyParkingResponse.class);
		assertThat(entity.getBody().getStatus()).isEqualTo(Status.OK.getStatusCode());
		ObjectMapper mapper = new ObjectMapper();
		String motorcycleJson = mapper.writeValueAsString(entity.getBody().getContent());
		Motorcycle motorcycle = mapper.readValue(motorcycleJson, Motorcycle.class);
		assertThat(motorcycle).hasFieldOrPropertyWithValue(plateField, plate);
		assertThat(motorcycle.getParkingOrders()).isNotEmpty();
		List<ParkingOrder> parkingOrders = motorcycle.getParkingOrders();
		for (ParkingOrder parkingOrder : parkingOrders) {
			assertThat(parkingOrder).hasFieldOrPropertyWithValue(priceField, BigDecimal.valueOf(2000.0));
		}
		
	}
	
	private void setupDatabase(long hoursBefore, String plate, boolean highCylinder) throws Exception {
		motorcycleRepository.save(getMotorcycle(hoursBefore, plate, highCylinder));
	}
	
	private Motorcycle getMotorcycle(long hoursBefore, String plate, boolean highCylinder) throws Exception {
		Motorcycle motorcycleToSave = MotorcycleTestBuilder.create()
		.withPlate(plate)
		.withIsParking()
		.addParkingOrderWithSartDate(parkingOrderFactory.getObject(), LocalDateTime.now().minusHours(hoursBefore).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli())
		.build();
		if(highCylinder)
			motorcycleToSave.setCc(700);
		return motorcycleToSave;
	}
	
	private HttpEntity<String> getRequest(String json){
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		return new HttpEntity<>(json, headers);
	}
	
	private String getJson(String plate) {
		return "{\"plate\":\""+plate+"\"}";
	}
	
	

}
