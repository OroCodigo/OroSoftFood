package ec.com.orocodigo.OroSoftWeb.controller.inventario;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import ec.com.orocodigo.OroSoftUtils.UtilsValidacion;
import ec.com.orocodigo.OroSoftUtils.inventario.TO.InvFunListadoProductosTO;
import ec.com.orocodigo.OroSoftUtils.inventario.TO.InvProductoCategoriaTO;
import ec.com.orocodigo.OroSoftUtils.inventario.TO.InvVentasDetalleTO;
import ec.com.orocodigo.OroSoftUtils.inventario.entity.InvMenuComida;
import ec.com.orocodigo.OroSoftUtils.sistema.entity.SisEmpresa;
import ec.com.orocodigo.OroSoftWeb.TO.TotalesFacturaTO;
import ec.com.orocodigo.OroSoftWeb.delegate.InventarioDelegate;
import ec.com.orocodigo.OroSoftWeb.delegate.SistemaDelegate;
import ec.com.orocodigo.OroSoftWeb.utils.UtilAplication;

@Controller
@Scope("session")
public class VentaRestaurantBean {
	private static final long serialVersionUID = 1L;

	private List<SisEmpresa> listEmpresas;
	private String empresa;

	private List<InvMenuComida> listInvMenuComida;

	private List<InvProductoCategoriaTO> listInvProductoCategoriaTO;

	List<InvFunListadoProductosTO> listaProductos;
	private String categoria;

	private List<InvVentasDetalleTO> listaInvVentasDetalleTO;
	private InvVentasDetalleTO invVentasDetalleTO;

	private TotalesFacturaTO totalesFacturaTO;

	public VentaRestaurantBean() {
	}

	@PostConstruct
	public void init() {
		limpiar();
	}

	public void limpiar() {
		listEmpresas = SistemaDelegate.getInstance().getEmpresasPorUsuarioItem();
		empresa = null;

		listInvMenuComida = InventarioDelegate.getInstance().getMenuComidaTO("ORC");
		listaInvVentasDetalleTO = new ArrayList<InvVentasDetalleTO>();
		invVentasDetalleTO = new InvVentasDetalleTO();

		totalesFacturaTO = new TotalesFacturaTO();
	}

	public void buscarCategorias(String codigoMenuComida) {
		listInvProductoCategoriaTO = InventarioDelegate.getInstance().getInvProductoCategoria("ORC", codigoMenuComida);
		listaProductos = new ArrayList<InvFunListadoProductosTO>();
	}

	public void buscarProductos(String codigoCategoria) {
		categoria = codigoCategoria;
		UtilAplication.presentaMensaje(FacesMessage.SEVERITY_INFO, categoria);
		listaProductos = InventarioDelegate.getInstance().getInvFunListadoProductosTO("ORC", categoria, null);
	}

	public void agregarProducto(InvFunListadoProductosTO producto) {
		invVentasDetalleTO = new InvVentasDetalleTO();
		invVentasDetalleTO.setBodCodigo("10");
		invVentasDetalleTO.setBodEmpresa("ORC");
		invVentasDetalleTO.setDetCantidad(new BigDecimal("1.00"));
		invVentasDetalleTO.setDetPrecio(producto.getPrdPrecio1());
		invVentasDetalleTO.setProCodigoPrincipal(producto.getPrdCodigoPrincipal());
		invVentasDetalleTO.setProEmpresa("ORC");
		invVentasDetalleTO.setProNombre(producto.getPrdNombre());
		invVentasDetalleTO.setSecEmpresa("ORC");
		invVentasDetalleTO.setSecCodigo("ORC");
		listaInvVentasDetalleTO.add(invVentasDetalleTO);
		calcularTotales(listaInvVentasDetalleTO);
	}

	public void eliminarFila(InvVentasDetalleTO ivd) {
		if (listaInvVentasDetalleTO.size() > 1) {
			int idx = listaInvVentasDetalleTO.indexOf(ivd);
			listaInvVentasDetalleTO.remove(idx);
			if (idx >= listaInvVentasDetalleTO.size())
				idx = listaInvVentasDetalleTO.size() - 1;
		}
		calcularTotales(listaInvVentasDetalleTO);
	}

	public void calcularTotales(List<InvVentasDetalleTO> listaInvVentasDetalleTO) {
		totalesFacturaTO.setSubtotal_12(new BigDecimal("0.00"));
		totalesFacturaTO.setSubtotal_0(new BigDecimal("0.00"));
		totalesFacturaTO.setIva(new BigDecimal("0.00"));
		totalesFacturaTO.setTotal(new BigDecimal("0.00"));

		for (InvVentasDetalleTO ivdto : listaInvVentasDetalleTO) {
			totalesFacturaTO.setSubtotal_12(totalesFacturaTO.getSubtotal_12().add(ivdto.getDetPrecio()));
		}

		totalesFacturaTO.setIva(totalesFacturaTO.getSubtotal_12().multiply(new BigDecimal("0.12")));
		totalesFacturaTO.setTotal(UtilsValidacion.redondeoDecimalBigDecimal(
				totalesFacturaTO.getSubtotal_12().add(totalesFacturaTO.getIva()), 2, RoundingMode.HALF_UP));

	}

	// GETTERS Y SETTERS

	public List<SisEmpresa> getListEmpresas() {
		return listEmpresas;
	}

	public void setListEmpresas(List<SisEmpresa> listEmpresas) {
		this.listEmpresas = listEmpresas;
	}

	public String getEmpresa() {
		return empresa;
	}

	public void setEmpresa(String empresa) {
		this.empresa = empresa;
	}

	public List<InvFunListadoProductosTO> getListaProductos() {
		return listaProductos;
	}

	public void setListaProductos(List<InvFunListadoProductosTO> listaProductos) {
		this.listaProductos = listaProductos;
	}

	public List<InvMenuComida> getListInvMenuComida() {
		return listInvMenuComida;
	}

	public void setListInvMenuComida(List<InvMenuComida> listInvMenuComida) {
		this.listInvMenuComida = listInvMenuComida;
	}

	public List<InvProductoCategoriaTO> getListInvProductoCategoriaTO() {
		return listInvProductoCategoriaTO;
	}

	public void setListInvProductoCategoriaTO(List<InvProductoCategoriaTO> listInvProductoCategoriaTO) {
		this.listInvProductoCategoriaTO = listInvProductoCategoriaTO;
	}

	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}

	public List<InvVentasDetalleTO> getListaInvVentasDetalleTO() {
		return listaInvVentasDetalleTO;
	}

	public void setListaInvVentasDetalleTO(List<InvVentasDetalleTO> listaInvVentasDetalleTO) {
		this.listaInvVentasDetalleTO = listaInvVentasDetalleTO;
	}

	public InvVentasDetalleTO getInvVentasDetalleTO() {
		return invVentasDetalleTO;
	}

	public void setInvVentasDetalleTO(InvVentasDetalleTO invVentasDetalleTO) {
		this.invVentasDetalleTO = invVentasDetalleTO;
	}

	public TotalesFacturaTO getTotalesFacturaTO() {
		return totalesFacturaTO;
	}

	public void setTotalesFacturaTO(TotalesFacturaTO totalesFacturaTO) {
		this.totalesFacturaTO = totalesFacturaTO;
	}

}
