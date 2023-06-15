package floyd;

import java.util.Locale;

import org.jgrapht.Graph;
import org.jgrapht.GraphPath;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.GraphWalk;
import org.jgrapht.graph.SimpleDirectedGraph;
import org.jgrapht.graph.SimpleWeightedGraph;

import utils.Union;
import graphs.Graphs2;
import graphs.SimpleEdge;
import hypergraphs.Data;
import hypergraphs.GraphTree;
import floyd.data.GraphsReader;
import floyd.data.IntegerVertexGraphView;
import floyd.data.Carretera;
import floyd.data.Ciudad;


public class TestFloyd2 {
	
	public static Ciudad ciudad(Graph<Ciudad,Carretera> graph, String nombre) {
		return graph.vertexSet().stream().filter(c->c.nombre().equals(nombre)).findFirst().get();
	}
	
	public static SimpleWeightedGraph<Ciudad, Carretera> leeDatos(String fichero) {
		SimpleWeightedGraph<Ciudad, Carretera> graph = GraphsReader.newGraph(fichero, 
				Ciudad::ofFormat, 
				Carretera::ofFormat,
				Graphs2::simpleWeightedGraph, 
				Carretera::km);
		return graph;
	}

	public static void main(String[] args) {
		Locale.setDefault(new Locale("en", "US"));
		
		SimpleWeightedGraph<Ciudad, Carretera> graph = leeDatos("./files/Floyd/andalucia.txt");
		IntegerVertexGraphView<Ciudad,Carretera> graph2 = IntegerVertexGraphView.of(graph);
		
		System.out.println(graph);
		System.out.println(graph2);
		
		Integer origen = graph2.getIndex(ciudad(graph,"Sevilla"));
		Integer destino = graph2.getIndex(ciudad(graph,"Almeria"));
		
		FloydVertex2.graph = graph2;
		FloydVertex2.n = graph2.vertexSet().size();
		FloydVertex2 p = FloydVertex2.initial(origen,destino);
		
//		GraphPath<Integer,SimpleEdge<Integer>> gp = p.solution();
//		String2.toConsole(FloydVertex2.n.toString());
		System.out.println(FloydVertex2.n.toString());
//		String2.toConsole(p.solutionWeight().toString());
		GraphPath<Integer, SimpleEdge<Integer>> gp = p.solution();
		GraphTree<FloydVertex2, FloydEdge2, Boolean, GraphWalk<Integer, SimpleEdge<Integer>>> t = p.graphTree();
		
//		String2.toConsole(gp.getVertexList().stream().map(i->graph2.vertex(i)).toList().toString());
		System.out.println(gp.getVertexList().stream().map(i->graph2.vertex(i)).toList().toString());
//		String2.toConsole(t.string());
		System.out.println(t.string());
		SimpleDirectedGraph<Union<FloydVertex2,FloydEdge2>, DefaultEdge> g = p.data().graph();
		
		Data.toDotHypergraph(g, "files/Floyd/hiperGraph.gv", p);
		Data.toDotAndOr(g, "files/Floyd/andOrGraph.gv", p);
	}

}
