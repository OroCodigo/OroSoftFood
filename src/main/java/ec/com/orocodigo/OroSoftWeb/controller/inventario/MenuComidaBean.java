package ec.com.orocodigo.OroSoftWeb.controller.inventario;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import ec.com.orocodigo.OroSoftUtils.MensajeTO;
import ec.com.orocodigo.OroSoftUtils.UtilsJSON;
import ec.com.orocodigo.OroSoftUtils.inventario.entity.InvMenuComida;
import ec.com.orocodigo.OroSoftUtils.inventario.entity.InvMenuComidaPK;
import ec.com.orocodigo.OroSoftUtils.sistema.TO.SisGrupoTO;
import ec.com.orocodigo.OroSoftUtils.sistema.entity.SisEmpresa;
import ec.com.orocodigo.OroSoftWeb.delegate.InventarioDelegate;
import ec.com.orocodigo.OroSoftWeb.delegate.SistemaDelegate;
import ec.com.orocodigo.OroSoftWeb.utils.UtilAplication;

@Controller
@Scope("session")
public class MenuComidaBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private List<SisEmpresa> listEmpresas;
	private String empresa;

	private List<InvMenuComida> listInvMenuComida;
	private InvMenuComida invMenuComida;

	private SisGrupoTO grupo;

	public MenuComidaBean() {
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
		invMenuComida = new InvMenuComida();
		invMenuComida.setInvMenuComidaPK(new InvMenuComidaPK(empresa, ""));
	}

	public void cargarDatos() {
		grupo = SistemaDelegate.getInstance().sisGrupoUsuariosTO(empresa);
		listInvMenuComida = InventarioDelegate.getInstance().getMenuComidaTO(empresa);
	}

	public void insertar() {
		if (invMenuComida.getInvMenuComidaPK().getMenCodigo() == null
				|| invMenuComida.getInvMenuComidaPK().getMenCodigo().isEmpty())
			UtilAplication.presentaMensaje(FacesMessage.SEVERITY_ERROR, "Debe ingresar el codigo");
		else if (invMenuComida.getMenNombre() == null || invMenuComida.getMenNombre().isEmpty())
			UtilAplication.presentaMensaje(FacesMessage.SEVERITY_ERROR, "Debe ingresar un nombre");
		else {
			invMenuComida.getInvMenuComidaPK()
					.setMenCodigo(invMenuComida.getInvMenuComidaPK().getMenCodigo().toUpperCase());
			invMenuComida.setMenNombre(invMenuComida.getMenNombre().toUpperCase());
			invMenuComida.setMenImagen("");

			MensajeTO mensajeTO = InventarioDelegate.getInstance().insertarMenuComida(invMenuComida);
			String control = String.valueOf(mensajeTO.getMensaje().charAt(0));
			String mensaje = mensajeTO.getMensaje().substring(1, mensajeTO.getMensaje().length());
			if (control.compareToIgnoreCase("T") == 0) {

				invMenuComida = UtilsJSON.jsonToObjeto(InvMenuComida.class, mensajeTO.getMap().get("menu"));
				listInvMenuComida.add(invMenuComida);
				UtilAplication.execute("mostarPanelInsertar('')");
				UtilAplication.presentaMensaje(FacesMessage.SEVERITY_INFO, mensaje);
				return;
			}
			UtilAplication.presentaMensaje(FacesMessage.SEVERITY_ERROR, mensaje);
		}
	}

	public void modificar() {
		if (invMenuComida.getInvMenuComidaPK().getMenCodigo() == null
				|| invMenuComida.getInvMenuComidaPK().getMenCodigo().isEmpty())
			UtilAplication.presentaMensaje(FacesMessage.SEVERITY_ERROR, "Debe ingresar el codigo");
		else if (invMenuComida.getMenNombre() == null || invMenuComida.getMenNombre().isEmpty())
			UtilAplication.presentaMensaje(FacesMessage.SEVERITY_ERROR, "Debe ingresar un nombre");
		else {
			invMenuComida.getInvMenuComidaPK()
					.setMenCodigo(invMenuComida.getInvMenuComidaPK().getMenCodigo().toUpperCase());
			invMenuComida.setMenNombre(invMenuComida.getMenNombre().toUpperCase());
			invMenuComida.setMenImagen("");

			String mensaje = InventarioDelegate.getInstance().modificarMenuComida(invMenuComida);
			String control = String.valueOf(mensaje.charAt(0));
			mensaje = mensaje.substring(1, mensaje.length());
			if (control.compareToIgnoreCase("T") == 0) {
				listInvMenuComida.set(listInvMenuComida.indexOf(invMenuComida), invMenuComida);
				UtilAplication.execute("mostarPanelEditar('')");
				UtilAplication.presentaMensaje(FacesMessage.SEVERITY_INFO, mensaje);
				return;
			}
			UtilAplication.presentaMensaje(FacesMessage.SEVERITY_ERROR, mensaje);
		}
	}

	public void eliminar() {
		String mensaje = InventarioDelegate.getInstance().eliminarMenuComida(invMenuComida);
		String control = String.valueOf(mensaje.charAt(0));
		mensaje = mensaje.substring(1, mensaje.length());
		if (control.compareToIgnoreCase("T") == 0) {
			listInvMenuComida.remove(invMenuComida);
			UtilAplication.presentaMensaje(FacesMessage.SEVERITY_INFO, mensaje);
			UtilAplication.execute("mostarPanelEliminar('')");
			return;
		}
		UtilAplication.presentaMensaje(FacesMessage.SEVERITY_ERROR, mensaje);
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

	public List<InvMenuComida> getListInvMenuComida() {
		return listInvMenuComida;
	}

	public void setListInvMenuComida(List<InvMenuComida> listInvMenuComida) {
		this.listInvMenuComida = listInvMenuComida;
	}

	public InvMenuComida getInvMenuComida() {
		return invMenuComida;
	}

	public void setInvMenuComida(InvMenuComida invMenuComida) {
		this.invMenuComida = invMenuComida;
	}

}