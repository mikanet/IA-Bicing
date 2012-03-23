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
		System.out.println("[ IA-2012, Bicing  -  (Estaciones, bicicletas, furgonetas, demanda, seed) ]");
		System.out.print("> ");

		// Modificar classe input!!!

		try {
			ESTACIONES = Input.readInt();
			BICICLETAS = Input.readInt();
			FURGONETAS = Input.readInt();
			DEMANDA = Input.readInt();
			SEED = Input.readInt();

		} catch (Exception e) {
			e.printStackTrace();
		}

		// Set random seed
		random.setSeed(SEED);

		// init data
		Ciudad ciudad = new Ciudad();
		ciudad.init(ESTACIONES, BICICLETAS, FURGONETAS, DEMANDA, SEED);

		ciudad.print();

		// end
		System.out.println("-");
		System.exit(0);

	}

}
