package co.com.ceiba.estacionamiento.andres.salazar.happyparking;

import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.context.annotation.Configuration;

import co.com.ceiba.estacionamiento.andres.salazar.happyparking.car.ParkingCarControllerJersey;
import co.com.ceiba.estacionamiento.andres.salazar.happyparking.motorcycle.ParkingMotorcycleControllerJersey;

@Configuration
public class JerseyConfig extends ResourceConfig {

	public JerseyConfig() {
		register(ParkingCarControllerJersey.class);
		register(ParkingMotorcycleControllerJersey.class);
		register(HappyParkingException.class);
	}

}
