package bicing.aima;

import java.util.List;
import java.util.Vector;

import aima.search.framework.HeuristicFunction;
import aima.search.framework.Successor;
import aima.search.framework.SuccessorFunction;
import bicing.Ciudad;
import bicing.Principal;

public class BicingSuccessorFunctionSA implements SuccessorFunction {

	@SuppressWarnings("unused")
	public List<Successor> getSuccessors(Object aState) {
		Vector<Successor> result = new Vector<Successor>();
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

		if (conj == 1) {

			// Aplicamos operadores al azar (Caso a–adir y eliminar)
			do {
				switch (Principal.random.nextInt(2)) {

				case 0:
					// Operador addTransporte

					// Si no tenemos ya todos los transportes colocados
					if (estCiudad.transportes.size() < Ciudad.getNumFurgonetas()) {
						// Elegimos ciudad origen
						int origen = Principal.random.nextInt(Ciudad.estaciones.getNumStations());

						// Si no habia una furgoneta en esa estacion, seguimos
						if (!estCiudad.hayFurgonetaEnEstacion(origen)) {

							int bcOrigen = Principal.random.nextInt(Ciudad.estaciones.getStationDoNotMove(origen));
							int paradaUno = Principal.random.nextInt(Ciudad.estaciones.getNumStations());

							// Si la parada uno es diferente de la origen,
							// seguimos
							if (paradaUno != origen) {
								int bcParadaUno = Principal.random.nextInt(bcOrigen);

								// Si nos sobran bicis, tendremos dos paradas
								if (bcParadaUno < bcOrigen) {
									int paradaDos = Principal.random.nextInt(Ciudad.estaciones.getNumStations());
									if ((paradaDos != paradaUno) && (paradaDos != origen)) {
										int bcParadaDos = bcOrigen - bcParadaUno;
										Ciudad nuevaCiudad = new Ciudad(estCiudad);
										nuevaCiudad.addTransporte(origen, bcOrigen, paradaUno, bcParadaUno, paradaDos, bcParadaDos);
										result.add(new Successor("", nuevaCiudad));
									}

									// Si la parada dos coincide con alguna,
									// dejamos
									// todas las bicis en
									// la parada uno y no tenemos parada dos
									else {
										Ciudad nuevaCiudad = new Ciudad(estCiudad);
										nuevaCiudad.addTransporte(origen, bcOrigen, paradaUno, bcOrigen, -1, -1);
										result.add(new Successor("", nuevaCiudad));
									}
								}

								// Si no nos sobran bicis, solo tendremos una
								// parada
								else {
									Ciudad nuevaCiudad = new Ciudad(estCiudad);
									nuevaCiudad.addTransporte(origen, bcOrigen, paradaUno, bcParadaUno, -1, -1);
									result.add(new Successor("", nuevaCiudad));
								}
							}

						}
					}
					break;

				case 1:
					// Operador delTransporte
					if (estCiudad.transportes.size() > 0) {
						Ciudad nuevaCiudad = new Ciudad(estCiudad);
						int t = Principal.random.nextInt(nuevaCiudad.transportes.size());
						nuevaCiudad.delTransporte(t);
						result.add(new Successor("", nuevaCiudad));
					}
					break;

				}
			} while (result.isEmpty());

		} else {

			// Aplicamos operadores al azar (Caso a–adir y modificar)
			do {
				switch (Principal.random.nextInt(2)) {

				case 0:
					// Operador addTransporte

					// Si no tenemos ya todos los transportes colocados
					if (estCiudad.transportes.size() < Ciudad.getNumFurgonetas()) {
						// Elegimos ciudad origen
						int origen = Principal.random.nextInt(Ciudad.estaciones.getNumStations());

						// Si no habia una furgoneta en esa estacion, seguimos
						if (!estCiudad.hayFurgonetaEnEstacion(origen)) {

							int bcOrigen = Principal.random.nextInt(Ciudad.estaciones.getStationDoNotMove(origen));
							int paradaUno = Principal.random.nextInt(Ciudad.estaciones.getNumStations());

							// Si la parada uno es diferente de la origen,
							// seguimos
							if (paradaUno != origen) {
								int bcParadaUno = Principal.random.nextInt(bcOrigen);

								// Si nos sobran bicis, tendremos dos paradas
								if (bcParadaUno < bcOrigen) {
									int paradaDos = Principal.random.nextInt(Ciudad.estaciones.getNumStations());
									if ((paradaDos != paradaUno) && (paradaDos != origen)) {
										int bcParadaDos = bcOrigen - bcParadaUno;
										Ciudad nuevaCiudad = new Ciudad(estCiudad);
										nuevaCiudad.addTransporte(origen, bcOrigen, paradaUno, bcParadaUno, paradaDos, bcParadaDos);
										result.add(new Successor("", nuevaCiudad));
									}

									// Si la parada dos coincide con alguna,
									// dejamos
									// todas las bicis en
									// la parada uno y no tenemos parada dos
									else {
										Ciudad nuevaCiudad = new Ciudad(estCiudad);
										nuevaCiudad.addTransporte(origen, bcOrigen, paradaUno, bcOrigen, -1, -1);
										result.add(new Successor("", nuevaCiudad));
									}
								}

								// Si no nos sobran bicis, solo tendremos una
								// parada
								else {
									Ciudad nuevaCiudad = new Ciudad(estCiudad);
									nuevaCiudad.addTransporte(origen, bcOrigen, paradaUno, bcParadaUno, -1, -1);
									result.add(new Successor("", nuevaCiudad));
								}
							}

						}
					}
					break;

				case 1:
					// Operador modTransporte
					// Modificamos un transporte al azar si hay algun transporte
					if (estCiudad.transportes.size() > 0) {
						// Elejimos el transporte
						int t = Principal.random.nextInt(estCiudad.transportes.size());

						// Elegimos ciudad origen
						int origen = Principal.random.nextInt(Ciudad.estaciones.getNumStations() + 1);

						if (origen == Ciudad.estaciones.getNumStations() + 1) {
							// Si al elejir elejimos un origen mas de los
							// existentes => eliminar el transporte
							String move = "mod(eliminar transporte " + t + ")";
							Ciudad nuevaCiudad = new Ciudad(estCiudad);
							nuevaCiudad.delTransporte(t);
							result.add(new Successor(move, nuevaCiudad));

						} else {

							// Si no habia una furgoneta en esa estacion,
							// seguimos
							if (!estCiudad.hayFurgonetaEnEstacion(origen)) {

								int bcOrigen = Principal.random.nextInt(Ciudad.estaciones.getStationDoNotMove(origen));
								int paradaUno = Principal.random.nextInt(Ciudad.estaciones.getNumStations());

								// Si la parada uno es diferente de la origen,
								// seguimos
								if (paradaUno != origen) {
									int bcParadaUno = Principal.random.nextInt(bcOrigen);

									// Si nos sobran bicis, tendremos dos
									// paradas
									if (bcParadaUno < bcOrigen) {
										int paradaDos = Principal.random.nextInt(Ciudad.estaciones.getNumStations());
										if ((paradaDos != paradaUno) && (paradaDos != origen)) {
											int bcParadaDos = bcOrigen - bcParadaUno;

											Ciudad nuevaCiudad = new Ciudad(estCiudad);
											nuevaCiudad.modificarTransporte(estCiudad.transportes.get(t).getOrigen(), origen, bcOrigen, paradaUno, bcParadaUno, paradaDos, bcParadaDos);
											result.add(new Successor("", nuevaCiudad));

										}

										// Si la parada dos coincide con alguna,
										// dejamos
										// todas las bicis en
										// la parada uno y no tenemos parada dos
										else {

											Ciudad nuevaCiudad = new Ciudad(estCiudad);

											nuevaCiudad.addTransporte(origen, bcOrigen, paradaUno, bcOrigen, -1, -1);
											result.add(new Successor("", nuevaCiudad));

										}
									}

									// Si no nos sobran bicis, solo tendremos
									// una
									// parada
									else {
										Ciudad nuevaCiudad = new Ciudad(estCiudad);
										nuevaCiudad.modificarTransporte(estCiudad.transportes.get(t).getOrigen(), origen, bcOrigen, paradaUno, bcParadaUno, -1, -1);
										result.add(new Successor("", nuevaCiudad));
									}
								}
							}
						}
					}
					break;

				}
			} while (result.isEmpty());
		}
		return result;
	}
}
