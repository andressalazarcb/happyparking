package co.com.ceiba.estacionamiento.andres.salazar.happyparking.parking;

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

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class ControllerParkingCarJerseyTest {
	
	@Autowired
    private TestRestTemplate restTemplate;

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testGetIn() {
		fail("Not yet implemented");
	}
	
	@Test
	public void testGetInThrowsBusinessException() {
		fail("Not yet implemented");
	}
	
	@Test
	public void testGetInThrowsSystemException() {
		fail("Not yet implemented");
	}
	
	@Test
	public void testGetInVehicleThatWasAlreadyIn() {
		fail("Not yet implemented");
	}
	
	@Test
	public void testGetInCarNoSpaceAvalible() {
		fail("Not yet implemented");
	}

	@Test
	public void testFindVehicles() {
		fail("Not yet implemented");
	}
	
	@Test
	public void testFindVehiclesThrowsSystemException() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetOut() {
		fail("Not yet implemented");
	}
	
	@Test
	public void testGetOutThrowsSystemException() {
		fail("Not yet implemented");
	}
	
	@Test
	public void testGetOutVehicleThatWasNotInThere() {
		fail("Not yet implemented");
	}
	
	@Test
	public void testFindVehicle() {
		ResponseEntity<String> entity = this.restTemplate.getForEntity("/parkinglot/cars/AAA321",
                String.class);
        assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(entity.getBody()).isEqualTo("Hello from Spring");
	}
	
	public void testFindVehicleNotFound() {
		fail("Not yet implemented");
	}
	
	@Test
	public void testFindVehicleThrowsSystemException() {
		fail("Not yet implemented");
	}
	
}
