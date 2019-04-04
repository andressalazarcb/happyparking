package co.com.ceiba.estacionamiento.andres.salazar.happyparking.integration;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
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

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import co.com.ceiba.estacionamiento.andres.salazar.happyparking.domain.car.Car;
import co.com.ceiba.estacionamiento.andres.salazar.happyparking.domain.car.CarTestBuilder;
import co.com.ceiba.estacionamiento.andres.salazar.happyparking.domain.parkingorder.ParkingOrder;
import co.com.ceiba.estacionamiento.andres.salazar.happyparking.infraestructure.jersey.HappyParkingResponse;
import co.com.ceiba.estacionamiento.andres.salazar.happyparking.infraestructure.repository.CarRepositoryMongo;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class CarControllerGetOutIntegrationTest {
	
	private String url = "/parkinglot/cars/out";

	@Autowired
	private TestRestTemplate restTemplate;

	@Autowired
	private CarRepositoryMongo carRepository;

	@After
	public void tearDown() throws Exception {
		carRepository.deleteAll();
	}

	@Test
	public void test() throws JsonParseException, JsonMappingException, IOException {
		// Arrange
		String plate = "AAA123";
		setupDatabase(8L, plate);
		String requestJson = "{\"plate\":\""+plate+"\"}";

		// Act
		ResponseEntity<HappyParkingResponse> entity = restTemplate.exchange(url, HttpMethod.PUT, getRequest(requestJson),
				HappyParkingResponse.class);

		// Assert
		assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(entity.getBody()).isInstanceOf(HappyParkingResponse.class);
		assertThat(entity.getBody().getStatus()).isEqualTo(Status.OK.getStatusCode());
		ObjectMapper mapper = new ObjectMapper();
		String carJson = mapper.writeValueAsString(entity.getBody().getContent());
		Car car = mapper.readValue(carJson, Car.class);
		assertThat(car).hasFieldOrPropertyWithValue("plate", "AAA123");
		assertThat(car.getParkingOrders()).isNotEmpty();
		List<ParkingOrder> parkingOrders = car.getParkingOrders();
		for (ParkingOrder parkingOrder : parkingOrders) {
			if (parkingOrder.isActive()) {
				assertThat(parkingOrder).hasFieldOrPropertyWithValue("price", BigDecimal.valueOf(8000L));
			}
		}
	}
	
	@Test
	public void testADayAndThreeHours() throws JsonParseException, JsonMappingException, IOException {
		// Arrange
		String plate = "AAA123";
		setupDatabase(27L, plate);
		String requestJson = "{\"plate\":\""+plate+"\"}";

		// Act
		ResponseEntity<HappyParkingResponse> entity = restTemplate.exchange(url, HttpMethod.PUT, getRequest(requestJson),
				HappyParkingResponse.class);

		// Assert
		assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(entity.getBody()).isInstanceOf(HappyParkingResponse.class);
		assertThat(entity.getBody().getStatus()).isEqualTo(Status.OK.getStatusCode());
		ObjectMapper mapper = new ObjectMapper();
		String carJson = mapper.writeValueAsString(entity.getBody().getContent());
		Car car = mapper.readValue(carJson, Car.class);
		assertThat(car).hasFieldOrPropertyWithValue("plate", "AAA123");
		assertThat(car.getParkingOrders()).isNotEmpty();
		List<ParkingOrder> parkingOrders = car.getParkingOrders();
		for (ParkingOrder parkingOrder : parkingOrders) {
			if (parkingOrder.isActive()) {
				assertThat(parkingOrder).hasFieldOrPropertyWithValue("price", BigDecimal.valueOf(11000L));
			}
		}
	}

	private void setupDatabase(long hoursBefore, String plate) {
		carRepository.save(getCar(hoursBefore, plate));
	}

	private Car getCar(long hoursBefore, String plate) {
		ParkingOrder parkingOrder = new ParkingOrder();
		parkingOrder.setParkingOrderId(plate + "_" + System.currentTimeMillis());
		parkingOrder.setActive(true);
		
		Car carToSave = CarTestBuilder.create()
				.withPlate(plate)
				.withIsParking()
				.addParkingOrderWithSartDate(parkingOrder, LocalDateTime.now().minusHours(hoursBefore).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli())
				.build();
		return carToSave;
	}
	
	private HttpEntity<String> getRequest(String json){
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> request = new HttpEntity<>(json, headers);
		return request;
	}

}
