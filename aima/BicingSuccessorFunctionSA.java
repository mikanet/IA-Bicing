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

		// Aplicamos operadores al azar

		do {
			switch (Principal.random.nextInt(2)) {

			case 0:
				// Operador addTransporte

				break;

			case 1:
				// Operador delTransporte

				break;

			}
		} while (result.isEmpty());

		return result;
	}

}
