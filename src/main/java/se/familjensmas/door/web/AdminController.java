package se.familjensmas.door.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import se.familjensmas.door.Door;

/**
 * @author jorgen.smas@entercash.com
 */
@Controller
public class AdminController {

	private Door door = new Door();

	@RequestMapping("/admin/index.html")
	public void getIndex(Model model) {
		model.addAttribute("foo", "bar");
		model.addAttribute("door", door);
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
