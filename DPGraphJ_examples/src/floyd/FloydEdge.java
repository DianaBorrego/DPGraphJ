package floyd;

import java.util.List;

import org.jgrapht.graph.GraphWalk;

import graphs.SimpleEdge;
import hypergraphs.HyperEdge;
import path.EGraphPath;

public record FloydEdge(FloydVertex source, List<FloydVertex> targets, Boolean alternative)
		implements HyperEdge<FloydVertex, FloydEdge, Boolean, GraphWalk<Integer,SimpleEdge<Integer>>> {

	public static FloydEdge of(FloydVertex source, List<FloydVertex> targets, Boolean action) {
		return new FloydEdge(source, targets, action);
	}

	@Override
	public Double solutionWeight(List<Double> solutions) {
		Double weight = null;
		if (!alternative()) weight = solutions.get(0);
		else weight = solutions.get(0) + solutions.get(1);
		return weight;
	}

	@Override
	public FloydEdge me() {
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
