package resources_allocation.DPR;


import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.jgrapht.GraphPath;
import graphs.virtual.VirtualVertex;

// x - cantidad de recursos
// k - actividad actual
// repartir x entre las actividades k..N-1
public record ResAlloOneVertex(Integer x, Integer k) 
		implements VirtualVertex<ResAlloOneVertex,ResAlloOneEdge,Integer> {
	
	
	public static ResAlloOneVertex initial() {	
		return new ResAlloOneVertex(X,0);
	}
	
	public static ResAlloOneVertex copy(ResAlloOneVertex v) {
		return of(v.x, v.k);
	}
	
	public static ResAlloOneVertex of(Integer x,Integer k) {	
		return new ResAlloOneVertex(x,k);
	}

	public static Predicate<ResAlloOneVertex> goal() {
		return v->v.k == N;
	}
	
	//nuúmero de actividades
	public static int N;
	//Cantidad de recursos
	public static int X;
	//retorno de actividad según cantidad de recurso
	public static List<Integer> rdata;
	
	public static String getSolucion(GraphPath<ResAlloOneVertex, ResAlloOneEdge> path){
		return ResAlloOneVertex.getSolucion(path.getEdgeList());
	}

	public static String getSolucion(List<ResAlloOneEdge> ls){
		String s = "";
		s = ls.stream().map(e->e.action().toString()).collect(Collectors.joining(", ","",""));
		return s;
	}

	@Override
	public Boolean isValid() {
		return 0 <= x && x <= X && 0<= k && k <= N;
	}
	
	public ResAlloOneEdge greedyEdge() {
		Integer a = x;
		return ResAlloOneEdge.of(this,this.neighbor(a), a);
	}
	
	public Integer greedyAction() {
		return x;
	}
	
//	public Double heuristicAction() {
//		return (double) greedyAction();
//	}

	@Override
	public ResAlloOneVertex neighbor(Integer a) {
		return ResAlloOneVertex.of(x-a, k+1);
	}

	@Override
	public List<Integer> actions() {
		List<Integer> ls;
		if (k==N)
			ls = List.of();
		else if (k==N-1)
			ls = List.of(x);
		else
			ls = IntStream.rangeClosed(0, x).boxed().toList(); 
		return ls;	
	}
	
	@Override
	public ResAlloOneEdge edge(Integer a) {
		return ResAlloOneEdge.of(this,this.neighbor(a), a);
	}
	

}
