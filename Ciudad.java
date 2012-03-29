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
			while (estacionOcupada.contains(st) || ((estaciones.getStationNextState(st) <= estaciones.getDemandNextHour(st)) || (estaciones.getStationDoNotMove(st) <= 0))) {
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
				transAux.bc_salida = aux + 1;
			}

			// Decidimos numero de paradas
			if (random.nextInt(2) == 0) {
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

	public double getBeneficios() {
		// Devuelve los beneficios obtenidos por llevar bicicletas y acercarse a
		// la demanda de cada estacion

		// El acuerdo con Bicing incluye que nos pague un euro por cada
		// bicicleta que transportemos que haga que el numero de bicicletas de
		// una estacion se acerque a la demanda

		// Por contra nos cobrara un euro por cada bicicleta que transportemos
		// que aleje a una estacion de su prevision. Es decir, nos descontaran
		// por las bicicletas que movamos que hagan que una estacion quede por
		// debajo de la demanda prevista.
		return 0.0;
	}

	public double getGastos() {
		// Devuelve los gastos producidos debido a los transportes de bicicletas

		// suponiendo que nb es el numero de bicicletas que transportamos en una
		// furgoneta y d(i,j) es la distancia entre dos estaciones, el coste en
		// euros es: coste(i,j)=((nb div 10)+1) * d(i,j)

		double gastos = 0.0;

		for (int i = 0; i < transportes.size(); i++) {
			// Gastos origen estacion uno
			gastos = gastos + (((transportes.get(i).getBc_salida() / 10) + 1) * estaciones.getStationsDistance(transportes.get(i).getOrigen(), transportes.get(i).getParada_uno()));

			// Gastos estacion uno estacion dos
			gastos = gastos + ((((transportes.get(i).getBc_salida() - transportes.get(i).getBc_p_uno()) / 10) + 1) * estaciones.getStationsDistance(transportes.get(i).getParada_uno(), transportes.get(i).getParada_dos()));

		}

		return gastos;
	}

	public void printEstaciones() {
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

	public void printTransportes() {
		System.out.println("Numero de transportes: " + transportes.size());
		for (int i = 0; i < transportes.size(); i++) {
			System.out.print("Transporte " + i);
			System.out.print(" - origen: " + transportes.get(i).getOrigen());
			System.out.print(" - bc_origen: " + transportes.get(i).getBc_salida());
			System.out.print(" - parada  uno: " + transportes.get(i).getParada_uno());
			System.out.print(" - bc_uno: " + transportes.get(i).getBc_p_uno());
			System.out.print(" - parada  dos: " + transportes.get(i).getParada_dos());
			System.out.print(" - bc_dos: " + transportes.get(i).getBc_p_dos());
			System.out.println();
		}

	}

}
