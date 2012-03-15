package bicing;

import java.io.IOException;

/**
 * Clase para leer desde el teclado
 * 
 * @author Javier Gonzalez
 * 
 */
public class Input {

	public static byte readByte() {
		char inputChar;
		String inputString = new String("");

		try {
			inputChar = (char) (System.in.read());
			while ((inputChar != '\n') && (inputChar != ' ') || inputString.equals("")) {
				inputString += inputChar;
				inputChar = (char) (System.in.read());
			}
		} catch (IOException ex) {
			return 0;
		}

		return Byte.parseByte(inputString);
	}

	public static short readShort() {
		char inputChar;
		String inputString = new String("");

		try {
			inputChar = (char) (System.in.read());
			while ((inputChar != '\n') && (inputChar != ' ')) {
				inputString += inputChar;
				inputChar = (char) (System.in.read());
			}
		} catch (IOException ex) {
			return 0;
		}

		return Short.parseShort(inputString);
	}

	public static int readInt() {
		char inputChar;
		String inputString = new String("");

		try {
			inputChar = (char) (System.in.read());
			while ((inputChar != '\n') && (inputChar != ' ')) {
				inputString += inputChar;
				inputChar = (char) (System.in.read());
			}
		} catch (IOException ex) {
			return 0;
		}

		return Integer.parseInt(inputString);
	}

	public static long readLong() {
		char inputChar;
		String inputString = new String("");

		try {
			inputChar = (char) (System.in.read());
			while ((inputChar != '\n') && (inputChar != ' ')) {
				inputString += inputChar;
				inputChar = (char) (System.in.read());
			}
		} catch (IOException ex) {
			return 0;
		}

		return Long.parseLong(inputString);
	}

	public static char readChar() {
		char inputChar = '\0';

		try {
			inputChar = (char) (System.in.read());
			while ((inputChar == '\n') || (inputChar == ' ')) {
				inputChar = (char) (System.in.read());
			}
		} catch (IOException ex) {
			return '\0';
		}

		return inputChar;
	}

	public static float readFloat() {
		char inputChar;
		String inputString = new String("");

		try {
			inputChar = (char) (System.in.read());
			while ((inputChar != '\n') && (inputChar != ' ')) {
				inputString += inputChar;
				inputChar = (char) (System.in.read());
			}
		} catch (IOException ex) {
			return 0.0f;
		}

		return Float.parseFloat(inputString);
	}

	public static double readDouble() {
		char inputChar;
		String inputString = new String("");

		try {
			inputChar = (char) (System.in.read());
			while ((inputChar != '\n') && (inputChar != ' ')) {
				inputString += inputChar;
				inputChar = (char) (System.in.read());
			}
		} catch (IOException ex) {
			return 0.0;
		}

		return Double.parseDouble(inputString);
	}

	public static String readString() {
		char inputChar;
		String inputString = new String("");

		try {
			inputChar = (char) (System.in.read());
			while ((inputChar != '\n')) {
				if (inputChar != ' ') {
					inputString += inputChar;
				}
				inputChar = (char) (System.in.read());
			}
		} catch (IOException ex) {
			return null;
		}

		return inputString;
	}

	public static String readLine() {
		char inputChar;
		String inputString = new String("");

		try {
			inputChar = (char) (System.in.read());
			while ((inputChar != '\n')) {
				inputString += inputChar;
				inputChar = (char) (System.in.read());
			}
		} catch (IOException ex) {
			return null;
		}

		return inputString;
	}

	public static String continuar() {
		try {
			String inputString = new String("");
			inputString = "" + (char) (System.in.read());
			return inputString;
		} catch (Exception e) {
			return null;
		}
	}

}