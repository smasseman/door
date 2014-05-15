package se.familjensmas.door.pi4j;

import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import com.pi4j.io.gpio.GpioPinDigitalOutput;

/**
 * @author jorgen.smas@entercash.com
 */
public class PwmTest {

	private long startTime = System.currentTimeMillis();
	private long lastTime = System.currentTimeMillis();

	@Test
	public void test() throws InterruptedException {
		Pwm pwm = new Pwm();
		GpioPinDigitalOutput pin = Mockito.mock(GpioPinDigitalOutput.class);
		Answer<Object> answer = new Answer<Object>() {

			@Override
			public Object answer(InvocationOnMock invocation) throws Throwable {
				System.out.println((System.currentTimeMillis() - startTime) + " : "
						+ (System.currentTimeMillis() - lastTime) + " : " + invocation.getMethod().getName());
				lastTime = System.currentTimeMillis();
				return null;
			}
		};
		Mockito.doAnswer(answer).when(pin).high();
		Mockito.doAnswer(answer).when(pin).low();
		pwm.setPin(pin);

		startTime = System.currentTimeMillis();
		lastTime = System.currentTimeMillis();

		// pwm.start(200, 1);
		pwm.start(1, 50);
		Thread.sleep(4500);
		pwm.stop();
		Mockito.verify(pin, Mockito.times(5)).high();
	}
}
