package hypergraphs;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public interface GraphTree<V extends HyperVertex<V, E, A, S>, E extends HyperEdge<V, E, A, S>, A, S> {

	public static <V extends HyperVertex<V, E, A, S>, E extends HyperEdge<V, E, A, S>, A, S>
		GraphTree<V, E, A, S> empty(V v) {
		return new GT<>(v, null);
	}

	public static <V extends HyperVertex<V, E, A, S>, E extends HyperEdge<V, E, A, S>, A, S>
		GraphTree<V, E, A, S> of(V v, A a) {
		return new GT<>(v, a);
	}

	public V vertex();

	public A alternative();

	public default E hyperEdge() {
		return vertex().edge(alternative());
	}

	public default Double weight() {
		return this.vertex().solutionWeight().weight();
	}

	public default S solution() {
		return this.vertex().solution();
	}

	public default List<GraphTree<V, E, A, S>> neighbors() {
		if(this.alternative() == null ) return List.of();
		return this.vertex().neighbors(this.alternative()).stream()
				.map(v -> v.isBaseCase() ? GraphTree.empty(v) : GraphTree.of(v, v.solutionWeight().edge().alternative()))
				.toList();
	}

	public default String string() {
		String label;
		if(vertex().isBaseCase()) label = "["+vertex().toString()+"]";
		else {
			label = "["+vertex().toString()+","+alternative().toString()+"]";
			label += neighbors().stream().map(g->g.string())
				.collect(Collectors.joining(",","(",")"));
		}
		return label;
	}

	public default Set<V> vertices(){
		Set<V> r = new HashSet<>();
		r.add(this.vertex());
		for(GraphTree<V,E,A,S> gt: this.neighbors()) {
			r.addAll(gt.vertices());
		}
		return r;
	}

	public default Set<E> hyperEdges(){
		Set<E> r = new HashSet<>();
		for(V v: vertices()) {
			if (!v.isBaseCase()) {
				GraphTree<V, E, A, S> gt = v.graphTree();
				r.add(gt.hyperEdge());
			}
		}
		return r;
	}

	public static record GT<V extends HyperVertex<V, E, A, S>, E extends HyperEdge<V, E, A, S>, A, S> (
			V vertex, A alternative) implements GraphTree<V, E, A, S> {
	}
}
