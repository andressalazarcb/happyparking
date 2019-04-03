package co.com.ceiba.estacionamiento.andres.salazar.happyparking.car;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.math.BigDecimal;
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

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import co.com.ceiba.estacionamiento.andres.salazar.happyparking.domain.Car;
import co.com.ceiba.estacionamiento.andres.salazar.happyparking.domain.HappyParkingResponse;
import co.com.ceiba.estacionamiento.andres.salazar.happyparking.domain.ParkingOrder;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class CarControllerGetOutIntegrationTest {

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
		setupDatabase(8L, "AAA123");

		String url = "/parkinglot/cars/out";
		String requestJson = "{\"plate\":\"AAA123\"}";
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> request = new HttpEntity<>(requestJson, headers);

		// Act
		ResponseEntity<HappyParkingResponse> entity = restTemplate.exchange(url, HttpMethod.PUT, request,
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
		setupDatabase(27L, "AAA123");

		String url = "/parkinglot/cars/out";
		String requestJson = "{\"plate\":\"AAA123\"}";
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> request = new HttpEntity<>(requestJson, headers);

		// Act
		ResponseEntity<HappyParkingResponse> entity = restTemplate.exchange(url, HttpMethod.PUT, request,
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
		Car carToSave = new Car();
		carToSave.setPlate(plate);
		carToSave.setParking(true);
		carToSave.setType("Carro");
		ParkingOrder parkingOrder = new ParkingOrder();
		parkingOrder.setParkingOrderId(carToSave.getPlate() + "_" + System.currentTimeMillis());
		parkingOrder.setActive(true);
		parkingOrder.setStartDate(System.currentTimeMillis());
		carToSave.setParkingOrders(Arrays.asList(parkingOrder));
		return carToSave;
	}

}
