package se.familjensmas.door;

import se.familjensmas.door.dt.User;

/**
 * @author jorgen.smas@entercash.com
 */
public interface EventListener {

	void unlockWithWrongCode(String code);

	void unlocked(User user);

	void unlockAttemptByDeactivatedUser(User user);

	void lockWithWrongCode(String code);

	void locked(User user);

	void lockAttemptByDeactivatedUser(User user);

}
