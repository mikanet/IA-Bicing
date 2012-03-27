package bicing;

public class Transporte {

	int origen; // estacion origen
	int bc_salida; // int bicicletas de salida

	int parada_uno; // destino 1
	int bc_p_uno; // bicicletas destino 1

	int parada_dos; // destino 2
	int bc_p_dos; // bicicletas destino 2

	public Transporte() {

		origen = -1;
		bc_salida = -1;

		parada_uno = -1;
		parada_dos = -1;

		bc_p_uno = -1;
		bc_p_dos = -1;

	}

	public int getOrigen() {
		return origen;
	}

	public void setOrigen(int origen) {
		this.origen = origen;
	}

	public int getBc_salida() {
		return bc_salida;
	}

	public void setBc_salida(int bc_salida) {
		this.bc_salida = bc_salida;
	}

	public int getParada_uno() {
		return parada_uno;
	}

	public void setParada_uno(int parada_uno) {
		this.parada_uno = parada_uno;
	}

	public int getBc_p_uno() {
		return bc_p_uno;
	}

	public void setBc_p_uno(int bc_p_uno) {
		this.bc_p_uno = bc_p_uno;
	}

	public int getParada_dos() {
		return parada_dos;
	}

	public void setParada_dos(int parada_dos) {
		this.parada_dos = parada_dos;
	}

	public int getBc_p_dos() {
		return bc_p_dos;
	}

	public void setBc_p_dos(int bc_p_dos) {
		this.bc_p_dos = bc_p_dos;
	}

	public void print(int i) {
		System.out.print("Transporte " + i + ", estacion origen: " + this.origen);
		System.out.print(" - bc salida: " + this.bc_salida);

		System.out.print(" - parada1: " + this.parada_uno);
		System.out.print(" - bc_p1: " + this.bc_p_uno);

		System.out.print(" - parada2: " + this.parada_dos);
		System.out.print(" - bc_p2: " + this.bc_p_dos);
		System.out.println();
	}
}