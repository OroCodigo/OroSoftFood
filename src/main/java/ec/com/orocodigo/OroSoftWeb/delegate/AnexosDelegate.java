package ec.com.orocodigo.OroSoftWeb.delegate;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import ec.com.orocodigo.OroSoftUtils.Propiedades;
import ec.com.orocodigo.OroSoftUtils.UtilsJSON;
import ec.com.orocodigo.OroSoftUtils.UtilsRESTFul;
import ec.com.orocodigo.OroSoftUtils.anexos.TO.AnxAnuladoTablaTO;
import ec.com.orocodigo.OroSoftUtils.anexos.TO.AnxAnuladosTO;
import ec.com.orocodigo.OroSoftUtils.anexos.TO.AnxCompraDetalleTO;
import ec.com.orocodigo.OroSoftUtils.anexos.TO.AnxCompraElectronicaTO;
import ec.com.orocodigo.OroSoftUtils.anexos.TO.AnxCompraFormaPagoTO;
import ec.com.orocodigo.OroSoftUtils.anexos.TO.AnxCompraReembolsoTO;
import ec.com.orocodigo.OroSoftUtils.anexos.TO.AnxCompraTO;
import ec.com.orocodigo.OroSoftUtils.anexos.TO.AnxConceptoComboTO;
import ec.com.orocodigo.OroSoftUtils.anexos.TO.AnxConceptoTO;
import ec.com.orocodigo.OroSoftUtils.anexos.TO.AnxCuentasContablesTO;
import ec.com.orocodigo.OroSoftUtils.anexos.TO.AnxEstablecimientoComboTO;
import ec.com.orocodigo.OroSoftUtils.anexos.TO.AnxFormaPagoTO;
import ec.com.orocodigo.OroSoftUtils.anexos.TO.AnxFormulario104TO;
import ec.com.orocodigo.OroSoftUtils.anexos.TO.AnxFormulario104ValoresTO;
import ec.com.orocodigo.OroSoftUtils.anexos.TO.AnxFunListadoDevolucionIvaTO;
import ec.com.orocodigo.OroSoftUtils.anexos.TO.AnxFunRegistroDatosCrediticiosTO;
import ec.com.orocodigo.OroSoftUtils.anexos.TO.AnxListaComprobanteAnuladoTO;
import ec.com.orocodigo.OroSoftUtils.anexos.TO.AnxListaConsolidadoRetencionesVentasTO;
import ec.com.orocodigo.OroSoftUtils.anexos.TO.AnxListaEstablecimientoRetencionesVentasTO;
import ec.com.orocodigo.OroSoftUtils.anexos.TO.AnxListaRetencionesFuenteIvaTO;
import ec.com.orocodigo.OroSoftUtils.anexos.TO.AnxListaRetencionesPendientesTO;
import ec.com.orocodigo.OroSoftUtils.anexos.TO.AnxListaRetencionesRentaTO;
import ec.com.orocodigo.OroSoftUtils.anexos.TO.AnxListaRetencionesTO;
import ec.com.orocodigo.OroSoftUtils.anexos.TO.AnxListaVentaElectronicaTO;
import ec.com.orocodigo.OroSoftUtils.anexos.TO.AnxListaVentasPendientesTO;
import ec.com.orocodigo.OroSoftUtils.anexos.TO.AnxListadoCompraElectronicaTO;
import ec.com.orocodigo.OroSoftUtils.anexos.TO.AnxListadoRetencionesVentasTO;
import ec.com.orocodigo.OroSoftUtils.anexos.TO.AnxNumeracionLineaTO;
import ec.com.orocodigo.OroSoftUtils.anexos.TO.AnxNumeracionTO;
import ec.com.orocodigo.OroSoftUtils.anexos.TO.AnxNumeracionTablaTO;
import ec.com.orocodigo.OroSoftUtils.anexos.TO.AnxPaisTO;
import ec.com.orocodigo.OroSoftUtils.anexos.TO.AnxProvinciaCantonTO;
import ec.com.orocodigo.OroSoftUtils.anexos.TO.AnxPuntoEmisionComboTO;
import ec.com.orocodigo.OroSoftUtils.anexos.TO.AnxSustentoComboTO;
import ec.com.orocodigo.OroSoftUtils.anexos.TO.AnxSustentoTO;
import ec.com.orocodigo.OroSoftUtils.anexos.TO.AnxTalonResumenTO;
import ec.com.orocodigo.OroSoftUtils.anexos.TO.AnxTalonResumenVentaTO;
import ec.com.orocodigo.OroSoftUtils.anexos.TO.AnxTipoComprobanteComboTO;
import ec.com.orocodigo.OroSoftUtils.anexos.TO.AnxTipoComprobanteTO;
import ec.com.orocodigo.OroSoftUtils.anexos.TO.AnxTipoComprobanteTablaTO;
import ec.com.orocodigo.OroSoftUtils.anexos.TO.AnxTipoIdentificacionTO;
import ec.com.orocodigo.OroSoftUtils.anexos.TO.AnxTipoListaTransaccionTO;
import ec.com.orocodigo.OroSoftUtils.anexos.TO.AnxUltimaAutorizacionTO;
import ec.com.orocodigo.OroSoftUtils.anexos.TO.AnxVentaElectronicaTO;
import ec.com.orocodigo.OroSoftUtils.anexos.TO.AnxVentaTO;
import ec.com.orocodigo.OroSoftUtils.anexos.TO.ComprobanteElectronico;
import ec.com.orocodigo.OroSoftUtils.anexos.TO.anxUrlWebServicesTO;
import ec.com.orocodigo.OroSoftUtils.anexos.entity.AnxCompra;
import ec.com.orocodigo.OroSoftUtils.sri.ws.autorizacionOffline.RespuestaComprobante;
import ec.com.orocodigo.OroSoftWeb.utils.UtilAplication;
import ec.com.orocodigo.OroSoftWeb.utils.UtilsExcepciones;

public class AnexosDelegate {

	private static AnexosDelegate grupoDelegate;

	public static AnexosDelegate getInstance() {
		if (grupoDelegate == null)
			try {
				grupoDelegate = new AnexosDelegate();
			} catch (final Exception e) {
				e.printStackTrace();
			}
		return grupoDelegate;
	}

	private final String conexionWS;

	private final RestTemplate restTemplate = new RestTemplate();

	private AnexosDelegate() throws Exception {
		final Properties propSistema = Propiedades.readPropiedades();
		conexionWS = "http://" + propSistema.getProperty("servidor.ip") + ":"
				+ propSistema.getProperty("servidor.puerto") + "/" + propSistema.getProperty("servidor.ubicacion")
				+ "/OroSoftWS/anexosController";
	}

	public String accionAnxCompraElectronica(final AnxCompraElectronicaTO anxCompraElectronicaTO, final char accion) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("anxCompraElectronicaTO", UtilsJSON.objetoToJson(anxCompraElectronicaTO));
			map.put("accion", accion);
			map.put("sisInfoTO", UtilsJSON.objetoToJson(UtilAplication.getSisInfoTO()));
			return restTemplate.postForObject(conexionWS + "/accionAnxCompraElectronica", UtilsJSON.objetoToJson(map),
					String.class);
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public String accionAnxUrlWebServicesTO(final anxUrlWebServicesTO anxUrlWebServicesTO, final char accion) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("anxUrlWebServicesTO", anxUrlWebServicesTO);
			map.put("accion", accion);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return restTemplate.postForObject(conexionWS + "/accionAnxUrlWebServicesTO", UtilsJSON.objetoToJson(map),
					String.class);
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public String accionAnxVenta(final AnxVentaTO anxVentaTO, final String numeroFactura, final String periodoFactura,
			final String cliCodigo, final char accion) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("anxVentaTO", anxVentaTO);
			map.put("numeroFactura", numeroFactura);
			map.put("periodoFactura", periodoFactura);
			map.put("cliCodigo", cliCodigo);
			map.put("accion", accion);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return restTemplate.postForObject(conexionWS + "/accionAnxVenta", UtilsJSON.objetoToJson(map),
					String.class);
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public String accionAnxVentaElectronica(final AnxVentaElectronicaTO anxVentaElectronicaTO, final char accion) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("anxVentaElectronicaTO", anxVentaElectronicaTO);
			map.put("accion", accion);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return restTemplate.postForObject(conexionWS + "/accionAnxVentaElectronica", UtilsJSON.objetoToJson(map),
					String.class);
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public String actualizarAnxCuentasContables(final AnxCuentasContablesTO anxCuentasContablesTO, final String empresa,
			final String usuario) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("anxCuentasContablesTO", anxCuentasContablesTO);
			map.put("empresa", empresa);
			map.put("usuario", usuario);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return restTemplate.postForObject(conexionWS + "/actualizarAnxCuentasContables",
					UtilsJSON.objetoToJson(map), String.class);
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public String comprobarAnxVentaElectronicaAutorizacion(final String empresa, final String periodo,
			final String motivo, final String numero) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("empresa", empresa);
			map.put("periodo", periodo);
			map.put("motivo", motivo);
			map.put("numero", numero);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return restTemplate.postForObject(conexionWS + "/comprobarAnxVentaElectronicaAutorizacion",
					UtilsJSON.objetoToJson(map), String.class);
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public boolean comprobarRetencionAutorizadaProcesamiento(final String empresa, final String periodo,
			final String motivo, final String numero) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("empresa", empresa);
			map.put("periodo", periodo);
			map.put("motivo", motivo);
			map.put("numero", numero);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return restTemplate.postForObject(conexionWS + "/comprobarRetencionAutorizadaProcesamiento",
					UtilsJSON.objetoToJson(map), boolean.class);
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return false;
	}

	public String descargarrAutorizarFacturaElectronica(final String empresa, final String vtaPerCodigo,
			final String vtaMotCodigo, final String vtaNumero, final String tipoAmbiente) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("empresa", empresa);
			map.put("vtaPerCodigo", vtaPerCodigo);
			map.put("vtaMotCodigo", vtaMotCodigo);
			map.put("vtaNumero", vtaNumero);
			map.put("tipoAmbiente", tipoAmbiente);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return restTemplate.postForObject(conexionWS + "/descargarrAutorizarFacturaElectronica",
					UtilsJSON.objetoToJson(map), String.class);
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public String descargarrAutorizarRetencionElectronica(final String empresa, final String perCodigo,
			final String motCodigo, final String compNumero, final String tipoAmbiente) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("empresa", empresa);
			map.put("perCodigo", perCodigo);
			map.put("motCodigo", motCodigo);
			map.put("compNumero", compNumero);
			map.put("tipoAmbiente", tipoAmbiente);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return restTemplate.postForObject(conexionWS + "/descargarrAutorizarRetencionElectronica",
					UtilsJSON.objetoToJson(map), String.class);
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public String eliminarAnexoAnuladoTO(final AnxAnuladosTO anxAnuladosTO) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("anxAnuladosTO", anxAnuladosTO);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return restTemplate.postForObject(conexionWS + "/eliminarAnexoAnuladoTO", UtilsJSON.objetoToJson(map),
					String.class);
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public String eliminarAnexoNumeracionTO(final AnxNumeracionTO anxNumeracionTO) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("anxNumeracionTO", anxNumeracionTO);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return restTemplate.postForObject(conexionWS + "/eliminarAnexoNumeracionTO", UtilsJSON.objetoToJson(map),
					String.class);
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public String eliminarAnxVentas(final AnxVentaTO anxVentaTO) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("anxVentaTO", anxVentaTO);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return restTemplate.postForObject(conexionWS + "/eliminarAnxVentas", UtilsJSON.objetoToJson(map),
					String.class);
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	///////////////
	public String enviarAutorizarFacturaElectronica(final String empresa, final String vtaPerCodigo,
			final String vtaMotCodigo, final String vtaNumero, final String tipoAmbiente, final char accion) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("empresa", empresa);
			map.put("vtaPerCodigo", vtaPerCodigo);
			map.put("vtaMotCodigo", vtaMotCodigo);
			map.put("vtaNumero", vtaNumero);
			map.put("tipoAmbiente", tipoAmbiente);
			map.put("accion", accion);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return restTemplate.postForObject(conexionWS + "/enviarAutorizarFacturaElectronica",
					UtilsJSON.objetoToJson(map), String.class);
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public String enviarAutorizarFaturasElectronicaLote(final String empresa,
			final List<AnxListaVentasPendientesTO> listaEnviar) {
		final Map<String, Object> map = new HashMap<>();
		map.put("empresa", empresa);
		map.put("listaEnviar", listaEnviar);
		map.put("sisInfoTO", UtilAplication.getSisInfoTO());
		try {
			return restTemplate.postForObject(conexionWS + "/enviarAutorizarFaturasElectronicaLote",
					UtilsJSON.objetoToJson(map), String.class);
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	// Envio de Retenciones al SRI
	public String enviarAutorizarRetencionElectronica(final String empresa, final String perCodigo,
			final String motCodigo, final String compNumero, final String tipoAmbiente, final char accion) {
		try {

			final Map<String, Object> map = new HashMap<>();
			map.put("empresa", empresa);
			map.put("perCodigo", perCodigo);
			map.put("motCodigo", motCodigo);
			map.put("compNumero", compNumero);
			map.put("tipoAmbiente", tipoAmbiente);
			map.put("accion", accion);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return restTemplate.postForObject(conexionWS + "/enviarAutorizarRetencionElectronica",
					UtilsJSON.objetoToJson(map), String.class);
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public String enviarAutorizarRetencionElectronicaLote(final String empresa,
			final List<AnxListaRetencionesPendientesTO> listaEnviar) {
		final Map<String, Object> map = new HashMap<>();
		map.put("empresa", empresa);
		map.put("listaEnviar", listaEnviar);
		map.put("sisInfoTO", UtilAplication.getSisInfoTO());
		try {
			return restTemplate.postForObject(conexionWS + "/enviarAutorizarRetencionElectronicaLote",
					UtilsJSON.objetoToJson(map), String.class);
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public String enviarComprobantes(final String cedulaRuc, final List<ComprobanteElectronico> comprobantes,
			final String mes, final String anio) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("cedulaRuc", cedulaRuc);
			map.put("comprobantes", comprobantes);
			map.put("mes", mes);
			map.put("anio", anio);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return restTemplate.postForObject(conexionWS + "/enviarComprobantes", UtilsJSON.objetoToJson(map),
					String.class);
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public String enviarEmailComprobantesElectronicos(final String empresa, final String ePeriodo, final String eMotivo,
			final String eNumero, final String claveAcceso, final String nombreReporteJasper, final String XmlString) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("empresa", empresa);
			map.put("ePeriodo", ePeriodo);
			map.put("eMotivo", eMotivo);
			map.put("eNumero", eNumero);
			map.put("claveAcceso", claveAcceso);
			map.put("nombreReporteJasper", nombreReporteJasper);
			map.put("XmlString", XmlString);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return restTemplate.postForObject(conexionWS + "/enviarEmailComprobantesElectronicos",
					UtilsJSON.objetoToJson(map), String.class);

		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public String generarReporteComprobanteElectronico(final String empresa, final String claveAcceso,
			final String XmlString, final String nombreReporteJasper) {
		try {

			final Map<String, Object> map = new HashMap<>();
			map.put("empresa", empresa);
			map.put("claveAcceso", claveAcceso);
			map.put("XmlString", XmlString);
			map.put("nombreReporteJasper", nombreReporteJasper);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			final String rutaArchivo = "" + claveAcceso + ".jrprint";
			return UtilsRESTFul.postForFile(conexionWS + "/generarReporteComprobanteElectronico", map, rutaArchivo);
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public String generarReporteListadoDevolucionIva(final String fechaDesde, final String fechaHasta,
			final List<AnxFunListadoDevolucionIvaTO> anxFunListadoDevolucionIvaTOs) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("usuarioEmpresaReporteTO", UtilAplication.usuarioEmpresaReporteTO);
			map.put("fechaDesde", fechaDesde);
			map.put("fechaHasta", fechaHasta);
			map.put("anxFunListadoDevolucionIvaTOs", UtilsJSON.objetoToJson(anxFunListadoDevolucionIvaTOs));
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			final String rutaArchivo = "reportListadoPagos.jrprint";
			return UtilsRESTFul.postForFile(conexionWS + "/generarReporteListadoPagos", map, rutaArchivo);
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public String generarReporteListadoRetencionesPorNumero(
			final List<AnxListaRetencionesTO> listaAnxListaRetencionesTO) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("usuarioEmpresaReporteTO", UtilAplication.usuarioEmpresaReporteTO);
			map.put("listaAnxListaRetencionesTO", listaAnxListaRetencionesTO);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			final String rutaArchivo = "reportListadoPagos.jrprint";
			return UtilsRESTFul.postForFile(conexionWS + "/generarReporteListadoRetencionesPorNumero", map,
					rutaArchivo);
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public String generarReporteListadoRetencionesVentas(final String fechaDesde, final String fechaHasta,
			final List<AnxListadoRetencionesVentasTO> anxListadoRetencionesVentasTOs) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("usuarioEmpresaReporteTO", UtilAplication.usuarioEmpresaReporteTO);
			map.put("fechaDesde", fechaDesde);
			map.put("fechaHasta", fechaHasta);
			map.put("anxListadoRetencionesVentasTOs", anxListadoRetencionesVentasTOs);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			final String rutaArchivo = "reportListadoPagos.jrprint";
			return UtilsRESTFul.postForFile(conexionWS + "/generarReporteListadoRetencionesVentas", map, rutaArchivo);
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public String generarReporteTalonResumen(final String desde, final String hasta,
			final List<AnxTalonResumenTO> listaAnxTalonResumenTO) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("usuarioEmpresaReporteTO", UtilAplication.usuarioEmpresaReporteTO);
			map.put("desde", desde);
			map.put("hasta", hasta);
			map.put("listaAnxTalonResumenTO", listaAnxTalonResumenTO);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			final String rutaArchivo = "reporteTalonResumenCompras.jrprint";
			return UtilsRESTFul.postForFile(conexionWS + "/generarReporteTalonResumen", map, rutaArchivo);
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public String generarReporteTalonResumenVentas(final String desde, final String hasta,
			final List<AnxTalonResumenVentaTO> listaAnxTalonResumenVentaTO) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("usuarioEmpresaReporteTO", UtilAplication.usuarioEmpresaReporteTO);
			map.put("desde", desde);
			map.put("hasta", hasta);
			map.put("listaAnxTalonResumenVentaTO", listaAnxTalonResumenVentaTO);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			final String rutaArchivo = "reportListadoPagos.jrprint";
			return UtilsRESTFul.postForFile(conexionWS + "/generarReporteTalonResumenVentas", map, rutaArchivo);
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public AnxCompra getAnexoCompra(final String empresa, final String periodo, final String motivo,
			final String numeroCompra) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("empresa", empresa);
			map.put("periodo", periodo);
			map.put("motivo", motivo);
			map.put("numeroCompra", numeroCompra);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return restTemplate.postForObject(conexionWS + "/getAnexoCompra", UtilsJSON.objetoToJson(map),
					AnxCompra.class);
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public List<AnxCompraDetalleTO> getAnexoCompraDetalleTO(final String empresa, final String periodo,
			final String motivo, final String numeroCompra) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("empresa", empresa);
			map.put("periodo", periodo);
			map.put("motivo", motivo);
			map.put("numeroCompra", numeroCompra);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return Arrays.asList(restTemplate.postForObject(conexionWS + "/getAnexoCompraDetalleTO",
					UtilsJSON.objetoToJson(map), AnxCompraDetalleTO[].class));
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public List<AnxCompraFormaPagoTO> getAnexoCompraFormaPagoTO(final String empresa, final String periodo,
			final String motivo, final String numero) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("empresa", empresa);
			map.put("periodo", periodo);
			map.put("motivo", motivo);
			map.put("numero", numero);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return Arrays.asList(restTemplate.postForObject(conexionWS + "/getAnexoCompraFormaPagoTO",
					UtilsJSON.objetoToJson(map), AnxCompraFormaPagoTO[].class));
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public List<AnxCompraReembolsoTO> getAnexoCompraReembolsoTOs(final String empresa, final String periodo,
			final String motivo, final String numeroCompra) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("empresa", empresa);
			map.put("periodo", periodo);
			map.put("motivo", motivo);
			map.put("numeroCompra", numeroCompra);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return Arrays.asList(restTemplate.postForObject(conexionWS + "/getAnexoCompraReembolsoTOs",
					UtilsJSON.objetoToJson(map), AnxCompraReembolsoTO[].class));
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public AnxCompraTO getAnexoCompraTO(final String empresa, final String periodo, final String motivo,
			final String numeroCompra) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("empresa", empresa);
			map.put("periodo", periodo);
			map.put("motivo", motivo);
			map.put("numeroCompra", numeroCompra);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return restTemplate.postForObject(conexionWS + "/getAnexoCompraTO", UtilsJSON.objetoToJson(map),
					AnxCompraTO.class);
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public List<AnxConceptoTO> getAnexoConceptoTO() {
		try {
			return Arrays.asList(restTemplate.postForObject(conexionWS + "/getAnexoConceptoTO",
					UtilAplication.getSisInfoTO(), AnxConceptoTO[].class));
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public List<AnxFormaPagoTO> getAnexoFormaPagoTO(final Date fechaFactura) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("fechaFactura", fechaFactura);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return Arrays.asList(restTemplate.postForObject(conexionWS + "/getAnexoFormaPagoTO",
					UtilsJSON.objetoToJson(map), AnxFormaPagoTO[].class));
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public List<String> getAnexoFunListadoComprobantesPendientes(final String empresa, final String fechaDesde,
			final String fechaHasta) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("empresa", empresa);
			map.put("fechaDesde", fechaDesde);
			map.put("fechaHasta", fechaHasta);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return Arrays.asList(restTemplate.postForObject(conexionWS + "/getAnexoFunListadoComprobantesPendientes",
					UtilsJSON.objetoToJson(map), String[].class));
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public List<String> getAnexoFunListadoRetencionesHuerfanas(final String empresa, final String fechaDesde,
			final String fechaHasta) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("empresa", empresa);
			map.put("fechaDesde", fechaDesde);
			map.put("fechaHasta", fechaHasta);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return Arrays.asList(restTemplate.postForObject(conexionWS + "/getAnexoFunListadoRetencionesHuerfanas",
					UtilsJSON.objetoToJson(map), String[].class));
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public List<AnxListaRetencionesTO> getAnexoFunListadoRetencionesPorNumero(final String empresa,
			final String fechaDesde, final String fechaHasta, final String parametroEstado) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("empresa", empresa);
			map.put("fechaDesde", fechaDesde);
			map.put("fechaHasta", fechaHasta);
			map.put("parametroEstado", parametroEstado);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return Arrays.asList(restTemplate.postForObject(conexionWS + "/getAnexoFunListadoRetencionesPorNumero",
					UtilsJSON.objetoToJson(map), AnxListaRetencionesTO[].class));
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public List<AnxListaRetencionesFuenteIvaTO> getAnexoListaRetencionesFuenteIvaTO(final String empresa,
			final String fechaDesde, final String fechaHasta) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("empresa", empresa);
			map.put("fechaDesde", fechaDesde);
			map.put("fechaHasta", fechaHasta);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return Arrays.asList(restTemplate.postForObject(conexionWS + "/getAnexoListaRetencionesFuenteIvaTO",
					UtilsJSON.objetoToJson(map), AnxListaRetencionesFuenteIvaTO[].class));
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public List<AnxListaRetencionesPendientesTO> getAnexoListaRetencionesPendienteTO(final String empresa) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("empresa", empresa);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return Arrays.asList(restTemplate.postForObject(conexionWS + "/getAnexoListaRetencionesPendienteTO",
					UtilsJSON.objetoToJson(map), AnxListaRetencionesPendientesTO[].class));
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public List<AnxListaRetencionesRentaTO> getAnexoListaRetencionesRentaTO(final String empresa,
			final String fechaDesde, final String fechaHasta, final String pOrden) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("empresa", empresa);
			map.put("fechaDesde", fechaDesde);
			map.put("fechaHasta", fechaHasta);
			map.put("pOrden", pOrden);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return Arrays.asList(restTemplate.postForObject(conexionWS + "/getAnexoListaRetencionesRentaTO",
					UtilsJSON.objetoToJson(map), AnxListaRetencionesRentaTO[].class));
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public List<AnxListaRetencionesTO> getAnexoListaRetencionesTO(final String empresa, final String fechaDesde,
			final String fechaHasta) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("empresa", empresa);
			map.put("fechaDesde", fechaDesde);
			map.put("fechaHasta", fechaHasta);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return Arrays.asList(restTemplate.postForObject(conexionWS + "/getAnexoListaRetencionesTO",
					UtilsJSON.objetoToJson(map), AnxListaRetencionesTO[].class));
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public AnxNumeracionTO getAnexoNumeracionTO(final Integer secuencia) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("secuencia", secuencia);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return restTemplate.postForObject(conexionWS + "/getAnexoNumeracionTO", UtilsJSON.objetoToJson(map),
					AnxNumeracionTO.class);
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public List<AnxSustentoTO> getAnexoSustentoTO() {
		try {
			return Arrays.asList(restTemplate.postForObject(conexionWS + "/getAnexoSustentoTO",
					UtilAplication.getSisInfoTO(), AnxSustentoTO[].class));
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public List<AnxTalonResumenTO> getAnexoTalonResumenTO(final String empresa, final String fechaDesde,
			final String fechaHasta) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("empresa", empresa);
			map.put("fechaDesde", fechaDesde);
			map.put("fechaHasta", fechaHasta);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return Arrays.asList(restTemplate.postForObject(conexionWS + "/getAnexoTalonResumenTO",
					UtilsJSON.objetoToJson(map), AnxTalonResumenTO[].class));
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public List<AnxTalonResumenVentaTO> getAnexoTalonResumenVentaTO(final String empresa, final String fechaDesde,
			final String fechaHasta) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("empresa", empresa);
			map.put("fechaDesde", fechaDesde);
			map.put("fechaHasta", fechaHasta);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return Arrays.asList(restTemplate.postForObject(conexionWS + "/getAnexoTalonResumenVentaTO",
					UtilsJSON.objetoToJson(map), AnxTalonResumenVentaTO[].class));
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public List<AnxTipoComprobanteTO> getAnexoTipoComprobanteTO() {
		try {
			return Arrays.asList(restTemplate.postForObject(conexionWS + "/getAnexoTipoComprobanteTO",
					UtilAplication.getSisInfoTO(), AnxTipoComprobanteTO[].class));
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public List<AnxTipoListaTransaccionTO> getAnexoTipoListaTransaccionTO() {
		try {
			return Arrays.asList(restTemplate.postForObject(conexionWS + "/getAnexoTipoListaTransaccionTO",
					UtilAplication.getSisInfoTO(), AnxTipoListaTransaccionTO[].class));
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public AnxVentaTO getAnexoVentaTO(final String empresa, final String periodo, final String motivo,
			final String numeroVenta) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("empresa", empresa);
			map.put("periodo", periodo);
			map.put("motivo", motivo);
			map.put("numeroVenta", numeroVenta);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return restTemplate.postForObject(conexionWS + "/getAnexoVentaTO", UtilsJSON.objetoToJson(map),
					AnxVentaTO.class);
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public AnxAnuladosTO getAnxAnuladosTO(final Integer secuencial) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("secuencial", secuencial);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return restTemplate.postForObject(conexionWS + "/getAnxAnuladosTO", UtilsJSON.objetoToJson(map),
					AnxAnuladosTO.class);
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public AnxCuentasContablesTO getAnxCuentasContablesTO(final String empresa, final String nombreCuenta) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("empresa", empresa);
			map.put("nombreCuenta", nombreCuenta);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return restTemplate.postForObject(conexionWS + "/getAnxCuentasContablesTO", UtilsJSON.objetoToJson(map),
					AnxCuentasContablesTO.class);
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public List<AnxFunListadoDevolucionIvaTO> getAnxFunListadoDevolucionIvaTOs(final String empCodigo,
			final String desde, final String hasta) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("empCodigo", empCodigo);
			map.put("desde", desde);
			map.put("hasta", hasta);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return Arrays.asList(restTemplate.postForObject(conexionWS + "/getAnxFunListadoDevolucionIvaTOs",
					UtilsJSON.objetoToJson(map), AnxFunListadoDevolucionIvaTO[].class));
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public List<AnxListaComprobanteAnuladoTO> getAnxListaComprobanteAnuladoTO(final String empresa,
			final String fechaDesde, final String fechaHasta) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("empresa", empresa);
			map.put("fechaDesde", fechaDesde);
			map.put("fechaHasta", fechaHasta);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return Arrays.asList(restTemplate.postForObject(conexionWS + "/getAnxListaComprobanteAnuladoTO",
					UtilsJSON.objetoToJson(map), AnxListaComprobanteAnuladoTO[].class));
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public List<AnxListaConsolidadoRetencionesVentasTO> getAnxListaConsolidadoRetencionesVentasTO(final String empresa,
			final String fechaDesde, final String fechaHasta) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("empresa", empresa);
			map.put("fechaDesde", fechaDesde);
			map.put("fechaHasta", fechaHasta);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return Arrays.asList(restTemplate.postForObject(conexionWS + "/getAnxListaConsolidadoRetencionesVentasTO",
					UtilsJSON.objetoToJson(map), AnxListaConsolidadoRetencionesVentasTO[].class));
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public List<AnxListadoRetencionesVentasTO> getAnxListadoRetencionesVentasTO(final String empresa,
			final String tipoDocumento, final String establecimiento, final String puntoEmision,
			final String fechaDesde, final String fechaHasta) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("empresa", empresa);
			map.put("tipoDocumento", tipoDocumento);
			map.put("establecimiento", establecimiento);
			map.put("puntoEmision", puntoEmision);
			map.put("fechaDesde", fechaDesde);
			map.put("fechaHasta", fechaHasta);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return Arrays.asList(restTemplate.postForObject(conexionWS + "/getAnxListadoRetencionesVentasTO",
					UtilsJSON.objetoToJson(map), AnxListadoRetencionesVentasTO[].class));
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public List<AnxListaEstablecimientoRetencionesVentasTO> getAnxListaEstablecimientoRetencionesVentasTO(
			final String empresa, final String fechaDesde, final String fechaHasta) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("empresa", empresa);
			map.put("fechaDesde", fechaDesde);
			map.put("fechaHasta", fechaHasta);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return Arrays
					.asList(restTemplate.postForObject(conexionWS + "/getAnxListaEstablecimientoRetencionesVentasTO",
							UtilsJSON.objetoToJson(map), AnxListaEstablecimientoRetencionesVentasTO[].class));
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public AnxUltimaAutorizacionTO getAnxUltimaAutorizacionTO(final String empresa, final String proveedor,
			final String tipoDocumento, final String secuencial, final String fechaFactura) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("empresa", empresa);
			map.put("proveedor", proveedor);
			map.put("tipoDocumento", tipoDocumento);
			map.put("secuencial", secuencial);
			map.put("fechaFactura", fechaFactura);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return restTemplate.postForObject(conexionWS + "/getAnxUltimaAutorizacionTO", UtilsJSON.objetoToJson(map),
					AnxUltimaAutorizacionTO.class);
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public anxUrlWebServicesTO getAnxUrlWebServicesTO() {
		try {
			return restTemplate.postForObject(conexionWS + "/getAnxUrlWebServicesTO", UtilAplication.getSisInfoTO(),
					anxUrlWebServicesTO.class);

		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public RespuestaComprobante getAutorizadocionComprobantes(final String claveAcceso, final String tipoAmbiente) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("claveAcceso", claveAcceso);
			map.put("tipoAmbiente", tipoAmbiente);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return restTemplate.postForObject(conexionWS + "/getAutorizadocionComprobantes",
					UtilsJSON.objetoToJson(map), RespuestaComprobante.class);
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public String getCodigoAnxTipoTransaccionTO(final String tipoIdentificacion, final String tipoTransaccion) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("tipoIdentificacion", tipoIdentificacion);
			map.put("tipoTransaccion", tipoTransaccion);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return restTemplate.postForObject(conexionWS + "/getCodigoAnxTipoTransaccionTO",
					UtilsJSON.objetoToJson(map), String.class);
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public List<AnxProvinciaCantonTO> getComboAnxCantonTO(final String provincia) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("provincia", provincia);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return Arrays.asList(restTemplate.postForObject(conexionWS + "/getComboAnxCantonTO",
					UtilsJSON.objetoToJson(map), AnxProvinciaCantonTO[].class));
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public List<AnxProvinciaCantonTO> getComboAnxDpaCantonTO(final String codigoProvincia) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("codigoProvincia", codigoProvincia);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return Arrays.asList(restTemplate.postForObject(conexionWS + "/getComboAnxDpaCantonTO",
					UtilsJSON.objetoToJson(map), AnxProvinciaCantonTO[].class));
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public List<AnxProvinciaCantonTO> getComboAnxDpaProvinciaTO() {
		try {
			return Arrays.asList(restTemplate.postForObject(conexionWS + "/getComboAnxDpaProvinciaTO",
					UtilAplication.getSisInfoTO(), AnxProvinciaCantonTO[].class));
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public List<AnxPaisTO> getComboAnxPaisTO() {
		try {
			return Arrays.asList(restTemplate.postForObject(conexionWS + "/getComboAnxPaisTO",
					UtilAplication.getSisInfoTO(), AnxPaisTO[].class));
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public List<AnxProvinciaCantonTO> getComboAnxParroquiaTO(final String codigoProvincia, final String codigoCanton) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("codigoProvincia", codigoProvincia);
			map.put("codigoCanton", codigoCanton);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return Arrays.asList(restTemplate.postForObject(conexionWS + "/getComboAnxParroquiaTO",
					UtilsJSON.objetoToJson(map), AnxProvinciaCantonTO[].class));
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public List<AnxProvinciaCantonTO> getComboAnxProvinciaTO() {
		try {
			return Arrays.asList(restTemplate.postForObject(conexionWS + "/getComboAnxProvinciaTO",
					UtilAplication.getSisInfoTO(), AnxProvinciaCantonTO[].class));
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public String getCompAutorizacion(final String empCodigo, final String provCodigo, final String codTipoComprobante,
			final String numComplemento) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("empCodigo", empCodigo);
			map.put("provCodigo", provCodigo);
			map.put("codTipoComprobante", codTipoComprobante);
			map.put("numComplemento", numComplemento);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return restTemplate.postForObject(conexionWS + "/getCompAutorizacion", UtilsJSON.objetoToJson(map),
					String.class);
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	/*
	 * public AnxComprobanteElectronicoUtilTO
	 * getEnvioComprobanteElectronicosWS(final byte[] eXmlFirmado, final String
	 * claveAcceso, final String tipoAmbiente) { try { final Map<String, Object>
	 * map = new HashMap<>(); map.put("eXmlFirmado", eXmlFirmado);
	 * map.put("claveAcceso", claveAcceso); map.put("tipoAmbiente",
	 * tipoAmbiente); map.put("sisInfoTO", UtilAplication.getSisInfoTO());
	 * return restTemplate.postForObject(conexionWS +
	 * "/getEnvioComprobanteElectronicosWS", UtilsJSON.objetoToJson(map),
	 * AnxComprobanteElectronicoUtilTO.class); } catch (final
	 * RestClientException e) { UtilsExcepciones.enviarError(e,
	 * getClass().getName()); } return null; }
	 */

	public List<AnxFunRegistroDatosCrediticiosTO> getFunRegistroDatosCrediticiosTOs(final String codigoEmpresa,
			final String fechaDesde, final String fechaHasta) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("codigoEmpresa", codigoEmpresa);
			map.put("fechaDesde", fechaDesde);
			map.put("fechaHasta", fechaHasta);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return Arrays.asList(restTemplate.postForObject(conexionWS + "/getFunRegistroDatosCrediticiosTOs",
					UtilsJSON.objetoToJson(map), AnxFunRegistroDatosCrediticiosTO[].class));
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public List<AnxNumeracionTablaTO> getListaAnexoNumeracionTO(final String empresa) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("empresa", empresa);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return Arrays.asList(restTemplate.postForObject(conexionWS + "/getListaAnexoNumeracionTO",
					UtilsJSON.objetoToJson(map), AnxNumeracionTablaTO[].class));
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public List<AnxTipoComprobanteTablaTO> getListaAnexoTipoComprobanteTO(final String codigo) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("empresa", codigo);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return Arrays.asList(restTemplate.postForObject(conexionWS + "/getListaAnexoTipoComprobanteTO",
					UtilsJSON.objetoToJson(map), AnxTipoComprobanteTablaTO[].class));
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public List<AnxAnuladoTablaTO> getListaAnxAnuladoTablaTO(final String empresa) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("empresa", empresa);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return Arrays.asList(restTemplate.postForObject(conexionWS + "/getListaAnxAnuladoTablaTO",
					UtilsJSON.objetoToJson(map), AnxAnuladoTablaTO[].class));
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public List<AnxListadoCompraElectronicaTO> getListaAnxComprasElectronicaTO(final String empresa,
			final String estado, final String fechaDesde, final String fechaHasta) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("empresa", empresa);
			map.put("estado", estado);
			map.put("fechaDesde", fechaDesde);
			map.put("fechaHasta", fechaHasta);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return Arrays.asList(restTemplate.postForObject(conexionWS + "/getListaAnxComprasElectronicaTO",
					UtilsJSON.objetoToJson(map), AnxListadoCompraElectronicaTO[].class));
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public List<AnxConceptoComboTO> getListaAnxConceptoComboTO() {
		try {
			return Arrays.asList(restTemplate.postForObject(conexionWS + "/getListaAnxConceptoComboTO",
					UtilAplication.getSisInfoTO(), AnxConceptoComboTO[].class));
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public List<AnxConceptoComboTO> getListaAnxConceptoTO(final String fechaRetencion, final String busqueda) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("fechaRetencion", fechaRetencion);
			map.put("busqueda", busqueda);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return Arrays.asList(restTemplate.postForObject(conexionWS + "/getListaAnxConceptoTO",
					UtilsJSON.objetoToJson(map), AnxConceptoComboTO[].class));
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public List<AnxSustentoComboTO> getListaAnxSustentoComboTO(final String tipoComprobante) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("tipoComprobante", tipoComprobante);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return Arrays.asList(restTemplate.postForObject(conexionWS + "/getListaAnxSustentoComboTO",
					UtilsJSON.objetoToJson(map), AnxSustentoComboTO[].class));
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public List<AnxTipoComprobanteComboTO> getListaAnxTipoComprobanteComboTO(final String codigoTipoTransaccion) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("codigoTipoTransaccion", codigoTipoTransaccion);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return Arrays.asList(restTemplate.postForObject(conexionWS + "/getListaAnxTipoComprobanteComboTO",
					UtilsJSON.objetoToJson(map), AnxTipoComprobanteComboTO[].class));
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public List<AnxTipoComprobanteComboTO> getListaAnxTipoComprobanteComboTOCompleto() {
		return Arrays.asList(restTemplate.postForObject(conexionWS + "/getListaAnxTipoComprobanteComboTOCompleto",
				UtilAplication.getSisInfoTO(), AnxTipoComprobanteComboTO[].class));
	}

	public List<AnxTipoIdentificacionTO> getListaAnxTipoIdentificacionTO() {
		try {
			return Arrays.asList(restTemplate.postForObject(conexionWS + "/getListaAnxTipoIdentificacionTO",
					UtilAplication.getSisInfoTO(), AnxTipoIdentificacionTO[].class));
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public List<AnxListaVentaElectronicaTO> getListaAnxVentaElectronicaTO(final String empresa, final String estado,
			final String fechaDesde, final String fechaHasta, final String tipoDocumento) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("empresa", empresa);
			map.put("estado", estado);
			map.put("fechaDesde", fechaDesde);
			map.put("fechaHasta", fechaHasta);
			map.put("tipoDocumento", tipoDocumento);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return Arrays.asList(restTemplate.postForObject(conexionWS + "/getListaAnxVentaElectronicaTO",
					UtilsJSON.objetoToJson(map), AnxListaVentaElectronicaTO[].class));
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public List<AnxEstablecimientoComboTO> getListaEstablecimientoComboto(final String empresa) {
		final Map<String, Object> map = new HashMap<>();
		map.put("empresa", empresa);
		map.put("sisInfoTO", UtilAplication.getSisInfoTO());
		return Arrays.asList(restTemplate.postForObject(conexionWS + "/getEstablecimientos",
				UtilsJSON.objetoToJson(map), AnxEstablecimientoComboTO[].class));
	}

	// public List<AnxEstablecimientoComboTO>
	// getListaPuntosEmisionComboto(String empresa, String establecimiento)
	public List<AnxPuntoEmisionComboTO> getListaPuntosEmisionComboto(final String empresa,
			final String establecimiento) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("empresa", empresa);
			map.put("establecimiento", establecimiento);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return Arrays.asList(restTemplate.postForObject(conexionWS + "/getPuntosEmision",
					UtilsJSON.objetoToJson(map), AnxPuntoEmisionComboTO[].class));
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public List<AnxListaVentasPendientesTO> getListaVentasPendientes(final String empresa) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("empresa", empresa);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return Arrays.asList(restTemplate.postForObject(conexionWS + "/getListaVentasPendientes",
					UtilsJSON.objetoToJson(map), AnxListaVentasPendientesTO[].class));
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public AnxNumeracionLineaTO getNumeroAutorizacion(final String empresa, final String numeroRetencion,
			final String numeroComprobante, final String fechaVencimiento) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("empresa", empresa);
			map.put("numeroRetencion", numeroRetencion);
			map.put("numeroComprobante", numeroComprobante);
			map.put("fechaVencimiento", fechaVencimiento);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			final AnxNumeracionLineaTO anxNumeracionLineaTO = restTemplate.postForObject(
					conexionWS + "/getNumeroAutorizacion", UtilsJSON.objetoToJson(map), AnxNumeracionLineaTO.class);
			return anxNumeracionLineaTO;
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public String getUltimaNumeracionComprobante(final String empresa, final String comprobante,
			final String secuencial) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("empresa", empresa);
			map.put("comprobante", comprobante);
			map.put("secuencial", secuencial);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return restTemplate.postForObject(conexionWS + "/getUltimaNumeracionComprobante",
					UtilsJSON.objetoToJson(map), String.class);

		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public BigDecimal getValorAnxMontoMaximoConsumidorFinalTO(final String fechaFactura) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("fechaFactura", fechaFactura);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return restTemplate.postForObject(conexionWS + "/getValorAnxMontoMaximoConsumidorFinalTO",
					UtilsJSON.objetoToJson(map), BigDecimal.class);
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public BigDecimal getValorAnxPorcentajeIvaTO(final String fechaFactura) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("fechaFactura", fechaFactura);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return restTemplate.postForObject(conexionWS + "/getValorAnxPorcentajeIvaTO", UtilsJSON.objetoToJson(map),
					BigDecimal.class);
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public String getXmlComprobanteElectronico(final String empresa, final String ePeriodo, final String eMotivo,
			final String eNumero) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("empresa", empresa);
			map.put("ePeriodo", ePeriodo);
			map.put("eMotivo", eMotivo);
			map.put("eNumero", eNumero);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return restTemplate.postForObject(conexionWS + "/getXmlComprobanteElectronico", UtilsJSON.objetoToJson(map),
					String.class);
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public String getXmlComprobanteRetencion(final String empresa, final String ePeriodo, final String eMotivo,
			final String eNumero) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("empresa", empresa);
			map.put("ePeriodo", ePeriodo);
			map.put("eMotivo", eMotivo);
			map.put("eNumero", eNumero);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return restTemplate.postForObject(conexionWS + "/getXmlComprobanteRetencion", UtilsJSON.objetoToJson(map),
					String.class);

		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public String insertarAnexoAnuladoTO(final AnxAnuladosTO anxAnuladosTO) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("anxAnuladosTO", anxAnuladosTO);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return restTemplate.postForObject(conexionWS + "/insertarAnexoAnuladoTO", UtilsJSON.objetoToJson(map),
					String.class);
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public String insertarAnexoNumeracionTO(final AnxNumeracionTO anxNumeracionTO) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("anxNumeracionTO", anxNumeracionTO);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return restTemplate.postForObject(conexionWS + "/insertarAnexoNumeracionTO", UtilsJSON.objetoToJson(map),
					String.class);
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public String modificarAnexoAnuladoTO(final AnxAnuladosTO anxAnuladosTO) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("anxAnuladosTO", anxAnuladosTO);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return restTemplate.postForObject(conexionWS + "/modificarAnexoAnuladoTO", UtilsJSON.objetoToJson(map),
					String.class);
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public String modificarAnexoNumeracionTO(final AnxNumeracionTO anxNumeracionTO) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("anxNumeracionTO", anxNumeracionTO);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return restTemplate.postForObject(conexionWS + "/modificarAnexoNumeracionTO", UtilsJSON.objetoToJson(map),
					String.class);
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public List<ComprobanteElectronico> obtenerDocumentosPorCedulaRucMesAnio(final String cedulaRuc, final String mes,
			final String anio) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("cedulaRuc", cedulaRuc);
			map.put("mes", mes);
			map.put("anio", anio);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return new ArrayList<>(
					Arrays.asList(restTemplate.postForObject(conexionWS + "/obtenerDocumentosPorCedulaRucMesAnio",
							UtilsJSON.objetoToJson(map), ComprobanteElectronico[].class)));
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	/////////////////////////// Metodos WEB ///////////////////////////

	public String reestructurarRetencion(final AnxCompraTO anxCompraTO, final String usuario) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("anxCompraTO", UtilsJSON.objetoToJson(anxCompraTO));
			map.put("usuario", UtilsJSON.objetoToJson(usuario));
			map.put("sisInfoTO", UtilsJSON.objetoToJson(UtilAplication.getSisInfoTO()));
			return restTemplate.postForObject(conexionWS + "/reestructurarRetencion", UtilsJSON.objetoToJson(map),
					String.class);
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public String validarExistenciaReportesElectronicos(final String empresa, final String claveAcceso,
			final String nombreReporteJasper) {
		try {

			final Map<String, Object> map = new HashMap<>();
			map.put("empresa", empresa);
			map.put("claveAcceso", claveAcceso);
			map.put("nombreReporteJasper", nombreReporteJasper);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return restTemplate.postForObject(conexionWS + "/validarExistenciaReportesElectronicos",
					UtilsJSON.objetoToJson(map), String.class);
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	/////////////////////
	public String getAnexoTrasaccionalSimplificado(String empresa, String fechaDesde, String fechaHasta, String ruta) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("empresa", empresa);
			map.put("fechaDesde", fechaDesde);
			map.put("fechaHasta", fechaHasta);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return UtilsRESTFul.postForFile(conexionWS + "/getAnexoTrasaccionalSimplificado", map, ruta);
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public List<AnxFormulario104TO> getAnexoFormulario104(String empresa, String fechaDesde, String fechaHasta,
			AnxFormulario104ValoresTO valor) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("empresa", empresa);
			map.put("fechaDesde", fechaDesde);
			map.put("fechaHasta", fechaHasta);
			map.put("valor", valor);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return new ArrayList<>(Arrays.asList(restTemplate.postForObject(conexionWS + "/getAnexoFormulario104",
					UtilsJSON.objetoToJson(map), AnxFormulario104TO[].class)));
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public String getAnexoFormulario104XML(String empresa, String fechaDesde, String fechaHasta,
			AnxFormulario104ValoresTO valor, String ruta) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("empresa", empresa);
			map.put("fechaDesde", fechaDesde);
			map.put("fechaHasta", fechaHasta);
			map.put("valor", valor);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return UtilsRESTFul.postForFile(conexionWS + "/getAnexoFormulario104XML", map, ruta);
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

}
