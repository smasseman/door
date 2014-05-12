package se.familjensmas.door;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author jorgen.smas@entercash.com
 */
public class Door {

	public enum DoorLockState {
		UNKNOWN, LOCKED, OPEN;
	}

	protected Logger logger = LoggerFactory.getLogger(getClass());
	private DoorLockState state = DoorLockState.UNKNOWN;

	public void lock() {
		logger.info("Door is locked.");
		state = DoorLockState.LOCKED;
	}

	public void unlock() {
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
