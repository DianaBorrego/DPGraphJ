package resources_allocation.DP;

import java.util.List;
import hypergraphs.HyperEdge;


public record ResAlloOneEdgePD(ResAlloOneVertexPD source, List<ResAlloOneVertexPD> targets,Integer alternative) 
        implements HyperEdge<ResAlloOneVertexPD,ResAlloOneEdgePD,Integer,SolStringDouble>{
	
	public static ResAlloOneEdgePD of(ResAlloOneVertexPD source, List<ResAlloOneVertexPD> targets, Integer action) {
		return new ResAlloOneEdgePD(source, targets, action);
	}

	@Override
	public Double solutionWeight(List<Double> solutions) {
		Double weight = solutions.get(0) + solutions.get(1);
//System.out.println("source: "+source+"\ntargets: "+targets+"\naction:"+action+"\nweight:"+weight);
		return weight;
	}

	@Override
	public SolStringDouble solution(List<SolStringDouble> solutions) {
		String r;
		Double weight;
		r = String.format("%s -> %s",solutions.get(0).s(),solutions.get(1).s());	
		weight = solutions.get(0).weight() + solutions.get(1).weight();
	
		return SolStringDouble.of(r, weight);	
	}

	@Override
	public String toString() {
		return this.alternative().toString();
	}

	public ResAlloOneEdgePD me() {
		return this;
	}

}