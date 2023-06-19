package equipment_replacement.DP;


import java.util.Locale;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleDirectedGraph;

import hypergraphs.Data;
import hypergraphs.GraphTree;
import utils.Union;

public class TestEquipReplace {

	
	public static void main(String[] args) {
	
		Locale.setDefault(new Locale("en", "US"));
		Auxiliar3.iniData("./files/equip_rep_DP/EqMant1.txt");
		EquipReplaceVertex p = EquipReplaceVertex.initial();
		SolStringDouble s = p.solution();
		System.out.println(s.weight().toString());
		System.out.println(s.s());
		GraphTree<EquipReplaceVertex, EquipReplaceEdge, Integer, SolStringDouble> t = p.graphTree();
		System.out.println(t.toString());
		System.out.println(t.string());
		
		SimpleDirectedGraph<Union<EquipReplaceVertex, EquipReplaceEdge>, DefaultEdge> g = p.data().graph();
		
		Data.toDotHypergraph(g, "files/equip_rep_DP/hiperEquipReplace8.gv", p);
		Data.toDotAndOr(g, "files/equip_rep_DP/andOrEquipReplace8.gv", p);

	
	
	}

}
