package co.com.ceiba.estacionamiento.andres.salazar.happyparking.domain.car;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import co.com.ceiba.estacionamiento.andres.salazar.happyparking.domain.HappyParkingException;
import co.com.ceiba.estacionamiento.andres.salazar.happyparking.domain.parkingorder.ParkingOrderFactory;
import co.com.ceiba.estacionamiento.andres.salazar.happyparking.infraestructure.repository.CarRepositoryMongo;
import co.com.ceiba.estacionamiento.andres.salazar.happyparking.infraestructure.system.HappyParkingTime;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CarServiceTest {
	
	@Autowired
	private CarService carService;
	
	@MockBean
	private CarRepositoryMongo carRepository;
	
	@MockBean
	private HappyParkingTime happyParkingTime;
	
	@Autowired
	private ParkingOrderFactory parkingOrderFactory;

	@Test
	public void testGetInVehicle(){
		Car car = CarTestBuilder
				.create()
				.build();
		
		when(carRepository.findById(anyString())).thenReturn(Optional.of(car));
		when(carRepository.save(any(Car.class))).thenReturn(car);
		when(carRepository.findCarByPlateAndIsParking(car.getPlate(), true) ).thenReturn(null);
		when(carRepository.findCountCarsByIsParking(true)).thenReturn(5l);
		when(happyParkingTime.geCurrentDay()).thenReturn("Sunday");
		
		Car carSaved = carService.getInVehicle(car);
		
		assertThat(carSaved).isNotNull();
	}
	
	@Test
	public void testGetInVehicleNew(){
		Car car = CarTestBuilder
				.create()
				.build();
		
		when(carRepository.findById(anyString())).thenReturn(Optional.empty());
		when(carRepository.save(any(Car.class))).thenReturn(car);
		when(carRepository.findCarByPlateAndIsParking(car.getPlate(), true) ).thenReturn(null);
		when(carRepository.findCountCarsByIsParking(true)).thenReturn(5l);
		when(happyParkingTime.geCurrentDay()).thenReturn("Monday");
		
		Car carSaved = carService.getInVehicle(car);
		
		assertThat(carSaved).isNotNull();
	}
	
	@Test(expected = HappyParkingException.class)
	public void testGetInVehicleNoAbleToParking(){
		Car car = CarTestBuilder
				.create()
				.withPlate("AAA123")
				.build();
		
		when(carRepository.findById(anyString())).thenReturn(Optional.of(car));
		when(carRepository.save(any(Car.class))).thenReturn(car);
		when(carRepository.findCarByPlateAndIsParking(car.getPlate(), true) ).thenReturn(null);
		when(carRepository.findCountCarsByIsParking(true)).thenReturn(5l);
		when(happyParkingTime.geCurrentDay()).thenReturn("Wednesday");
		
		carService.getInVehicle(car);
	}
	
	@Test(expected = HappyParkingException.class)
	public void testGetInVehicleSameVehicleInParkingLot(){
		Car car = CarTestBuilder
				.create()
				.build();
		
		when(carRepository.findById(anyString())).thenReturn(Optional.of(car));
		when(carRepository.save(any(Car.class))).thenReturn(car);
		when(carRepository.findCarByPlateAndIsParking(car.getPlate(), true) ).thenReturn(car);
		when(carRepository.findCountCarsByIsParking(true)).thenReturn(5l);
		when(happyParkingTime.geCurrentDay()).thenReturn("Tuesday");
		
		carService.getInVehicle(car);
	}
	
	@Test(expected = HappyParkingException.class)
	public void testGetInVehicleNotSpaceToParking(){
		Car car = CarTestBuilder
				.create()
				.build();
		
		when(carRepository.findById(anyString())).thenReturn(Optional.of(car));
		when(carRepository.save(any(Car.class))).thenReturn(car);
		when(carRepository.findCarByPlateAndIsParking(car.getPlate(), true) ).thenReturn(null);
		when(carRepository.findCountCarsByIsParking(true)).thenReturn(20l);
		when(happyParkingTime.geCurrentDay()).thenReturn("Friday");
		
		carService.getInVehicle(car);
	}

	@Test
	public void testFindAllVehiclesParking() {
		List<Car> cars = new ArrayList<>();
		for (int i = 0; i < 5; i++) {
			cars.add(CarTestBuilder
				.create()
				.build());
		}
		when(carRepository.findCountCarsByIsParking(true)).thenReturn(5l);
		when(carRepository.findAllCarsByIsParkingTrueAndStream()).thenReturn(cars.stream());
		
		Stream<Car> stream = carService.findAllVehiclesParking();
		
		assertThat(stream).isNotNull();
	}
	
	@Test
	public void testFindAllVehiclesParkingEmpty() {
		when(carRepository.findCountCarsByIsParking(true)).thenReturn(0l);
		when(carRepository.findAllCarsByIsParkingTrueAndStream()).thenReturn(Stream.empty());
		
		Stream<Car> stream = carService.findAllVehiclesParking();
		
		assertThat(stream).isNull();
	}

	@Test
	public void testGetOutVehiclePriceZero() throws Exception {
		Car car = CarTestBuilder
				.create()
				.addParkingOrder(parkingOrderFactory.getObject())
				.build();
		
		when(carRepository.findByPlateAndParkingActive(anyString())).thenReturn(car);
		when(carRepository.save(any(Car.class))).thenReturn(car);
		
		Car update = carService.getOutVehicle(car.getPlate());
		
		assertThat(update).isNotNull();
	}
	
	@Test(expected = HappyParkingException.class)
	public void testGetOutVehicleNotFound() throws Exception {
		Car car = CarTestBuilder
				.create()
				.addParkingOrder(parkingOrderFactory.getObject())
				.build();
		
		when(carRepository.findByPlateAndParkingActive(anyString())).thenReturn(null);
		when(carRepository.save(any(Car.class))).thenReturn(car);
		
		carService.getOutVehicle(car.getPlate());
	}
	
	@Test
	public void testGetOutVehicle() throws Exception {
		Car car = CarTestBuilder
				.create()
				.addParkingOrderWithSartDate(parkingOrderFactory.getObject(), 1546318800000l)
				.build();
		
		when(carRepository.findByPlateAndParkingActive(anyString())).thenReturn(car);
		when(carRepository.save(any(Car.class))).thenReturn(car);
		
		Car update = carService.getOutVehicle(car.getPlate());
		
		assertThat(update).isNotNull();
	}
	
	@Test
	public void testGetOutVehicleActiveAndEndDateNotNull() throws Exception {
		Car car = CarTestBuilder
				.create()
				.addParkingOrderWithEndDate(parkingOrderFactory.getObject(), 1546318800000l)
				.build();
		
		when(carRepository.findByPlateAndParkingActive(anyString())).thenReturn(car);
		when(carRepository.save(any(Car.class))).thenReturn(car);
		
		Car update = carService.getOutVehicle(car.getPlate());
		
		assertThat(update).isNotNull();
	}
	
	@Test
	public void testFindVehicle(){
		String plate = "JHG365";
		Car car = CarTestBuilder
				.create()
				.withPlate(plate)
				.build();
		
		when(carRepository.findById(anyString())).thenReturn(Optional.of(car));
		
		Car carSaved = carService.findVehicle(plate);
		
		assertThat(carSaved).isNotNull();
	}
	
	@Test
	public void testFindVehicleNotContent(){
		String plate = "JNG365";
		
		when(carRepository.findById(anyString())).thenReturn(Optional.empty());
		
		Car carSaved = carService.findVehicle(plate);
		
		assertThat(carSaved).isNull();
	}

}
