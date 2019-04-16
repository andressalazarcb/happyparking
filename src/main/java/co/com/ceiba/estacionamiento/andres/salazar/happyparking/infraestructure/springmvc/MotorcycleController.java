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

import co.com.ceiba.estacionamiento.andres.salazar.happyparking.domain.motorcycle.Motorcycle;
import co.com.ceiba.estacionamiento.andres.salazar.happyparking.domain.parkinglot.ParkingLot;
import co.com.ceiba.estacionamiento.andres.salazar.happyparking.domain.parkinglot.ParkingLotService;
import co.com.ceiba.estacionamiento.andres.salazar.happyparking.infraestructure.repository.MotorcycleEntity;
import co.com.ceiba.estacionamiento.andres.salazar.happyparking.infraestructure.repository.MotorcycleRepositoryMongo;

@RestController
public class MotorcycleController {
	
	private MotorcycleRepositoryMongo repositoryMongo;
	
	private ModelMapper modelMapper;
	
	private ParkingLotService<Motorcycle> parkingLotService;
	
	
	@Autowired
	public MotorcycleController(MotorcycleRepositoryMongo repositoryMongo, ModelMapper modelMapper,
			ParkingLotService<Motorcycle> parkingLotService) {
		this.repositoryMongo = repositoryMongo;
		this.modelMapper = modelMapper;
		this.parkingLotService = parkingLotService;
	}

	@PostMapping("/motorcycles/")
	@ResponseBody
	public MotorcycleEntity getInVehicle(@RequestBody VehicleDTO vehicle) {
		Optional<MotorcycleEntity> optional = repositoryMongo.findById(vehicle.getPlate());
		ParkingLot<Motorcycle> parkingLot = new ParkingLot<>();
		parkingLot.setQuantity(repositoryMongo.findCountMotorcycleByIsParking(true).intValue());
		if(optional.isPresent()) {
			parkingLot.setVehicleIn(convertToDomain(optional.get()));
		} else {
			parkingLot.setVehicleIn(modelMapper.map(vehicle, Motorcycle.class));
		}
		Motorcycle car = parkingLotService.getInVehicle(parkingLot);
		return repositoryMongo.save(convertToEntity(car));
	}
	
	@PutMapping("/motorcycles/")
	@ResponseBody
	public MotorcycleEntity getOutVehicle(@RequestBody VehicleDTO vehicle) {
		Optional<MotorcycleEntity> optional = repositoryMongo.findById(vehicle.getPlate());
		ParkingLot<Motorcycle> parkingLot = new ParkingLot<>();
		if(optional.isPresent())
			parkingLot.setVehicleOut(convertToDomain(optional.get()));
		Motorcycle car = parkingLotService.getOutVehicle(parkingLot);
		return repositoryMongo.save(convertToEntity(car));
	}
	
	@GetMapping("/motorcycles/{plate}")
	@ResponseBody
	public MotorcycleEntity findVehicleByPlate(@PathVariable String plate) {
		Optional<MotorcycleEntity> optional = repositoryMongo.findById(plate);
		if(optional.isPresent())
			return optional.get();
		return null;
	}
	
	@GetMapping("/motorcycles/")
	@ResponseBody
	public List<MotorcycleEntity> findAllVehiclesParking() {
		return repositoryMongo.findAllMotorcyclesByIsParkingTrueAndStream().collect(Collectors.toList());
	}
	
	private MotorcycleEntity convertToEntity(Motorcycle vehicle) {
		return modelMapper.map(vehicle, MotorcycleEntity.class);
	}
	
	private Motorcycle convertToDomain(MotorcycleEntity vehicle) {
		return modelMapper.map(vehicle, Motorcycle.class);
	}

}
