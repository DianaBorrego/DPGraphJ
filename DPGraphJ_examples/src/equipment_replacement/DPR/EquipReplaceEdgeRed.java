package equipment_replacement.DPR;

import graphs.virtual.SimpleEdgeAction;


public record EquipReplaceEdgeRed(EquipReplaceVertexRed source, EquipReplaceVertexRed target, Integer action, Double weight) 
        implements SimpleEdgeAction<EquipReplaceVertexRed,Integer>{
	
	public static EquipReplaceEdgeRed of(EquipReplaceVertexRed source, EquipReplaceVertexRed target, Integer action) {
		Double weight = 
				switch (action) {
				case 0 -> (double) -EquipReplaceVertexRed.tradeinCost.get(source.i());
				case 1 -> (double) EquipReplaceVertexRed.operatingCost.get(0) 
									+ EquipReplaceVertexRed.tradeinCost.get(0) 
									- EquipReplaceVertexRed.tradeinCost.get(source.i());
				default -> (double) EquipReplaceVertexRed.operatingCost.get(source.i());
				};
		return new EquipReplaceEdgeRed(source, target, action, weight);
	}

}
	
