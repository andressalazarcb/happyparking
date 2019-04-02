package co.com.ceiba.estacionamiento.andres.salazar.happyparking.motorcycle;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.ListIterator;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.com.ceiba.estacionamiento.andres.salazar.happyparking.HappyParkingException;
import co.com.ceiba.estacionamiento.andres.salazar.happyparking.domain.Motorcycle;
import co.com.ceiba.estacionamiento.andres.salazar.happyparking.domain.ParkingOrder;

@Service
public class MotorcycleServiceBusiness implements MotorcycleService {
	
	private MotorcycleRepositoryMongo motorcycleRepository;

	@Autowired
	public MotorcycleServiceBusiness(MotorcycleRepositoryMongo motorcycleRepository) {
		this.motorcycleRepository = motorcycleRepository;
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
			throw new HappyParkingException("No esta el vehiulo en el parqueadero");
		}
		ListIterator<ParkingOrder> parkingOrders = motorcycleFound.getParkingOrders().listIterator();
		while (parkingOrders.hasNext()) {
			ParkingOrder parkingOrder = (ParkingOrder) parkingOrders.next();
			if(parkingOrder.isActive() && parkingOrder.getEndDate() == null) {
				parkingOrder.setEndDate(System.currentTimeMillis());
				BigDecimal price = a(parkingOrder);
				if (motorcycleFound.getCc() >= 500) {
					price = price.add(BigDecimal.valueOf(2000d));
				}
				parkingOrder.setPrice(price);
				if(parkingOrder.getPrice().intValue() == BigDecimal.valueOf(0d).intValue()) {
					parkingOrder.setActive(false);
				}
				motorcycleFound.setParking(false);
			}
		}
		
		return motorcycleRepository.save(motorcycleFound);
	}
	
	private BigDecimal a(ParkingOrder parkingOrder) {
		long totalHours = TimeUnit.MILLISECONDS.toHours(parkingOrder.getEndDate() - parkingOrder.getStartDate());
		long days = calculateDays(totalHours);
		long hours = calculateHours(totalHours);
		BigDecimal hourTotalPrice = getPriceToPayHours(hours);
		BigDecimal dayTotalPrice = getPriceToPayDays(days);
		return hourTotalPrice.add(dayTotalPrice);
	}
	
	@Override
	public long calculateDays(long totalHours) {
		double res1 = totalHours / 9d;
		if(res1 >= 1) {
			double res2 = totalHours / 24d;
			if(res2 >= 1) {
				return (int) res2;
			}
		}
		return (long) res1;
	}

	@Override
	public long calculateHours(long totalHours) {
		long days = calculateDays(totalHours);
		if(days >= 1) {
			long res = totalHours - (24 * days);
			if(res < 0) {
				return 0;
			}
			return res;
		}
		return totalHours;
	}
	
	private BigDecimal getPriceToPayHours(long hours) {
		return BigDecimal.valueOf(500L).multiply(BigDecimal.valueOf(hours));
	}
	
	private BigDecimal getPriceToPayDays(long days) {
		return BigDecimal.valueOf(4000L).multiply(BigDecimal.valueOf(days));
	}

}
