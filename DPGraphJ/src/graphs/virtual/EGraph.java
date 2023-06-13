package graphs.virtual;

import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

import org.jgrapht.Graph;

import path.EGraphPath;
import path.EGraphPath.PathType;
import utils.TriFunction;

public interface EGraph<V, E> extends Graph<V, E> {

	public static <G extends Graph<V,E>, V,E> EGraphBuilder<V, E> ofGraph(G graph){
		return new EGraphBuilderReal<>(graph);
	}

	public static <G extends Graph<V, E>, V, E> EGraphBuilder<V, E> ofGraph(G graph, V startVertex, Predicate<V> goal) {
		return new EGraphBuilderReal<>(graph, startVertex, goal);
	}

	public static <G extends Graph<V,E>, V,E>
		EGraphBuilder<V, E> ofGraph(G graph,V startVertex,Predicate<V> goal,PathType pathType,Type type){
		return new EGraphBuilderReal<>(graph,startVertex,goal,pathType,type);
	}

	public static <V extends VirtualVertex<V,E,?>, E extends SimpleEdgeAction<V,?>> EGraphBuilder<V, E> virtual(){
		return new EGraphBuilderVirtual<>();
	}

	public static <V extends VirtualVertex<V, E, ?>, E extends SimpleEdgeAction<V, ?>> EGraphBuilder<V, E> virtual(
			V startVertex, Predicate<V> goal) {
		return new EGraphBuilderVirtual<>(startVertex, goal);
	}

	public static <V extends VirtualVertex<V,E,?>, E extends SimpleEdgeAction<V,?>>
		EGraphBuilder<V, E> virtual(V startVertex,Predicate<V> goal,PathType pathType,Type type){
		return new EGraphBuilderVirtual<>(startVertex,goal,pathType,type);
	}

	double getVertexPassWeight(V vertex, E edgeIn, E edgeOut);

	double getVertexWeight(V vertex);

	List<E> edgesListOf(V v);

	EGraphPath<V, E> initialPath();

	V oppositeVertex(E edge, V v);

	V startVertex();

	Predicate<V> goal();

	V endVertex();

	Predicate<V> goalHasSolution();

	PathType pathType();

	Function<V, E> greedyEdge();

	TriFunction<V, Predicate<V>, V, Double> heuristic();

	public static enum Type{Min,Max,All,One}

	Type type();

	Integer solutionNumber();

	public default Double add(V vertexActual, Double acumulateValue, E nextEdge, E lastEdge) {
		return this.initialPath().add(vertexActual, acumulateValue, nextEdge, lastEdge);
	}

	public default Double boundedValue(V vertexActual, Double acumulateValue, E edge) {
		return this.initialPath().boundedValue(vertexActual, acumulateValue, edge, this.goal(), this.endVertex(),
				this.heuristic());
	}

	public default Double estimatedWeightToEnd(V vertexActual, Double acumulateValue) {
		return this.initialPath().estimatedWeightToEnd(vertexActual, acumulateValue, this.goal(), this.endVertex(),
				this.heuristic());
	}

	public default Double goalSolution(V vertexActual) {
		return pathType().equals(PathType.Sum) ? 0. : getVertexWeight(vertexActual);
	}

	public default Double fromNeighbordSolution(V vertexActual, Double weight, E edge, E lastEdge) {
		 return pathType().equals(PathType.Sum) ? initialPath().add(vertexActual, weight, edge, lastEdge) : weight;
	}
}
