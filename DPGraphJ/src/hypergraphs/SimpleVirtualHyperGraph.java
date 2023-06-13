package hypergraphs;

import java.util.List;
import java.util.Set;

/**
 * <p> Implementation of a simple virtual hypergraph
 * We assume that vertices are subtypes of HyperVertex &lt; V,E &gt;
 * We assume that edges are subtypes of HyperEdge &lt; V &gt;
 * </p>
 *
 * <p> The graph is immutable so no modification operations are allowed. Query 
 * operations on all vertices or all edges are also not allowed.
 * If any of them are invoked, the UnsupportedOperationException will be thrown. </p>
 *
 *
 *
 *
 * @param <V> type of the vertices
 * @param <E> type of the edges
 * @param <A> type of the alternatives
 * @param <S> type of the solution
 *
 */

public class SimpleVirtualHyperGraph<V extends HyperVertex<V,E,A,S>, E extends HyperEdge<V,E,A,S>, A, S> {


	private V startVertex;

	public SimpleVirtualHyperGraph(V startVertex) {
		super();
		this.startVertex = startVertex;
	}

	public V getStartVertex() {
		return startVertex;
	}

	public boolean containsEdge(E e) {
		return e.source().alternatives().contains(e.alternative());
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
		return e.solutionWeight(weights);
	}

	public E getEdge(V v1, V v2) {
		return this.edgesOf(v1).stream().filter(e->e.targets().contains(v2)).findFirst().get();
	}

	/**
	 * @return Set of vertices of the graph that have been made explicit in the constructor..
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
		return v.baseCaseSolutionWeight();
	}

	public static <V extends HyperVertex<V, E, A, S>, E extends HyperEdge<V,E,A,S>, A, S> SimpleVirtualHyperGraph<V, E, A, S>
		simpleVirtualHyperGraph(V start) {
		return new SimpleVirtualHyperGraph<>(start);
	}

}
