package floyd.data;



public record Road(Integer id, Double km, String nombre) {

	private static int num =0;
	
	public static Road ofFormat(String[] formato) {
		Double km = Double.parseDouble(formato[3]);
		String nomb = formato[2];		
		Integer id = num;
		num++;
		return new Road(id, km, nomb);
	}
	
	public static Road of(Double kms) {
		Double km = kms;
		String nomb = null;		
		Integer id = num;
		num++;
		return new Road(id, km, nomb);
	}

	@Override
	public String toString() {
		String nn = this.nombre==null?"":this.nombre+",";
		return nn+this.km+")";
	}
	
}
