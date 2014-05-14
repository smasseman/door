package se.familjensmas.door;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

/**
 * @author jorgen.smas@entercash.com
 */
@Service
public class Keypad {

	public class MyButton implements Button {

		private char c;

		public MyButton(char c) {
			this.c = c;
		}

		@Override
		public void push() {
			Keypad.this.push(MyButton.this);
		}

		@Override
		public char getSign() {
			return c;
		}

		@Override
		public String toString() {
			return "Button[" + (c) + "]";
		}
	}

	private Map<Character, Button> buttons = new HashMap<>();
	private List<ButtonListener> listerns = new LinkedList<>();

	public Keypad() {
		add('1');
		add('2');
		add('3');
		add('4');
		add('5');
		add('6');
		add('7');
		add('8');
		add('9');
		add('0');
		add('*');
		add('#');
	}

	public void addListener(ButtonListener listener) {
		listerns.add(listener);
	}

	private void push(MyButton myButton) {
		for (ButtonListener listener : listerns) {
			listener.pushed(myButton);
		}
	}

	private void add(char c) {
		buttons.put(c, new MyButton(c));
	}

	public Button getButton(final char sign) {
		return buttons.get(Character.valueOf(sign));
	}
}
