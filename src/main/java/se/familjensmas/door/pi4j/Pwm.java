package se.familjensmas.door.pi4j;

import com.pi4j.io.gpio.GpioPinDigitalOutput;

/**
 * @author jorgen.smas@entercash.com
 */
public class Pwm {

	private Thread thread;
	private GpioPinDigitalOutput pin;

	public void start(final long upDuration, int frequency) throws InterruptedException {
		stop();
		final long downDuration = 1000 / frequency - upDuration;
		thread = new Thread() {
			@Override
			public void run() {
				try {
					while (true) {
						pin.high();
						sleep(upDuration);
						pin.low();
						sleep(downDuration);
					}
				} catch (InterruptedException e) {
					try {
						Pwm.this.stop();
					} catch (InterruptedException ignore) {
					}
				}
			}
		};
		thread.start();
	}

	public void stop() throws InterruptedException {
		if (thread != null) {
			Thread thread = this.thread;
			this.thread = null;
			pin.low();
			thread.interrupt();
			thread.join();
		}
	}

	public GpioPinDigitalOutput getPin() {
		return pin;
	}

	public void setPin(GpioPinDigitalOutput pin) {
		this.pin = pin;
	}
}
