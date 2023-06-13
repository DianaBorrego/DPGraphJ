package resources_allocation.DPR;

import java.util.Locale;
import graphs.virtual.EGraph;
import graphs.virtual.EGraph.Type;
import path.EGraphPath.PathType;

import static colors.GraphColors.all;
import static colors.GraphColors.colorIf;
import static colors.GraphColors.styleIf;

import java.util.Optional;
import java.util.function.Predicate;

import org.jgrapht.GraphPath;


import colors.GraphColors;
import colors.GraphColors.Color;
import colors.GraphColors.Style;
//import graphs.alg.DPR;
import graphs.alg.DPRunbounded;
//import graphs.alg.GreedyOnGraph;


//import java.util.List;
//import java.util.stream.Collectors;
//
//import org.jgrapht.Graph;
//import org.jgrapht.graph.DefaultEdge;
//import org.jgrapht.graph.SimpleDirectedGraph;
//import org.jgrapht.graph.SimpleWeightedGraph;



public class TestResAlloOnePDR {
	

	
	public static void main(String[] args) {
	
		Locale.setDefault(new Locale("en", "US"));
		Auxiliar.iniDatos("./ficheros/ResAllo1.txt");
		
		ResAlloOneVertex.X = 10;
		ResAlloOneVertex.N = 3;
		
		Auxiliar.iniDatos(1000, 20, 100, 20);
		
		System.out.println(ResAlloOneVertex.rdata);
		
		
		ResAlloOneVertex e1 = ResAlloOneVertex.initial();
		
		EGraph<ResAlloOneVertex, ResAlloOneEdge> graph = 
				EGraph.virtual(e1,ResAlloOneVertex.goal(), PathType.Sum, Type.Max)
				.greedyEdge(ResAlloOneVertex::greedyEdge)
				.heuristic(EquipReplaceHeuristic::heuristic)
				.build();	
		
//		GreedyOnGraph<ResAlloOneVertex, ResAlloOneEdge> rr = GreedyOnGraph.of(graph);
//		
//		GraphPath<ResAlloOneVertex, ResAlloOneEdge> path = rr.path();	
//		Double bv = path.getWeight();
//		
//		System.out.println("1 = "+bv);

		GraphPath<ResAlloOneVertex, ResAlloOneEdge> path = null;	
		Double bv = null;

		
		DPRunbounded<ResAlloOneVertex, ResAlloOneEdge, String> ms = 
				DPRunbounded.of(graph,null,bv,path,true);
		
		Optional<GraphPath<ResAlloOneVertex, ResAlloOneEdge>>  sp = ms.search();
		GraphPath<ResAlloOneVertex, ResAlloOneEdge> s1 = sp.get();
		
		Double optimalWeight = s1.getWeight();
		System.out.println(optimalWeight);
		
		String s = ResAlloOneVertex.getSolucion(s1);
		System.out.println(s);
		
		Predicate<ResAlloOneVertex> pv = v->ms.optimalPath().get().getVertexList().contains(v);
		Predicate<ResAlloOneEdge> pe= e->ms.optimalPath().get().getEdgeList().contains(e);
		
		GraphColors.toDot(ms.outGraph,"ficheros/EquipReplacePDRGraph.gv",
				v->String.format("(%d,%d)",v.x(),v.k()),
				e->e.action().toString(),
				v->all(colorIf(Color.red,pv.test(v)),styleIf(Style.bold,pv.test(v))),
				e->all(colorIf(Color.red,pe.test(e)),styleIf(Style.bold,pe.test(e)))
				);

		
		
		

	
	}

}
