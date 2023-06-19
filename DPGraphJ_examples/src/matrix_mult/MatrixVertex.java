package matrix_mult;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import hypergraphs.HyperVertex;
	
public record MatrixVertex(Integer i,Integer j) 
		implements HyperVertex<MatrixVertex,MatrixEdge,Integer,String>{

		
		public static MatrixVertex of(Integer i, Integer j) {
			return new MatrixVertex(i, j);
		}
		
		public static MatrixVertex initial() {
			return new MatrixVertex(0, n);
		}
		
		public static List<MatrixInf> matrices;
		public static Integer n;

		
		@Override
		public MatrixVertex me() {
			return this;
		}

		@Override
		public Boolean isValid() {
			return i>=0 && i<=n && j>=i && j<=n;
		}

		@Override
		public List<Integer> alternatives() {
			return IntStream.range(i+1,j).boxed().collect(Collectors.toList());
		}

		@Override
		public Boolean isBaseCase() {
			return j-i < 3;
		}

		@Override
		public Double baseCaseSolutionWeight() {
			Integer d = j-i;
			return switch(d) {
			case 0 -> 0.; 
			case 1 -> 0.; 
			case 2 -> (double) matrices.get(i).nf*matrices.get(i).nc*matrices.get(i+1).nc; 
			default -> null;
			};
		}
		
		@Override
		public String baseCaseSolution() {
			Integer d = j-i;
			return switch(d) {
			case 0 -> ""; 
			case 1 -> MatrixVertex.matrices.get(i()).toString();
			case 2 -> String.format("(%s * %s)",MatrixVertex.matrices.get(i).toString(),
					MatrixVertex.matrices.get(i+1).toString());
			default -> null;
			};
		}

		@Override
		public List<MatrixVertex> neighbors(Integer a) {
			return Arrays.asList(MatrixVertex.of(i, a),MatrixVertex.of(a, j));
		}

		@Override
		public MatrixEdge edge(Integer a) {
			return MatrixEdge.of(this, this.neighbors(a),a);
		}

		@Override
		public String toString() {
			return String.format("(%d,%d)",i,j);
		}
	
}
