package se.familjensmas.door.pi4j;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.PinState;
import com.pi4j.io.gpio.RaspiPin;

/**
 * @author jorgen.smas@entercash.com
 */
public class Blink {

	public static void main(String[] args) throws InterruptedException {
		final GpioController gpio = GpioFactory.getInstance();
		final GpioPinDigitalOutput pin = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_01, "MyLED", PinState.LOW);
		System.out.println("ON");
		pin.high();
		Thread.sleep(1000);
		pin.low();
		System.out.println("OFF");
		gpio.shutdown();
	}
}
