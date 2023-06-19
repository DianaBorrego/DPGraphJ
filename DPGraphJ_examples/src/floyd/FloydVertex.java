package floyd;

import java.util.List;

import org.jgrapht.Graph;
import org.jgrapht.graph.GraphWalk;

//import us.lsi.alg.floyd.FloydVertex;
import utils.List2;
import graphs.SimpleEdge;
import hypergraphs.HyperVertex;

public record FloydVertex(Integer i,Integer j,Integer k) 
		implements HyperVertex<FloydVertex,FloydEdge,Boolean,GraphWalk<Integer,SimpleEdge<Integer>>>{

	
	public static FloydVertex initial(Integer i,Integer j) {	
		return new FloydVertex(i,j,0);
	}
	
	public static FloydVertex of(Integer i,Integer j,Integer k) {	
		return new FloydVertex(i,j,k);
	}

	public static Graph<Integer,SimpleEdge<Integer>> graph;
	public static Integer n;
	
	@Override
	public Boolean isValid() {
		return 0 <= i && i < n && 0 <= j && j < n && 0<= k && j <= n;
	}
	@Override
	public List<Boolean> alternatives() {
		if(this.isBaseCase()) return List.of();
		if(i == k || k ==j) return List.of(false);
		else return List.of(false,true);
	}
	
	@Override
	public List<FloydVertex> neighbors(Boolean a) {
		List<FloydVertex> r=null;
		if(!a) r = List.of(FloydVertex.of(i,j,k+1)); 
		else r = List.of(FloydVertex.of(i, k, k+1),FloydVertex.of(k, j, k+1)); 
		return r;
	}
	
	@Override
	public FloydEdge edge(Boolean a) {
		return FloydEdge.of(this,this.neighbors(a), a);
	}
	
	@Override
	public Boolean isBaseCase() {
		return  k == n; //FloydVertex.graph.containsEdge(this.i,this.j) ;
	}
	
	@Override
	public Double baseCaseSolutionWeight() {
		Double r = null;
		if(k ==n && FloydVertex.graph.containsEdge(this.i, this.j)){
			r = FloydVertex.graph.getEdge(i, j).weight();
		} else if(k ==n && !FloydVertex.graph.containsEdge(this.i, this.j)) {
			r = null;
		}
		return r;
	}
	
	public Integer getSource() {
		return this.i;
	}
	
	public Integer getTarget() {
		return j;
	}

	@Override
	public GraphWalk<Integer, SimpleEdge<Integer>> baseCaseSolution() {
		GraphWalk<Integer, SimpleEdge<Integer>> gp = null;
		if(this.i.equals(this.j)) 
			gp = new GraphWalk<>(FloydVertex.graph,List2.of(i),0.);
		else if(k ==n && FloydVertex.graph.containsEdge(this.i, this.j)){
			Double w = FloydVertex.graph.getEdge(i, j).weight();
			List<Integer> ls = List2.of(i,j);
			gp = new GraphWalk<>(FloydVertex.graph,ls,w);
		} else if(k ==n && !FloydVertex.graph.containsEdge(this.i, this.j)) {
			gp = null;
		}
		return gp;
	}

	@Override
	public FloydVertex me() {
		return this;
	}
	
	@Override
	public String toString() {
		return String.format("(%d,%d,%d)",i(),j(),k());
	}
	
}
