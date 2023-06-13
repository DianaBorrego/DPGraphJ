package equipment_replacement.DPR;


import java.util.function.Predicate;

public class EquipReplaceHeuristic {
	
	public static Double heuristic(EquipReplaceVertexRed v1, Predicate<EquipReplaceVertexRed> goal, EquipReplaceVertexRed v2) {
		if (v1.i()==0)
			return 0.;
		else
			return (double) (v1.k()*EquipReplaceVertexRed.operatingCost.get(0) - EquipReplaceVertexRed.tradeinCost.get(v1.i()));
	}
	
}
