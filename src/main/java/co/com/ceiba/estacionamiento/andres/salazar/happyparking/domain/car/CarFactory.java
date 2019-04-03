package co.com.ceiba.estacionamiento.andres.salazar.happyparking.domain.car;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.stereotype.Component;

import co.com.ceiba.estacionamiento.andres.salazar.happyparking.domain.VehicleType;

@Component
public class CarFactory implements FactoryBean<Car>{
	
	public Car getObject() throws Exception {
		Car car = new Car();
		car.setParking(true);
		car.setType(VehicleType.CAR.getValue());
		return car;
	}

	@Override
	public Class<?> getObjectType() {
		return Car.class;
	}
	
	@Override
    public boolean isSingleton() {
        return false;
    }

}
