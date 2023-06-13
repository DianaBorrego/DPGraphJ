package hypergraphs;

import java.util.List;
import graphs.alg.DP.Sp;

/**
 * <p> Interface of a HyperVertex, to model a problem
 * </p>
 *
 *
 *
 * @param <V> type of the vertices
 * @param <E> type of the edges
 * @param <A> type of the alternatives
 * @param <S> type of the solution
 *
 */

public interface HyperVertex<V extends HyperVertex<V, E, A, S>, E extends HyperEdge<V,E,A,S>, A, S> {

	/**
	 * 
	 * @return list of alternatives for the problem represented by the hypervertex
	 */
	public List<A> alternatives();
	
	/**
	 * 
	 * @return boolean indicating if the (sub)problem is a base case
	 */
	public Boolean isBaseCase();
	
	/**
	 * 
	 * @return weight of the solution to the problem when this is a base case, 
	 * being the weight the value for the objective function to be optimised
	 */
	public Double baseCaseSolutionWeight();
	
	/**
	 * 
	 * @return solution to the problem when it is a base case
	 */
	public S baseCaseSolution();
	
	/**
	 * 
	 * @return current vertex
	 */
	public V me();
	
	/**
	 * 
	 * @param a
	 * @return list of subproblems (i.e. vertices) that are neighbors of the 
	 * current problem when considering alternative a
	 */
	public List<V> neighbors(A a);
	
	/**
	 * 
	 * @param a
	 * @return edge that originates from taking alternative a
	 */
	public E edge(A a);

	/**
	 * 
	 * @return Boolean value to indicate if a HyperVertex represents a 
	 * valid problem than can be solved
	 */
	public default Boolean isValid() {
		return true;
	}

	/**
	 * 
	 * @return Data structure storing the information of the problems (vertices) 
	 * that have been generated so far
	 */
	public default Data<V,E,A> data() {
		return Data.get();
	}
	
	/**
	 * 
	 * @return the partial solution to the problem, calculated as the best option 
	 * that can be found from the current vertex
	 */
	public default Sp<E> solutionWeight() {
		Sp<E> r;
		if (data().contains(me()))
			r = data().get(me());
		else {
			r = null;
			if (this.isBaseCase()) {
				Double br = baseCaseSolutionWeight();
				if (br != null) r = Sp.of(br, null);
			} else if (!this.edgesOf().isEmpty()) {
				r = this.edgesOf().stream()
						.map(e -> e.solutionWeight())
						.filter(s -> s != null)
						.peek(e -> data().putAll(me(), e))
						.min(data().order()).orElse(null);
			}
			data().put(me(), r);
		}
		return r;
	}

	/**
	 * 
	 * @return solution to the problem represented by the vertex 
	 */
	default public S solution() {
		Sp<E> sp = this.solutionWeight();
		S r;
		if (this.isBaseCase())
			r = this.baseCaseSolution();
		else {
			r = sp.edge().solution();
		}
		return r;
	}

	/**
	 *
	 * @return List of edges towards neighbors
	 */
	default public List<E> edgesOf() {
		return this.alternatives().stream().map(a -> this.edge(a)).toList();
	}

	default GraphTree<V, E, A, S> graphTree() {
		return GraphTree.of(this.me(), this.solutionWeight().edge().alternative());
	}
}
