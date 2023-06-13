package resources_allocation.DP;

import java.util.List;
import java.util.stream.IntStream;


import utils.Files2;
import utils.List2;


public class Auxiliar {
	public static void iniDatos(String fichero) {
		List<String> lineas = Files2.linesFromFile(fichero);
		for (String linea : lineas) {
			String[] tokens = linea.split(":");
			if (tokens[0].equals("N"))
				ResAlloOneVertexPD.N = Integer.parseInt(tokens[1]);
			else if (tokens[0].equals("X"))
				ResAlloOneVertexPD.X = Integer.parseInt(tokens[1]);
			else if (tokens[0].equals("return data")) {
				ResAlloOneVertexPD.rdata = List2.parse(tokens[1].trim().split(","), Integer::parseInt);
			}
		}
	
	}

	// genera para rdata una función no decreciente f(x), con:
	// f(0) = 0
	// f'(X) = 0
	// f(X) = KX
	// g(x) = f(x)/x, tal que g'(M) = 0 
	// => M<3X/4
	public static void iniDatos(Integer X, Integer N, Integer M, float K) {
		ResAlloOneVertexPD.N = N;
		ResAlloOneVertexPD.X = X;
		// f(x) = -ax^3+bx^2+cx
		float a = K/(2*X*X-2*M*X);
		float b = 2*a*M;
		float c = -a*X*X+2*K;
		ResAlloOneVertexPD.rdata = IntStream.rangeClosed(0, X)
				.map(x -> Math.round(-a*x*x*x + b*x*x + c*x))
				.boxed().toList();
		System.out.println("M->"+ResAlloOneVertexPD.rdata.get(M));
		System.out.println("X->"+ResAlloOneVertexPD.rdata.get(X));
		
		
	}

}
