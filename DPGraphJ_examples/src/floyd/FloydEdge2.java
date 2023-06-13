package floyd;

import java.util.List;

import org.jgrapht.graph.GraphWalk;

import graphs.SimpleEdge;
import hypergraphs.HyperEdge;
import path.EGraphPath;

public record FloydEdge2(FloydVertex2 source, List<FloydVertex2> targets, Boolean alternative)
		implements HyperEdge<FloydVertex2, FloydEdge2, Boolean, GraphWalk<Integer,SimpleEdge<Integer>>> {

	public static FloydEdge2 of(FloydVertex2 source, List<FloydVertex2> targets, Boolean action) {
		return new FloydEdge2(source, targets, action);
	}

	@Override
	public Double solutionWeight(List<Double> solutions) {
		Double weight = null;
		if (!alternative()) weight = solutions.get(0);
		else weight = solutions.get(0) + solutions.get(1);
		return weight;
	}

	@Override
	public FloydEdge2 me() {
		return this;
	}

	@Override
	public GraphWalk<Integer, SimpleEdge<Integer>> solution(List<GraphWalk<Integer, SimpleEdge<Integer>>> solutions) {
		GraphWalk<Integer, SimpleEdge<Integer>> gp;
		if (!alternative())
			gp = solutions.get(0);
		else
			gp = solutions.get(0).concat(solutions.get(1),g->EGraphPath.weight(g));
		return gp;
	}

	@Override
	public String toString() {
		return this.alternative()?"T":"F";
	}
	
	

}
