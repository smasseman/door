package se.familjensmas.door.pi4j;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import se.familjensmas.door.Servo;

/**
 * @author jorgen.smas@entercash.com
 */
public class BlasterServo implements Servo {

	// 500us = right
	// 2330us = left
	private Logger logger = LoggerFactory.getLogger(getClass());
	private int pin = 0;
	private File file = new File("/dev/servoblaster");

	@Override
	public void set(int degrees) {
		if (degrees < -90)
			throw new IllegalArgumentException("-90 is min and " + degrees + " is invalid.");
		if (degrees > 90)
			throw new IllegalArgumentException("90 is max and " + degrees + " is invalid.");
		int mappedValue = map(degrees);
		String toWrite = mappedValue + "us";
		write(toWrite);
		logger.debug("Written {} which is same as {} degrees.", toWrite, degrees);
	}

	private int map(int degrees) {
		return (degrees + 90) * 10 + 500;
	}

	@Override
	public void off() {
		write("0");
	}

	private void write(String string) {
		try {
			FileOutputStream w = new FileOutputStream(file, false);
			w.write((pin + "=" + string + "\n").getBytes());
			w.close();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	@Override
	public void setDuration(long duration) {
		// TODO Auto-generated method stub

	}
}
