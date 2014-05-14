package se.familjensmas.door.jpa;

import java.util.LinkedList;
import java.util.List;

import se.familjensmas.door.dt.User;

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
}
