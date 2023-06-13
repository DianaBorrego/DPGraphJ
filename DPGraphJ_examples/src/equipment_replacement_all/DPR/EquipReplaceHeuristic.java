package equipment_replacement_all.DPR;


import java.util.function.Predicate;

public class EquipReplaceHeuristic {
	
	public static Double heuristic(EquipReplaceAllVertexRed v1, Predicate<EquipReplaceAllVertexRed> goal, EquipReplaceAllVertexRed v2) {
		if (v1.i()==0)
			return 0.;
		else
			return (double) (v1.k()*EquipReplaceAllVertexRed.operatingCost.get(0) - EquipReplaceAllVertexRed.tradeinCost.get(v1.i()));
	}
	
}
