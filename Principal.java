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
import bicing.aima.BicingSuccessorFunctionHC;
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

		// Read data from user
		readDataFromUser();

		// State initialization
		Ciudad ciudad = new Ciudad(ESTACIONES, BICICLETAS, FURGONETAS, DEMANDA, SEED, random);

		// Initialization mode
		if (initMode == 1) {
			ciudad.initEstrategiaSimple();
		} else {
			ciudad.initEstrategiaElaborada();
		}

		// AIMA search
		if (saHc == 1) {
			simulatedAnnealingSearch(ciudad, heur, SAIterations, SAIterationsPerStep, SAK, SALambda);
		} else {
			hillClimbingSearch(ciudad, heur);
		}

		// heuristic 1: Maximizar acercarse a la demanda
		// heuristic 2: Maximizar beneficios (min transportes, acercarse a la
		// demanda)

		System.out.println("[ END ]");
		System.exit(0);
	}

	private static void hillClimbingSearch(Ciudad ciudad, int heur) {
		try {

			Problem problem = null;
			if (heur == 1) {
				problem = new Problem(ciudad, new BicingSuccessorFunctionHC(), new BicingGoalTest(), new Bicing_HF_maxDistribucion());
			} else if (heur == 2) {
				problem = new Problem(ciudad, new BicingSuccessorFunctionHC(), new BicingGoalTest(), new Bicing_HF_maxBeneficios());
			}
			Search search = new HillClimbingSearch();
			SearchAgent agent = new SearchAgent(problem, search);

			// System.out.println(search.getGoalState());
			printInstrumentation(agent.getInstrumentation());

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void simulatedAnnealingSearch(Ciudad ciudad, int heur, int sAIterations2, int sAIterationsPerStep2, int sAK2, int sALambda2) {
		try {

			Problem problem = null;
			if (heur == 1) {
				problem = new Problem(ciudad, new BicingSuccessorFunctionSA(), new BicingGoalTest(), new Bicing_HF_maxDistribucion());
			} else if (heur == 2) {
				problem = new Problem(ciudad, new BicingSuccessorFunctionSA(), new BicingGoalTest(), new Bicing_HF_maxBeneficios());
			}
			Search search = new SimulatedAnnealingSearch(SAIterations, SAIterationsPerStep, SAK, SALambda);
			SearchAgent agent = new SearchAgent(problem, search);

			// System.out.println(search.getGoalState());
			printInstrumentation(agent.getInstrumentation());

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
		System.out.println("[ IA-2012, Bicing ]");

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

}
