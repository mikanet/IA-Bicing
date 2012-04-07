package bicing;

import java.util.Collections;
import java.util.Comparator;
import java.util.Random;
import java.util.Vector;

import IA.Bicing.Bicing;

public class Ciudad {

	public static Bicing estaciones;
	public static Integer numFurgonetas;
	public static Random random;

	public Vector<Transporte> transportes;
	public Vector<Boolean> estacionesOcupadas;

	public Ciudad(int est, int bc, int f, int demanda, int seed, Random pRandom) {
		// init estaciones
		estaciones = new Bicing(est, bc, demanda, seed);
		// init transportes
		this.transportes = new Vector<Transporte>();
		// vars
		this.estacionesOcupadas = new Vector<Boolean>();
		numFurgonetas = f;
		random = pRandom;

		// Marcamos las estaciones como libres
		for (int i = 0; i < estaciones.getNumStations(); i++) {
			this.estacionesOcupadas.add(false);
		}
	}

	// Copia
	public Ciudad(Ciudad ciudadOriginal) {
		// Copiamos las variables no estaticas
		transportes = new Vector<Transporte>();
		estacionesOcupadas = new Vector<Boolean>();

		// Copiamos los transportes
		for (int i = 0; i < ciudadOriginal.transportes.size(); i++) {

			Transporte aux = new Transporte();
			aux.setOrigen(ciudadOriginal.transportes.get(i).getOrigen());
			aux.setBcOrigen(ciudadOriginal.transportes.get(i).getBcOrigen());
			aux.setParadaUno(ciudadOriginal.transportes.get(i).getParadaUno());
			aux.setBcParadaUno(ciudadOriginal.transportes.get(i).getBcParadaUno());
			aux.setParadaDos(ciudadOriginal.transportes.get(i).getParadaDos());
			aux.setBcParadaDos(ciudadOriginal.transportes.get(i).getBcParadaDos());
			transportes.add(aux);
		}

		// Copiamos las estaciones ocupadas
		for (int i = 0; i < ciudadOriginal.estacionesOcupadas.size(); i++) {
			if (ciudadOriginal.estacionesOcupadas.get(i)) {
				estacionesOcupadas.add(new Boolean(true));
			} else {
				estacionesOcupadas.add(new Boolean(false));
			}
		}

	}

	public void initEstrategiaSimple() {
		// Estrategia simple para crear estado inicial
		// Inicializamos f transportes de forma aleatoria comprobando que no
		// repetimos furgonetas por estacion y que una furgoneta no sale con mas
		// bicicletas de las disponibles (las sobrantes)
		int timeout = 0;

		Vector<Integer> estacionOcupada = new Vector<Integer>();
		int numEstaciones = estaciones.getNumStations();
		int aux, st;
		Transporte transAux;

		for (int i = 0; i < numFurgonetas; i++) {

			// No enviamos furgonetas a estaciones donde no sobraran o no se
			// pueden llevar ninguna
			st = (random.nextInt(numEstaciones));
			timeout = 0;
			while (estacionOcupada.contains(st) || ((estaciones.getStationNextState(st) <= estaciones.getDemandNextHour(st)) || (estaciones.getStationDoNotMove(st) <= 0))) {
				st = (random.nextInt(numEstaciones));
				timeout = timeout + 1;
				if (timeout > numEstaciones) {
					break;
				}
			}

			if (timeout > numEstaciones) {
				while (estacionOcupada.contains(st)) {
					st = (random.nextInt(numEstaciones));
				}
			}
			estacionOcupada.add(st);

			// st = estacion de donde sale la furgoneta
			this.estacionesOcupadas.set(st, true);
			transAux = new Transporte();

			transAux.setOrigen(st);

			// Calculamos bicicletas de salida
			if (estaciones.getStationDoNotMove(st) > 0)
				aux = random.nextInt(estaciones.getStationDoNotMove(st));
			else
				aux = 0;

			if (aux > 30) {
				transAux.bcOrigen = 30;
			} else {
				transAux.bcOrigen = aux + 1;
			}

			// Decidimos numero de paradas
			if (random.nextInt(2) == 0) {
				// una parada
				aux = random.nextInt(numEstaciones);
				while (aux == transAux.getOrigen()) {
					aux = random.nextInt(numEstaciones);
				}
				transAux.setParadaUno(aux);
				transAux.setBcParadaUno(transAux.bcOrigen);

			} else {
				// dos paradas
				aux = random.nextInt(numEstaciones);
				while (aux == transAux.getOrigen()) {
					aux = random.nextInt(numEstaciones);
				}
				transAux.setParadaUno(aux);
				if (transAux.bcOrigen > 0)
					transAux.setBcParadaUno(random.nextInt(transAux.bcOrigen));
				else
					transAux.setBcParadaUno(0);

				aux = random.nextInt(numEstaciones);
				while (aux == transAux.getParadaUno() || aux == transAux.getOrigen()) {
					aux = random.nextInt(numEstaciones);
				}

				transAux.setParadaDos(aux);
				transAux.setBcParadaDos(transAux.bcOrigen - transAux.getBcParadaUno());

			}
			transportes.add(transAux);
		}

	}

	public void ordena(Vector<EstacionesCompare> v, boolean ganancia) {
		// ordena el vector v en orden descendente

		if (ganancia == false) {
			Collections.sort(v, new Comparator<Object>() {
				public int compare(Object a, Object b) {
					return (-(new Integer(((EstacionesCompare) a).getSobrantes())).compareTo(new Integer(((EstacionesCompare) b).getSobrantes())));
				}
			});
		} else {
			Collections.sort(v, new Comparator<Object>() {
				public int compare(Object a, Object b) {
					return (-(new Double(((EstacionesCompare) a).getGanancia())).compareTo(new Double(((EstacionesCompare) b).getGanancia())));
				}
			});
		}
	}

	public void initEstrategiaElaborada() {
		// Estrategia elaborada para crear estado inicial
		// Inicializamos f(o menos) transportes de forma que haya furgonetas
		// en las estaciones con mayor numero de bicicletas sobrantes
		// comprobando que no repetimos furgonetas por estacion y que una
		// furgoneta no sale con mas bicicletas de las disponibles (las
		// sobrantes)

		Vector<EstacionesCompare> estAuxSobrantes = new Vector<EstacionesCompare>();
		Vector<EstacionesCompare> estAuxDemanda = new Vector<EstacionesCompare>();
		for (int i = 0; i < estaciones.getNumStations(); i++) {
			int notMove = estaciones.getStationDoNotMove(i);
			int faltan = estaciones.getDemandNextHour(i) - estaciones.getStationNextState(i);

			if ((notMove > 0) && (faltan <= 0)) {
				// limitamos el numero de bicicletas que cargaremos a 30
				if (notMove > 30)
					notMove = 30;

				EstacionesCompare aux = new EstacionesCompare(i, notMove);
				estAuxSobrantes.add(aux);
			} else if (faltan > 0) {
				EstacionesCompare aux2 = new EstacionesCompare(i, faltan);
				estAuxDemanda.add(aux2);
			}
		}

		ordena(estAuxSobrantes, false);

		for (int j = 0; ((j < numFurgonetas) && (estAuxSobrantes.size() > 0) && (estAuxDemanda.size() > 0)); j++) {
			Transporte trans = new Transporte();

			// Ordenamos el vector de demandas por la relacion
			// bicicletas/distancia respecto al origen.
			for (int k = 0; k < estAuxDemanda.size(); k++) {
				EstacionesCompare e = estAuxDemanda.elementAt(k);
				e.setGanancia((int) (e.getSobrantes() / estaciones.getStationsDistance(estAuxSobrantes.elementAt(0).origen, e.origen)));
			}
			ordena(estAuxDemanda, true);

			EstacionesCompare estDem0 = estAuxDemanda.elementAt(0);
			EstacionesCompare estSob0 = estAuxSobrantes.elementAt(0);

			trans.setOrigen(estSob0.getOrigen());
			trans.setParadaUno(estDem0.getOrigen());
			int dif = estSob0.getSobrantes() - estDem0.getSobrantes();
			// Si disponemos de mas bicicletas de las que necesita la estacion
			// habra dos paradas
			if (dif > 0) {
				trans.setBcParadaUno(estDem0.getSobrantes());
				estSob0.setSobrantes(dif);
				estAuxDemanda.remove(0);
				if (estAuxDemanda.size() > 0) {
					estDem0 = estAuxDemanda.elementAt(0);
					trans.setParadaDos(estDem0.getOrigen());
					dif = estSob0.getSobrantes() - estDem0.getSobrantes();
					if (dif > 0) {
						trans.setBcParadaDos(estDem0.getSobrantes());
						estAuxDemanda.remove(0);
					} else {
						trans.setBcParadaDos(estSob0.getSobrantes());
						estDem0.setSobrantes(-dif);
					}
					trans.setBcOrigen(trans.getBcParadaUno() + trans.getBcParadaDos());
				}
			}
			// Sino, habra solo una parada
			else {
				trans.setBcParadaUno(estSob0.getSobrantes());
				trans.setBcOrigen(trans.getBcParadaUno());
				estDem0.setSobrantes(-dif);
				if (dif == 0)
					estAuxDemanda.remove(0);
			}
			estAuxSobrantes.remove(0);
			transportes.add(trans);
		}

	}

	private double calculaBeneficiosParada(int bc, int faltaban) {
		// Funcion auxiliar para calcular los beneficios de una parada
		// bc: bicicletas que lleva la furgoneta a esa estacion
		// faltaban: bicicletas que faltaban para cubrir la demanda
		double beneficios = 0.0;

		// si no faltaba ninguna bicicleta, no nos pagan
		if (faltaban > 0) {
			// si llevamos mas de las que faltaban, solo nos pagan por las
			// que faltaban
			if (bc >= faltaban) {
				beneficios += faltaban;
			}
			// si llevamos menos de las que faltaban, nos pagan por todas
			// las que llevamos
			else {
				beneficios += bc;
			}
		}
		return beneficios;
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

		double beneficios = 0.0;
		for (int i = 0; i < transportes.size(); i++) {
			Transporte t = transportes.elementAt(i);
			int sobrantes = estaciones.getStationNextState(t.getOrigen()) - estaciones.getDemandNextHour(t.getOrigen());

			// nos restan un euro por cada bici que se aleje de la demanda
			if (t.getBcOrigen() > sobrantes) {
				beneficios -= (t.getBcOrigen() - sobrantes);
			}

			int faltabanParadaUno = estaciones.getDemandNextHour(t.getParadaUno()) - estaciones.getStationNextState(t.getParadaUno());
			beneficios += calculaBeneficiosParada(t.getBcParadaUno(), faltabanParadaUno);

			// solo si tenemos parada dos
			if (t.getParadaDos() != -1) {
				int faltabanParadaDos = estaciones.getDemandNextHour(t.getParadaDos()) - estaciones.getStationNextState(t.getParadaDos());
				beneficios += calculaBeneficiosParada(t.getBcParadaDos(), faltabanParadaDos);
			}
		}
		return beneficios;
	}

	public double getGastos() {
		// Devuelve los gastos producidos debido a los transportes de bicicletas

		// suponiendo que nb es el numero de bicicletas que transportamos en una
		// furgoneta y d(i,j) es la distancia entre dos estaciones, el coste en
		// euros es: coste(i,j)=((nb div 10)+1) * d(i,j)

		double gastos = 0.0;

		for (int i = 0; i < transportes.size(); i++) {
			// Gastos origen estacion uno
			if (transportes.get(i).getBcParadaUno() != -1) {
				gastos = gastos + (((transportes.get(i).getBcOrigen() / 10) + 1) * estaciones.getStationsDistance(transportes.get(i).getOrigen(), transportes.get(i).getParadaUno()));

				// Gastos estacion uno estacion dos
				if (transportes.get(i).getBcParadaDos() > 0)
					gastos = gastos + ((((transportes.get(i).getBcOrigen() - transportes.get(i).getBcParadaUno()) / 10) + 1) * estaciones.getStationsDistance(transportes.get(i).getParadaUno(), transportes.get(i).getParadaDos()));
			}
		}

		return gastos;
	}

	public static Integer getNumFurgonetas() {
		return numFurgonetas;
	}

	public int getTransporteConEstacion(int st) {
		// Devuelve el identificador del transporte (indice en el vector
		// transportes) del transporte con furgoneta en la estacion origen st

		for (int i = 0; i < this.transportes.size(); i++) {
			if (this.transportes.get(i).getOrigen() == st) {
				return i;
			}
		}
		return -1;

	}

	public Boolean hayFurgonetaEnEstacion(int st) {
		// Devuelve cierto si existe un transporte con origen igual a st, falso
		// en caso contrario
		return this.estacionesOcupadas.elementAt(st);
	}

	public void addTransporte(int origen, int bcOrigen, int paradaUno, int bcParadaUno, int paradaDos, int bcParadaDos) {
		// Creamos un nuevo transporte y lo a–adimos al vector de transportes
		Transporte transAux = new Transporte();
		transAux.setOrigen(origen);
		transAux.setBcOrigen(bcOrigen);
		transAux.setParadaUno(paradaUno);
		transAux.setBcParadaUno(bcParadaUno);
		transAux.setParadaDos(paradaDos);
		transAux.setBcParadaDos(bcParadaDos);
		this.transportes.add(transAux);

		// Marcamos la estacion como ocupada
		this.estacionesOcupadas.set(origen, true);
	}

	public void modTransporte(int origen, int nuevoOrigen, int bcOrigen, int paradaUno, int bcParadaUno, int paradaDos, int bcParadaDos) {
		Transporte transAux = this.transportes.get(origen);
		transAux.setOrigen(nuevoOrigen);
		transAux.setBcOrigen(bcOrigen);
		transAux.setParadaUno(paradaUno);
		transAux.setBcParadaUno(bcParadaUno);
		transAux.setParadaDos(paradaDos);
		transAux.setBcParadaDos(bcParadaDos);

		// Marcamos la nueva estacion como ocupada
		if (origen != nuevoOrigen) {
			this.estacionesOcupadas.set(origen, false);
			this.estacionesOcupadas.set(nuevoOrigen, true);
		}
	}

	public void delTransporte(int index) {
		// Eliminamos el transporte y desmarcamos la estacion como ocupada
		this.estacionesOcupadas.set(this.transportes.get(index).origen, false);
		this.transportes.remove(index);
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
			System.out.print(", Origen: " + transportes.get(i).getOrigen());
			System.out.print(", BC origen: " + transportes.get(i).getBcOrigen());
			System.out.print("  ---  ");
			System.out.print(", ST uno: " + transportes.get(i).getParadaUno());
			System.out.print(", BC uno: " + transportes.get(i).getBcParadaUno());
			System.out.print("  ---  ");
			System.out.print(", STdos: " + transportes.get(i).getParadaDos());
			System.out.print(", BCdos: " + transportes.get(i).getBcParadaDos());
			System.out.println();
		}

	}

	public String transportesToString() {
		String result = "-------------------- Transportes(" + transportes.size() + ") ------------------------\n";

		for (int i = 0; i < transportes.size(); i++) {
			result = result + "t[" + i + "]";
			result = result + "\t@origen:" + transportes.get(i).getOrigen() + "\t#" + transportes.get(i).getBcOrigen();
			result = result + "\t@p1:" + transportes.get(i).getParadaUno() + "\t#" + transportes.get(i).getBcParadaUno();
			result = result + "\t@p2:" + transportes.get(i).getParadaDos() + "\t#" + transportes.get(i).getBcParadaDos();
			result = result + "\n";
		}

		result = result + "------------------------------------------------------------\n";

		return result;
	}

	public String toString() {

		String result = this.transportesToString();
		result = result + "\n";

		double beneficios = (double) Math.round(getBeneficios() * 100000) / 100000;
		double gastos = (double) Math.round(getGastos() * 100000) / 100000;

		result = result + "-----------------------\n";
		result = result + " Beneficios: ";
		result = result + beneficios;
		result = result + "\n Gastos: ";
		result = result + gastos;
		result = result + "\n Balance: ";
		result = result + (double) Math.round((beneficios - gastos) * 100000) / 100000;
		result = result + "\n-----------------------\n";

		return result;
	}

}
