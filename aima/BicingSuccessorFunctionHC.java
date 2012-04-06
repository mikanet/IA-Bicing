package bicing.aima;

import java.util.List;
import java.util.Vector;

import aima.search.framework.HeuristicFunction;
import aima.search.framework.Successor;
import aima.search.framework.SuccessorFunction;
import bicing.Ciudad;
import bicing.Principal;

public class BicingSuccessorFunctionHC implements SuccessorFunction {

	@SuppressWarnings("unused")
	public List<Successor> getSuccessors(Object aState) {
		Vector<Successor> result = new Vector<Successor>();
		// Cast del objecto aState a ciudad
		Ciudad estCiudad = (Ciudad) aState;
		HeuristicFunction heuristic;

		// Heuristico elejido
		if (Principal.heur == 1) {
			heuristic = new Bicing_HF_maxDistribucion();
		} else {
			heuristic = new Bicing_HF_maxBeneficios();
		}

		// Elejir conjunto de operadores --------
		// 1 = a–adir y eliminar
		// 2 = a–adir y modificar
		int conj = 2;
		// --------------------------------------

		// -----------------------------------------------------------------------
		// Aplicamos operador addTransporte
		// -----------------------------------------------------------------------

		// Por cada posible furgoneta, sin passarnos
		for (int t = 0; t < Ciudad.getNumFurgonetas() - estCiudad.transportes.size(); t++) {
			// Por cada posible origen
			for (int origen = 0; origen < Ciudad.estaciones.getNumStations(); origen++) {
				// Si no furgoneta origen, doNotMove > = 1
				if (!estCiudad.hayFurgonetaEnEstacion(origen) && (Ciudad.estaciones.getStationDoNotMove(origen) >= 1)) {
					// Por cada possible parada uno
					for (int paradaUno = 0; paradaUno < Ciudad.estaciones.getNumStations(); paradaUno++) {
						// Si parada uno != origen
						if (paradaUno != origen) {
							// Por cada possible parada dos
							for (int paradaDos = -1; paradaDos < Ciudad.estaciones.getNumStations(); paradaDos++) {
								// Si parada dos != de parada uno y origen
								if (paradaDos != paradaUno && paradaDos != origen) {
									// Por cada posible bicicleta en el
									// origen
									for (int bcOrigen = 1; bcOrigen < Ciudad.estaciones.getStationDoNotMove(origen); bcOrigen++) {

										// Por cada bicicleta en la
										// paradaUno
										for (int bcParadaUno = 1; bcParadaUno <= bcOrigen; bcParadaUno++) {
											String move = "";

											if (paradaDos == -1) {
												// No hay parada dos
												move = "add(" + origen + "," + bcOrigen + "," + paradaUno + "," + bcParadaUno + "," + "-1,-1)";
												Ciudad nuevaCiudad = new Ciudad(estCiudad);
												nuevaCiudad.addTransporte(origen, bcOrigen, paradaUno, bcParadaUno, -1, -1);
												result.add(new Successor(move, nuevaCiudad));
											} else {
												// Hay parada dos
												move = "add(" + origen + "," + bcOrigen + "," + paradaUno + "," + bcParadaUno + "," + paradaDos + "," + (bcOrigen - bcParadaUno) + ")";
												Ciudad nuevaCiudad = new Ciudad(estCiudad);
												nuevaCiudad.addTransporte(origen, bcOrigen, paradaUno, bcParadaUno, paradaDos, bcOrigen - bcParadaUno);
												result.add(new Successor(move, nuevaCiudad));
											}
										}

									}
								}
							}
						}
					}

				}
			}
		}

		if (conj == 2) {

			// -----------------------------------------------------------------------
			// Aplicamos el operador modificarTransporte
			// -----------------------------------------------------------------------

			// Por cada posible possible transporte
			for (int t = 0; t < estCiudad.transportes.size(); t++) {
				// Por cada posible origen
				for (int origen = -1; origen < Ciudad.estaciones.getNumStations(); origen++) {

					if (origen == -1) {
						// -1 => eliminar el transporte
						String move = "mod(eliminar transporte " + t + ")";
						Ciudad nuevaCiudad = new Ciudad(estCiudad);
						nuevaCiudad.delTransporte(t);
						result.add(new Successor(move, nuevaCiudad));

					} else {
						// Si no furgoneta origen, do not move >=1
						if (!estCiudad.hayFurgonetaEnEstacion(origen) && (Ciudad.estaciones.getStationDoNotMove(origen) >= 1)) {
							// Por cada possible parada uno
							for (int paradaUno = 0; paradaUno < Ciudad.estaciones.getNumStations(); paradaUno++) {
								// Si parada uno != origen
								if (paradaUno != origen) {
									// Por cada possible parada dos
									for (int paradaDos = -1; paradaDos < Ciudad.estaciones.getNumStations(); paradaDos++) {
										// Si parada dos != de parada uno y
										// origen
										if (paradaDos != paradaUno && paradaDos != origen) {
											// Por cada posible bicicleta en el
											// origen
											for (int bcOrigen = 1; bcOrigen < Ciudad.estaciones.getStationDoNotMove(origen); bcOrigen++) {

												// Por cada bicicleta en la
												// paradaUno
												for (int bcParadaUno = 1; bcParadaUno <= bcOrigen; bcParadaUno++) {

													String move = "";
													if (paradaDos == -1) {
														// No hay parada dos
														move = "mod(" + origen + "," + bcOrigen + "," + paradaUno + "," + bcParadaUno + "," + "-1,-1)";
														Ciudad nuevaCiudad = new Ciudad(estCiudad);
														nuevaCiudad.modificarTransporte(t, origen, bcOrigen, paradaUno, bcParadaUno, -1, -1);
														result.add(new Successor(move, nuevaCiudad));
													} else {
														// Hay parada dos
														move = "mod(" + origen + "," + bcOrigen + "," + paradaUno + "," + bcParadaUno + "," + paradaDos + "," + (bcOrigen - bcParadaUno) + ")";
														Ciudad nuevaCiudad = new Ciudad(estCiudad);
														nuevaCiudad.modificarTransporte(t, origen, bcOrigen, paradaUno, bcParadaUno, paradaDos, bcOrigen - bcParadaUno);
														result.add(new Successor(move, nuevaCiudad));
													}
												}

											}
										}
									}
								}
							}
						}
					}
				}
			}
		}

		if (conj == 1) {
			// -----------------------------------------------------------------------
			// Aplicamos operador delTransporte
			// -----------------------------------------------------------------------
			for (int t = 0; t < estCiudad.transportes.size(); t++) {

				Ciudad nuevaCiudad = new Ciudad(estCiudad);
				nuevaCiudad.delTransporte(t);
				result.add(new Successor("del(" + t + ")", nuevaCiudad));

			}
		}

		return result;
	}
}
