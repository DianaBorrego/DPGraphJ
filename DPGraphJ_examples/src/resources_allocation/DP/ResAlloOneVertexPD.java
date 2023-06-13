package resources_allocation.DP;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.jgrapht.Graph;
import org.jgrapht.GraphPath;
import org.jgrapht.graph.GraphWalk;

import utils.List2;
//import us.lsi.common.Preconditions;
//import us.lsi.graphs.SimpleEdge;
//import us.lsi.graphs.alg.DP.Sp;
//import us.lsi.graphs.virtual.VirtualVertex;
//import us.lsi.hypergraphs.GraphTree;
//import us.lsi.hypergraphs.VirtualHyperVertex;
//import us.lsi.path.EGraphPath;

import hypergraphs.HyperVertex;

// x - cantidad de recursos
// k - número de actividades entre las que repartir
// repartir x entre k actividades 
public record ResAlloOneVertexPD(Integer x, Integer k) 
	implements HyperVertex<ResAlloOneVertexPD,ResAlloOneEdgePD,Integer,SolStringDouble> {
	
	
	public static ResAlloOneVertexPD initial() {	
		return new ResAlloOneVertexPD(X,N);
	}
		
	public static ResAlloOneVertexPD of(Integer x,Integer k) {	
		return new ResAlloOneVertexPD(x,k);
	}

	//nuúmero de actividades
	public static int N;
	//Cantidad de recursos
	public static int X;
	//retorno de actividad según cantidad de recurso
	public static List<Integer> rdata;

	@Override
	public ResAlloOneVertexPD me() {
		return this;
	}

	public ResAlloOneVertexPD neigbord(Integer x, Integer k){
		return new ResAlloOneVertexPD(x,k);
	}

	@Override
	public Boolean isValid() {
		return 1 <= k && k <= N && 0 <= x && x <= X;
	}
	
	@Override
	public List<Integer> alternatives() {
		if (k==1)
			return List.of();
		if (k>1 && k%2==0)
			return List2.rangeList(0, x/2+1);
		else
			return List2.rangeList(0, x+1);
	}
	
	@Override
	public List<ResAlloOneVertexPD> neighbors(Integer a) {
		return List.of(this.neigbord(a, k/2), this.neigbord(x-a, k-k/2));
	}

	@Override
	public ResAlloOneEdgePD edge(Integer a) {
		return ResAlloOneEdgePD.of(this,this.neighbors(a), a);
	}
	
	@Override
	public Boolean isBaseCase() {
		return  k == 1; 
	}
	
	@Override
	public Double baseCaseSolutionWeight() {
		Double r = (double) rdata.get(x);
		return r;
	}
	
	@Override
	public SolStringDouble baseCaseSolution() {
		Double weight = baseCaseSolutionWeight();
		String s=x.toString();
		return SolStringDouble.of(s, weight);
	}

	
}
