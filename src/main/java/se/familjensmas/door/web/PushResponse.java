package se.familjensmas.door.web;

import com.google.gson.annotations.Expose;

/**
 * @author jorgen.smas@entercash.com
 */
public class PushResponse {

	public enum Status {
		OK, FAILURE;
	}

	public PushResponse(Status s) {
		this.status = s;
	}

	@Expose
	private Status status;

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

}
