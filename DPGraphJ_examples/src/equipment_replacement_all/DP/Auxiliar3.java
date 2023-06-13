package equipment_replacement_all.DP;

import java.util.List;

import utils.Files2;
import utils.List2;


public class Auxiliar3 {
	public static void iniDatos(String fichero) {
		List<String> lineas = Files2.linesFromFile(fichero);
		for (String linea : lineas) {
			String[] tokens = linea.split(":");
			if (tokens[0].equals("N"))
				EquipReplaceVertexAllPD.N = Integer.parseInt(tokens[1]);
			else if (tokens[0].equals("M"))
				EquipReplaceVertexAllPD.M = Integer.parseInt(tokens[1]);
			else if (tokens[0].equals("e0"))
				EquipReplaceVertexAllPD.e0 = Integer.parseInt(tokens[1]);
			else if (tokens[0].equals("operating costs")) {
				EquipReplaceVertexAllPD.operatingCost = List2.parse(tokens[1].trim().split(","), Integer::parseInt);
			}
			else if (tokens[0].equals("trade-in costs")) {
				EquipReplaceVertexAllPD.tradeinCost = List2.parse(tokens[1].trim().split(","), Integer::parseInt);
			}
			else if (tokens[0].equals("precio"))
				EquipReplaceVertexAllPD.priceNew = Integer.parseInt(tokens[1]);
		}
	
	}

}
