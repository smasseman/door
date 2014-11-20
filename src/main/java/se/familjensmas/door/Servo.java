package se.familjensmas.door;

/**
 * @author jorgen.smas@entercash.com
 */
public interface Servo {

	void setDuration(long duration);

	void set(int degrees);

	void off();
}
