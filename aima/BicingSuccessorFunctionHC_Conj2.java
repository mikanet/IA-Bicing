package bicing.aima;

import java.util.List;
import java.util.Vector;

import aima.search.framework.Successor;
import aima.search.framework.SuccessorFunction;
import bicing.Ciudad;

public class BicingSuccessorFunctionHC_Conj2 implements SuccessorFunction {

	public List<Successor> getSuccessors(Object aState) {
		Vector<Successor> result = new Vector<Successor>();
		// Cast del objecto aState a ciudad
		Ciudad estCiudad = (Ciudad) aState;

		// Conjunto addTransporte, delTransporte

		// -----------------------------------------------------------------------
		// Aplicamos operador addTransporte
		// -----------------------------------------------------------------------

		// Por cada posible furgoneta, sin passarnos
		for (int t = 0; t < Ciudad.getNumFurgonetas() - estCiudad.transportes.size(); t++) {
			// Por cada posible origen
			for (int origen = 0; origen < Ciudad.estaciones.getNumStations(); origen++) {
				// Si no furgoneta origen, doNotMove > = 1
				if (!estCiudad.hayFurgonetaEnEstacion(origen) && (Ciudad.estaciones.getStationDoNotMove(origen) > 0)) {
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
										if (bcOrigen <= 30) {
											// Por cada bicicleta en la
											// paradaUno
											for (int bcParadaUno = 1; bcParadaUno <= bcOrigen; bcParadaUno++) {
												if (paradaDos == -1) {
													// No hay parada dos
													Ciudad nuevaCiudad = new Ciudad(estCiudad);
													nuevaCiudad.addTransporte(origen, bcOrigen, paradaUno, bcOrigen, -1, -1);
													result.add(new Successor("", nuevaCiudad));
													bcParadaUno = bcOrigen + 1;
												} else {
													// Hay parada dos
													Ciudad nuevaCiudad = new Ciudad(estCiudad);
													nuevaCiudad.addTransporte(origen, bcOrigen, paradaUno, bcParadaUno, paradaDos, bcOrigen - bcParadaUno);
													result.add(new Successor("", nuevaCiudad));
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

		// -----------------------------------------------------------------------
		// Aplicamos operador delTransporte
		// -----------------------------------------------------------------------
		for (int t = 0; t < estCiudad.transportes.size(); t++) {
			Ciudad nuevaCiudad = new Ciudad(estCiudad);
			nuevaCiudad.delTransporte(t);
			result.add(new Successor("", nuevaCiudad));

		}

		return result;
	}
}
