package ec.com.orocodigo.OroSoftWeb.enums;

public enum TipoRRHH {
	ANTICIPO(1, "Anticipo", "recursoshumanos.rh_anticipo"), BONO(2, "Bono", "recursoshumanos.rh_bono"), PRESTAMO(3,
			"Prestamo", "recursoshumanos.rh_prestamo"), ROL(2, "Rol", "recursoshumanos.rh_rol"), UTILIDAD(2, "Utilidad",
					"recursoshumanos.rh_utilidades"), XIIISUELDO(2, "XIII Sueldo",
							"recursoshumanos.rh_xiii_sueldo"), XIVSUELDO(2, "XIV Sueldo",
									"recursoshumanos.rh_xiv_sueldo");

	private Integer id;
	private String nombre;
	private String tabla;

	private TipoRRHH(Integer id, String nombre, String tabla) {
		this.id = id;
		this.nombre = nombre;
		this.tabla = tabla;
	}

	public Integer getId() {
		return id;
	}

	public String getNombre() {
		return nombre;
	}

	public String getTabla() {
		return tabla;
	}

	public static TipoRRHH getTipoRRHH(String tabla) {
		for (TipoRRHH tipoRRHH : values())
			if (tipoRRHH.getTabla().compareToIgnoreCase(tabla) == 0)
				return tipoRRHH;
		return null;
	}

}
