package equipment_replacement.DPR;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import org.jgrapht.GraphPath;
import graphs.virtual.VirtualVertex;

// i - edad etapa actual
// k - periodos que faltan
public record EquipReplaceVertexRed(Integer i, Integer k) 
		implements VirtualVertex<EquipReplaceVertexRed,EquipReplaceEdgeRed,Integer> {
	
	
	public static EquipReplaceVertexRed initial() {	
		return new EquipReplaceVertexRed(e0,N);
	}
	
	public static EquipReplaceVertexRed endVertex() {	
		return new EquipReplaceVertexRed(0,0);
	}
	
	public static EquipReplaceVertexRed copy(EquipReplaceVertexRed v) {
		return of(v.i, v.k);
	}
	
	public static EquipReplaceVertexRed of(Integer i,Integer k) {	
		return new EquipReplaceVertexRed(i,k);
	}

	public static Predicate<EquipReplaceVertexRed> goal() {
		return v->(v.k == 0 && v.i == 0);
	}
	
	//número de periodos
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
	
	public static String getSolucion(GraphPath<EquipReplaceVertexRed, EquipReplaceEdgeRed> path){
		return EquipReplaceVertexRed.getSolucion(path.getEdgeList());
	}

	public static String getSolucion(List<EquipReplaceEdgeRed> ls){
		String s = "";
		s = ls.stream().map(e->e.target().i().toString()).collect(Collectors.joining("->","",""));
		return s;
	}

	@Override
	public Boolean isValid() {
		return 0 <= i && i <= M && 0<= k && k <= N;
	}
	
	public EquipReplaceEdgeRed greedyEdge() {
		Integer a = (k==0)? 0 : (i<M?i+1:1);
		return EquipReplaceEdgeRed.of(this,this.neighbor(a), a);
	}
	
	public Integer greedyAction() {
		return (k==0)? 0 : (i<M?i+1:1);
	}
	
	@Override
	public EquipReplaceVertexRed neighbor(Integer a) {
		if (a>0)
			return EquipReplaceVertexRed.of(a, k-1);
		else
			return EquipReplaceVertexRed.of(a, 0);
	}

	@Override
	public List<Integer> actions() {
		if (k>0)
			return i<M ? List.of(1, i+1) : List.of(1);
		else if (i>0)
			return List.of(0);
		else
			return List.of();
	}
	
	@Override
	public EquipReplaceEdgeRed edge(Integer a) {
		return EquipReplaceEdgeRed.of(this,this.neighbor(a), a);
	}
	

}
