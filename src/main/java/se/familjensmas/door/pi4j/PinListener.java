package se.familjensmas.door.pi4j;

import java.util.LinkedList;
import java.util.List;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalInput;
import com.pi4j.io.gpio.Pin;
import com.pi4j.io.gpio.PinPullResistance;
import com.pi4j.io.gpio.RaspiPin;
import com.pi4j.io.gpio.event.GpioPinDigitalStateChangeEvent;
import com.pi4j.io.gpio.event.GpioPinListenerDigital;

/**
 * @author jorgen.smas@entercash.com
 */
public class PinListener {

	public static void main(String[] args) throws InterruptedException {

		final GpioController gpio = GpioFactory.getInstance();

		List<Pin> pins = new LinkedList<>();
		pins.add(RaspiPin.GPIO_00);
		pins.add(RaspiPin.GPIO_01);
		pins.add(RaspiPin.GPIO_02);
		pins.add(RaspiPin.GPIO_03);
		pins.add(RaspiPin.GPIO_04);
		pins.add(RaspiPin.GPIO_05);
		pins.add(RaspiPin.GPIO_06);
		pins.add(RaspiPin.GPIO_07);

		for (final Pin pin : pins) {
			GpioPinDigitalInput input = gpio.provisionDigitalInputPin(pin, pin.getName(), PinPullResistance.PULL_DOWN);
			GpioPinListenerDigital listener = new PrintStateListener();
			input.addListener(listener);
		}

		synchronized (PinListener.class) {
			PinListener.class.wait();
		}
	}

	private static class PrintStateListener implements GpioPinListenerDigital {

		@Override
		public void handleGpioPinDigitalStateChangeEvent(GpioPinDigitalStateChangeEvent event) {
			System.out.println(event.getPin().getName() + " is " + event.getState());
		}
	};

}
