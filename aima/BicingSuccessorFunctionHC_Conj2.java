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

		// Conjunto addTransporte, modTransporte, delTransporte

		// -----------------------------------------------------------------------
		// Aplicamos operador addTransporte
		// -----------------------------------------------------------------------

		// Anadimos el primer transporte que se pueda, senzillo, una parada
		for (int t = 0; t < Ciudad.getNumFurgonetas() - estCiudad.transportes.size(); t++) {
			// Buscamos el primer origen valido
			for (int origen = 0; origen < Ciudad.estaciones.getNumStations(); origen++) {
				if (!estCiudad.hayFurgonetaEnEstacion(origen)) {
					// Buscamos la primera parada
					for (int paradaUno = 0; paradaUno < Ciudad.estaciones.getNumStations(); paradaUno++) {
						// Comprovamos que no sea el origen
						if (paradaUno != origen) {
							// Cogemos todas las bicicletas possibles
							if (Ciudad.estaciones.getStationDoNotMove(origen) > 0) {
								int bcOrigen = Ciudad.estaciones.getStationDoNotMove(origen);
								int bcParadaUno = bcOrigen;

								// No hay parada dos
								String move = "add(" + origen + "," + bcOrigen + "," + paradaUno + "," + bcParadaUno + "," + "-1,-1)";
								Ciudad nuevaCiudad = new Ciudad(estCiudad);
								nuevaCiudad.addTransporte(origen, bcOrigen, paradaUno, bcParadaUno, -1, -1);
								result.add(new Successor("", nuevaCiudad));

								// Forzamos salir del for de origen y parada uno
								origen = Ciudad.estaciones.getNumStations() + 1000;
								paradaUno = Ciudad.estaciones.getNumStations() + 1000;

							}
						}
					}
				}
			}

		}

		// -----------------------------------------------------------------------
		// Aplicamos el operador modificarTransporte
		// -----------------------------------------------------------------------

		// Por cada posible possible transporte
		for (int t = 0; t < estCiudad.transportes.size(); t++) {
			// Por cada posible origen
			for (int origen = 0; origen < Ciudad.estaciones.getNumStations(); origen++) {
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
									// Por cada posible bicicleta en el origen
									for (int bcOrigen = 1; bcOrigen < Ciudad.estaciones.getStationDoNotMove(origen); bcOrigen++) {

										// Por cada bicicleta en paradaUno
										for (int bcParadaUno = 1; bcParadaUno <= bcOrigen; bcParadaUno++) {

											String move = "";
											if (paradaDos == -1) {
												// No hay parada dos
												move = "mod(" + origen + "," + bcOrigen + "," + paradaUno + "," + bcParadaUno + "," + "-1,-1)";
												Ciudad nuevaCiudad = new Ciudad(estCiudad);
												nuevaCiudad.modificarTransporte(t, origen, bcOrigen, paradaUno, bcParadaUno, -1, -1);
												result.add(new Successor("", nuevaCiudad));
											} else {
												// Hay parada dos
												move = "mod(" + origen + "," + bcOrigen + "," + paradaUno + "," + bcParadaUno + "," + paradaDos + "," + (bcOrigen - bcParadaUno) + ")";
												Ciudad nuevaCiudad = new Ciudad(estCiudad);
												nuevaCiudad.modificarTransporte(t, origen, bcOrigen, paradaUno, bcParadaUno, paradaDos, bcOrigen - bcParadaUno);
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
