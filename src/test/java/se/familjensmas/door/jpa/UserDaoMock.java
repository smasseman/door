package se.familjensmas.door.jpa;

import java.util.LinkedList;
import java.util.List;

import se.familjensmas.door.dt.User;
import se.familjensmas.door.dt.UserEvent;

/**
 * @author jorgen.smas@entercash.com
 */
public class UserDaoMock implements UserDao {

	private List<User> users = new LinkedList<User>();

	@Override
	public User getUserByCode(String code) {
		for (User user : users) {
			if (code.equals(user.getCode()))
				return user;
		}
		return null;
	}

	@Override
	public void add(User user) {
		users.add(user);
	}

	@Override
	public List<User> getAllUsers() {
		return users;
	}

	@Override
	public void add(UserEvent event) {
	}

	@Override
	public void update(User user) {
	}

	@Override
	public User getById(Long id) {
		for (User user : users) {
			if (id.equals(user.getId()))
				return user;
		}
		return null;
	}
}
