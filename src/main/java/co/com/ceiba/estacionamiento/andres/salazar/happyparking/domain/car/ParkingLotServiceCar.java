package co.com.ceiba.estacionamiento.andres.salazar.happyparking.domain.car;

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
public class ParkingLotServiceCar implements ParkingLotService<Car>{
	
	private Settlement<Car> settlement;
	
	private VerifyGetIn<Car> verifyGetIn;
	
	private ParkingOrderFactory parkingOrderFactory;
	
	private CarFactory carFactory;
	
	@Autowired
	public ParkingLotServiceCar(Settlement<Car> settlement, VerifyGetIn<Car> verifyGetIn,
			ParkingOrderFactory parkingOrderFactory, CarFactory carFactory) {
		this.settlement = settlement;
		this.verifyGetIn = verifyGetIn;
		this.parkingOrderFactory = parkingOrderFactory;
		this.carFactory = carFactory;
	}

	public Car getInVehicle(ParkingLot<Car> parkingLot) {
		verifyGetIn(parkingLot);
		try {
			Car currentCar = null;
			if(parkingLot.getVehicleIn() != null) {
				currentCar = carFactory.getObject();
				currentCar.copy(parkingLot.getVehicleIn());
			}else {
				currentCar = carFactory.getObject();
				currentCar.setPlate(parkingLot.getVehicleIn().getPlate());
			}
			currentCar.setParking(true);
			ParkingOrder parkingOrder = parkingOrderFactory.getObject();
			parkingOrder.createParkingOrderId(currentCar.getPlate());
			currentCar.addParkingOrders(Arrays.asList(parkingOrder));
			return currentCar;
		} catch (Exception e) {
			throw new HappyParkingSystemException(e);
		}
	}
	
	private void verifyGetIn(ParkingLot<Car> parkingLot) {
		HappyParkingException exception = verifyGetIn.check(parkingLot);
		if (exception != null)
			throw exception;
	}
	
	public Car getOutVehicle(ParkingLot<Car> parkingLot) {
		try {
			Car carOut = carFactory.getObject();
			carOut.copy(parkingLot.getVehicleOut());
			ListIterator<ParkingOrder> parkingOrders = parkingLot.getVehicleOut().getParkingOrders().listIterator();
			while (parkingOrders.hasNext()) {
				ParkingOrder parkingOrder = parkingOrders.next();
				if (parkingOrder.isActive() && parkingOrder.getEndDate() == null) {
					parkingOrder.setEndDate(System.currentTimeMillis());
					parkingOrder.setPrice(settlement.calculate(parkingLot.getVehicleOut(), parkingOrder));
					if (parkingOrder.getPrice().equals(BigDecimal.ZERO))
						parkingOrder.setActive(false);
					carOut.setParking(false);
				}
			}
			return carOut;
		} catch (Exception e) {
			throw new HappyParkingSystemException(e);
		}
		
	}

}
