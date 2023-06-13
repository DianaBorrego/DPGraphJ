package equipment_replacement_all.DPR;

import java.util.List;

import utils.Files2;
import utils.List2;


public class Auxiliar2 {
	public static void iniDatos(String fichero) {
		List<String> lineas = Files2.linesFromFile(fichero);
		for (String linea : lineas) {
			String[] tokens = linea.split(":");
			if (tokens[0].equals("N"))
				EquipReplaceAllVertexRed.N = Integer.parseInt(tokens[1]);
			else if (tokens[0].equals("M"))
				EquipReplaceAllVertexRed.M = Integer.parseInt(tokens[1]);
			else if (tokens[0].equals("e0"))
				EquipReplaceAllVertexRed.e0 = Integer.parseInt(tokens[1]);
			else if (tokens[0].equals("operating costs")) {
				EquipReplaceAllVertexRed.operatingCost = List2.parse(tokens[1].trim().split(","), Integer::parseInt);
			}
			else if (tokens[0].equals("trade-in costs")) {
				EquipReplaceAllVertexRed.tradeinCost = List2.parse(tokens[1].trim().split(","), Integer::parseInt);
			}
			else if (tokens[0].equals("precio"))
				EquipReplaceAllVertexRed.priceNew = Integer.parseInt(tokens[1]);
		}
	
	}

}
