package bicing;

public class Transporte {

	int origen; // estacion origen
	int bcOrigen; // int bicicletas de origen

	int paradaUno; // destino 1
	int bcParadaUno; // bicicletas destino 1

	int paradaDos; // destino 2
	int bcParadaDos; // bicicletas destino 2

	public Transporte() {

		origen = -1;
		bcOrigen = -1;

		paradaUno = -1;
		paradaDos = -1;

		bcParadaUno = -1;
		bcParadaDos = -1;

	}

	public int getOrigen() {
		return origen;
	}

	public void setOrigen(int origen) {
		this.origen = origen;
	}

	public int getBcOrigen() {
		return bcOrigen;
	}

	public void setBcOrigen(int bc_salida) {
		this.bcOrigen = bc_salida;
	}

	public int getParadaUno() {
		return paradaUno;
	}

	public void setParadaUno(int parada_uno) {
		this.paradaUno = parada_uno;
	}

	public int getBcParadaUno() {
		return bcParadaUno;
	}

	public void setBcParadaUno(int bc_p_uno) {
		this.bcParadaUno = bc_p_uno;
	}

	public int getParadaDos() {
		return paradaDos;
	}

	public void setParadaDos(int parada_dos) {
		this.paradaDos = parada_dos;
	}

	public int getBcParadaDos() {
		return bcParadaDos;
	}

	public void setBcParadaDos(int bc_p_dos) {
		this.bcParadaDos = bc_p_dos;
	}

	public void print(int i) {
		System.out.print("Transporte " + i + ", estacion origen: " + this.origen);
		System.out.print(" - bc salida: " + this.bcOrigen);

		System.out.print(" - parada1: " + this.paradaUno);
		System.out.print(" - bc_p1: " + this.bcParadaUno);

		System.out.print(" - parada2: " + this.paradaDos);
		System.out.print(" - bc_p2: " + this.bcParadaDos);
		System.out.println();
	}
}