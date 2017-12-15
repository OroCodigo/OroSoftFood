package ec.com.orocodigo.OroSoftWeb.controller;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.primefaces.model.menu.DefaultMenuItem;
import org.primefaces.model.menu.DefaultMenuModel;
import org.primefaces.model.menu.DefaultSubMenu;
import org.primefaces.model.menu.MenuModel;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;

import ec.com.orocodigo.OroSoftUtils.sistema.TO.SisInfoTO;
import ec.com.orocodigo.OroSoftUtils.sistema.TO.SisMenu;
import ec.com.orocodigo.OroSoftUtils.sistema.entity.SisUsuario;
import ec.com.orocodigo.OroSoftWeb.delegate.SistemaDelegate;
import ec.com.orocodigo.OroSoftWeb.utils.UtilAplication;

@Controller
@Scope("session")
public class LoginBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private String nombreUsuario;
	private MenuModel model;

	public LoginBean() {
	}

	@PostConstruct
	public void init() {
		ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
		String ipPublica = ((HttpServletRequest) ec.getRequest()).getRemoteHost();
		String url = ((ServletContext) ec.getContext()).getContextPath();
		SisUsuario usuario = SistemaDelegate.getInstance()
				.buscarUsuarioPorNick(SecurityContextHolder.getContext().getAuthentication().getName());
		if (usuario.getUsrIP() != null && usuario.getUsrIP().compareToIgnoreCase(ipPublica) != 0) {
			UtilAplication.redireccionar(url + logoutSistema());
			return;
		}

		System.out.println("entre init login");

		SisInfoTO sisInfoTO = new SisInfoTO();
		sisInfoTO.setEmpresa("");
		sisInfoTO.setUsuarioNick(SecurityContextHolder.getContext().getAuthentication().getName());
		sisInfoTO.setUsuario(usuario.getUsrCodigo());
		sisInfoTO.setMac(ipPublica);
		sisInfoTO.setAmbiente("WEB");

		UtilAplication.addSisInfoTO(sisInfoTO);

		// SistemaDelegate.getInstance().sisRegistrarEventosUsuario("LOGIN", "
		// se logueo en el sistema a las ",
		// "sistema.sis_usuario", sisInfoTO);
		setNombreUsuario(usuario.getUsrNombre() + " " + usuario.getUsrApellido());
		menu(usuario.getUsrCodigo());
	}

	private void menu(String usuario) {
		model = new DefaultMenuModel();
		DefaultSubMenu sistema = new DefaultSubMenu("Sistema", "settings_application");
		DefaultSubMenu inventario = new DefaultSubMenu("Inventario", "settings_application");
		DefaultSubMenu cartera = new DefaultSubMenu("Cartera", "settings_application");
		DefaultSubMenu contabilidad = new DefaultSubMenu("Contabilidad", "settings_application");
		DefaultSubMenu rrhh = new DefaultSubMenu("Recursos Humanos", "people");
		DefaultSubMenu tributacion = new DefaultSubMenu("Tributacion", "settings_application");
		DefaultSubMenu banco = new DefaultSubMenu("Bancos", "settings_application");
		DefaultSubMenu produccion = new DefaultSubMenu("Produccion", "settings_application");
		DefaultMenuItem salir = new DefaultMenuItem("Salir", "power");
		salir.setCommand("#{loginBean.logoutSistema}");

		List<SisMenu> menus = SistemaDelegate.getInstance().getMenuWeb(usuario, false);

		subMenu(sistema, menus, "WEB SISTEMA");
		subMenu(inventario, menus, "WEB INVENTARIO");
		subMenu(cartera, menus, "WEB CARTERA");
		subMenu(contabilidad, menus, "WEB CONTABILIDAD");
		subMenu(rrhh, menus, "WEB RECURSOS HUMANOS");
		subMenu(tributacion, menus, "WEB TRIBUTACION");
		subMenu(banco, menus, "WEB BANCO");
		subMenu(produccion, menus, "WEB PRODUCCION");

		if (!sistema.getElements().isEmpty())
			model.addElement(sistema);
		if (!inventario.getElements().isEmpty())
			model.addElement(inventario);
		if (!cartera.getElements().isEmpty())
			model.addElement(cartera);
		if (!contabilidad.getElements().isEmpty())
			model.addElement(contabilidad);
		if (!rrhh.getElements().isEmpty())
			model.addElement(rrhh);
		if (!tributacion.getElements().isEmpty())
			model.addElement(tributacion);
		if (!banco.getElements().isEmpty())
			model.addElement(banco);
		if (!produccion.getElements().isEmpty())
			model.addElement(produccion);
		model.addElement(salir);
	}

	private void subMenu(DefaultSubMenu subMenu, List<SisMenu> menus, String modulo) {
		DefaultSubMenu archivo = new DefaultSubMenu("Archivo", "folder");
		DefaultSubMenu transaccion = new DefaultSubMenu("Transacciones", "settings_application");
		DefaultSubMenu consulta = new DefaultSubMenu("Consultas", "event_note");

		for (SisMenu menu : menus) {
			if (modulo.compareToIgnoreCase(menu.getPerModulo()) == 0) {
				DefaultMenuItem menuItem = new DefaultMenuItem(menu.getPerItemEtiqueta(), menu.getPerIcono());
				if (menu.getPerUrl().startsWith("#"))
					menuItem.setCommand(menu.getPerUrl());
				else
					menuItem.setUrl(menu.getPerUrl());

				if (menu.getPerMenu().compareToIgnoreCase("Archivo") == 0)
					archivo.addElement(menuItem);
				else if (menu.getPerMenu().compareToIgnoreCase("Transacciones") == 0)
					transaccion.addElement(menuItem);
				else if (menu.getPerMenu().compareToIgnoreCase("Consultas") == 0)
					consulta.addElement(menuItem);
			}
		}

		if (!archivo.getElements().isEmpty())
			subMenu.addElement(archivo);
		if (!transaccion.getElements().isEmpty())
			subMenu.addElement(transaccion);
		if (!consulta.getElements().isEmpty())
			subMenu.addElement(consulta);
	}

	public String logoutSistema() {
		UtilAplication.removeSisInfoTO();
		// SistemaDelegate.getInstance().sisRegistrarEventosUsuario("LOGOUT", "
		// salio del sistema a las",
		// "sistema.sis_usuario", UtilsAplicacion.removeSisInfoTO());
		return "/views/publicas/logout.jsf?faces-redirect=true";
	}

	public String getNombreUsuario() {
		return nombreUsuario;
	}

	public void setNombreUsuario(String nombreUsuario) {
		this.nombreUsuario = nombreUsuario;
	}

	public MenuModel getModel() {
		return model;
	}

	public void setModel(MenuModel model) {
		this.model = model;
	}

}
