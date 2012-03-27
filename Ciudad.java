package bicing;

import java.util.Random;
import java.util.Vector;

import IA.Bicing.Bicing;

public class Ciudad {

	Bicing estaciones;
	Vector<Transporte> transportes;
	Integer numFurgonetas;
	Random random;

	public Ciudad(int est, int bc, int f, int demanda, int seed, Random random) {
		// init estaciones
		this.estaciones = new Bicing(est, bc, demanda, seed);
		// init transportes
		this.transportes = new Vector<Transporte>();
		// vars
		this.numFurgonetas = f;
		this.random = random;
	}

	public void initEstrategiaSimple() {
		// Estrategia simple para crear estado inicial
		// Inicializamos f transportes de forma aleatoria comprobando que no
		// repetimos furgonetas por estacion y que una furgoneta no sale con mas
		// bicicletas de las disponibles (las sobrantes)

		Vector<Integer> estacionOcupada = new Vector<Integer>();
		int numEstaciones = estaciones.getNumStations();
		int aux, st;
		Transporte transAux;

		for (int i = 0; i < numFurgonetas; i++) {
			st = (random.nextInt(numEstaciones));
			// No enviamos furgonetas a estaciones donde no sobraran o no se
			// pueden llevar ninguna
			while (estacionOcupada.contains(st) || ((estaciones.getStationNextState(st) <= estaciones.getDemandNextHour(st)) || (estaciones.getStationDoNotMove(st) == 0))) {
				st = (random.nextInt(numEstaciones));
			}
			estacionOcupada.add(st);

			// st = estacion de donde sale la furgoneta
			transAux = new Transporte();

			transAux.setOrigen(st);

			// Calculamos bicicletas de salida
			aux = random.nextInt(estaciones.getStationDoNotMove(st));
			if (aux > 30) {
				transAux.bc_salida = 30;
			} else {
				transAux.bc_salida = aux;
			}

			// Decidimos numero de paradas
			if (random.nextInt(1) == 0) {
				// una parada
				aux = random.nextInt(numEstaciones);
				while (aux == transAux.getOrigen()) {
					aux = random.nextInt(numEstaciones);
				}
				transAux.setParada_uno(aux);
				transAux.setBc_p_uno(transAux.bc_salida);

			} else {
				// dos paradas
				aux = random.nextInt(numEstaciones);
				while (aux == transAux.getOrigen()) {
					aux = random.nextInt(numEstaciones);
				}
				transAux.setParada_uno(aux);
				transAux.setBc_p_uno(random.nextInt(transAux.bc_salida));

				aux = random.nextInt(numEstaciones);
				while (aux == transAux.getParada_uno() || aux == transAux.getOrigen()) {
					aux = random.nextInt(numEstaciones);
				}

				transAux.setParada_dos(aux);
				transAux.setBc_p_dos(transAux.bc_salida - transAux.getBc_p_uno());

			}
			transportes.add(transAux);
		}

	}

	public void initEstrategiaElaborada() {
		// Estrategia elaborada para crear estado inicial
		// Inicializamos f(o menos ?) transportes de forma que haya furgonetas
		// en las estaciones con mayor numero de bicicletas sobrantes
		// comprobando que no repetimos furgonetas por estacion y que una
		// furgoneta no sale con mas bicicletas de las disponibles (las
		// sobrantes)

	}

	public int getBeneficios() {
		// Devuelve los beneficios obtenidos por llevar bicicletas y acercarse a
		// la demanda de cada estacion
		return 0;
	}

	public int getGastos() {
		// Devuelve los gastos producidos debido a los transportes de bicicletas
		return 0;
	}

	public void print() {
		// print estaciones
		System.out.println("Numero de estaciones: " + estaciones.getNumStations());
		for (int i = 0; i < estaciones.getNumStations(); i++) {
			System.out.print("Estacion " + i);
			System.out.print(", coord[" + estaciones.getStationCoord(i)[0] + "," + estaciones.getStationCoord(i)[1] + "]");
			System.out.print(" - prev_demand_next_hr: " + estaciones.getDemandNextHour(i));
			System.out.print(" - prev_do_not_move: " + estaciones.getStationDoNotMove(i));
			System.out.print(" - prev_after_user_moves: " + estaciones.getStationNextState(i));
			System.out.println();
		}
	}

}
