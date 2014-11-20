package se.familjensmas.door.pi4j;

import java.io.File;
import java.io.FileReader;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author jorgen.smas@entercash.com
 */
public class BlasterServoTest {

	private BlasterServo s;

	@Before
	public void init() {
		s = new BlasterServo();
		File file = new File(getClass().getSimpleName());
		s.setFile(file);
	}

	@After
	public void destroy() {
		s.getFile().delete();
	}

	@Test(expected = IllegalArgumentException.class)
	public void testToLowArgument() {
		BlasterServo s = new BlasterServo();
		s.set(-91);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testToHighArgument() {
		BlasterServo s = new BlasterServo();
		s.set(92);
	}

	@Test
	public void testOff() {
		s.off();
		expectFileContent("0=0");

	}

	@Test
	public void test() {
		s.set(0);
		expectFileContent("0=1400us");

		s.set(-90);
		expectFileContent("0=500us");

		s.set(90);
		expectFileContent("0=2300us");
	}

	private void expectFileContent(String string) {
		try {
			FileReader r = new FileReader(s.getFile());
			char[] cbuf = new char[1000];
			r.read(cbuf, 0, cbuf.length);
			r.close();
			Assert.assertEquals(string.trim(), new String(cbuf).trim());
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
