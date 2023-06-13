package equipment_replacement_all.DP;

import java.util.List;

import hypergraphs.HyperEdge;

public record EquipReplaceEdgeAllPD(EquipReplaceVertexAllPD source,List<EquipReplaceVertexAllPD> targets,Integer alternative) 
        implements HyperEdge<EquipReplaceVertexAllPD,EquipReplaceEdgeAllPD,Integer,SolStringDouble>{
	
	public static EquipReplaceEdgeAllPD of(EquipReplaceVertexAllPD source, List<EquipReplaceVertexAllPD> targets, Integer action) {
		return new EquipReplaceEdgeAllPD(source, targets, action);
	}

	@Override
	public Double solutionWeight(List<Double> solutions) {
		Double weight = null;
		if (source.j()==0) {
			weight = solutions.get(0) - EquipReplaceVertexAllPD.tradeinCost.get(this.alternative());
		} else {
			weight = solutions.get(0) + solutions.get(1);
		}
		return weight;
	}

	@Override
	public SolStringDouble solution(List<SolStringDouble> solutions) {
		String r;
		Double weight;
		if (solutions.size()==1) {
			r = solutions.get(0).s();
		} else {
			r = String.format("%s -> %s",solutions.get(0).s(),solutions.get(1).s());	
		}
		if (source.j()==0) {
			weight = solutions.get(0).weight() - EquipReplaceVertexAllPD.tradeinCost.get(this.alternative());
		} else {
			weight = solutions.get(0).weight() + solutions.get(1).weight();
		}
	
		return SolStringDouble.of(r, weight);	
	}

	@Override
	public String toString() {
		return this.alternative().toString();
	}

	public EquipReplaceEdgeAllPD me() {
		return this;
	}

}
	
