package ec.com.orocodigo.OroSoftWeb.controller.inventario;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import ec.com.orocodigo.OroSoftUtils.inventario.TO.InvProductoCategoriaTO;
import ec.com.orocodigo.OroSoftUtils.inventario.entity.InvMenuComida;
import ec.com.orocodigo.OroSoftUtils.sistema.TO.SisGrupoTO;
import ec.com.orocodigo.OroSoftUtils.sistema.entity.SisEmpresa;
import ec.com.orocodigo.OroSoftWeb.delegate.InventarioDelegate;
import ec.com.orocodigo.OroSoftWeb.delegate.SistemaDelegate;
import ec.com.orocodigo.OroSoftWeb.utils.UtilAplication;

@Controller
@Scope("session")
public class CategoriaMenuComidaBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private List<SisEmpresa> listEmpresas;
	private String empresa;

	private List<InvMenuComida> listInvMenuComida;

	private List<InvProductoCategoriaTO> listInvProductoCategoriaTO;
	private InvProductoCategoriaTO invProductoCategoriaTO;

	private SisGrupoTO grupo;

	public CategoriaMenuComidaBean() {
	}

	@PostConstruct
	public void init() {
		limpiar();
	}

	public void limpiar() {
		listEmpresas = SistemaDelegate.getInstance().getEmpresasPorUsuarioItem();
		empresa = null;

		if (listEmpresas.size() == 1) {
			empresa = listEmpresas.get(0).getEmpCodigo();
			cargarDatos();
		}
	}

	public void limpiarNuevo() {
		UtilAplication.execute("mostarPanelInsertar('')");

		invProductoCategoriaTO = new InvProductoCategoriaTO();
		invProductoCategoriaTO.setCatEmpresa(empresa);
		invProductoCategoriaTO.setMenEmpresa(empresa);
		invProductoCategoriaTO.setUsrEmpresa(empresa);
		invProductoCategoriaTO.setUsrCodigo(UtilAplication.getSisInfoTO().getUsuario());
		invProductoCategoriaTO.setUsrFechaInserta(new Date().toString());
	}

	public void cargarDatos() {
		grupo = SistemaDelegate.getInstance().sisGrupoUsuariosTO(empresa);
		listInvProductoCategoriaTO = InventarioDelegate.getInstance().getInvProductoCategoria("ORC", null);
		listInvMenuComida = InventarioDelegate.getInstance().getMenuComidaTO(empresa);
	}

	public void insertar() {
		if (invProductoCategoriaTO.getCatCodigo() == null || invProductoCategoriaTO.getCatCodigo().isEmpty())
			UtilAplication.presentaMensaje(FacesMessage.SEVERITY_ERROR, "Debe ingresar el codigo");
		else if (invProductoCategoriaTO.getCatDetalle() == null || invProductoCategoriaTO.getCatDetalle().isEmpty())
			UtilAplication.presentaMensaje(FacesMessage.SEVERITY_ERROR, "Debe ingresar un nombre");
		else {
			invProductoCategoriaTO.setCatCodigo(invProductoCategoriaTO.getCatCodigo().toUpperCase());
			invProductoCategoriaTO.setCatDetalle(invProductoCategoriaTO.getCatDetalle().toUpperCase());
			invProductoCategoriaTO.setCatActiva(true);
			String mensaje = InventarioDelegate.getInstance().accionInvProductoCategoria(invProductoCategoriaTO, 'I');
			if (mensaje.charAt(0) == 'T') {
				listInvProductoCategoriaTO.add(invProductoCategoriaTO);
				UtilAplication.execute("mostarPanelInsertar('')");
				UtilAplication.presentaMensaje(FacesMessage.SEVERITY_INFO, mensaje.substring(1));
				return;
			}
			UtilAplication.presentaMensaje(FacesMessage.SEVERITY_ERROR, mensaje.substring(1));
		}
	}

	public void modificar() {
		if (invProductoCategoriaTO.getCatCodigo() == null || invProductoCategoriaTO.getCatCodigo().isEmpty())
			UtilAplication.presentaMensaje(FacesMessage.SEVERITY_ERROR, "Debe ingresar el codigo");
		else if (invProductoCategoriaTO.getCatDetalle() == null || invProductoCategoriaTO.getCatDetalle().isEmpty())
			UtilAplication.presentaMensaje(FacesMessage.SEVERITY_ERROR, "Debe ingresar un nombre");
		else {
			invProductoCategoriaTO.setCatCodigo(invProductoCategoriaTO.getCatCodigo().toUpperCase());
			invProductoCategoriaTO.setCatDetalle(invProductoCategoriaTO.getCatDetalle().toUpperCase());
			String mensaje = InventarioDelegate.getInstance().accionInvProductoCategoria(invProductoCategoriaTO, 'M');
			if (mensaje.charAt(0) == 'T') {
				listInvProductoCategoriaTO.set(listInvProductoCategoriaTO.indexOf(invProductoCategoriaTO),
						invProductoCategoriaTO);
				UtilAplication.execute("mostarPanelEditar('')");
				UtilAplication.presentaMensaje(FacesMessage.SEVERITY_INFO, mensaje.substring(1));
				return;
			}
			UtilAplication.presentaMensaje(FacesMessage.SEVERITY_ERROR, mensaje.substring(1));
		}
	}

	public void eliminar() {
		String mensaje = InventarioDelegate.getInstance().accionInvProductoCategoria(invProductoCategoriaTO, 'E');
		if (mensaje.charAt(0) == 'T') {
			listInvProductoCategoriaTO.remove(invProductoCategoriaTO);
			UtilAplication.presentaMensaje(FacesMessage.SEVERITY_INFO, mensaje.substring(1));
			UtilAplication.execute("mostarPanelEliminar('')");
			return;
		}
		UtilAplication.presentaMensaje(FacesMessage.SEVERITY_ERROR, mensaje.substring(1));
	}

	// GETTERS Y SETTERS

	public SisGrupoTO getGrupo() {
		return grupo;
	}

	public void setGrupo(SisGrupoTO grupo) {
		this.grupo = grupo;
	}

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

	public List<InvProductoCategoriaTO> getListInvProductoCategoriaTO() {
		return listInvProductoCategoriaTO;
	}

	public void setListInvProductoCategoriaTO(List<InvProductoCategoriaTO> listInvProductoCategoriaTO) {
		this.listInvProductoCategoriaTO = listInvProductoCategoriaTO;
	}

	public InvProductoCategoriaTO getInvProductoCategoriaTO() {
		return invProductoCategoriaTO;
	}

	public void setInvProductoCategoriaTO(InvProductoCategoriaTO invProductoCategoriaTO) {
		this.invProductoCategoriaTO = invProductoCategoriaTO;
	}

	public List<InvMenuComida> getListInvMenuComida() {
		return listInvMenuComida;
	}

	public void setListInvMenuComida(List<InvMenuComida> listInvMenuComida) {
		this.listInvMenuComida = listInvMenuComida;
	}

}