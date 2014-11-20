package se.familjensmas.door.web;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import se.familjensmas.door.Door;
import se.familjensmas.door.Servo;
import se.familjensmas.door.dt.User;
import se.familjensmas.door.jpa.UserDao;

import com.google.gson.JsonObject;

/**
 * @author jorgen.smas@entercash.com
 */
@Controller
public class AdminController {

	@Resource
	private Servo servo;
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

	@RequestMapping("/admin/servo.json")
	public void servo(@RequestParam String value, @RequestParam String duration, HttpServletResponse resp)
			throws IOException {
		setServoValue(value, duration);
		JsonObject json = new JsonObject();
		json.addProperty("status", "OK");
		WebUtil.writeJson(json.toString(), resp);
	}

	private void setServoValue(String value, String duration) {
		if ("off".equals(value)) {
			servo.off();
		} else {
			if (duration != null)
				servo.setDuration(Long.parseLong(duration));
			servo.set(Integer.parseInt(value));
		}
	}

	@RequestMapping("/admin/servo.html")
	public void servo(Model model, @RequestParam(required = false) String value) {
		if (value != null)
			setServoValue(value, null);
		List<String> values = new LinkedList<>();
		for (int i = -90; i <= 90; i++) {
			values.add(String.valueOf(i));
		}
		model.addAttribute("values", values);
	}
}
