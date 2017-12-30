package ec.com.orocodigo.OroSoftWeb.TO;

public class NumeracionTipoDocumentoVenta {
	private String codigoTipoDocumento;
	private String parteEstatica;
	private Integer secuenciaNumeroFactura;

	public NumeracionTipoDocumentoVenta() {
	}

	public NumeracionTipoDocumentoVenta(final String codigoTipoDocumento, final String parteEstatica,
			final Integer secuenciaNumeroFactura) {
		this.codigoTipoDocumento = codigoTipoDocumento;
		this.parteEstatica = parteEstatica;
		this.secuenciaNumeroFactura = secuenciaNumeroFactura;
	}

	public String getCodigoTipoDocumento() {
		return codigoTipoDocumento;
	}

	public String getParteEstatica() {
		return parteEstatica;
	}

	public Integer getSecuenciaNumeroFactura() {
		return secuenciaNumeroFactura;
	}

	public void setCodigoTipoDocumento(final String codigoTipoDocumento) {
		this.codigoTipoDocumento = codigoTipoDocumento;
	}

	public void setParteEstatica(final String parteEstatica) {
		this.parteEstatica = parteEstatica;
	}

	public void setSecuenciaNumeroFactura(final Integer secuenciaNumeroFactura) {
		this.secuenciaNumeroFactura = secuenciaNumeroFactura;
	}
}
