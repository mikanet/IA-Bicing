package bicing;

public class EstacionesCompare {
	int origen;
	int sobrantes;
	double ganancia;

	public EstacionesCompare() {
	}

	public EstacionesCompare(int origen, int sobrantes) {
		this.origen = origen;
		this.sobrantes = sobrantes;
	}

	public int getOrigen() {
		return this.origen;
	}

	public void setOrigen(int origen) {
		this.origen = origen;
	}

	public int getSobrantes() {
		return this.sobrantes;
	}

	public void setSobrantes(int sobrantes) {
		this.sobrantes = sobrantes;
	}

	public void setGanancia(double ganancia) {
		this.ganancia = ganancia;
	}

	public double getGanancia() {
		return this.ganancia;
	}
}
