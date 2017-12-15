package ec.com.orocodigo.OroSoftWeb.utils;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Map;

import org.postgresql.util.PSQLException;

import ec.com.orocodigo.OroSoftUtils.UtilsAplicacion;
import ec.com.orocodigo.OroSoftUtils.UtilsDate;
import ec.com.orocodigo.OroSoftUtils.sistema.TO.SisErrorTO;
import ec.com.orocodigo.OroSoftUtils.sistema.TO.SisInfoTO;
import ec.com.orocodigo.OroSoftWeb.delegate.SistemaDelegate;

public class UtilsExcepciones {

	public static SisErrorTO crearSisErrorTO(Exception e, String clase, String mensaje, SisInfoTO sisInfoTO) {
		e.printStackTrace();

		if (sisInfoTO == null)
			sisInfoTO = new SisInfoTO(" ", " ", " ", " ", " ");

		StringWriter error = new StringWriter();
		e.printStackTrace(new PrintWriter(error));

		String tipoError = "N/A";
		int linea = -1;
		String metodo = "N/A";

		e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
		if (e instanceof PSQLException) {
			tipoError = e.getMessage();
			metodo = e.getLocalizedMessage();
		} else
			for (StackTraceElement elementosRastreo : e.getStackTrace())
				if (elementosRastreo.getClassName().equals(clase)) {
					tipoError = e.getLocalizedMessage();
					clase = elementosRastreo.getFileName();
					linea = elementosRastreo.getLineNumber();
					metodo = elementosRastreo.getMethodName();
					break;
				}

		SisErrorTO sisErrorTO = new SisErrorTO();
		sisErrorTO.setTipoError(tipoError);
		sisErrorTO.setClase(clase);
		sisErrorTO.setLinea(linea);
		sisErrorTO.setMetodo(metodo);
		sisErrorTO.setFecha(UtilsDate.timestamp());
		sisErrorTO.setException(error.toString());
		sisErrorTO.setUsuario(sisInfoTO.getUsuario());
		sisErrorTO.setMensaje(mensaje);
		sisErrorTO.setMac(sisInfoTO.getMac());
		sisErrorTO.setEmpresa(sisInfoTO.getEmpresa());

		return sisErrorTO;
	}

	public static void enviarError(Exception e, String nombreClase) {
		e.printStackTrace();
		SisInfoTO sisInfoTO = UtilsAplicacion.getSisInfoTO();
		if (sisInfoTO.getAmbiente() != null && sisInfoTO.getAmbiente().compareToIgnoreCase("WEB") == 0)
			SistemaDelegate.getInstance().enviarError(crearSisErrorTO(e, nombreClase, "", sisInfoTO));
		else {
			if (e.getCause() != null && e.getCause().getMessage().contains("Connection refused: connect")) {
				UtilsMensaje.errorConexion();
			} else {
				Map<String, Object> map = UtilsMensaje.mensajeError(e, nombreClase);
				if ((Integer) map.get("opcion") == 0)
					SistemaDelegate.getInstance().enviarError(
							UtilsExcepciones.crearSisErrorTO(e, nombreClase, (String) map.get("mensaje"), sisInfoTO));
				else
					e.printStackTrace();
			}
		}
	}

	public static Throwable obtenerErrorPostgreSQL(Exception e) {
		Throwable t = e.getCause();
		while ((t != null) && !(t instanceof PSQLException))
			t = t.getCause();
		return t instanceof PSQLException ? t : e;
	}

}