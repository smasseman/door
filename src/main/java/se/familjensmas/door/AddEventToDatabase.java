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
		user.setLatestAction(new Date());
		UserEvent event = new UserEvent();
		event.setEventType(EventType.UNLOCK);
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
}
