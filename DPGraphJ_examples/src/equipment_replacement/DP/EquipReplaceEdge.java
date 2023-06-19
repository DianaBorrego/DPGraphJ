package equipment_replacement.DP;

import java.util.List;

import hypergraphs.HyperEdge;

public record EquipReplaceEdge(EquipReplaceVertex source,List<EquipReplaceVertex> targets,Integer alternative) 
        implements HyperEdge<EquipReplaceVertex,EquipReplaceEdge,Integer,SolStringDouble>{
	
	public static EquipReplaceEdge of(EquipReplaceVertex source, List<EquipReplaceVertex> targets, Integer action) {
		return new EquipReplaceEdge(source, targets, action);
	}

	@Override
	public Double solutionWeight(List<Double> solutions) {
		Double weight = null;
		if (source.j()==0) {
			weight = solutions.get(0) - EquipReplaceVertex.tradeinCost.get(this.alternative());
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
			weight = solutions.get(0).weight() - EquipReplaceVertex.tradeinCost.get(this.alternative());
		} else {
			weight = solutions.get(0).weight() + solutions.get(1).weight();
		}
	
		return SolStringDouble.of(r, weight);	
	}

	@Override
	public String toString() {
		return this.alternative().toString();
	}

	public EquipReplaceEdge me() {
		return this;
	}

}
	
