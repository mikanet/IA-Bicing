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

		return result;
	}
}
