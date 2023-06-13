package hypergraphs_useless;

import java.util.*;

import hypergraphs2.HyperEdge2;
import hypergraphs2.HyperVertex2;

/**
 * <p> Implementaci�n de un hypergrafo virtual simple 
 * Asumimos que los v�rtices son subtipo de VirtualHyperVertex &lt; V,E &gt;
 * Asumimos que las aristas son subtipos de SimpleHyperEdge &lt; V &gt; 
 * </p>
 * 
 * <p> El grafo es inmutable por lo que no est�n permitadas las operaci�n de modificaci�n. Tampoco
 * est�n permitidas las operaciones de consulta de todos los v�rtices o todas las aristas.
 *  Si se invocan alguna de ellas se disparar� 
 * la excepci�n UnsupportedOperationException </p>
 * 
 *
 * 
 * 
 * 
 * @author Miguel Toro
 *
 * @param <V> El tipo de los v�rtices
 * @param <E> El tipo de las aristas
 * 
 */

public class SimpleVirtualHyperGraph<V extends HyperVertex2<V,E,A,S>, E extends HyperEdge2<V,E,A,S>, A, S> {
			
	
	private V startVertex;
	
	public SimpleVirtualHyperGraph(V startVertex) {
		super();
		this.startVertex = startVertex;
	}

	public V getStartVertex() {
		return startVertex;
	}

	public boolean containsEdge(E e) {
		return e.source().actions().contains(e.action());
	}

	public boolean containsEdge(V v1, V v2) {
		return this.edgesOf(v1).stream().anyMatch(e->e.targets().contains(v2));
	}

	public boolean containsVertex(V v) {
		return v.isValid();
	}
	
	public V getEdgeSource(E e) {
		return e.source();
	}

	public List<V> getEdgeTargets(E e) {
		return e.targets();
	}

	public double getEdgeWeight(E e, List<Double> weights) {
//		return e.weight(weights);
		return e.solutionWeight(weights); //diana
	}
	
	public E getEdge(V v1, V v2) {
		return this.edgesOf(v1).stream().filter(e->e.targets().contains(v2)).findFirst().get();
	}	
	
	/** 
	 * @return Conjunto de v�rtices del grafo que se han hecho expl�citos en el constructor.
	 */
	
	public Set<V> vertexSet(){
		return Set.of(this.startVertex);
	}
	
	public List<E> edgesOf(V v) {
		return v.edgesOf();
	}	
	public int degreeOf(V v) {
		return v.edgesOf().size();	
	}	
	public int outDegreeOf(V v) {
		return v.edgesOf().size();
	}
	public List<E> outgoingEdgesOf(V v) {
		return edgesOf(v);
	}
	
	public Boolean isBaseCase(V v) {
		return v.isBaseCase();
	}
	
	public Double baseCaseSolution(V v) {
//		return v.baseCaseSolution();
		return v.baseCaseSolutionWeight();
	}

	public static <V extends HyperVertex2<V, E, A, S>, E extends HyperEdge2<V,E,A,S>, A, S> SimpleVirtualHyperGraph<V, E, A, S> 
		simpleVirtualHyperGraph(V start) {
		return new SimpleVirtualHyperGraph<V, E, A, S>(start);
	}
	
}
