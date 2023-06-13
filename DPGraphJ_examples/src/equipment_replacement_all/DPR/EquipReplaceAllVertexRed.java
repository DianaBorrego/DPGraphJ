package equipment_replacement_all.DPR;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.jgrapht.GraphPath;
import graphs.virtual.VirtualVertex;

// i - edad etapa actual
// k - periodos que faltan
public record EquipReplaceAllVertexRed(Integer i, Integer k) 
		implements VirtualVertex<EquipReplaceAllVertexRed,EquipReplaceAllEdgeRed,Integer> {
	
	
	public static EquipReplaceAllVertexRed initial() {	
		return new EquipReplaceAllVertexRed(e0,N);
	}
	
	public static EquipReplaceAllVertexRed endVertex() {	
		return new EquipReplaceAllVertexRed(0,0);
	}
	
	public static EquipReplaceAllVertexRed copy(EquipReplaceAllVertexRed v) {
		return of(v.i, v.k);
	}
	
	public static EquipReplaceAllVertexRed of(Integer i,Integer k) {	
		return new EquipReplaceAllVertexRed(i,k);
	}

	public static Predicate<EquipReplaceAllVertexRed> goal() {
		return v->(v.k == 0 && v.i == 0);
	}
	
	//nuúmero de periodos
	public static int N;
	//edad máxima
	public static int M;
	//edad inicial
	public static int e0;
	//coste mantenimiento según edad
	public static List<Integer> operatingCost;
	//coste intercambio por nuevo según edad
	public static List<Integer> tradeinCost;
	//precio nuevo equipo
	public static int priceNew;
	
	public static String getSolucion(GraphPath<EquipReplaceAllVertexRed, EquipReplaceAllEdgeRed> path){
		return EquipReplaceAllVertexRed.getSolucion(path.getEdgeList());
	}

	public static String getSolucion(List<EquipReplaceAllEdgeRed> ls){
		String s = "";
		s = ls.stream().map(e->e.target().i().toString()).collect(Collectors.joining("->","",""));
		return s;
	}

	@Override
	public Boolean isValid() {
		return 0 <= i && i <= M && 0<= k && k <= N;
	}
	
	public EquipReplaceAllEdgeRed greedyEdge() {
		Integer a = (k==0)? 0 : (i<M?i+1:1);
		return EquipReplaceAllEdgeRed.of(this,this.neighbor(a), a);
	}
	
	public Integer greedyAction() {
		return (k==0)? 0 : (i<M?i+1:1);
	}
	
	@Override
	public EquipReplaceAllVertexRed neighbor(Integer a) {
		if (a>0)
			return EquipReplaceAllVertexRed.of(a, k-1);
		else
			return EquipReplaceAllVertexRed.of(a, 0);
	}

	@Override
	public List<Integer> actions() {
		if (k>0)
			return IntStream.rangeClosed(1,M).boxed().toList();
		else if (i>0)
			return List.of(0);
		else
			return List.of();
	}
	
	@Override
	public EquipReplaceAllEdgeRed edge(Integer a) {
		return EquipReplaceAllEdgeRed.of(this,this.neighbor(a), a);
	}
	

}
