package ec.com.orocodigo.OroSoftWeb.utils;

import java.util.HashMap;
import java.util.Map;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.UIManager;

public class UtilsMensaje {

	public static Map<String, Object> mensajeError(Exception e, String nombreClase) {
		JLabel jlMensaje = new JLabel("<html>Lo sentimos, hemos detectado un error interno.<br/><br/>"
				+ "<font size=5>¿Desea informar este problema?</font><br/><br/>"
				+ "Se ha creado un informe de error que puede enviar para mejorar OroSoft.<br/>"
				+ "Adicionalmente puede detallarnos sobre que provocó este error.</html>");
		JTextArea jtaMensaje = new JTextArea();
		jtaMensaje.setRows(6);
		jtaMensaje.setColumns(40);
		Object[] comp = { jlMensaje, jtaMensaje };
		Object[] botones = { "Enviar informe de error", "Cancelar" };
		int i = JOptionPane.showOptionDialog(null, comp, "ShrimSoft", JOptionPane.YES_NO_OPTION,
				JOptionPane.ERROR_MESSAGE, UIManager.getIcon("OptionPane.errorIcon"), botones, null);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("opcion", i);
		map.put("mensaje", jtaMensaje.getText());
		return map;
	}

	public static boolean showConfirmDialogWarning(String mensaje) {
		JLabel jlMensaje = new JLabel(mensaje);
		Object[] botones = { "Aceptar", "Cancelar" };
		int i = JOptionPane.showOptionDialog(null, jlMensaje, "OroSoft", JOptionPane.YES_NO_OPTION,
				JOptionPane.WARNING_MESSAGE, UIManager.getIcon("OptionPane.warningIcon"), botones, null);
		return i == 0 ? true : false;
	}

	public static void errorConexion() {
		JOptionPane.showMessageDialog(null,
				"No existe conexión con el servidor, posibles causas: \n\n-Revise su conexión de internet. \n"
						+ "-La conexion a internet es demasiado lenta.\n\nSi el problema persiste contacte al departamento de Soporte Técnico.",
				"OroSoft", JOptionPane.ERROR_MESSAGE);
	}

	public static void mensajeReportePDF() {
		JOptionPane.showMessageDialog(null, "No hay datos para imprimir", "OroSoft", JOptionPane.WARNING_MESSAGE);
	}

}