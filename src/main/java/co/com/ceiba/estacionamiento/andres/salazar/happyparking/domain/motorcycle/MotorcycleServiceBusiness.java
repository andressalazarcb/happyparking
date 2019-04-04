package co.com.ceiba.estacionamiento.andres.salazar.happyparking.domain.motorcycle;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.ListIterator;
import java.util.Optional;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.com.ceiba.estacionamiento.andres.salazar.happyparking.domain.HappyParkingException;
import co.com.ceiba.estacionamiento.andres.salazar.happyparking.domain.HappyParkingSystemException;
import co.com.ceiba.estacionamiento.andres.salazar.happyparking.domain.Settlement;
import co.com.ceiba.estacionamiento.andres.salazar.happyparking.domain.VehicleType;
import co.com.ceiba.estacionamiento.andres.salazar.happyparking.domain.VerifyGetIn;
import co.com.ceiba.estacionamiento.andres.salazar.happyparking.domain.parkingorder.ParkingOrder;
import co.com.ceiba.estacionamiento.andres.salazar.happyparking.domain.parkingorder.ParkingOrderFactory;
import co.com.ceiba.estacionamiento.andres.salazar.happyparking.infraestructure.repository.MotorcycleRepositoryMongo;

@Service
public class MotorcycleServiceBusiness implements MotorcycleService {
	
	private MotorcycleFactory motorcycleFactory;
	
	private ParkingOrderFactory parkingOrderFactory;
	
	private MotorcycleRepositoryMongo motorcycleRepository;
	
	private Settlement<Motorcycle> motorcycleSettlement;
	
	private VerifyGetIn<Motorcycle> verifyGetIn;

	@Autowired
	public MotorcycleServiceBusiness(MotorcycleFactory motorcycleFactory, ParkingOrderFactory parkingOrderFactory,
			MotorcycleRepositoryMongo motorcycleRepository, Settlement<Motorcycle> motorcycleSettlement,
			VerifyGetIn<Motorcycle> verifyGetIn) {
		this.motorcycleFactory = motorcycleFactory;
		this.parkingOrderFactory = parkingOrderFactory;
		this.motorcycleRepository = motorcycleRepository;
		this.motorcycleSettlement = motorcycleSettlement;
		this.verifyGetIn = verifyGetIn;
	}

	@Override
	public Motorcycle getOutVehicle(String plate) {
		Motorcycle motorcycleFound = motorcycleRepository.findByPlateAndParkingActive(plate);
		if(motorcycleFound == null) {
			throw new HappyParkingException("No esta el vehiculo en el parqueadero");
		}
		ListIterator<ParkingOrder> parkingOrders = motorcycleFound.getParkingOrders().listIterator();
		while (parkingOrders.hasNext()) {
			ParkingOrder parkingOrder = parkingOrders.next();
			if(parkingOrder.isActive() && parkingOrder.getEndDate() == null) {
				parkingOrder.setEndDate(System.currentTimeMillis());
				parkingOrder.setPrice(motorcycleSettlement.calculate(motorcycleFound, parkingOrder));
				if(parkingOrder.getPrice().equals(BigDecimal.ZERO))
					parkingOrder.setActive(false);
				motorcycleFound.setParking(false);
			}
		}
		
		return motorcycleRepository.save(motorcycleFound);
	}

	@Override
	public Stream<Motorcycle> findAllVehiclesParking() {
		if (motorcycleRepository.findCountMotorcycleByIsParking(true) > 0) {
			return motorcycleRepository.findAllMotorcyclesByIsParkingTrueAndStream();
		}
		return null;
	}
	
	@Override
	public Motorcycle getInVehicle(Motorcycle vehicle){
		verifyGetIn(vehicle);
		try {
			Motorcycle currentMotorcycle = null;
			
			Optional<Motorcycle> optional = motorcycleRepository.findById(vehicle.getPlate());
			
			if(optional.isPresent())
				currentMotorcycle = optional.get();
			
			if(currentMotorcycle != null) {
				currentMotorcycle.setParking(true);
				ParkingOrder parkingOrder = parkingOrderFactory.getObject();
				parkingOrder.createParkingOrderId(vehicle.getPlate());
				currentMotorcycle.getParkingOrders().add(parkingOrder);
			}else {
				currentMotorcycle = motorcycleFactory.getObject();
				currentMotorcycle.copy(vehicle);
				currentMotorcycle.setPlate(vehicle.getPlate());
				currentMotorcycle.setParking(true);
				currentMotorcycle.setType(VehicleType.MOTORCYCLE.getValue());
				ParkingOrder parkingOrder = parkingOrderFactory.getObject();
				parkingOrder.createParkingOrderId(vehicle.getPlate());
				currentMotorcycle.setParkingOrders(Arrays.asList(parkingOrder));
			}
			return motorcycleRepository.save(currentMotorcycle);
		} catch (Exception e) {
			throw new HappyParkingSystemException(e);
		}
		
	}
	
	private void verifyGetIn(Motorcycle motorcycle) {
		HappyParkingException exception = verifyGetIn.check(motorcycle);
		if(exception != null)
			throw exception;
	}
	

}
