package co.com.ceiba.estacionamiento.andres.salazar.happyparking;

import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.context.annotation.Configuration;

import co.com.ceiba.estacionamiento.andres.salazar.happyparking.car.ParkingCarControllerJersey;

@Configuration
public class JerseyConfig extends ResourceConfig {

	public JerseyConfig() {
		register(ParkingCarControllerJersey.class);
	}

}
