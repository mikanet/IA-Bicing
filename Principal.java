package bicing;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Random;

public class Principal {

	// Vars
	public static int ESTACIONES;
	public static int BICICLETAS;
	public static int FURGONETAS;
	public static int DEMANDA;
	public static int SEED;

	public static final Random random = new Random();

	public static void main(String[] args) {

		// Read data from user
		System.out.println("[ IA-2012, Bicing ]");

		try {

			System.out.print("Estaciones: ");
			ESTACIONES = readInt();
			System.out.print("Bicicletas: ");
			BICICLETAS = readInt();
			System.out.print("Furgonetas: ");
			FURGONETAS = readInt();
			System.out.print("Demanda (1.equilibrada, 2.hora punta): ");
			DEMANDA = readInt();
			System.out.print("Seed: ");
			SEED = readInt();

		} catch (Exception e) {
			e.printStackTrace();
		}

		// Set random seed
		random.setSeed(SEED);

		// init data
		Ciudad ciudad = new Ciudad();
		ciudad.init(ESTACIONES, BICICLETAS, FURGONETAS, DEMANDA, SEED);

		ciudad.print();

		// System.out.println("Estaciones: " + ESTACIONES + " Biciletas: " +
		// BICICLETAS + " Furgonetas: " + FURGONETAS + " Demanda: " + DEMANDA +
		// " Seed: " + SEED);

		// end
		System.out.println("-");
		System.exit(0);

	}

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
