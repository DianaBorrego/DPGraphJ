package equipment_replacement.DP;

import java.util.List;

import utils.Files2;
import utils.List2;

public class Auxiliar3 {
	public static void iniData(String fichero) {
		List<String> lineas = Files2.linesFromFile(fichero);
		for (String linea : lineas) {
			String[] tokens = linea.split(":");
			if (tokens[0].equals("N"))
				EquipReplaceVertex3.N = Integer.parseInt(tokens[1]);
			else if (tokens[0].equals("M"))
				EquipReplaceVertex3.M = Integer.parseInt(tokens[1]);
			else if (tokens[0].equals("e0"))
				EquipReplaceVertex3.e0 = Integer.parseInt(tokens[1]);
			else if (tokens[0].equals("operating costs")) {
				EquipReplaceVertex3.operatingCost = List2.parse(tokens[1].trim().split(","), Integer::parseInt);
			}
			else if (tokens[0].equals("trade-in costs")) {
				EquipReplaceVertex3.tradeinCost = List2.parse(tokens[1].trim().split(","), Integer::parseInt);
			}
			else if (tokens[0].equals("precio"))
				EquipReplaceVertex3.priceNew = Integer.parseInt(tokens[1]);
		}
	
	}

}
