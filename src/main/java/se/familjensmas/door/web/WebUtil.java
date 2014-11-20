package se.familjensmas.door.web;

import java.io.IOException;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

/**
 * @author jorgen.smas@entercash.com
 */
public class WebUtil {

	public static void writeJson(String json, HttpServletResponse resp) throws IOException {
		byte[] data = json.getBytes("utf-8");
		resp.setContentType("application/json");
		resp.setCharacterEncoding("utf-8");
		resp.setContentLength(data.length);
		ServletOutputStream out = resp.getOutputStream();
		out.write(data);
	}
}
