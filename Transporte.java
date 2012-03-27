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