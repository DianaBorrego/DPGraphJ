package equipment_replacement_all.DP;

import java.util.List;
import utils.List2;

import hypergraphs.HyperVertex;


public record EquipReplaceVertexAllPD(Integer i,Integer j,Integer k) 
		implements HyperVertex<EquipReplaceVertexAllPD,EquipReplaceEdgeAllPD,Integer,SolStringDouble> {
	
	
	public static EquipReplaceVertexAllPD initial() {	
		return new EquipReplaceVertexAllPD(e0,0,N);
	}
	
	public static EquipReplaceVertexAllPD of(Integer i,Integer j,Integer k) {	
		return new EquipReplaceVertexAllPD(i,j,k);
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
	
	@Override
	public EquipReplaceVertexAllPD me() {
		return this;
	}

	public EquipReplaceVertexAllPD neigbord(Integer i, Integer j, Integer k){
		return new EquipReplaceVertexAllPD(i,j,k);
	}
	
	@Override
	public Boolean isValid() {
		return 0 <= i && i <= M && 0 <= j && j <= M && 0<= k && k <= N;
	}
	@Override
	public List<Integer> alternatives() {
		if (k>1)
			return List2.rangeList(1, M+1);
		else 
			return List.of();
	}
	
	@Override
	public List<EquipReplaceVertexAllPD> neighbors(Integer a) {
		List<EquipReplaceVertexAllPD> r=null;
		if (j==0) {
			r = List.of(this.neigbord(i, a, k));
		} else {
			r = List.of(this.neigbord(i, a, k/2), this.neigbord(a, j, k-k/2));
		}
		return r;
	}
	
	@Override
	public EquipReplaceEdgeAllPD edge(Integer a) {
		return EquipReplaceEdgeAllPD.of(this,this.neighbors(a), a);
	}
	
	@Override
	public Boolean isBaseCase() {
		return  k == 1; 
	}
	
	@Override
	public Double baseCaseSolutionWeight() {
		Double r = null;
		if (j==i+1) {
			r = (double) operatingCost.get(i);
		} else {
			r = (double) (tradeinCost.get(j-1)-tradeinCost.get(i)+operatingCost.get(j-1));
		}
		return r;
	}
	
	@Override
	public SolStringDouble baseCaseSolution() {
		Double weight = baseCaseSolutionWeight();
		String s=j.toString();
		return SolStringDouble.of(s, weight);
	}

}
