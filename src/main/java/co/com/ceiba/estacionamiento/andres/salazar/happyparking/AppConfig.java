package co.com.ceiba.estacionamiento.andres.salazar.happyparking;

import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.ResourceBundle;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {
	
	@Bean
	public SimpleDateFormat simpleDateFormat() {
		return new SimpleDateFormat("EEEE");
	}
	
	@Bean
	public ResourceBundle resourceBundle() {
		Locale.setDefault(new Locale("en", "US"));
		return ResourceBundle.getBundle("messages");
	}
	
	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

}
