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
import floyd.data.Road;
import floyd.data.City;


public class TestFloyd {
	
	public static City city(Graph<City,Road> graph, String nombre) {
		return graph.vertexSet().stream().filter(c->c.nombre().equals(nombre)).findFirst().get();
	}
	
	public static SimpleWeightedGraph<City, Road> leeDatos(String fichero) {
		SimpleWeightedGraph<City, Road> graph = GraphsReader.newGraph(fichero, 
				City::ofFormat, 
				Road::ofFormat,
				Graphs2::simpleWeightedGraph, 
				Road::km);
		return graph;
	}

	public static void main(String[] args) {
		Locale.setDefault(new Locale("en", "US"));
		
		SimpleWeightedGraph<City, Road> graph = leeDatos("./files/Floyd/andalucia.txt");
		IntegerVertexGraphView<City,Road> graph2 = IntegerVertexGraphView.of(graph);
		
		System.out.println(graph);
		System.out.println(graph2);
		
		Integer origen = graph2.getIndex(city(graph,"Sevilla"));
		Integer destino = graph2.getIndex(city(graph,"Almeria"));
		
		FloydVertex.graph = graph2;
		FloydVertex.n = graph2.vertexSet().size();
		FloydVertex p = FloydVertex.initial(origen,destino);
		
//		GraphPath<Integer,SimpleEdge<Integer>> gp = p.solution();
//		String2.toConsole(FloydVertex2.n.toString());
		System.out.println(FloydVertex.n.toString());
//		String2.toConsole(p.solutionWeight().toString());
		GraphPath<Integer, SimpleEdge<Integer>> gp = p.solution();
		GraphTree<FloydVertex, FloydEdge, Boolean, GraphWalk<Integer, SimpleEdge<Integer>>> t = p.graphTree();
		
//		String2.toConsole(gp.getVertexList().stream().map(i->graph2.vertex(i)).toList().toString());
		System.out.println(gp.getVertexList().stream().map(i->graph2.vertex(i)).toList().toString());
//		String2.toConsole(t.string());
		System.out.println(t.string());
		SimpleDirectedGraph<Union<FloydVertex,FloydEdge>, DefaultEdge> g = p.data().graph();
		
		Data.toDotHypergraph(g, "files/Floyd/hiperGraph.gv", p);
		Data.toDotAndOr(g, "files/Floyd/andOrGraph.gv", p);
	}

}
