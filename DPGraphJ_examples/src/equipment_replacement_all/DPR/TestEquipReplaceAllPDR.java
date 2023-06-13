package equipment_replacement_all.DPR;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import graphs.virtual.EGraph;
import graphs.virtual.EGraph.Type;
import path.EGraphPath.PathType;

import java.util.Optional;
import java.util.stream.IntStream;

import org.jgrapht.GraphPath;

import graphs.alg.DPRunbounded;

public class TestEquipReplaceAllPDR {

	record TResult(Integer tam, Long t) {
		public static TResult of(Integer tam, Long t) {
			return new TResult(tam, t);
		}

		public String toString() {
			return String.format("%d,%d", tam, t);
		}
	}

	public static void main(String[] args) {

		Locale.setDefault(new Locale("en", "US"));
		Auxiliar2.iniDatos("./files/equip_rep_all_DPR/EqMantMB3.txt"); // CAMBIAR

		List<Integer> tam = List.of(4, 10, 20, 40, 100, 200, 400, 1000, 2000, 4000, 10000, 20000, 40000, 100000);
//		List<Integer> tam = List.of(8,16,25,30,35,40,50,80,100,200,400,1000,2000,4000,10000,20000,40000,100000);
		List<String> metodos = List.of("PDRcota1", "PDRcota1voraz", "PDRsincota", "PDRcota23", "PDRcota23voraz", "PD");
		String metodo = metodos.get(2); // CAMBIAR
		String fichero_salida = String.format("./files/equip_rep_all_DPR/TiemposAllMB3%s", metodo); // CAMBIAR
		List<Long> tiempos_min = new ArrayList<Long>();

		for (int t = 0; t < tam.size() - 1; t++) { // CAMBIAR
			EquipReplaceAllVertexRed.N = tam.get(t);
			long elapsed_min = Long.MAX_VALUE;
			for (int i = 0; i < 50; i++) {
				long start = System.nanoTime();

				EquipReplaceAllVertexRed e1 = EquipReplaceAllVertexRed.initial();

				EGraph<EquipReplaceAllVertexRed, EquipReplaceAllEdgeRed> graph = EGraph
						.virtual(e1, EquipReplaceAllVertexRed.goal(), PathType.Sum, Type.Min)
						.greedyEdge(EquipReplaceAllVertexRed::greedyEdge).heuristic(EquipReplaceHeuristic::heuristic)
						.build();

				GraphPath<EquipReplaceAllVertexRed, EquipReplaceAllEdgeRed> path = null;
				Double bv = null;

				DPRunbounded<EquipReplaceAllVertexRed, EquipReplaceAllEdgeRed, String> ms = DPRunbounded.of(graph, null,
						bv, path, true);
				Optional<GraphPath<EquipReplaceAllVertexRed, EquipReplaceAllEdgeRed>> sp = ms.search();
				GraphPath<EquipReplaceAllVertexRed, EquipReplaceAllEdgeRed> s1 = sp.orElse(path);
				Double optimalWeight = s1.getWeight();

				// some try with nested loops
				long end = System.nanoTime();
				long elapsedTime = end - start;

				// convert to seconds
//				TimeUnit seconds = new TimeUnit(); 

//				TimeUnit.SECONDS.convert(elapsedTime, TimeUnit.NANOSECONDS);
//
//				System.out.println("elapsed: " + elapsedTime + "seconds\n");

				double seconds = (double) elapsedTime / 1_000_000_000.0;

				System.out.println("elapsed: " + seconds + "seconds\n");

				System.out.println(EquipReplaceAllVertexRed.N + " " + optimalWeight);
				String s = EquipReplaceAllVertexRed.getSolucion(s1);
				System.out.println(s);

				// some try with nested loops
				end = System.nanoTime();
				long elapsedTime2 = end - start;

//				System.out.println("elapsed: " + elapsedTime + "nano seconds\n");
//				System.out.println("elapsed: " + elapsedTime2 + "nano seconds\n");

				// convert to seconds
//				TimeUnit seconds = new TimeUnit(); 

//				TimeUnit.SECONDS.convert(elapsedTime, TimeUnit.NANOSECONDS);
				//
//				System.out.println("elapsed: " + elapsedTime + "seconds\n");

				double seconds2 = (double) elapsedTime2 / 1_000_000_000.0;

				System.out.println("elapsed: " + seconds + "seconds\n");
//				System.out.println("elapsed: " + seconds2 + "seconds\n");

				elapsed_min = elapsedTime < elapsed_min ? elapsedTime : elapsed_min;
			}
			System.out.println("elapsed: " + elapsed_min + "nano seconds\n");
			double seconds_min = (double) elapsed_min / 1_000_000_000.0;
			System.out.println("elapsed: " + seconds_min + "seconds\n");

			tiempos_min.add(elapsed_min);
		}

		Resultados.toFile(IntStream.range(0, tam.size() - 1) // CAMBIAR
				.mapToObj(x -> TResult.of(tam.get(x), tiempos_min.get(x))).map(TResult::toString), fichero_salida,
				true);
//		Predicate<EquipReplaceVertexRed> pv = v->ms.optimalPath().get().getVertexList().contains(v);
//		Predicate<EquipReplaceEdgeRed> pe= e->ms.optimalPath().get().getEdgeList().contains(e);
//		
//		GraphColors.toDot(ms.outGraph,"ficheros/EquipReplacePDRGraph8.gv",
//				v->String.format("(%d,%d)",v.i(),v.k()),
//				e->e.action().toString(),
//				v->all(colorIf(Color.red,pv.test(v)),styleIf(Style.bold,pv.test(v))),
//				e->all(colorIf(Color.red,pe.test(e)),styleIf(Style.bold,pe.test(e)))
//				);

	}

}
