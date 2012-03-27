package bicing;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Input {

	public static int readInt() {
		BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));
		try {
			return Integer.parseInt(stdin.readLine());
		} catch (java.io.IOException e) {
			e.printStackTrace();
		}
		return 0;
	}

	public static double readDouble() {
		BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));
		try {
			return Double.parseDouble((stdin.readLine()));
		} catch (java.io.IOException e) {
			e.printStackTrace();
		}
		return 0;
	}

}