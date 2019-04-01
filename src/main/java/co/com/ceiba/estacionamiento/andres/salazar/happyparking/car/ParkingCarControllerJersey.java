package co.com.ceiba.estacionamiento.andres.salazar.happyparking.car;

import java.util.ArrayList;
import java.util.Optional;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;

import co.com.ceiba.estacionamiento.andres.salazar.happyparking.domain.Car;
import co.com.ceiba.estacionamiento.andres.salazar.happyparking.domain.HappyParkingResponse;

@Controller
@Path("/parkinglot/cars")
public class ParkingCarControllerJersey implements ParkingController {
	
	@Autowired
    private ObjectFactory<HappyParkingResponse> happyParkingResponseObjectFactory;
	
	private CarRepository carRepository;
	
	private CarService carService;

	@Autowired
	public ParkingCarControllerJersey(CarRepository carRepository, CarService carService) {
		this.carRepository = carRepository;
		this.carService = carService;
	}

	public ParkingCarControllerJersey() {
	}

	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Object getIn(Car car) {
		Car carSaved = carService.save(car);
		HappyParkingResponse happyParkingResponse = happyParkingResponseObjectFactory.getObject();
		happyParkingResponse.setHttpStatus(HttpStatus.CREATED);
		happyParkingResponse.setContent(carSaved);
		return happyParkingResponse;
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Object findVehicles() {
		return new ArrayList<String>();
	}
	
	@GET
	@Path("/{vehiclePlate}")
	@Produces(MediaType.APPLICATION_JSON)
	public Object findVehicle(@PathParam("vehiclePlate") String vehiclePlateParam) {
		Optional<Car> optional = carRepository.findById(vehiclePlateParam);
		
		HappyParkingResponse happyParkingResponse = happyParkingResponseObjectFactory.getObject();
		if(optional.isPresent()) {
			happyParkingResponse.setHttpStatus(HttpStatus.OK);
			happyParkingResponse.setContent(optional.get());
		}else {
			happyParkingResponse.setHttpStatus(HttpStatus.NO_CONTENT);
		}
		
		return happyParkingResponse;
	}

	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	public Object getOut() {
		return "Hello from Spring";
	}

}
