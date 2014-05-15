package se.familjensmas.door.jpa;

import java.util.LinkedList;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import se.familjensmas.door.dt.User;
import se.familjensmas.door.dt.UserEvent;

/**
 * @author jorgen.smas@entercash.com
 */
@Repository
public class UserDaoImpl implements UserDao {

	@Resource
	private SessionFactory sessionFactory;

	@Override
	public User getUserByCode(String code) {
		Session session = session();
		Criteria crit = session.createCriteria(User.class);
		crit.add(Restrictions.eq("code", code));
		User user = (User) crit.uniqueResult();
		return user;
	}

	@Override
	public void add(User user) {
		session().save(user);
	}

	@Override
	public List<User> getAllUsers() {
		List<User> result = new LinkedList<>();
		for (Object obj : session().createCriteria(User.class).list()) {
			if (!result.contains(obj))
				result.add((User) obj);
		}
		return result;
	}

	private Session session() {
		return sessionFactory.getCurrentSession();
	}

	@Override
	public void add(UserEvent event) {
		session().save(event);
	}

	@Override
	public void update(User user) {
		session().update(user);
	}

	@Override
	public User getById(Long id) {
		return (User) session().get(User.class, id);
	}
}
