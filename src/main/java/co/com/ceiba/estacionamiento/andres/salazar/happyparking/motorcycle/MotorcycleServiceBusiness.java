package co.com.ceiba.estacionamiento.andres.salazar.happyparking.motorcycle;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.ListIterator;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.com.ceiba.estacionamiento.andres.salazar.happyparking.HappyParkingException;
import co.com.ceiba.estacionamiento.andres.salazar.happyparking.domain.Motorcycle;
import co.com.ceiba.estacionamiento.andres.salazar.happyparking.domain.ParkingOrder;
import co.com.ceiba.estacionamiento.andres.salazar.happyparking.motorcycle.settlement.MotorcycleSettlement;

@Service
public class MotorcycleServiceBusiness implements MotorcycleService {
	
	private MotorcycleRepositoryMongo motorcycleRepository;
	
	private MotorcycleSettlement motorcycleSettlement;

	@Autowired
	public MotorcycleServiceBusiness(MotorcycleRepositoryMongo motorcycleRepository,
			MotorcycleSettlement motorcycleSettlement) {
		this.motorcycleRepository = motorcycleRepository;
		this.motorcycleSettlement = motorcycleSettlement;
	}

	public Motorcycle save(Motorcycle motorcycle) {
		checkIfThereWasSameVehicleInParkingLot(motorcycle.getPlate());
		checkIfThereAreSpaceToParking();
		Motorcycle motorcycleToSave = motorcycle.copy();
		motorcycleToSave.setParking(true);
		motorcycleToSave.setType("Moto");
		ParkingOrder parkingOrder = new ParkingOrder();
		parkingOrder.setParkingOrderId(motorcycleToSave.getPlate()+"_"+System.currentTimeMillis());
		parkingOrder.setActive(true);
		parkingOrder.setStartDate(System.currentTimeMillis());
		motorcycleToSave.setParkingOrders(Arrays.asList(parkingOrder));
		return motorcycleRepository.save(motorcycleToSave);
	}
	
	private void checkIfThereWasSameVehicleInParkingLot(String plate) {
		Motorcycle motorcycleFound = motorcycleRepository.findMotorcycleByPlateAndIsParking(plate, true);
		if (motorcycleFound != null) {
			throw new HappyParkingException("hay una moto parqueada");
		}
	}
	
	private void checkIfThereAreSpaceToParking() {
		Long countMotorcycles = motorcycleRepository.findCountMotorcycleByIsParking(true);
		if (countMotorcycles >= 10) {
			throw new HappyParkingException("no hay espacio para motos");
		}
	}

	@Override
	public Stream<Motorcycle> findAllMotorcyclesParking() {
		return motorcycleRepository.findAllMotorcyclesByIsParkingTrueAndStream();
	}

	@Override
	public Motorcycle getOutVehicle(String plate) {
		Motorcycle motorcycleFound = motorcycleRepository.findByPlateAndParkingActive(plate);
		if(motorcycleFound == null) {
			throw new HappyParkingException("No esta el vehiculo en el parqueadero");
		}
		ListIterator<ParkingOrder> parkingOrders = motorcycleFound.getParkingOrders().listIterator();
		while (parkingOrders.hasNext()) {
			ParkingOrder parkingOrder = (ParkingOrder) parkingOrders.next();
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
	

}
