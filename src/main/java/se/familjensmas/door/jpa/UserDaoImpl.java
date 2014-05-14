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

/**
 * @author jorgen.smas@entercash.com
 */
@Repository
public class UserDaoImpl implements UserDao {

	@Resource
	private SessionFactory sessionFactory;

	@Override
	public User getUserByCode(String code) {
		Session session = sessionFactory.getCurrentSession();
		Criteria crit = session.createCriteria(User.class);
		crit.add(Restrictions.eq("code", code));
		User user = (User) crit.uniqueResult();
		return user;
	}

	@Override
	public void add(User user) {
		sessionFactory.getCurrentSession().save(user);
	}

	@Override
	public List<User> getAllUsers() {
		List<User> result = new LinkedList<>();
		for (Object obj : sessionFactory.getCurrentSession().createCriteria(User.class).list()) {
			if (!result.contains(obj))
				result.add((User) obj);
		}
		return result;
	}
}
