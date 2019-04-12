package co.com.ceiba.estacionamiento.andres.salazar.happyparking.infraestructure.jersey.car;

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

import co.com.ceiba.estacionamiento.andres.salazar.happyparking.domain.car.Car;
import co.com.ceiba.estacionamiento.andres.salazar.happyparking.domain.car.CarTestBuilder;
import co.com.ceiba.estacionamiento.andres.salazar.happyparking.domain.parkingorder.ParkingOrder;
import co.com.ceiba.estacionamiento.andres.salazar.happyparking.domain.parkingorder.ParkingOrderFactory;
import co.com.ceiba.estacionamiento.andres.salazar.happyparking.infraestructure.jersey.HappyParkingResponse;
import co.com.ceiba.estacionamiento.andres.salazar.happyparking.infraestructure.repository.CarRepositoryMongo;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class CarControllerJerseyGetOutIntegrationTest {
	
	private String url = "/parkinglot/cars/";
	private String plateField = "plate";
	private String priceField = "price";

	@Autowired
	private TestRestTemplate restTemplate;

	@Autowired
	private CarRepositoryMongo carRepository;
	
	@Autowired
	private ParkingOrderFactory parkingOrderFactory;

	@After
	public void tearDown(){
		carRepository.deleteAll();
	}

	@Test
	public void testEightHours() throws Exception{
		// Arrange
		String plate = "LKJ789";
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
		assertThat(car).hasFieldOrPropertyWithValue(plateField, plate);
		assertThat(car.getParkingOrders()).isNotEmpty();
		List<ParkingOrder> parkingOrders = car.getParkingOrders();
		for (ParkingOrder parkingOrder : parkingOrders) {
			if (parkingOrder.isActive()) {
				assertThat(parkingOrder).hasFieldOrPropertyWithValue(priceField, BigDecimal.valueOf(8000L));
			}
		}
	}
	
	@Test
	public void testADayAndThreeHours() throws Exception{
		// Arrange
		String plate = "BHG567";
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
		assertThat(car).hasFieldOrPropertyWithValue(plateField, plate);
		assertThat(car.getParkingOrders()).isNotEmpty();
		List<ParkingOrder> parkingOrders = car.getParkingOrders();
		for (ParkingOrder parkingOrder : parkingOrders) {
			if (parkingOrder.isActive()) {
				assertThat(parkingOrder).hasFieldOrPropertyWithValue(priceField, BigDecimal.valueOf(11000L));
			}
		}
	}

	private Car getCar(long hoursBefore, String plate) throws Exception {
		ParkingOrder parkingOrder = parkingOrderFactory.getObject();
		parkingOrder.createParkingOrderId(plate);
		
		return CarTestBuilder.create()
				.withPlate(plate)
				.withIsParking()
				.addParkingOrderWithSartDate(parkingOrder, LocalDateTime.now().minusHours(hoursBefore).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli())
				.build();
	}
	
	private HttpEntity<String> getRequest(String json){
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		return new HttpEntity<>(json, headers);
	}
	
	private void setupDatabase(long hoursBefore, String plate) throws Exception {
		carRepository.save(getCar(hoursBefore, plate));
	}

}
