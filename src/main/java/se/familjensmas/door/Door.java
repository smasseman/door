package se.familjensmas.door;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * @author jorgen.smas@entercash.com
 */
@Service
public class Door {

	@Resource
	private Servo servo;

	public enum DoorLockState {
		UNKNOWN, LOCKED, OPEN;
	}

	protected Logger logger = LoggerFactory.getLogger(getClass());
	private DoorLockState state = DoorLockState.UNKNOWN;

	public void lock() {
		servo.set(-60);
		logger.info("Door is locked.");
		state = DoorLockState.LOCKED;
	}

	public void unlock() {
		servo.set(47);
		logger.info("Door is unlocked.");
		state = DoorLockState.OPEN;
	}

	public DoorLockState getState() {
		return state;
	}

	@Override
	public String toString() {
		return getClass().getSimpleName() + "[" + state + "]";
	}
}
