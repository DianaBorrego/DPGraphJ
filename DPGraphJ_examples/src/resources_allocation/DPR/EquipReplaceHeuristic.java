package resources_allocation.DPR;

import java.util.Comparator;
import java.util.function.Predicate;
import java.util.stream.IntStream;

public class EquipReplaceHeuristic {
	
	private static Double maxRatio(Integer x) {
		return IntStream.rangeClosed(0, x).mapToDouble(i -> (double) ResAlloOneVertex.rdata.get(i)/i).boxed().max(Comparator.comparing(r->r)).orElseThrow();
	}


		public static Double heuristic(ResAlloOneVertex v1, Predicate<ResAlloOneVertex> goal, ResAlloOneVertex v2) {
		if (v1.k()==0)
			return 0.;
//		else if (v1.k() > EquipReplaceVertexRed.M - v1.i()) 
//			return (double) (v1.k()*EquipReplaceVertexRed.operatingCost.get(0) + EquipReplaceVertexRed.tradeinCost.get(0) - EquipReplaceVertexRed.tradeinCost.get(1));
//		else 
			return v1.x()*maxRatio(v1.x());
	}
	
}
