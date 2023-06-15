package equipment_replacement.DP;

import java.util.List;

import hypergraphs.HyperVertex;
import utils.List2;


public record EquipReplaceVertex3(Integer i,Integer j,Integer k) 
		implements HyperVertex<EquipReplaceVertex3,EquipReplaceEdge3,Integer,SolStringDouble> {
	
	
	public static EquipReplaceVertex3 initial() {	
		return new EquipReplaceVertex3(e0,0,N);
	}
	
	public static EquipReplaceVertex3 of(Integer i,Integer j,Integer k) {	
		return new EquipReplaceVertex3(i,j,k);
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
	public EquipReplaceVertex3 me() {
		return this;
	}

	public EquipReplaceVertex3 neighbor(Integer i, Integer j, Integer k){
		return new EquipReplaceVertex3(i,j,k);
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
	public List<EquipReplaceVertex3> neighbors(Integer a) {
		List<EquipReplaceVertex3> r=null;
		if (j==0) {
			r = List.of(this.neighbor(i, a, k));
		} else {
			r = List.of(this.neighbor(i, a, k/2), this.neighbor(a, j, k-k/2));
		}
		return r;
	}
	
	@Override
	public EquipReplaceEdge3 edge(Integer a) {
		return EquipReplaceEdge3.of(this,this.neighbors(a), a);
	}
	
	@Override
	public Boolean isBaseCase() {
		return  k == 1; 
	}
	
	@Override
	public Double baseCaseSolutionWeight() {
		Double r = null;
		if (j==1) {
			r = (double) (tradeinCost.get(0)-tradeinCost.get(i)+operatingCost.get(0));
		} else if (j==i+1) {
			r = (double) operatingCost.get(i);
		}
		return r;
	}
	
	@Override
	public SolStringDouble baseCaseSolution() {
		Double weight = baseCaseSolutionWeight();
		String s=null;
		if (j==1) {
			s="1";
		} else if (j==i+1) {
			s=j.toString();
		}
		return SolStringDouble.of(s, weight);
	}

}
