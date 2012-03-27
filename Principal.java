package bicing;

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
		readDataFromUser();

		// Initialize data
		Ciudad ciudad = new Ciudad();
		ciudad.init(ESTACIONES, BICICLETAS, FURGONETAS, DEMANDA, SEED);

		ciudad.print();

		System.exit(0);
	}

	public static void readDataFromUser() {
		// Read data from user
		System.out.println("[ IA-2012, Bicing ]");

		try {

			System.out.print("Estaciones: ");
			ESTACIONES = Input.readInt();
			System.out.print("Bicicletas: ");
			BICICLETAS = Input.readInt();
			System.out.print("Furgonetas: ");
			FURGONETAS = Input.readInt();
			System.out.print("Demanda (1.equilibrada, 2.hora punta): ");
			DEMANDA = Input.readInt();
			System.out.print("Seed: ");
			SEED = Input.readInt();

		} catch (Exception e) {
			e.printStackTrace();
		}

		// Set random seed
		random.setSeed(SEED);
	}

}
