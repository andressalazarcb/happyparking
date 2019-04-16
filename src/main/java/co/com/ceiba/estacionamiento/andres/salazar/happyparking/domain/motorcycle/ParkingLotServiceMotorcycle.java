package co.com.ceiba.estacionamiento.andres.salazar.happyparking.domain.motorcycle;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.ListIterator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.com.ceiba.estacionamiento.andres.salazar.happyparking.domain.exception.HappyParkingException;
import co.com.ceiba.estacionamiento.andres.salazar.happyparking.domain.exception.HappyParkingSystemException;
import co.com.ceiba.estacionamiento.andres.salazar.happyparking.domain.parkinglot.ParkingLot;
import co.com.ceiba.estacionamiento.andres.salazar.happyparking.domain.parkinglot.ParkingLotService;
import co.com.ceiba.estacionamiento.andres.salazar.happyparking.domain.parkinglot.rule.VerifyGetIn;
import co.com.ceiba.estacionamiento.andres.salazar.happyparking.domain.parkinglot.settlement.Settlement;
import co.com.ceiba.estacionamiento.andres.salazar.happyparking.domain.parkingorder.ParkingOrder;
import co.com.ceiba.estacionamiento.andres.salazar.happyparking.domain.parkingorder.ParkingOrderFactory;

@Service
public class ParkingLotServiceMotorcycle implements ParkingLotService<Motorcycle>{
	
	private VerifyGetIn<Motorcycle> verifyGetIn;
	
	private MotorcycleFactory motorcycleFactory;
	
	private ParkingOrderFactory parkingOrderFactory;
	
	private Settlement<Motorcycle> settlement;
	
	
	@Autowired
	public ParkingLotServiceMotorcycle(VerifyGetIn<Motorcycle> verifyGetIn, MotorcycleFactory motorcycleFactory,
			ParkingOrderFactory parkingOrderFactory, Settlement<Motorcycle> settlement) {
		this.verifyGetIn = verifyGetIn;
		this.motorcycleFactory = motorcycleFactory;
		this.parkingOrderFactory = parkingOrderFactory;
		this.settlement = settlement;
	}

	@Override
	public Motorcycle getInVehicle(ParkingLot<Motorcycle> parkingLot) {
		verifyGetIn(parkingLot);
		try {
			Motorcycle currentMotorcycle = null;
			if(parkingLot.getVehicleIn() != null) {
				currentMotorcycle = motorcycleFactory.getObject();
				currentMotorcycle.copy(parkingLot.getVehicleIn());
			}else {
				currentMotorcycle = motorcycleFactory.getObject();
				currentMotorcycle.setPlate(parkingLot.getVehicleIn().getPlate());
			}
			currentMotorcycle.setParking(true);
			ParkingOrder parkingOrder = parkingOrderFactory.getObject();
			parkingOrder.createParkingOrderId(currentMotorcycle.getPlate());
			currentMotorcycle.addParkingOrders(Arrays.asList(parkingOrder));
			return currentMotorcycle;
		} catch (Exception e) {
			throw new HappyParkingSystemException(e);
		}
	}
	
	private void verifyGetIn(ParkingLot<Motorcycle> parkingLot) {
		HappyParkingException exception = verifyGetIn.check(parkingLot);
		if(exception != null)
			throw exception;
	}

	@Override
	public Motorcycle getOutVehicle(ParkingLot<Motorcycle> parkingLot) {
		try {
			Motorcycle motoOut = motorcycleFactory.getObject();
			motoOut.copy(parkingLot.getVehicleOut());
			ListIterator<ParkingOrder> parkingOrders = parkingLot.getVehicleOut().getParkingOrders().listIterator();
			while (parkingOrders.hasNext()) {
				ParkingOrder parkingOrder = parkingOrders.next();
				if (parkingOrder.isActive() && parkingOrder.getEndDate() == null) {
					parkingOrder.setEndDate(System.currentTimeMillis());
					parkingOrder.setPrice(settlement.calculate(parkingLot.getVehicleOut(), parkingOrder));
					if (parkingOrder.getPrice().equals(BigDecimal.ZERO))
						parkingOrder.setActive(false);
					motoOut.setParking(false);
				}
			}
			return motoOut;
		} catch (Exception e) {
			throw new HappyParkingSystemException(e);
		}
	}

}
