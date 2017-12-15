package ec.com.orocodigo.OroSoftWeb.enums;

public enum Actividad {
	DEDUCIBLE(1, "CAMARONERA"), NO_DEDUCIBLE(2, "MINERA"), TODOS(3, "COMERCIAL");

	private final Integer id;
	private final String nombre;

	private Actividad(Integer id, String nombre) {
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
