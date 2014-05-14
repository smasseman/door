package se.familjensmas.door;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;

import se.familjensmas.door.dt.User;
import se.familjensmas.door.jpa.UserDaoMock;

/**
 * @author jorgen.smas@entercash.com
 */
public class BrainTest {

	private Brain b;
	private Door door;
	private UserDaoMock userDao;
	private User user;
	private EventListener eventListener;
	private Keypad keypad;

	@Before
	public void init() {
		this.b = new Brain();

		door = Mockito.mock(Door.class);
		b.setDoor(door);

		keypad = new Keypad();
		b.setKeypad(keypad);

		PlatformTransactionManager txMgr = Mockito.mock(PlatformTransactionManager.class);
		TransactionStatus tx = Mockito.mock(TransactionStatus.class);
		Mockito.when(txMgr.getTransaction(Mockito.any(TransactionDefinition.class))).thenReturn(tx);
		b.setTxMgr(txMgr);

		userDao = new UserDaoMock();
		user = new User();
		user.setCode("1234");
		user.setActivated(true);
		userDao.add(user);
		b.setUserDao(userDao);

		eventListener = Mockito.mock(EventListener.class);
		b.setEventListener(eventListener);

		b.init();
	}

	@Test
	public void testUnlockOk() {
		enterCode(keypad, "*1234");
		Mockito.verify(door, Mockito.times(1)).unlock();
		Mockito.verify(eventListener, Mockito.times(1)).unlocked(user);
	}

	@Test
	public void testUnlockOk2() {
		enterCode(keypad, "*12#12*1234");
		Mockito.verify(door, Mockito.times(1)).unlock();
		Mockito.verify(eventListener, Mockito.times(1)).unlocked(user);
	}

	@Test
	public void testNotActiveUser() {
		user.setActivated(false);
		enterCode(keypad, "*1234");
		Mockito.verify(door, Mockito.times(0)).unlock();
		Mockito.verify(eventListener, Mockito.times(1)).unlockAttemptByDeactivatedUser(user);
	}

	@Test
	public void testWrongCode() {
		user.setActivated(false);
		enterCode(keypad, "*9999");
		Mockito.verify(door, Mockito.times(0)).unlock();
		Mockito.verify(eventListener, Mockito.times(1)).unlockWithWrongCode("9999");
	}

	private void enterCode(Keypad keypad, String string) {
		for (char c : string.toCharArray()) {
			Button b = keypad.getButton(c);
			Assert.assertNotNull("No button on keypad with sign " + c, b);
			b.push();
		}
	}
}
