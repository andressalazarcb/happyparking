package co.com.ceiba.estacionamiento.andres.salazar.happyparking.infraestructure.jersey.car;

import java.util.stream.Stream;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import co.com.ceiba.estacionamiento.andres.salazar.happyparking.application.VehicleParking;
import co.com.ceiba.estacionamiento.andres.salazar.happyparking.domain.car.Car;
import co.com.ceiba.estacionamiento.andres.salazar.happyparking.infraestructure.jersey.HappyParkingResponse;

@Controller
@Path("/parkinglot/cars")
public class CarControllerJersey {

	private ObjectFactory<HappyParkingResponse> happyParkingResponseObjectFactory;

	private VehicleParking<Car> vehicleParking;

	@Autowired
	public CarControllerJersey(ObjectFactory<HappyParkingResponse> happyParkingResponseObjectFactory,
			VehicleParking<Car> vehicleParking) {
		this.happyParkingResponseObjectFactory = happyParkingResponseObjectFactory;
		this.vehicleParking = vehicleParking;
	}

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response getIn(CarRequestJersey requestJersey) {
		Car car = getCarFromCarRequestJersey(requestJersey);
		Car carSaved = vehicleParking.getInVehicleToParkingLot(car);
		HappyParkingResponse happyParkingResponse = happyParkingResponseObjectFactory.getObject();
		happyParkingResponse.setStatus(Status.CREATED.getStatusCode());
		happyParkingResponse.setContent(carSaved);
		return Response.status(Status.CREATED).entity(happyParkingResponse).type(MediaType.APPLICATION_JSON).build();
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response findAllVehiclesParking() {
		Status status = Status.NO_CONTENT;
		HappyParkingResponse happyParkingResponse = happyParkingResponseObjectFactory.getObject();
		Stream<Car> stream = vehicleParking.findVehiclesThatAreParking();
		if (stream != null) {
			status = Status.OK;
			happyParkingResponse.setStatus(Status.OK.getStatusCode());
			happyParkingResponse.setContent(vehicleParking.findVehiclesThatAreParking());
		}
		return Response.status(status).entity(happyParkingResponse).build();
	}

	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response getOut(CarRequestJersey requestJersey) {
		HappyParkingResponse happyParkingResponse = happyParkingResponseObjectFactory.getObject();
		Car car = getCarFromCarRequestJersey(requestJersey);
		Car carOut = vehicleParking.getOutVehicleOfParkingLot(car.getPlate());
		happyParkingResponse.setContent(carOut);
		happyParkingResponse.setStatus(Status.OK.getStatusCode());
		return Response.status(Status.OK).entity(happyParkingResponse).build();
	}

	@GET
	@Path("{plate}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response findVehicleByPlate(@PathParam("plate") String plate) {
		Status status = Status.NO_CONTENT;
		HappyParkingResponse happyParkingResponse = happyParkingResponseObjectFactory.getObject();
		Car car = vehicleParking.findVehicleByPlate(plate);
		if (car != null) {
			status = Status.OK;
			happyParkingResponse.setStatus(Status.OK.getStatusCode());
			happyParkingResponse.setContent(car);
		}
		return Response.status(status).entity(happyParkingResponse).build();
	}

	private Car getCarFromCarRequestJersey(CarRequestJersey requestJersey) {
		Car car = new Car();
		car.setPlate(requestJersey.getPlate());
		return car;
	}

}
