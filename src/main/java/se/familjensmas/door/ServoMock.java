package se.familjensmas.door;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author jorgen.smas@entercash.com
 */
public class ServoMock implements Servo {

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Override
	public void set(int degrees) {
		logger.info("Setting servo to " + degrees);
	}

	@Override
	public void off() {
		logger.info("Turn off servo.");
	}

	@Override
	public void setDuration(long duration) {
		logger.info("Ignore setting duration to " + duration);
	}
}
