package bicing;

import java.util.Vector;

import IA.Bicing.Bicing;

public class Ciudad {

	Bicing estaciones;
	Vector<Transporte> transportes;

	public Ciudad(int est, int bc, int f, int demanda, int seed) {
		// init estaciones
		this.estaciones = new Bicing(est, bc, demanda, seed);
		// init transportes
		this.transportes = new Vector<Transporte>();
	}

	public void initEstrategiaSimple() {
		// Estrategia simple para crear estado inicial

	}

	public void initEstrategiaElaborada() {
		// Estrategia elaborada para crear estado inicial

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
