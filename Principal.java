package bicing;

import java.util.Iterator;
import java.util.Properties;
import java.util.Random;

import aima.search.framework.Problem;
import aima.search.framework.Search;
import aima.search.framework.SearchAgent;
import aima.search.informed.HillClimbingSearch;
import aima.search.informed.SimulatedAnnealingSearch;
import bicing.aima.BicingGoalTest;
import bicing.aima.BicingSuccessorFunctionHC_Conj2;
import bicing.aima.BicingSuccessorFunctionSA;
import bicing.aima.Bicing_HF_maxBeneficios;
import bicing.aima.Bicing_HF_maxDistribucion;

public class Principal {

	// Vars
	public static int ESTACIONES;
	public static int BICICLETAS;
	public static int FURGONETAS;
	public static int DEMANDA;
	public static int SEED;
	public static int initMode = 0;
	public static int saHc = 0;
	public static int heur = 0;

	public static int SAIterations = 0;
	public static int SAIterationsPerStep = 0;
	public static int SAK = 0;
	public static int SALambda = 0;

	public static final Random random = new Random();

	public static void main(String[] args) {
		System.out.println("[ IA-2012, Bicing ]");

		// Read data

		readFixedDataVero();
		// readFixedDataMarti();
		// readDataFromUser();

		System.out.println("[ Init ciudad... ]");

		// State initialization
		Ciudad ciudad = new Ciudad(ESTACIONES, BICICLETAS, FURGONETAS, DEMANDA, SEED, random);

		// Initialization mode
		if (initMode == 1) {
			System.out.println("[ Estrategia simple... ]");
			ciudad.initEstrategiaSimple();
		} else {
			System.out.println("[ Estrategia elaborada... ]");
			ciudad.initEstrategiaElaborada();
		}

		// Mostrar estado inicial
		System.out.println(ciudad);

		System.out.println("[ AIMA search ]");

		// AIMA search
		if (saHc == 1) {
			simulatedAnnealingSearch(ciudad, heur, SAIterations, SAIterationsPerStep, SAK, SALambda);
		} else {
			hillClimbingSearch(ciudad, heur);
		}

		System.out.println("[ END ]");
		System.exit(0);
	}

	private static void hillClimbingSearch(Ciudad ciudad, int heur) {
		try {

			long start = System.currentTimeMillis();

			Problem problem = null;
			if (heur == 1) {
				problem = new Problem(ciudad, new BicingSuccessorFunctionHC_Conj2(), new BicingGoalTest(), new Bicing_HF_maxDistribucion());
			} else if (heur == 2) {
				problem = new Problem(ciudad, new BicingSuccessorFunctionHC_Conj2(), new BicingGoalTest(), new Bicing_HF_maxBeneficios());
			}
			Search search = new HillClimbingSearch();
			SearchAgent agent = new SearchAgent(problem, search);
			long end = System.currentTimeMillis();

			System.out.println("[ Instrumentation ]");
			printInstrumentation(agent.getInstrumentation());
			System.out.println("Time: " + ((long) (end - start) / 1000.0) + "s");

			System.out.println();

			// Estado final
			System.out.println(search.getGoalState());

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void simulatedAnnealingSearch(Ciudad ciudad, int heur, int sAIterations2, int sAIterationsPerStep2, int sAK2, int sALambda2) {
		try {

			long start = System.currentTimeMillis();

			Problem problem = null;
			if (heur == 1) {
				problem = new Problem(ciudad, new BicingSuccessorFunctionSA(), new BicingGoalTest(), new Bicing_HF_maxDistribucion());
			} else if (heur == 2) {
				problem = new Problem(ciudad, new BicingSuccessorFunctionSA(), new BicingGoalTest(), new Bicing_HF_maxBeneficios());
			}
			Search search = new SimulatedAnnealingSearch(SAIterations, SAIterationsPerStep, SAK, SALambda);
			SearchAgent agent = new SearchAgent(problem, search);
			long end = System.currentTimeMillis();

			System.out.println("[ Instrumentation ]");
			printInstrumentation(agent.getInstrumentation());
			System.out.println("Time: " + ((long) (end - start) / 1000.0) + "s");

			System.out.println();

			// Estado final
			System.out.println("[Goal state: " + search.getGoalState() + "]");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void printInstrumentation(Properties properties) {
		Iterator<Object> keys = properties.keySet().iterator();
		while (keys.hasNext()) {
			String key = (String) keys.next();
			String property = properties.getProperty(key);
			System.out.println(key + " : " + property);
		}
	}

	public static void readDataFromUser() {
		// Read data from user

		try {

			System.out.print("Seed: ");
			SEED = Input.readInt();
			System.out.print("Estaciones: ");
			ESTACIONES = Input.readInt();
			System.out.print("Bicicletas: ");
			BICICLETAS = Input.readInt();
			System.out.print("Furgonetas: ");
			FURGONETAS = Input.readInt();
			System.out.print("Demanda equilibrada(1), hora punta(2): ");
			DEMANDA = Input.readInt();
			System.out.print("Estrategia simple(1), elaborada(2): ");
			initMode = Input.readInt();
			System.out.print("SA(1), HC(2): ");
			saHc = Input.readInt();
			System.out.print("Heur Max Distribucion(1), Max Beneficios(2): ");
			heur = Input.readInt();

			if (saHc == 1) {
				System.out.print("SA Iteraciones: ");
				SAIterations = Input.readInt();
				System.out.print("SA Iteraciones por paso: ");
				SAIterationsPerStep = Input.readInt();
				System.out.print("SA k: ");
				SAK = Input.readInt();
				System.out.print("SA lambda: ");
				SALambda = Input.readInt();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		// Set random seed
		random.setSeed(SEED);
	}

	@SuppressWarnings("unused")
	private static void readFixedDataVero() {
		// Harcoded data

		// SEED = 678652;
		System.out.print("Seed: ");
		SEED = Input.readInt();
		ESTACIONES = 25;
		BICICLETAS = 1250;
		FURGONETAS = 5;
		DEMANDA = 1;
		System.out.print("Estrategia simple(1), elaborada(2): ");
		initMode = Input.readInt();

		// Estrategia simple(1), elaborada(2)
		// initMode = 2;

		// SA(1), HC(2)
		saHc = 2;

		// Heur Max Distribucion(1), Max Beneficios(2): ");
		// (1) Maximizacion de lo que obtenemos por los traslados de las bc
		// (2): (1) + Minimizacion de los costes de transporte de las bicicletas
		heur = 1;

		// Simulated anealing
		SAIterations = 0;
		SAIterationsPerStep = 0;
		SAK = 0;
		SALambda = 0;

		// Set random seed
		random.setSeed(SEED);

	}

	private static void readFixedDataMarti() {
		// Harcoded data

		SEED = 384765;
		ESTACIONES = 25;
		BICICLETAS = 1250;
		FURGONETAS = 5;
		DEMANDA = 1;

		// Estrategia simple(1), elaborada(2)
		initMode = 2;

		// SA(1), HC(2)
		saHc = 2;

		// Heur Max Distribucion(1), Max Beneficios(2): ");
		// (1) Maximizacion de lo que obtenemos por los traslados de las bc
		// (2): (1) + Minimizacion de los costes de transporte de las bicicletas
		heur = 1;

		// Simulated anealing
		SAIterations = 0;
		SAIterationsPerStep = 0;
		SAK = 0;
		SALambda = 0;

		// Set random seed
		random.setSeed(SEED);

	}

}
