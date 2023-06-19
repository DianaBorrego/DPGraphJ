package matrix_mult;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import utils.Files2;

public class Auxiliar2 {
	
	public static void leeFichero(String fichero){
		List<Integer> ls = Files2.streamFromFile(fichero)
				.map(ln->Integer.parseInt(ln))
				.collect(Collectors.toList());
		Integer n = ls.size();
		List<MatrixInf> r = new ArrayList<>();
		for(int i = 0; i<n-1;i++) {
			MatrixInf m = MatrixInf.of(ls.get(i),ls.get(i+1));
			r.add(m);
		}
		MatrixVertex.matrices = r;
		MatrixVertex.n = r.size();
		System.out.println(r);
	}
	
	

}
