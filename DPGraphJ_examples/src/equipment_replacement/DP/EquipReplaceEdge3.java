package equipment_replacement.DP;

import java.util.List;

import hypergraphs.HyperEdge;

public record EquipReplaceEdge3(EquipReplaceVertex3 source,List<EquipReplaceVertex3> targets,Integer alternative) 
        implements HyperEdge<EquipReplaceVertex3,EquipReplaceEdge3,Integer,SolStringDouble>{
	
	public static EquipReplaceEdge3 of(EquipReplaceVertex3 source, List<EquipReplaceVertex3> targets, Integer action) {
		return new EquipReplaceEdge3(source, targets, action);
	}

	@Override
	public Double solutionWeight(List<Double> solutions) {
		Double weight = null;
		if (source.j()==0) {
			weight = solutions.get(0) - EquipReplaceVertex3.tradeinCost.get(this.alternative());
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
			weight = solutions.get(0).weight() - EquipReplaceVertex3.tradeinCost.get(this.alternative());
		} else {
			weight = solutions.get(0).weight() + solutions.get(1).weight();
		}
	
		return SolStringDouble.of(r, weight);	
	}

	@Override
	public String toString() {
		return this.alternative().toString();
	}

	public EquipReplaceEdge3 me() {
		return this;
	}

}
	
