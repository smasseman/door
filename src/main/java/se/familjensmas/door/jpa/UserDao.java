package se.familjensmas.door.jpa;

import java.util.List;

import se.familjensmas.door.dt.User;

/**
 * @author jorgen.smas@entercash.com
 */
public interface UserDao {

	User getUserByCode(String code);

	void add(User user);

	List<User> getAllUsers();
}
