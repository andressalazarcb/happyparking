package co.com.ceiba.estacionamiento.andres.salazar.happyparking.car;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import co.com.ceiba.estacionamiento.andres.salazar.happyparking.domain.Car;
import co.com.ceiba.estacionamiento.andres.salazar.happyparking.jersey.HappyParkingResponse;

@Controller
@Path("/parkinglot/cars")
public class CarControllerJersey implements CarController {

	@Autowired
	private ObjectFactory<HappyParkingResponse> happyParkingResponseObjectFactory;

	private CarRepositoryMongo carRepository;

	private CarService carService;

	@Autowired
	public CarControllerJersey(CarRepositoryMongo carRepository, CarService carService) {
		this.carRepository = carRepository;
		this.carService = carService;
	}

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response getIn(CarRequestJersey requestJersey) {
		Car car = getCarFromCarRequestJersey(requestJersey);
		Car carSaved = carService.save(car);
		HappyParkingResponse happyParkingResponse = happyParkingResponseObjectFactory.getObject();
		happyParkingResponse.setStatus(Status.CREATED.getStatusCode());
		happyParkingResponse.setContent(carSaved);

		return Response.status(Status.CREATED).entity(happyParkingResponse).type(MediaType.APPLICATION_JSON).build();
	}

	@Override
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response findAllVehiclesParking() {
		HappyParkingResponse happyParkingResponse = happyParkingResponseObjectFactory.getObject();
		Status status = Status.NO_CONTENT;
		if (carRepository.findCountCarsByIsParking(true) > 0) {
			status = Status.OK;
			happyParkingResponse.setStatus(Status.OK.getStatusCode());
			happyParkingResponse.setContent(carService.findAllCarsParking());
		}

		return Response.status(status).entity(happyParkingResponse).build();
	}

	private Car getCarFromCarRequestJersey(CarRequestJersey requestJersey) {
		Car car = new Car();
		car.setPlate(requestJersey.getPlate());
		return car;
	}

	@PUT
	@Path("out")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response getOut(CarRequestJersey requestJersey) {
		HappyParkingResponse happyParkingResponse = happyParkingResponseObjectFactory.getObject();
		Car car = getCarFromCarRequestJersey(requestJersey);
		Car carOut = carService.getOutVehicle(car.getPlate());
		happyParkingResponse.setContent(carOut);
		happyParkingResponse.setStatus(Status.OK.getStatusCode());
		return Response
			      .status(Status.OK)
			      .entity(happyParkingResponse)
			      .build();
	}

}
