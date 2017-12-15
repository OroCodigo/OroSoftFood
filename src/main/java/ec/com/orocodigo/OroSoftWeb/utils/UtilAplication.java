package ec.com.orocodigo.OroSoftWeb.utils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.context.FacesContext;

import org.primefaces.context.RequestContext;
import org.springframework.security.core.context.SecurityContextHolder;

import ec.com.orocodigo.OroSoftUtils.UtilsAplicacion;
import ec.com.orocodigo.OroSoftUtils.sistema.TO.SisInfoTO;
import ec.com.orocodigo.OroSoftUtils.sistema.TO.UsuarioEmpresaReporteTO;
import ec.com.orocodigo.OroSoftUtils.sistema.entity.SisEmpresa;

public class UtilAplication {
	private static List<SisInfoTO> listSisInfoTO = new ArrayList<SisInfoTO>();
	public static UsuarioEmpresaReporteTO usuarioEmpresaReporteTO;
	public static BigDecimal cero = new BigDecimal("0.00");
	public static BigDecimal uno = new BigDecimal("1.00");

	public static void addSisInfoTO(SisInfoTO sisInfoTO) {
		listSisInfoTO.add(sisInfoTO);
	}

	public static boolean removeSisInfoTO(SisInfoTO sisInfoTO) {
		return listSisInfoTO.remove(sisInfoTO);
	}

	public static SisInfoTO removeSisInfoTO() {
		for (int i = 0; i < listSisInfoTO.size(); i++)
			if (listSisInfoTO.get(i).getUsuarioNick()
					.compareToIgnoreCase(SecurityContextHolder.getContext().getAuthentication().getName()) == 0)
				return listSisInfoTO.remove(i);
		return null;
	}

	public static SisInfoTO getSisInfoTO() {
		try {
			for (SisInfoTO sisInfoTO : listSisInfoTO) {
				if (sisInfoTO.getUsuarioNick()
						.compareToIgnoreCase(SecurityContextHolder.getContext().getAuthentication().getName()) == 0)
					return sisInfoTO;
			}
		} catch (Exception e) {
			return listSisInfoTO.get(0);
		}
		return null;
	}

	public static String armarUsuarioEmpresaReporteTO(List<SisEmpresa> list, String empresaCodigo) {
		UtilsAplicacion.getSisInfoTO().setEmpresa(empresaCodigo);

		UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = new UsuarioEmpresaReporteTO();
		for (SisEmpresa se : list)
			if (se.getEmpCodigo().compareToIgnoreCase(empresaCodigo) == 0) {
				usuarioEmpresaReporteTO.setEmpNombre(se.getEmpNombre());
				usuarioEmpresaReporteTO.setEmpRuc(se.getEmpRuc());
				usuarioEmpresaReporteTO.setEmpDireccion(se.getEmpDireccion());
				usuarioEmpresaReporteTO.setEmpTelefono(se.getEmpTelefono());
				usuarioEmpresaReporteTO.setUsrNick(UtilsAplicacion.getSisInfoTO().getUsuario());
				break;
			}

		UtilsAplicacion.usuarioEmpresaReporteTO = usuarioEmpresaReporteTO;

		return usuarioEmpresaReporteTO.getEmpNombre();
	}

	public static void enviarVariableVista(String variable, boolean valor) {
		RequestContext.getCurrentInstance().addCallbackParam(variable, valor);
	}

	public static void update(String... updates) {
		RequestContext.getCurrentInstance().update(new ArrayList<String>(Arrays.asList(updates)));
	}

	public static void execute(String... jqs) {
		for (String jq : jqs)
			RequestContext.getCurrentInstance().execute(jq);
	}

	public static String generarMensajeLista(List<String> listaMensaje) {
		String mensaje = "";
		for (String menAux : listaMensaje)
			mensaje += menAux + "\n";
		return mensaje += "";
	}

	public static void presentaMensaje(Severity severity, String mensaje) {
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(severity, "MENSAJE DEL SISTEMA", mensaje));
	}

	public static void presentaMensaje(Severity severity, String mensaje, String variable, boolean valor) {
		presentaMensaje(severity, mensaje);
		enviarVariableVista(variable, valor);
	}

	public static void redireccionar(String url) {
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect(url);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static double redondear(double numero, int numeroDecimales) {
		long mult = (long) Math.pow(10, numeroDecimales);
		double resultado = (Math.round(numero * mult)) / (double) mult;
		return resultado;
	}

}