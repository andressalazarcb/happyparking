package co.com.ceiba.estacionamiento.andres.salazar.happyparking.domain.car;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.stereotype.Component;

@Component
public class CarFactory implements FactoryBean<Car>{
	
	public Car getObject() throws Exception {
		Car car = new Car();
		car.setParking(true);
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
