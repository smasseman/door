package se.familjensmas.door;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import se.familjensmas.door.dt.User;

/**
 * @author jorgen.smas@entercash.com
 */
@Service
public class EventListenerImpl implements EventListener {

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Override
	public void unlockWithWrongCode(String code) {
		logger.warn("Invalid code was used when attempting to unlock door. Code=" + code);
	}

	@Override
	public void unlocked(User user) {
		logger.info("Door is unlocked by " + user);
	}

	@Override
	public void unlockAttemptByDeactivatedUser(User user) {
		logger.warn("Non active user tried to unlock door. " + user);
	}
}
