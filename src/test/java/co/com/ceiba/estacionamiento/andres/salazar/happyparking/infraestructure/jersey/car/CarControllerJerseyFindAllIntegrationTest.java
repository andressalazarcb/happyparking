package co.com.ceiba.estacionamiento.andres.salazar.happyparking.infraestructure.jersey.car;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import co.com.ceiba.estacionamiento.andres.salazar.happyparking.domain.VehicleType;
import co.com.ceiba.estacionamiento.andres.salazar.happyparking.domain.car.Car;
import co.com.ceiba.estacionamiento.andres.salazar.happyparking.domain.car.CarService;
import co.com.ceiba.estacionamiento.andres.salazar.happyparking.domain.car.CarTestBuilder;
import co.com.ceiba.estacionamiento.andres.salazar.happyparking.infraestructure.jersey.HappyParkingResponse;
import co.com.ceiba.estacionamiento.andres.salazar.happyparking.infraestructure.repository.CarRepositoryMongo;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class CarControllerJerseyFindAllIntegrationTest {
	
	private String url = "/parkinglot/cars/";
	private String plateField = "plate";
	private String typeField = "type";
	private String parkingField = "parking";
	private String parkingOrdersField = "parkingOrders";
	
	@Autowired
    private TestRestTemplate restTemplate;
	
	@Autowired
	private CarService carService;
	
	@Autowired
	private CarRepositoryMongo carRepository;
	
	@After
	public void tearDown(){
		carRepository.deleteAll();
	}
	
	@Test
	public void testFindAllVehiclesParkingEmpty() {
		ResponseEntity<HappyParkingResponse> entity = restTemplate.getForEntity(url, HappyParkingResponse.class);
		
        assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
        assertThat(entity.getBody()).isEqualTo(null);
	}
	
	@Test
	public void testFindAllVehiclesParking() {
		setupDatabase(2);
		ResponseEntity<HappyParkingResponse> entity = restTemplate.getForEntity(url, HappyParkingResponse.class);
		
        assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(entity.getBody()).isInstanceOf(HappyParkingResponse.class);
        List<?> list = (List<?>) entity.getBody().getContent();
        for (Object object : list) {
			assertThat(object).extracting(plateField).isNotEmpty();
			assertThat(object).extracting(typeField).contains(VehicleType.CAR.getValue());
			assertThat(object).extracting(parkingField).isNotEmpty();
			assertThat(object).extracting(parkingOrdersField).isNotEmpty();
		}
	}
	
	private void setupDatabase(int size){
		for (int i = 0; i < size; i++) {
			setupDatabase("DFG1"+i);
		}
	}
	
	private void setupDatabase(String plate){
		Car car = CarTestBuilder.create()
				.withPlate(plate).withIsParking().build();
		carService.getInVehicle(car);
	}

}