package utils;

public record Enumerate<E>(Integer counter, E value) {

	public static <E> Enumerate<E> of(Integer num, E value) {
		return new Enumerate<>(num, value);
	}

	@Override
	public String toString() {
		return String.format("(%d,%s)", counter(), value().toString());
	}

}