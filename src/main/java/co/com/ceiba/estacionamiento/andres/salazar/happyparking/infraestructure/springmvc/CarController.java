package co.com.ceiba.estacionamiento.andres.salazar.happyparking.infraestructure.springmvc;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import co.com.ceiba.estacionamiento.andres.salazar.happyparking.domain.car.Car;
import co.com.ceiba.estacionamiento.andres.salazar.happyparking.domain.parkinglot.ParkingLot;
import co.com.ceiba.estacionamiento.andres.salazar.happyparking.domain.parkinglot.ParkingLotService;
import co.com.ceiba.estacionamiento.andres.salazar.happyparking.infraestructure.repository.CarEntity;
import co.com.ceiba.estacionamiento.andres.salazar.happyparking.infraestructure.repository.CarRepositoryMongo;

@RestController
public class CarController{
	
	private ParkingLotService<Car> parkingLotService;
	
	private ModelMapper modelMapper;
	
	private CarRepositoryMongo repositoryMongo;
	
	@Autowired
	public CarController(ParkingLotService<Car> parkingLotService, ModelMapper modelMapper,
			CarRepositoryMongo repositoryMongo) {
		this.parkingLotService = parkingLotService;
		this.modelMapper = modelMapper;
		this.repositoryMongo = repositoryMongo;
	}

	@PostMapping("/cars/")
	@ResponseBody
	public CarEntity getInVehicle(@RequestBody VehicleDTO vehicle) {
		Optional<CarEntity> optional = repositoryMongo.findById(vehicle.getPlate());
		ParkingLot<Car> parkingLot = new ParkingLot<>();
		parkingLot.setQuantity(repositoryMongo.findCountCarsByIsParking(true).intValue());
		if(optional.isPresent()) {
			parkingLot.setVehicleIn(convertToDomain(optional.get()));
		} else {
			parkingLot.setVehicleIn(modelMapper.map(vehicle, Car.class));
		}
		Car car = parkingLotService.getInVehicle(parkingLot);
		return repositoryMongo.save(convertToEntity(car));
	}

	@PutMapping("/cars/")
	@ResponseBody
	public CarEntity getOutVehicle(@RequestBody VehicleDTO vehicle) {
		Optional<CarEntity> optional = repositoryMongo.findById(vehicle.getPlate());
		ParkingLot<Car> parkingLot = new ParkingLot<>();
		if(optional.isPresent())
			parkingLot.setVehicleOut(convertToDomain(optional.get()));
		Car car = parkingLotService.getOutVehicle(parkingLot);
		return repositoryMongo.save(convertToEntity(car));
	}

	@GetMapping("/cars/{plate}")
	@ResponseBody
	public CarEntity findVehicleByPlate(@PathVariable String plate) {
		Optional<CarEntity> optional = repositoryMongo.findById(plate);
		if(optional.isPresent())
			return optional.get();
		return null;
	}
	
	@GetMapping("/cars")
	@ResponseBody
	public List<CarEntity> findAllVehiclesParking() {
		return repositoryMongo.findAllCarsByIsParkingTrueAndStream().collect(Collectors.toList());
	}
	
	private CarEntity convertToEntity(Car vehicle) {
		return modelMapper.map(vehicle, CarEntity.class);
	}
	
	private Car convertToDomain(CarEntity vehicle) {
		return modelMapper.map(vehicle, Car.class);
	}

}
