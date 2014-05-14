package se.familjensmas.door;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import se.familjensmas.door.dt.User;
import se.familjensmas.door.jpa.UserDao;

/**
 * @author jorgen.smas@entercash.com
 */
@Service
public class Brain {

	@Resource
	private PlatformTransactionManager txMgr;
	@Resource
	private Keypad keypad;
	private StringBuffer chars = new StringBuffer();
	private Mode mode;
	private Logger logger = LoggerFactory.getLogger(getClass());
	@Resource
	private UserDao userDao;
	@Resource
	private Door door;
	@Resource
	private EventListener eventListener;

	private enum Mode {
		OPEN, CLOSE;
	}

	@PostConstruct
	public void init() {
		keypad.addListener(new ButtonListener() {

			@Override
			public void pushed(Button button) {
				Brain.this.pushed(button);
			}
		});
	}

	private void pushed(Button button) {
		if (button.getSign() == '*') {
			mode = Mode.OPEN;
			resetCode();
		} else if (button.getSign() == '#') {
			mode = Mode.CLOSE;
			resetCode();
		} else {
			if (mode == null)
				return;
			chars.append(button.getSign());
			if (chars.length() == 4) {
				String code = chars.toString();
				resetCode();
				if (mode == Mode.OPEN) {
					attemptToOpen(code);
				} else if (mode == Mode.CLOSE) {
					attemptToClose(code);
				} else {
					logger.error("Unknown mode: " + mode);
				}
			}
		}
	}

	private void attemptToClose(String code) {
		logger.error("Unimplemented.");
	}

	private void attemptToOpen(String code) {
		TransactionStatus tx = txMgr.getTransaction(new DefaultTransactionDefinition());
		try {
			User user = userDao.getUserByCode(code);
			if (user == null) {
				eventListener.unlockWithWrongCode(code);
			} else {
				if (user.isActivated()) {
					getDoor().unlock();
					eventListener.unlocked(user);
				} else {
					eventListener.unlockAttemptByDeactivatedUser(user);
				}
			}
			txMgr.commit(tx);
		} finally {
			if (!tx.isCompleted())
				txMgr.rollback(tx);
		}
	}

	private void resetCode() {
		chars.delete(0, chars.length());
	}

	public PlatformTransactionManager getTxMgr() {
		return txMgr;
	}

	public void setTxMgr(PlatformTransactionManager txMgr) {
		this.txMgr = txMgr;
	}

	public Keypad getKeypad() {
		return keypad;
	}

	public void setKeypad(Keypad keypad) {
		this.keypad = keypad;
	}

	public UserDao getUserDao() {
		return userDao;
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	public Door getDoor() {
		return door;
	}

	public void setDoor(Door door) {
		this.door = door;
	}

	public EventListener getEventListener() {
		return eventListener;
	}

	public void setEventListener(EventListener eventListener) {
		this.eventListener = eventListener;
	}
}
