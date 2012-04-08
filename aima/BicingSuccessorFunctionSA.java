package bicing.aima;

import java.util.List;
import java.util.Vector;

import aima.search.framework.Successor;
import aima.search.framework.SuccessorFunction;
import bicing.Ciudad;
import bicing.Principal;

public class BicingSuccessorFunctionSA implements SuccessorFunction {

	public List<Successor> getSuccessors(Object aState) {
		Vector<Successor> result = new Vector<Successor>();
		Ciudad estCiudad = (Ciudad) aState;

		int t;

		do {
			switch (Principal.random.nextInt(3)) {

			case 0:
				// -----------------------------------------------------------------------
				// Aplicamos operador addTransporte
				// -----------------------------------------------------------------------

				// Si quedan furgonetas libres
				if (estCiudad.transportes.size() < Ciudad.getNumFurgonetas()) {
					// Elegimos ciudad origen
					int origen = Principal.random.nextInt(Ciudad.estaciones.getNumStations());

					// Si no habia una furgoneta en esa estacion y
					// ndnm > y nh-ns > 0
					if (!estCiudad.hayFurgonetaEnEstacion(origen) && (Ciudad.estaciones.getStationDoNotMove(origen) > 0) && ((Ciudad.estaciones.getDemandNextHour(origen) - Ciudad.estaciones.getStationNextState(origen)) > 0)) {

						int bcOrigen = Principal.random.nextInt(Ciudad.estaciones.getStationDoNotMove(origen));
						int paradaUno = Principal.random.nextInt(Ciudad.estaciones.getNumStations());

						// Capacidad furgoneta
						if (bcOrigen <= 30) {
							if (bcOrigen > 0) {

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

									// Si no nos sobran bicis, solo tendremos
									// una
									// parada
									else {
										Ciudad nuevaCiudad = new Ciudad(estCiudad);
										nuevaCiudad.addTransporte(origen, bcOrigen, paradaUno, bcParadaUno, -1, -1);
										result.add(new Successor("", nuevaCiudad));
									}
								}
							}
						}

					}
				}
				break;

			case 1:
				// -----------------------------------------------------------------------
				// Aplicamos operador modTransporte
				// -----------------------------------------------------------------------

				// Si hay transportes
				if (estCiudad.transportes.size() > 0) {

					// Elegimos el transporte
					t = Principal.random.nextInt(estCiudad.transportes.size());
					int origen = estCiudad.transportes.get(t).getOrigen();
					int paradaDos = estCiudad.transportes.get(t).getParadaDos();

					// Elegimos bc origen
					int bcOrigen = Principal.random.nextInt(Ciudad.estaciones.getStationDoNotMove(origen));

					// Limite furgoneta
					if (bcOrigen > 30) {
						bcOrigen = 30;
					}

					// No podemos coger 0 bicicletas
					if (bcOrigen == 0) {
						bcOrigen = 1;
					}

					// Repartimos bicicletas
					int bcParadaUno = Principal.random.nextInt(bcOrigen);
					int bcParadaDos = bcOrigen - bcParadaUno;

					// Hay paradaDos
					if (paradaDos != -1) {
						Ciudad nuevaCiudad = new Ciudad(estCiudad);
						nuevaCiudad.modTransporte(t, bcOrigen, bcParadaUno, bcParadaDos);
						result.add(new Successor("", nuevaCiudad));
					} else {
						Ciudad nuevaCiudad = new Ciudad(estCiudad);
						nuevaCiudad.modTransporte(t, bcOrigen, bcOrigen, -1);
						result.add(new Successor("", nuevaCiudad));
					}
				}

				break;

			case 2:
				// -----------------------------------------------------------------------
				// Aplicamos operador delTransporte
				// -----------------------------------------------------------------------
				if (estCiudad.transportes.size() > 0) {
					Ciudad nuevaCiudad = new Ciudad(estCiudad);
					t = Principal.random.nextInt(nuevaCiudad.transportes.size());
					nuevaCiudad.delTransporte(t);
					result.add(new Successor("", nuevaCiudad));
				}
				break;

			}
		} while (result.isEmpty());

		return result;
	}
}
