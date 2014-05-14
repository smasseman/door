package se.familjensmas.door.web;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

/**
 * @author jorgen.smas@entercash.com
 */
public class HttpException extends RuntimeException {

	private static final long serialVersionUID = -8448756386289482941L;
	private int code;

	public HttpException(int code, String message) {
		super(message);
		this.code = code;
	}

	public void writeError(HttpServletResponse resp) throws IOException {
		resp.sendError(code, getMessage());
	}
}
