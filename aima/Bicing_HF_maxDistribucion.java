package bicing.aima;

import aima.search.framework.HeuristicFunction;
import bicing.Ciudad;

public class Bicing_HF_maxDistribucion implements HeuristicFunction {

	public double getHeuristicValue(Object ciudad) {
		return ((Ciudad) ciudad).getBeneficios();
	}

}
