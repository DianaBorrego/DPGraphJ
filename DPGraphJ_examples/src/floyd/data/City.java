package floyd.data;

public record City(String nombre, Integer habitantes)  {

	public static City ofFormat(String[] formato) {
		String nombre = formato[0];
		Integer habitantes = Integer.parseInt(formato[1]);
		return new City(nombre,habitantes);
	}
	
	public static City of(String nombre, Integer habitantes) {
		return new City(nombre,habitantes);
	}
	
	@Override
	public String toString() {
		return this.nombre;
	}
}
