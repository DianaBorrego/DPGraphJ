package matrix_mult;

import java.util.List;

import hypergraphs.HyperEdge;

public record MatrixEdge(MatrixVertex source,List<MatrixVertex> targets,Integer alternative) 
           implements HyperEdge<MatrixVertex,MatrixEdge,Integer,String> {
	
	public static MatrixEdge of(MatrixVertex source, List<MatrixVertex> targets, Integer action) {
		return new MatrixEdge(source, targets, action);
	}
	
	public MatrixEdge me() {
		return this;
	}

	@Override
	public Double solutionWeight(List<Double> solutions) {		
		Double weight = solutions.get(0)+solutions.get(1);
		Integer i = source.i();
		Integer a = alternative();
		Integer j = source.j();
		weight += MatrixVertex.matrices.get(i).nf*MatrixVertex.matrices.get(a).nf*MatrixVertex.matrices.get(j-1).nc;
		return weight;
	}

	@Override
	public String solution(List<String> solutions) {
		return String.format("(%s) * (%s)",solutions.get(0),solutions.get(1));
	}

	@Override
	public String toString() {
		return this.alternative().toString();
	}
	
	

}
