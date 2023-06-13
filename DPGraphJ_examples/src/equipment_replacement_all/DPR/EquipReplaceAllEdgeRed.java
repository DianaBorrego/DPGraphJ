package equipment_replacement_all.DPR;

import graphs.virtual.SimpleEdgeAction;


public record EquipReplaceAllEdgeRed(EquipReplaceAllVertexRed source, EquipReplaceAllVertexRed target, Integer action, Double weight) 
        implements SimpleEdgeAction<EquipReplaceAllVertexRed,Integer>{
	
	public static EquipReplaceAllEdgeRed of(EquipReplaceAllVertexRed source, EquipReplaceAllVertexRed target, Integer action) {
		Double weight;
		if (action == 0)
			weight = (double) -EquipReplaceAllVertexRed.tradeinCost.get(source.i());
		else if (action == source.i()+1)
			weight = (double) EquipReplaceAllVertexRed.operatingCost.get(source.i());
		else
			weight = (double) EquipReplaceAllVertexRed.operatingCost.get(action-1) 
					+ EquipReplaceAllVertexRed.tradeinCost.get(action-1) 
					- EquipReplaceAllVertexRed.tradeinCost.get(source.i());
		return new EquipReplaceAllEdgeRed(source, target, action, weight);
	}

}
	
