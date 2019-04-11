package co.com.ceiba.estacionamiento.andres.salazar.happyparking.infraestructure.jersey.car;

import static org.assertj.core.api.Assertions.*;


import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import co.com.ceiba.estacionamiento.andres.salazar.happyparking.domain.car.Car;
import co.com.ceiba.estacionamiento.andres.salazar.happyparking.domain.car.CarTestBuilder;
import co.com.ceiba.estacionamiento.andres.salazar.happyparking.infraestructure.jersey.HappyParkingResponse;
import co.com.ceiba.estacionamiento.andres.salazar.happyparking.infraestructure.repository.CarRepositoryMongo;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class CarControllerJerseyTest {
	
	private String url = "/parkinglot/cars/";
	
	@Autowired
    private TestRestTemplate restTemplate;
	
	@Autowired
	private CarRepositoryMongo carRepository;
	
	private String plate = "AFGKKJS";

	@Before
	public void setUp() {
		Car car = CarTestBuilder.create()
		.withIsParking()
		.withPlate(plate)
		.build();
		
		carRepository.save(car);
	}

	@After
	public void tearDown(){
		carRepository.deleteAll();
	}

	@Test
	public void testFindVehicleByPlate() {
		ResponseEntity<HappyParkingResponse> entity = restTemplate.getForEntity(url+plate, HappyParkingResponse.class);
		
        assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(entity.getBody()).isInstanceOf(HappyParkingResponse.class);
        Object object =  entity.getBody().getContent();
        assertThat(object).extracting("plate").isNotEmpty();
		assertThat(object).extracting("type").contains("Carro");
		assertThat(object).extracting("parking").isNotEmpty();
		assertThat(object).extracting("parkingOrders").isNotEmpty();
	}
	
	@Test
	public void testFindVehicleByPlateNoContent() {
		ResponseEntity<HappyParkingResponse> entity = restTemplate.getForEntity(url+"KLP852", HappyParkingResponse.class);
		
        assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
        assertThat(entity.getBody()).isNull();
	}

}
