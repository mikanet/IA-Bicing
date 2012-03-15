package bicing;

import IA.Bicing.Bicing;

public class Ciudad {

	Bicing estaciones;
	Furgonetas furgonetas;

	public Ciudad() {

	}

	public void init(int est, int bc, int f, int demanda, int seed) {
		// init estaciones
		this.estaciones = new Bicing(est, bc, demanda, seed);
		// init furgonetas, numero de forgunetas
		this.furgonetas = new Furgonetas();
		furgonetas.init(f);
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
		// print furgonetas
		furgonetas.print();
	}

}
