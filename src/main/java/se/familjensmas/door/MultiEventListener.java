package se.familjensmas.door;

import java.util.Collection;

import se.familjensmas.door.dt.User;

/**
 * @author jorgen.smas@entercash.com
 */
public class MultiEventListener implements EventListener {

	private Collection<EventListener> listeners;

	public MultiEventListener(Collection<EventListener> others) {
		this.listeners = others;
	}

	@Override
	public void unlockWithWrongCode(String code) {
		for (EventListener listener : listeners) {
			listener.unlockWithWrongCode(code);
		}
	}

	@Override
	public void unlocked(User user) {
		for (EventListener listener : listeners) {
			listener.unlocked(user);
		}
	}

	@Override
	public void unlockAttemptByDeactivatedUser(User user) {
		for (EventListener listener : listeners) {
			listener.unlockAttemptByDeactivatedUser(user);
		}
	}
}
