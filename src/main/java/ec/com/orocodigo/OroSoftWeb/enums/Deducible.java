package ec.com.orocodigo.OroSoftWeb.enums;

public enum Deducible {
	DEDUCIBLE(1, "DEDUCIBLE"), NO_DEDUCIBLE(2, "NO DEDUCIBLE"), TODOS(3, "TODOS");

	private final Integer id;
	private final String nombre;

	private Deducible(Integer id, String nombre) {
		this.id = id;
		this.nombre = nombre;
	}

	public int getId() {
		return id;
	}

	public String getNombre() {
		return nombre;
	}
}
