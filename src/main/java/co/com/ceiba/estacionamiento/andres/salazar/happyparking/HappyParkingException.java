package co.com.ceiba.estacionamiento.andres.salazar.happyparking;

public class HappyParkingException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1232007252240090307L;

	public HappyParkingException() {
	}

	public HappyParkingException(String arg0) {
		super(arg0);
	}

	public HappyParkingException(Throwable arg0) {
		super(arg0);
	}

	public HappyParkingException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public HappyParkingException(String arg0, Throwable arg1, boolean arg2, boolean arg3) {
		super(arg0, arg1, arg2, arg3);
	}

}
