package resources_allocation.DP;


import java.util.Locale;
import hypergraphs.Data;
import hypergraphs.GraphTree;





public class TestResAlloOnePD {
	

	
	public static void main(String[] args) {
	
		Locale.setDefault(new Locale("en", "US"));
		Data.type = Data.DpType.Max;
		Auxiliar.iniDatos("./ficheros/ResAllo1.txt");
		
//		ResAlloOneVertexPD.X = 10;
//		ResAlloOneVertexPD.N = 4;

		Auxiliar.iniDatos(1000, 20, 100, 20);
		
		System.out.println(ResAlloOneVertexPD.rdata);
		
		ResAlloOneVertexPD p = ResAlloOneVertexPD.initial();
		SolStringDouble s = p.solution();
		
		System.out.println(s.weight().toString());
		System.out.println(s.s());
		
		GraphTree<ResAlloOneVertexPD, ResAlloOneEdgePD, Integer, SolStringDouble> t = p.graphTree();
		System.out.println(t.toString());

		
		
//		SimpleDirectedGraph<Union<EquipReplaceVertex3, EquipReplaceEdge3>, DefaultEdge> g = p.datos().graph();
//	
//		Datos.toDotHypergraph(g, "ficheros/hiperEquipReplace8.gv", p);
//		Datos.toDotAndOr(g, "ficheros/andOrEquipReplace8.gv", p);

		
		
		

	
	}

}
