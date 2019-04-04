package co.com.ceiba.estacionamiento.andres.salazar.happyparking.domain.motorcycle;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import co.com.ceiba.estacionamiento.andres.salazar.happyparking.domain.HappyParkingException;
import co.com.ceiba.estacionamiento.andres.salazar.happyparking.domain.VehicleType;
import co.com.ceiba.estacionamiento.andres.salazar.happyparking.domain.parkingorder.ParkingOrderFactory;
import co.com.ceiba.estacionamiento.andres.salazar.happyparking.infraestructure.repository.MotorcycleRepositoryMongo;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MotorcycleServiceTest {
	
	@MockBean
	private MotorcycleRepositoryMongo motorcycleRepository;
	
	@Autowired
	private MotorcycleService motorcycleService;
	
	@Autowired
	private ParkingOrderFactory parkingOrderFactory;

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testSaveExist() throws Exception {
		Motorcycle motorcycle = MotorcycleTestBuilder
				.create()
				.withCC(400)
				.withIsParking()
				.withType(VehicleType.MOTORCYCLE.getValue())
				.build();
		
		when(motorcycleRepository.findById(anyString())).thenReturn(Optional.of(motorcycle));
		when(motorcycleRepository.save(any(Motorcycle.class))).thenReturn(motorcycle);
		when(motorcycleRepository.findMotorcycleByPlateAndIsParking(motorcycle.getPlate(), true)).thenReturn(null);
		when(motorcycleRepository.findCountMotorcycleByIsParking(true)).thenReturn(5l);
		
		
		Motorcycle motorcycleSaved = motorcycleService.getInVehicle(motorcycle);
		
		assertThat(motorcycleSaved).isNotNull();
	}
	
	@Test
	public void testSaveNew() throws Exception {
		Motorcycle motorcycle = MotorcycleTestBuilder
				.create()
				.withCC(400)
				.withIsParking()
				.withType(VehicleType.MOTORCYCLE.getValue())
				.build();
		
		when(motorcycleRepository.findById(anyString())).thenReturn(Optional.empty());
		when(motorcycleRepository.save(any(Motorcycle.class))).thenReturn(motorcycle);
		when(motorcycleRepository.findMotorcycleByPlateAndIsParking(motorcycle.getPlate(), true)).thenReturn(null);
		when(motorcycleRepository.findCountMotorcycleByIsParking(true)).thenReturn(5l);
		
		Motorcycle motorcycleSaved = motorcycleService.getInVehicle(motorcycle);
		
		assertThat(motorcycleSaved).isNotNull();
	}
	
	@Test(expected = HappyParkingException.class)
	public void testSaveExistNoSpaceToParking() throws Exception {
		Motorcycle motorcycle = MotorcycleTestBuilder
				.create()
				.withCC(400)
				.withIsParking()
				.withType(VehicleType.MOTORCYCLE.getValue())
				.build();
		
		when(motorcycleRepository.findById(anyString())).thenReturn(Optional.of(motorcycle));
		when(motorcycleRepository.save(any(Motorcycle.class))).thenReturn(motorcycle);
		when(motorcycleRepository.findMotorcycleByPlateAndIsParking(motorcycle.getPlate(), true)).thenReturn(null);
		when(motorcycleRepository.findCountMotorcycleByIsParking(true)).thenReturn(10l);
		
		
		motorcycleService.getInVehicle(motorcycle);
	}
	
	@Test(expected = HappyParkingException.class)
	public void testSaveExistIsInThereSameVehicle() throws Exception {
		Motorcycle motorcycle = MotorcycleTestBuilder
				.create()
				.withCC(400)
				.withIsParking()
				.withType(VehicleType.MOTORCYCLE.getValue())
				.build();
		
		when(motorcycleRepository.findById(anyString())).thenReturn(Optional.of(motorcycle));
		when(motorcycleRepository.save(any(Motorcycle.class))).thenReturn(motorcycle);
		when(motorcycleRepository.findMotorcycleByPlateAndIsParking(motorcycle.getPlate(), true)).thenReturn(motorcycle);
		when(motorcycleRepository.findCountMotorcycleByIsParking(true)).thenReturn(5l);
		
		
		motorcycleService.getInVehicle(motorcycle);
	}
	
	@Test
	public void testFindAllMotorcyclesParking() {
		List<Motorcycle> motorcycles = new ArrayList<>();
		for (int i = 0; i < 5; i++) {
			motorcycles.add(MotorcycleTestBuilder
				.create()
				.withCC(400)
				.withIsParking()
				.withType(VehicleType.MOTORCYCLE.getValue())
				.build());
		}
		when(motorcycleRepository.findAllMotorcyclesByIsParkingTrueAndStream()).thenReturn(motorcycles.stream());
		when(motorcycleRepository.findCountMotorcycleByIsParking(true)).thenReturn(5l);
		
		Stream<Motorcycle> stream = motorcycleService.findAllVehiclesParking();
		
		assertThat(stream).isNotNull();
	}

	@Test
	public void testGetOutVehicle() throws Exception {
		
		Motorcycle motorcycle = MotorcycleTestBuilder
				.create()
				.withCC(400)
				.withIsParking()
				.withType(VehicleType.MOTORCYCLE.getValue())
				.addParkingOrder(parkingOrderFactory.getObject())
				.build();
		
		when(motorcycleRepository.findByPlateAndParkingActive(anyString())).thenReturn(motorcycle);
		when(motorcycleRepository.save(any(Motorcycle.class))).thenReturn(motorcycle);
		
		Motorcycle update = motorcycleService.getOutVehicle(motorcycle.getPlate());
		
		assertThat(update).isNotNull();
		
	}
	
	@Test
	public void testGetOutVehicle2() throws Exception {
		
		Motorcycle motorcycle = MotorcycleTestBuilder
				.create()
				.withCC(400)
				.withIsParking()
				.withType(VehicleType.MOTORCYCLE.getValue())
				.addParkingOrderWithEndDate(parkingOrderFactory.getObject(), 1546318800000l)
				.build();
		
		when(motorcycleRepository.findByPlateAndParkingActive(anyString())).thenReturn(motorcycle);
		when(motorcycleRepository.save(any(Motorcycle.class))).thenReturn(motorcycle);
		
		Motorcycle update = motorcycleService.getOutVehicle(motorcycle.getPlate());
		
		assertThat(update).isNotNull();
		
	}
	
	@Test
	public void testGetOutVehicle3() throws Exception {
		
		Motorcycle motorcycle = MotorcycleTestBuilder
				.create()
				.withCC(400)
				.withIsParking()
				.withType(VehicleType.MOTORCYCLE.getValue())
				.addParkingOrderWithSartDate(parkingOrderFactory.getObject(), 1546318800000l)
				.build();
		
		when(motorcycleRepository.findByPlateAndParkingActive(anyString())).thenReturn(motorcycle);
		when(motorcycleRepository.save(any(Motorcycle.class))).thenReturn(motorcycle);
		
		Motorcycle update = motorcycleService.getOutVehicle(motorcycle.getPlate());
		
		assertThat(update).isNotNull();
		
	}
	
	@Test(expected = HappyParkingException.class)
	public void testGetOutVehicle4() throws Exception {
		
		Motorcycle motorcycle = MotorcycleTestBuilder
				.create()
				.withCC(400)
				.withIsParking()
				.withType(VehicleType.MOTORCYCLE.getValue())
				.addParkingOrderWithSartDate(parkingOrderFactory.getObject(), 1546318800000l)
				.build();
		
		when(motorcycleRepository.findByPlateAndParkingActive(anyString())).thenReturn(null);
		when(motorcycleRepository.save(any(Motorcycle.class))).thenReturn(motorcycle);
		
		motorcycleService.getOutVehicle(motorcycle.getPlate());
		
	}
}
