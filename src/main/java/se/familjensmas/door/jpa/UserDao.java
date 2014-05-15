package se.familjensmas.door.jpa;

import java.util.List;

import se.familjensmas.door.dt.User;
import se.familjensmas.door.dt.UserEvent;

/**
 * @author jorgen.smas@entercash.com
 */
public interface UserDao {

	User getUserByCode(String code);

	void add(User user);

	List<User> getAllUsers();

	void add(UserEvent event);

	void update(User user);

	User getById(Long id);
}
