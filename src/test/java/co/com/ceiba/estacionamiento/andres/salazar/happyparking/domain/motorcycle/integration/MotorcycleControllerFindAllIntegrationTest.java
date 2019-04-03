package co.com.ceiba.estacionamiento.andres.salazar.happyparking.domain.motorcycle.integration;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.After;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import co.com.ceiba.estacionamiento.andres.salazar.happyparking.domain.motorcycle.Motorcycle;
import co.com.ceiba.estacionamiento.andres.salazar.happyparking.domain.motorcycle.MotorcycleService;
import co.com.ceiba.estacionamiento.andres.salazar.happyparking.infraestructure.jersey.HappyParkingResponse;
import co.com.ceiba.estacionamiento.andres.salazar.happyparking.infraestructure.repository.MotorcycleRepositoryMongo;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class MotorcycleControllerFindAllIntegrationTest {
	
	@Autowired
    private TestRestTemplate restTemplate;
	
	@Autowired
	private MotorcycleService motorcycleService;
	
	@Autowired
	private MotorcycleRepositoryMongo motorcycleRepository;

	@After
	public void tearDown(){
		motorcycleRepository.deleteAll();
	}

	@Test
	@Ignore
	public void testFindAllVehiclesParkingEmpty() {
		String url = "/parkinglot/motorcycles/";
		
		ResponseEntity<HappyParkingResponse> entity = restTemplate.getForEntity(url, HappyParkingResponse.class);
		
        assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
        assertThat(entity.getBody()).isEqualTo(null);
	}
	
	@Test
	public void testFindAllVehiclesParking() throws Exception {
		setupDatabase(2);
		String url = "/parkinglot/motorcycles/";
		
		ResponseEntity<HappyParkingResponse> entity = restTemplate.getForEntity(url, HappyParkingResponse.class);
		
        assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(entity.getBody()).isInstanceOf(HappyParkingResponse.class);
        List<?> list = (List<?>) entity.getBody().getContent();
        for (Object object : list) {
			assertThat(object).extracting("plate").isNotEmpty();
			assertThat(object).extracting("type").contains("Moto");
			assertThat(object).extracting("parking").isNotEmpty();
			assertThat(object).extracting("parkingOrders").isNotEmpty();
		}
	}
	
	private void setupDatabase(int size) throws Exception {
		for (int i = 0; i < size; i++) {
			setupDatabase("DFH1"+i);
		}
	}
	
	private void setupDatabase(String plate) throws Exception {
		Motorcycle motorcycle = new Motorcycle();
		motorcycle.setPlate(plate);
		motorcycleService.getInVehicle(motorcycle);
	}

}
