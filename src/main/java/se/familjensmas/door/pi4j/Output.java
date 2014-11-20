package se.familjensmas.door.pi4j;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.Pin;
import com.pi4j.io.gpio.PinState;
import com.pi4j.io.gpio.RaspiPin;

/**
 * @author jorgen.smas@entercash.com
 */
public class Output {

	public static void main(String[] args) throws Exception {
		Integer pinNumber = Integer.parseInt(args[0]);
		String pinName = pinNumber.toString();
		if (pinName.length() == 1)
			pinName = "0" + pinName;
		pinName = "GPIO_" + pinName;
		final GpioController gpio = GpioFactory.getInstance();
		Pin x = (Pin) RaspiPin.class.getField(pinName).get(null);
		final GpioPinDigitalOutput pin = gpio.provisionDigitalOutputPin(x, "OutputTest", PinState.LOW);

		boolean on = false;
		for (int i = 1; i < args.length; i++) {
			Long duration = Long.parseLong(args[i]);
			if (on) {
				System.out.println("OFF");
			} else {
				System.out.println("ON");
			}
			on = !on;
			pin.toggle();
			Thread.sleep(duration);
		}
		System.out.println("QUIT");
		pin.low();
		gpio.shutdown();
	}
}
