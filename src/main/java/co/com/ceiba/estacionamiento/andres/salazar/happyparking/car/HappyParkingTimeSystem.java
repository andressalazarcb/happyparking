package co.com.ceiba.estacionamiento.andres.salazar.happyparking.car;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class HappyParkingTimeSystem implements HappyParkingTime{
	
	private SimpleDateFormat simpleDateformat;
	
	@Autowired
	public HappyParkingTimeSystem(SimpleDateFormat simpleDateformat) {
		this.simpleDateformat = simpleDateformat;
	}

	public String geCurrentDay() {
		return simpleDateformat.format(new Date());
	}

}
