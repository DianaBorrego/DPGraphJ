package hypergraphs;

import java.util.List;
import graphs.alg.DP.Sp;

/**
 * 
 * <p> Interface of a HyperEdge, to model relations between a problem and its neighbor 
 * subproblems, reached by taking a concrete alternative.
 * </p>
 *
 * @param <V> type of the vertices
 * @param <E> type of the edges
 * @param <A> type of the alternatives
 * @param <S> type of the solution
 */

public interface HyperEdge<V extends HyperVertex<V, E, A, S>,
		E extends HyperEdge<V,E,A,S>, A, S> {
	
	/**
	 * 
	 * @return Source vertex of the edge
	 */
	V source();
	
	/**
	 * 
	 * @return Alternative that led to taking the edge
	 */
	A alternative();
	
	/**
	 * 
	 * @param solutions List of solutions of the subproblems
	 * @return Weight associated with the solution of the problem that corresponds 
	 * to the source vertex, combining the weights of the solutions of the subproblems
	 */
	Double solutionWeight(List<Double> solutions);
	
	/**
	 * 
	 * @param solutions List of solutions of the subproblems
	 * @return Solution to the problem by combining the solutions of the subproblem
	 */
	S solution(List<S> solutions);
	
	/**
	 * 
	 * @return This edge
	 */
	E me();
	
	/**
	 * 
	 * @return List of vertices target of the hyperedge
	 */
	default List<V> targets() {
		return this.source().neighbors(this.alternative());
	}
	
	/**
	 * 
	 * @return Partial solution of the subproblems target of the edge
	 */
	default Sp<E> solutionWeight() {
		List<Sp<E>> ls = this.targets().stream().map(v->v.solutionWeight()).toList();
		if(ls.contains(null)) return null;
		else {
			Double weight = this.solutionWeight(ls.stream().map(e->e.weight()).toList());
			return Sp.of(weight,me());
		}
	}
	
	/**
	 * 
	 * @return Solution of the subproblems target of the edge
	 */
	default S solution() {
		return solution(this.targets().stream().map(v->v.solution()).toList());
	}
}
