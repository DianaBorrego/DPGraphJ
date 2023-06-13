package equipment_replacement_all.DP;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.stream.IntStream;

import hypergraphs.GraphTree;


public class TestEquipReplaceAllPD {
	
	record TResult(Integer tam, Long t) {
		public static TResult of(Integer tam, Long t){
			return new TResult(tam, t);
		}
		
		public String toString() {
			return String.format("%d,%d", tam, t);
		}
	}

	
	public static void main(String[] args) {
	
		Locale.setDefault(new Locale("en", "US"));
		Auxiliar3.iniDatos("./files/equip_rep_all_DP/EqMantMC15.txt");  //CAMBIAR

		List<Integer> tam = List.of(4,10,20,40,100,200,400,1000,2000,4000,10000,20000,40000,100000);
//		List<Integer> tam = List.of(8,16,25,30,35,40,50,80,100,200,400,1000,2000,4000,10000,20000,40000,100000);
		List<String> metodos = List.of("PDRcota1","PDRcota1voraz","PDRsincota","PDRcota23","PDRcota23voraz","PD");
		String metodo = metodos.get(5);
		String fichero_salida = String.format("./files/equip_rep_all_DP/TiemposAllMC15%s", metodo); //CAMBIAR
		List<Long> tiempos_min = new ArrayList<Long>();
		
for (int t1=0;t1<tam.size();t1++) {
	EquipReplaceVertexAllPD.N = tam.get(t1);

		long elapsed_min = Long.MAX_VALUE;
		for (int i=0;i<50;i++) {
			long start = System.nanoTime(); 

		
			EquipReplaceVertexAllPD p = EquipReplaceVertexAllPD.initial();
			SolStringDouble s = p.solution();

			//some try with nested loops 
			long end = System.nanoTime(); 
			long elapsedTime = end - start;

			System.out.println("elapsed: " + elapsedTime + "nano seconds\n");

		//convert to seconds 
//		TimeUnit seconds = new TimeUnit(); 
		
//		TimeUnit.SECONDS.convert(elapsedTime, TimeUnit.NANOSECONDS);
//
//		System.out.println("elapsed: " + elapsedTime + "seconds\n");

			double seconds = (double)elapsedTime / 1_000_000_000.0;
		
			System.out.println("elapsed: " + seconds + "seconds\n");
		
		
			System.out.println(s.weight().toString());
			System.out.println(s.s());

			//some try with nested loops 
			end = System.nanoTime(); 
			long elapsedTime2 = end - start;

			System.out.println("elapsed: " + elapsedTime + "nano seconds\n");
			System.out.println("elapsed: " + elapsedTime2 + "nano seconds\n");

		//convert to seconds 
//		TimeUnit seconds = new TimeUnit(); 
		
//		TimeUnit.SECONDS.convert(elapsedTime, TimeUnit.NANOSECONDS);
//
//		System.out.println("elapsed: " + elapsedTime + "seconds\n");

			double seconds2 = (double)elapsedTime2 / 1_000_000_000.0;
			
			System.out.println("elapsed: " + seconds + "seconds\n");
			System.out.println("elapsed: " + seconds2 + "seconds\n");

		
			GraphTree<EquipReplaceVertexAllPD, EquipReplaceEdgeAllPD, Integer, SolStringDouble> t = p.graphTree();
			System.out.println(t.toString());

			
			elapsed_min = elapsedTime<elapsed_min ? elapsedTime : elapsed_min;
		}	
		System.out.println("elapsed: " + elapsed_min + "nano seconds\n");
		double seconds_min = (double)elapsed_min / 1_000_000_000.0;
		System.out.println("elapsed: " + seconds_min + "seconds\n");

		tiempos_min.add(elapsed_min);
	}

	Resultados.toFile(IntStream.range(0, tam.size())
			.mapToObj(x->TResult.of(tam.get(x), tiempos_min.get(x)))
			.map(TResult::toString),
			fichero_salida, true);

//			SimpleDirectedGraph<Union<EquipReplaceVertex3, EquipReplaceEdge3>, DefaultEdge> g = p.datos().graph();
//		
//			Datos.toDotHypergraph(g, "ficheros/hiperEquipReplace8.gv", p);
//			Datos.toDotAndOr(g, "ficheros/andOrEquipReplace8.gv", p);

	
		

	
	}

}
