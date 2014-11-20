package se.familjensmas.door;

import java.util.Date;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import se.familjensmas.door.dt.EventType;
import se.familjensmas.door.dt.User;
import se.familjensmas.door.dt.UserEvent;
import se.familjensmas.door.jpa.UserDao;

/**
 * @author jorgen.smas@entercash.com
 */
@Service
public class AddEventToDatabase implements EventListener {

	@Resource
	private UserDao dao;

	@Override
	public void unlockWithWrongCode(String code) {
		// Not logged
	}

	@Override
	public void unlocked(User user) {
		EventType et = EventType.UNLOCK;
		addEvent(user, et);
	}

	private void addEvent(User user, EventType et) {
		user.setLatestAction(new Date());
		UserEvent event = new UserEvent();
		event.setEventType(et);
		event.setTs(new Date());
		event.setUser(user);
		dao.add(event);
		user.getEvents().add(event);
		dao.update(user);
	}

	@Override
	public void unlockAttemptByDeactivatedUser(User user) {
		// Not logged.
	}

	@Override
	public void lockWithWrongCode(String code) {
		// Not logged.
	}

	@Override
	public void locked(User user) {
		EventType et = EventType.LOCK;
		addEvent(user, et);
	}

	@Override
	public void lockAttemptByDeactivatedUser(User user) {
		// Not logged
	}
}
