package bicing;

import java.util.Vector;

public class Furgonetas {

	public Vector<Furgoneta> furgonetas;

	public Furgonetas() {
		furgonetas = new Vector<Furgoneta>();
	}

	public void init(int f) {
		// TODO
		// falta inicializar
	}

	public void print() {
		System.out.println("Numero de furgonetas: " + this.furgonetas.size());
		for (int i = 0; i < furgonetas.size(); i++) {
			furgonetas.get(i).print(i);
		}
	}

}
