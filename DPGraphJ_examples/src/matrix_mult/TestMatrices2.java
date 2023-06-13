package matrix_mult;

import java.util.Locale;

import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleDirectedGraph;

import hypergraphs.Data;
import hypergraphs.GraphTree;
import utils.Union;


public class TestMatrices2 {

		public static void main(String[] args) {
			Locale.setDefault(new Locale("en", "US"));
			Auxiliar2.leeFichero("./files/matrix_mult/matrices.txt");
			MatrixVertex2 p = MatrixVertex2.initial();
			String s = p.solution();
//			String2.toConsole(s);
			System.out.println(s);
			GraphTree<MatrixVertex2, MatrixEdge2, Integer, String> t = p.graphTree();
//			String2.toConsole(t.toString());
			System.out.println(t.toString());
//			String2.toConsole(t.string());
			System.out.println(t.string());
			
			SimpleDirectedGraph<Union<MatrixVertex2, MatrixEdge2>, DefaultEdge> g = p.data().graph();
			
			Data.toDotHypergraph(g, "files/matrix_mult/hiperMatrix.gv", p);
			Data.toDotAndOr(g, "files/matrix_mult/andOrMatrix.gv", p);
		}

}
