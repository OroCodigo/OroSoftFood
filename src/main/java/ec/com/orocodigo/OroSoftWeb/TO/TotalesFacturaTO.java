package ec.com.orocodigo.OroSoftWeb.TO;

import java.io.Serializable;
import java.math.BigDecimal;

public class TotalesFacturaTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private BigDecimal subtotal_0;
	private BigDecimal subtotal_12;
	private BigDecimal iva;
	private BigDecimal total;

	public TotalesFacturaTO() {

	}

	public BigDecimal getSubtotal_0() {
		return subtotal_0;
	}

	public void setSubtotal_0(BigDecimal subtotal_0) {
		this.subtotal_0 = subtotal_0;
	}

	public BigDecimal getSubtotal_12() {
		return subtotal_12;
	}

	public void setSubtotal_12(BigDecimal subtotal_12) {
		this.subtotal_12 = subtotal_12;
	}

	public BigDecimal getIva() {
		return iva;
	}

	public void setIva(BigDecimal iva) {
		this.iva = iva;
	}

	public BigDecimal getTotal() {
		return total;
	}

	public void setTotal(BigDecimal total) {
		this.total = total;
	}

}
