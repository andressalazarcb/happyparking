package co.com.ceiba.estacionamiento.andres.salazar.happyparking.infraestructure.jersey.motorcycle;

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

import co.com.ceiba.estacionamiento.andres.salazar.happyparking.domain.VehicleType;
import co.com.ceiba.estacionamiento.andres.salazar.happyparking.domain.motorcycle.Motorcycle;
import co.com.ceiba.estacionamiento.andres.salazar.happyparking.domain.motorcycle.MotorcycleTestBuilder;
import co.com.ceiba.estacionamiento.andres.salazar.happyparking.infraestructure.jersey.HappyParkingResponse;
import co.com.ceiba.estacionamiento.andres.salazar.happyparking.infraestructure.repository.MotorcycleRepositoryMongo;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class MotorcycleControllerJerseyFindVehicleByPlateTest {

	private String url = "/parkinglot/motorcycles/";
	private String plateField = "plate";
	private String typeField = "type";
	private String parkingField = "parking";
	private String parkingOrdersField = "parkingOrders";
	
	@Autowired
    private TestRestTemplate restTemplate;
	
	@Autowired
	private MotorcycleRepositoryMongo motorcycleRepository;
	
	private String plate = "AFGKKJS";
	
	@Before
	public void setUp() {
		Motorcycle motorcycle = MotorcycleTestBuilder.create()
				.withIsParking()
				.withPlate(plate)
				.build();
		
		motorcycleRepository.save(motorcycle);
	}

	@After
	public void tearDown(){
		motorcycleRepository.deleteAll();
	}

	@Test
	public void testFindVehicleByPlate() {
		ResponseEntity<HappyParkingResponse> entity = restTemplate.getForEntity(url+plate, HappyParkingResponse.class);
		
        assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(entity.getBody()).isInstanceOf(HappyParkingResponse.class);
        Object object =  entity.getBody().getContent();
        assertThat(object).extracting(plateField).isNotEmpty();
		assertThat(object).extracting(typeField).contains(VehicleType.MOTORCYCLE.getValue());
		assertThat(object).extracting(parkingField).isNotEmpty();
		assertThat(object).extracting(parkingOrdersField).isNotEmpty();
	}
	
	@Test
	public void testFindVehicleByPlateNoContent() {
		ResponseEntity<HappyParkingResponse> entity = restTemplate.getForEntity(url+"KLP852", HappyParkingResponse.class);
		
        assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
        assertThat(entity.getBody()).isNull();
	}

}
