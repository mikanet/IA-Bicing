package bicing.aima;

import java.util.List;
import java.util.Vector;

import aima.search.framework.Successor;
import aima.search.framework.SuccessorFunction;
import bicing.Ciudad;

public class BicingSuccessorFunctionSA implements SuccessorFunction {

	@SuppressWarnings("unused")
	public List<Successor> getSuccessors(Object aState) {
		Vector<Successor> result = new Vector<Successor>();
		Ciudad estCiudad = (Ciudad) aState;

		return null;
	}

}
