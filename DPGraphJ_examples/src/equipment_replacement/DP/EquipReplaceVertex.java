package equipment_replacement.DP;

import java.util.List;

import hypergraphs.HyperVertex;
import utils.List2;


public record EquipReplaceVertex(Integer i,Integer j,Integer k) 
		implements HyperVertex<EquipReplaceVertex,EquipReplaceEdge,Integer,SolStringDouble> {
	
	
	public static EquipReplaceVertex initial() {	
		return new EquipReplaceVertex(e0,0,N);
	}
	
	public static EquipReplaceVertex of(Integer i,Integer j,Integer k) {	
		return new EquipReplaceVertex(i,j,k);
	}

	//number of periods
	public static int N;
	//maximum age
	public static int M;
	//initial age
	public static int e0;
	//maintenance cost according to age
	public static List<Integer> operatingCost;
	//cost to exchange for a new one according to age
	public static List<Integer> tradeinCost;
	//price of new equipment
	public static int priceNew;
	
	@Override
	public EquipReplaceVertex me() {
		return this;
	}

	public EquipReplaceVertex neighbor(Integer i, Integer j, Integer k){
		return new EquipReplaceVertex(i,j,k);
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
	public List<EquipReplaceVertex> neighbors(Integer a) {
		List<EquipReplaceVertex> r=null;
		if (j==0) {
			r = List.of(this.neighbor(i, a, k));
		} else {
			r = List.of(this.neighbor(i, a, k/2), this.neighbor(a, j, k-k/2));
		}
		return r;
	}
	
	@Override
	public EquipReplaceEdge edge(Integer a) {
		return EquipReplaceEdge.of(this,this.neighbors(a), a);
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
