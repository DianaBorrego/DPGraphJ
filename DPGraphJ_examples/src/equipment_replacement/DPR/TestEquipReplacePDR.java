package equipment_replacement.DPR;


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
import graphs.alg.DPR;
import graphs.alg.GreedyOnGraph;



public class TestEquipReplacePDR {
	

	
	public static void main(String[] args) {
	
		Locale.setDefault(new Locale("en", "US"));
		Auxiliar2.iniDatos("./files/equip_rep_DPR/EqMant1.txt");
		EquipReplaceVertexRed e1 = EquipReplaceVertexRed.initial();
		
		EGraph<EquipReplaceVertexRed, EquipReplaceEdgeRed> graph = 
				EGraph.virtual(e1,EquipReplaceVertexRed.goal(), PathType.Sum, Type.Min)
				.greedyEdge(EquipReplaceVertexRed::greedyEdge)
				.heuristic(EquipReplaceHeuristic::heuristic)
				.build();	
		
		GreedyOnGraph<EquipReplaceVertexRed, EquipReplaceEdgeRed> rr = GreedyOnGraph.of(graph);
		
		GraphPath<EquipReplaceVertexRed, EquipReplaceEdgeRed> path = rr.path();	
		Double bv = path.getWeight();
		
		System.out.println("1 = "+bv);
		
		DPR<EquipReplaceVertexRed, EquipReplaceEdgeRed, String> ms = 
				DPR.of(graph,null,bv,path,true);
//		DPR23<EquipReplaceVertexRed, EquipReplaceEdgeRed, String> ms = 
//				DPR23.of(graph,null,bv,path,true);
		
		Optional<GraphPath<EquipReplaceVertexRed, EquipReplaceEdgeRed>>  sp = ms.search();
		GraphPath<EquipReplaceVertexRed, EquipReplaceEdgeRed> s1 = sp.orElse(path);
		Double optimalWeight = s1.getWeight();
		System.out.println(optimalWeight);
		String s = EquipReplaceVertexRed.getSolucion(s1);
		System.out.println(s);
		
		Predicate<EquipReplaceVertexRed> pv = v->ms.optimalPath().get().getVertexList().contains(v);
		Predicate<EquipReplaceEdgeRed> pe= e->ms.optimalPath().get().getEdgeList().contains(e);
		
		GraphColors.toDot(ms.outGraph,"files/equip_rep_DPR/EquipReplacePDRGraph8.gv",
				v->String.format("(%d,%d)",v.i(),v.k()),
				e->e.action().toString(),
				v->all(colorIf(Color.red,pv.test(v)),styleIf(Style.bold,pv.test(v))),
				e->all(colorIf(Color.red,pe.test(e)),styleIf(Style.bold,pe.test(e)))
				);

		
		
		

	
	}

}
