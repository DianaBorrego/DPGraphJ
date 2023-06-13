package resources_allocation.DP;

public record SolStringDouble(String s, Double weight) {
	public static SolStringDouble of(String s, Double weight) {
		return new SolStringDouble(s, weight);
	}

}
