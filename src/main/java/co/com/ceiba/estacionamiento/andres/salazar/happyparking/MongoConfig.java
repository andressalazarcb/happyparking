package co.com.ceiba.estacionamiento.andres.salazar.happyparking;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;

import com.mongodb.MongoClient;

@Configuration
public class MongoConfig extends AbstractMongoConfiguration {

	@Autowired
	private Environment environment;

	@Override
	public MongoClient mongoClient() {
		String url = environment.getProperty("MONGODB_URL") == null ? "localhost"
				: environment.getProperty("MONGODB_URL");
		int port = environment.getProperty("MONGODB_PORT") == null ? 27017
				: Integer.parseInt(environment.getProperty("MONGODB_PORT"));
		return new MongoClient(url, port);
	}

	@Override
	protected String getDatabaseName() {
		return environment.getProperty("MONGODB_DATABASE") == null ? "test"
				: environment.getProperty("MONGODB_DATABASE");
	}

}
