package se.familjensmas.door.web;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import se.familjensmas.door.Door;
import se.familjensmas.door.dt.User;
import se.familjensmas.door.jpa.UserDao;

/**
 * @author jorgen.smas@entercash.com
 */
@Controller
public class AdminController {

	@Resource
	private Door door = new Door();
	@Resource
	private UserDao userDao;

	@RequestMapping("/admin/newuser.html")
	public void getNewUserFrom(Model model) {

	}

	@RequestMapping("/admin/savenewuser.html")
	public String getNewUserFrom(@RequestParam String active, @RequestParam String name, @RequestParam String code,
			Model model) {
		User user = new User();
		user.setActivated(active != null);
		user.setName(name);
		user.setCode(code);
		userDao.add(user);
		return "redirect:index.html";
	}

	@RequestMapping("/admin/index.html")
	public void getIndex(Model model) {
		model.addAttribute("foo", "bar");
		model.addAttribute("door", door);
		List<User> users = userDao.getAllUsers();
		model.addAttribute("users", users);
	}

	@RequestMapping("/admin/userdetails.html")
	public void getUserDetails(Model model, Long id) {
		User user = userDao.getById(id);
		model.addAttribute("user", user);
	}

	@RequestMapping("/admin/unlock.html")
	public String unlock(Model model) {
		model.addAttribute("door", door);
		return "redirect:index.html";
	}

	@RequestMapping("/admin/lock.html")
	public String lock(Model model) {
		model.addAttribute("door", door);
		return "redirect:index.html";
	}
}
