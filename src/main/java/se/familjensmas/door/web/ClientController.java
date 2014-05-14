package se.familjensmas.door.web;

import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;

import se.familjensmas.door.Button;
import se.familjensmas.door.Keypad;
import se.familjensmas.door.web.PushResponse.Status;

import com.google.gson.GsonBuilder;

/**
 * @author jorgen.smas@entercash.com
 */
@Controller
public class ClientController {

	private Logger logger = LoggerFactory.getLogger(getClass());
	@Resource
	private Keypad keypad;

	@RequestMapping("/public/index.html")
	public void index(Model model) {

	}

	@RequestMapping("/public/push.json")
	public void push(String key, HttpServletResponse resp) throws IOException {
		logger.debug("Pushed key: " + key);
		if (key.length() != 1)
			throw new HttpException(400, "Invalid length " + key.length() + " for key value '" + key + "'");
		Button b = keypad.getButton(key.charAt(0));
		if (b == null)
			throw new HttpException(400, "There is no '" + key + "' key on the keypad.");
		b.push();
		PushResponse r = new PushResponse(Status.OK);
		writeJson(r, resp);
	}

	private void writeJson(PushResponse r, HttpServletResponse resp) throws IOException {
		String json = new GsonBuilder().setPrettyPrinting().create().toJson(r);
		byte[] data = json.getBytes("utf-8");
		resp.setContentType("application/json");
		resp.setCharacterEncoding("utf-8");
		resp.setContentLength(data.length);
		ServletOutputStream out = resp.getOutputStream();
		out.write(data);
		logger.debug("Written:\n" + json);
	}

	@ExceptionHandler(HttpException.class)
	public void handleHttpException(HttpServletResponse resp, HttpException e) throws IOException {
		logger.warn(e.getMessage());
		e.writeError(resp);
	}
}
