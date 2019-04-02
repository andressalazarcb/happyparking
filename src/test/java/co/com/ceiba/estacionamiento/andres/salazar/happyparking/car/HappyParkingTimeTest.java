package co.com.ceiba.estacionamiento.andres.salazar.happyparking.car;

import static org.assertj.core.api.Assertions.*;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class HappyParkingTimeTest {
	
	@Autowired
	private SimpleDateFormat simpleDateFormat;
	
	@Autowired
	private HappyParkingTime happyParkingTime;

	@Test
	public void test() {
		String expectedDate = simpleDateFormat.format(new Date());
		String currentDate = happyParkingTime.geCurrentDay();
		assertThat(currentDate).isEqualTo(expectedDate);
	}

}
