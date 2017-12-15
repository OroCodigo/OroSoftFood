package ec.com.orocodigo.OroSoftWeb.delegate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import ec.com.orocodigo.OroSoftUtils.MensajeTO;
import ec.com.orocodigo.OroSoftUtils.Propiedades;
import ec.com.orocodigo.OroSoftUtils.RetornoTO;
import ec.com.orocodigo.OroSoftUtils.UtilsJSON;
import ec.com.orocodigo.OroSoftUtils.UtilsRESTFul;
import ec.com.orocodigo.OroSoftUtils.contabilidad.TO.ConBalanceComprobacionTO;
import ec.com.orocodigo.OroSoftUtils.contabilidad.TO.ConBalanceGeneralComparativoTO;
import ec.com.orocodigo.OroSoftUtils.contabilidad.TO.ConBalanceGeneralTO;
import ec.com.orocodigo.OroSoftUtils.contabilidad.TO.ConBalanceResultadoTO;
import ec.com.orocodigo.OroSoftUtils.contabilidad.TO.ConContableReporteTO;
import ec.com.orocodigo.OroSoftUtils.contabilidad.TO.ConContableTO;
import ec.com.orocodigo.OroSoftUtils.contabilidad.TO.ConCuentasFlujoDetalleTO;
import ec.com.orocodigo.OroSoftUtils.contabilidad.TO.ConCuentasFlujoTO;
import ec.com.orocodigo.OroSoftUtils.contabilidad.TO.ConCuentasTO;
import ec.com.orocodigo.OroSoftUtils.contabilidad.TO.ConDetalleTO;
import ec.com.orocodigo.OroSoftUtils.contabilidad.TO.ConDetalleTablaTO;
import ec.com.orocodigo.OroSoftUtils.contabilidad.TO.ConDiarioAuxiliarTO;
import ec.com.orocodigo.OroSoftUtils.contabilidad.TO.ConEstructuraFlujoTO;
import ec.com.orocodigo.OroSoftUtils.contabilidad.TO.ConEstructuraTO;
import ec.com.orocodigo.OroSoftUtils.contabilidad.TO.ConFunBalanceGeneralNecTO;
import ec.com.orocodigo.OroSoftUtils.contabilidad.TO.ConFunBalanceResultadosNecTO;
import ec.com.orocodigo.OroSoftUtils.contabilidad.TO.ConFunContablesVerificacionesComprasTO;
import ec.com.orocodigo.OroSoftUtils.contabilidad.TO.ConFunContablesVerificacionesTO;
import ec.com.orocodigo.OroSoftUtils.contabilidad.TO.ConFunIPPTO;
import ec.com.orocodigo.OroSoftUtils.contabilidad.TO.ConListaBalanceResultadosVsInventarioTO;
import ec.com.orocodigo.OroSoftUtils.contabilidad.TO.ConMayorAuxiliarTO;
import ec.com.orocodigo.OroSoftUtils.contabilidad.TO.ConMayorGeneralTO;
import ec.com.orocodigo.OroSoftUtils.contabilidad.TO.ConNumeracionTO;
import ec.com.orocodigo.OroSoftUtils.contabilidad.TO.ConTipoTO;
import ec.com.orocodigo.OroSoftUtils.contabilidad.TO.ListaConContableTO;
import ec.com.orocodigo.OroSoftUtils.contabilidad.TO.PersonaTO;
import ec.com.orocodigo.OroSoftUtils.contabilidad.entity.ConContable;
import ec.com.orocodigo.OroSoftUtils.contabilidad.entity.ConContablePK;
import ec.com.orocodigo.OroSoftUtils.contabilidad.entity.ConDetalle;
import ec.com.orocodigo.OroSoftUtils.contabilidad.entity.ConTipo;
import ec.com.orocodigo.OroSoftUtils.inventario.report.ReporteCompraDetalle;
import ec.com.orocodigo.OroSoftUtils.produccion.TO.PrdContabilizarCorridaTO;
import ec.com.orocodigo.OroSoftWeb.utils.UtilAplication;
import ec.com.orocodigo.OroSoftWeb.utils.UtilsExcepciones;

public class ContabilidadDelegate {

	private static ContabilidadDelegate grupoDelegate;

	public static ContabilidadDelegate getInstance() {
		if (grupoDelegate == null)
			try {
				grupoDelegate = new ContabilidadDelegate();
			} catch (final Exception e) {
				e.printStackTrace();
			}
		return grupoDelegate;
	}

	private final String conexionWS;

	private final RestTemplate restTemplate = new RestTemplate();

	private ContabilidadDelegate() throws Exception {
		final Properties propSistema = Propiedades.readPropiedades();
		conexionWS = "http://" + propSistema.getProperty("servidor.ip") + ":"
				+ propSistema.getProperty("servidor.puerto") + "/" + propSistema.getProperty("servidor.ubicacion")
				+ "/OroSoftWS/contabilidadController";
	}

	public String anularReversarConContable(final ConContablePK conContablePK, final boolean anularReversar) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("conContablePK", conContablePK);
			map.put("anularReversar", anularReversar);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return restTemplate.postForObject(conexionWS + "/anularReversarConContable", UtilsJSON.objetoToJson(map),
					String.class);
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public List<ConDetalleTO> buscarConContable(final String codEmpresa, final String perCodigo, final String tipCodigo,
			final String conNumero) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("codEmpresa", codEmpresa);
			map.put("perCodigo", perCodigo);
			map.put("tipCodigo", tipCodigo);
			map.put("conNumero", conNumero);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return Arrays.asList(restTemplate.postForObject(conexionWS + "/buscarConContable",
					UtilsJSON.objetoToJson(map), ConDetalleTO[].class));
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public Boolean buscarConDetallesActivosBiologicos(final String empresa) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("empresa", empresa);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return restTemplate.postForObject(conexionWS + "/buscarConDetallesActivosBiologicos",
					UtilsJSON.objetoToJson(map), Boolean.class);
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public int buscarConteoDetalleEliminarCuenta(final String empCodigo, final String cuentaCodigo) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("empCodigo", empCodigo);
			map.put("cuentaCodigo", cuentaCodigo);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return restTemplate.postForObject(conexionWS + "/buscarConteoDetalleEliminarCuenta",
					UtilsJSON.objetoToJson(map), int.class);
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return 0;
	}

	public ConDetalle buscarDetalleContable(final Long codDetalle) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("codDetalle", codDetalle);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return restTemplate.postForObject(conexionWS + "/buscarDetalleContable", UtilsJSON.objetoToJson(map),
					ConDetalle.class);
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public String desbloquearConContable(final String empresa, final String periodo, final String tipo,
			final String numero, final String usuario) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("empresa", empresa);
			map.put("periodo", periodo);
			map.put("tipo", tipo);
			map.put("numero", numero);
			map.put("usuario", usuario);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return restTemplate.postForObject(conexionWS + "/desbloquearConContable", UtilsJSON.objetoToJson(map),
					String.class);
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public String desmayorizarConContable(final ConContablePK conContablePK) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("conContablePK", conContablePK);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return restTemplate.postForObject(conexionWS + "/desmayorizarConContable", UtilsJSON.objetoToJson(map),
					String.class);
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public String eliminarConCuenta(final ConCuentasTO conCuentasTO) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("conCuentasTO", conCuentasTO);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return restTemplate.postForObject(conexionWS + "/eliminarConCuenta", UtilsJSON.objetoToJson(map),
					String.class);
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public String eliminarConCuentaFlujo(final ConCuentasFlujoTO conCuentasFlujoTO) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("conCuentasFlujoTO", conCuentasFlujoTO);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return restTemplate.postForObject(conexionWS + "/eliminarConCuentaFlujo", UtilsJSON.objetoToJson(map),
					String.class);
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public String eliminarConCuentaFlujoDetalle(final ConCuentasFlujoDetalleTO conCuentasFlujoDetalleTO) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("conCuentasFlujoDetalleTO", conCuentasFlujoDetalleTO);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return restTemplate.postForObject(conexionWS + "/eliminarConCuentaFlujoDetalle",
					UtilsJSON.objetoToJson(map), String.class);
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public boolean eliminarConTipo(final ConTipoTO conTipoTO) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("conTipoTO", conTipoTO);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return restTemplate.postForObject(conexionWS + "/eliminarConTipo", UtilsJSON.objetoToJson(map),
					boolean.class);
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return false;
	}

	public String generarReporteBalanceComprobacion(final String fechaDesde, final String fechaHasta,
			final List<ConBalanceComprobacionTO> listConBalanceComprobacionTO) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("usuarioEmpresaReporteTO", UtilAplication.usuarioEmpresaReporteTO);
			map.put("fechaDesde", fechaDesde);
			map.put("fechaHasta", fechaHasta);
			map.put("listConBalanceComprobacionTO", listConBalanceComprobacionTO);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			final String rutaArchivo = "reportContabilidadBalanceComprobacion.jrprint";
			return UtilsRESTFul.postForFile(conexionWS + "/generarReporteBalanceComprobacion", map, rutaArchivo);
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public String generarReporteBalanceGeneral(final String fechaHasta, final String codigoSector,
			final List<ConFunBalanceGeneralNecTO> listConFunBalanceGeneralNecTO,
			final List<ConBalanceGeneralTO> listConBalanceGeneralTO) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("usuarioEmpresaReporteTO", UtilAplication.usuarioEmpresaReporteTO);
			map.put("fechaHasta", fechaHasta);
			map.put("codigoSector", codigoSector);
			map.put("listConFunBalanceGeneralNecTO", listConFunBalanceGeneralNecTO);
			map.put("listConBalanceGeneralTO", listConBalanceGeneralTO);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			final String rutaArchivo = "reportContabilidadBalanceGeneral.jrprint";
			return UtilsRESTFul.postForFile(conexionWS + "/generarReporteBalanceGeneral", map, rutaArchivo);
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		} catch (final Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public String generarReporteBalanceGeneralComparativo(final String fechaAnterior, final String fechaActual,
			final String codigoSector, final List<ConBalanceGeneralComparativoTO> listConBalanceGeneralComparativoTO) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("usuarioEmpresaReporteTO", UtilAplication.usuarioEmpresaReporteTO);
			map.put("fechaAnterior", fechaAnterior);
			map.put("fechaActual", fechaActual);
			map.put("codigoSector", codigoSector);
			map.put("listConBalanceGeneralComparativoTO", listConBalanceGeneralComparativoTO);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			final String rutaArchivo = "reportContabilidadBalanceGeneralComparativo.jrprint";
			return UtilsRESTFul.postForFile(conexionWS + "/generarReporteBalanceGeneralComparativo", map, rutaArchivo);
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public String generarReporteBalanceResultado(final String fechaDesde, final String fechaHasta,
			final String codigoSector, final Integer columnasEstadosFinancieros,
			final List<ConFunBalanceResultadosNecTO> listConFunBalanceResultadosNecTO) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("usuarioEmpresaReporteTO", UtilAplication.usuarioEmpresaReporteTO);
			map.put("fechaDesde", fechaDesde);
			map.put("fechaHasta", fechaHasta);
			map.put("codigoSector", codigoSector);
			map.put("columnasEstadosFinancieros", columnasEstadosFinancieros);
			map.put("listConFunBalanceResultadosNecTO", listConFunBalanceResultadosNecTO);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			final String rutaArchivo = "reportContabilidadBalanceResultados.jrprint";
			return UtilsRESTFul.postForFile(conexionWS + "/generarReporteBalanceResultado", map, rutaArchivo);
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public String generarReporteCompraContableDetalle(final List<ReporteCompraDetalle> reporteCompraDetalles,
			final List<ConContableReporteTO> list) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("usuarioEmpresaReporteTO", UtilAplication.usuarioEmpresaReporteTO);
			map.put("reporteCompraDetalles", reporteCompraDetalles);
			map.put("list", list);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			final String rutaArchivo = "reportComprobanteCompraContable.jrprint";
			return UtilsRESTFul.postForFile(conexionWS + "/generarReporteCompraContableDetalle", map, rutaArchivo);
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public String generarReporteConListaBalanceResultadosVsInventario(final String fechaDesde, final String fechaHasta,
			final List<ConListaBalanceResultadosVsInventarioTO> listConListaBalanceResultadosVsInventarioTO) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("usuarioEmpresaReporteTO", UtilAplication.usuarioEmpresaReporteTO);
			map.put("fechaDesde", fechaDesde);
			map.put("fechaHasta", fechaHasta);
			map.put("listConListaBalanceResultadosVsInventarioTO", listConListaBalanceResultadosVsInventarioTO);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			final String rutaArchivo = "reportBalanceResultadoVSInventario.jrprint";
			return UtilsRESTFul.postForFile(conexionWS + "/generarReporteConListaBalanceResultadosVsInventario", map,
					rutaArchivo);
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public String generarReporteContableDetalle(final List<ConContableReporteTO> listConContableReporteTO) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("usuarioEmpresaReporteTO", UtilAplication.usuarioEmpresaReporteTO);
			map.put("listConContableReporteTO", listConContableReporteTO);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			final String rutaArchivo = "reportComprobanteContable.jrprint";
			return UtilsRESTFul.postForFile(conexionWS + "/generarReporteContableDetalle", map, rutaArchivo);
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public String generarReporteContablesVerificacionesCompras(final String fechaDesde, final String fechaHasta,
			final List<ConFunContablesVerificacionesComprasTO> listConFunContablesVerificacionesComprasTO) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("usuarioEmpresaReporteTO", UtilAplication.usuarioEmpresaReporteTO);
			map.put("fechaDesde", fechaDesde);
			map.put("fechaHasta", fechaHasta);
			map.put("listConFunContablesVerificacionesComprasTO", listConFunContablesVerificacionesComprasTO);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			final String rutaArchivo = "reportContabilidadMayorGeneral.jrprint";
			return UtilsRESTFul.postForFile(conexionWS + "/generarReporteContablesVerificacionesCompras", map,
					rutaArchivo);
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public String generarReporteContablesVerificacionesErrores(
			final List<ConFunContablesVerificacionesTO> listConFunContablesVerificacionesTO) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("usuarioEmpresaReporteTO", UtilAplication.usuarioEmpresaReporteTO);
			map.put("listConFunContablesVerificacionesTO", listConFunContablesVerificacionesTO);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			final String rutaArchivo = "ReporteContablesVerificacionesErrores.jrprint";
			return UtilsRESTFul.postForFile(conexionWS + "/generarReporteContablesVerificacionesErrores", map,
					rutaArchivo);
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public String generarReporteDiarioAuxiliar(final String fechaDesde, final String fechaHasta,
			final String codigoTipo, final List<ConDiarioAuxiliarTO> listConDiarioAuxiliarTO) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("usuarioEmpresaReporteTO", UtilAplication.usuarioEmpresaReporteTO);
			map.put("fechaDesde", fechaDesde);
			map.put("fechaHasta", fechaHasta);
			map.put("codigoTipo", codigoTipo);
			map.put("listConDiarioAuxiliarTO", listConDiarioAuxiliarTO);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			final String rutaArchivo = "reportContabilidadDiarioGeneral.jrprint";
			return UtilsRESTFul.postForFile(conexionWS + "/generarReporteDiarioAuxiliar", map, rutaArchivo);
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public String generarReporteMayorAuxiliar(final String fechaDesde, final String fechaHasta,
			final String cuentaContableDesde, final String cuentaContablePadreDesde, final String cuentaContableHasta,
			final String cuentaContablePadreHasta, final List<ConMayorAuxiliarTO> listConMayorAuxiliarTO) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("usuarioEmpresaReporteTO", UtilAplication.usuarioEmpresaReporteTO);
			map.put("fechaDesde", fechaDesde);
			map.put("fechaHasta", fechaHasta);
			map.put("cuentaContableDesde", cuentaContableDesde);
			map.put("cuentaContablePadreDesde", cuentaContablePadreDesde);
			map.put("cuentaContableHasta", cuentaContableHasta);
			map.put("cuentaContablePadreHasta", cuentaContablePadreHasta);
			map.put("listConMayorAuxiliarTO", listConMayorAuxiliarTO);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			final String rutaArchivo = "reportContabilidadMayorAuxiliar.jrprint";
			return UtilsRESTFul.postForFile(conexionWS + "/generarReporteMayorAuxiliar", map, rutaArchivo);

		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public String generarReporteMayorGeneral(final String fechaHasta, final String cuentaContable,
			final List<ConMayorGeneralTO> listConMayorGeneralTO) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("usuarioEmpresaReporteTO", UtilAplication.usuarioEmpresaReporteTO);
			map.put("fechaHasta", fechaHasta);
			map.put("cuentaContable", cuentaContable);
			map.put("listConMayorGeneralTO", listConMayorGeneralTO);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			final String rutaArchivo = "reportContabilidadMayorGeneral.jrprint";
			return UtilsRESTFul.postForFile(conexionWS + "/generarReporteMayorGeneral", map, rutaArchivo);
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public String generarReporteMayorGeneral(final String fechaHasta, final String cuentaContable,
			final Object[][] datos) {// List<ConMayorGeneralTO>
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("usuarioEmpresaReporteTO", UtilAplication.usuarioEmpresaReporteTO);
			map.put("fechaHasta", fechaHasta);
			map.put("cuentaContable", cuentaContable);
			map.put("datos", datos);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			final String rutaArchivo = "reportContabilidadMayorGeneral.jrprint";
			return UtilsRESTFul.postForFile(conexionWS + "/generarReporteMayorGeneralDatos", map, rutaArchivo);
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public RetornoTO getBalanceResultadoMensualizado(final String empresa, final String codigoSector,
			final String fechaInicio, final String fechaFin, final String usuario) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("empresa", empresa);
			map.put("codigoSector", codigoSector);
			map.put("fechaInicio", fechaInicio);
			map.put("fechaFin", fechaFin);
			map.put("usuario", usuario);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return restTemplate.postForObject(conexionWS + "/getBalanceResultadoMensualizado",
					UtilsJSON.objetoToJson(map), RetornoTO.class);
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public List<ConBalanceResultadoTO> getConBalanceResultadoTO(final String empresa, final String sector,
			final String fechaDesde, final String fechaHasta, final Boolean ocultarDetalle) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("empresa", empresa);
			map.put("sector", sector);
			map.put("fechaDesde", fechaDesde);
			map.put("fechaHasta", fechaHasta);
			map.put("ocultarDetalle", ocultarDetalle);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return Arrays.asList(restTemplate.postForObject(conexionWS + "/getConBalanceResultadoTO",
					UtilsJSON.objetoToJson(map), ConBalanceResultadoTO[].class));
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public ConContable getConContable(final ConContablePK conContablePK) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("conContablePK", conContablePK);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return restTemplate.postForObject(conexionWS + "/getConContable", UtilsJSON.objetoToJson(map),
					ConContable.class);
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public List<ConBalanceResultadoTO> getConFunBalanceFlujoEfectivo(final String empresa, final String sector,
			final String fechaDesde, final String fechaHasta) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("empresa", empresa);
			map.put("sector", sector);
			map.put("fechaDesde", fechaDesde);
			map.put("fechaHasta", fechaHasta);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return Arrays.asList(restTemplate.postForObject(conexionWS + "/getConFunBalanceFlujoEfectivo",
					UtilsJSON.objetoToJson(map), ConBalanceResultadoTO[].class));
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public List<ConFunBalanceGeneralNecTO> getConFunBalanceGeneralNecTO(final String empresa, final String sector,
			final String fecha, final Boolean ocultar, final Boolean ocultarDetalle,
			final Integer columnasEstadosFinancieros) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("empresa", empresa);
			map.put("sector", sector);
			map.put("fecha", fecha);
			map.put("ocultar", ocultar);
			map.put("ocultarDetalle", ocultarDetalle);
			map.put("columnasEstadosFinancieros", columnasEstadosFinancieros);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return Arrays.asList(restTemplate.postForObject(conexionWS + "/getConFunBalanceGeneralNecTO",
					UtilsJSON.objetoToJson(map), ConFunBalanceGeneralNecTO[].class));
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public List<ConFunBalanceResultadosNecTO> getConFunBalanceResultadosNecTO(final String empresa, final String sector,
			final String fechaDesde, final String fechaHasta, final Integer columnasEstadosFinancieros,
			final Boolean ocultarDetalle) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("empresa", empresa);
			map.put("sector", sector);
			map.put("fechaDesde", fechaDesde);
			map.put("fechaHasta", fechaHasta);
			map.put("columnasEstadosFinancieros", columnasEstadosFinancieros);
			map.put("ocultarDetalle", ocultarDetalle);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return Arrays.asList(restTemplate.postForObject(conexionWS + "/getConFunBalanceResultadosNecTO",
					UtilsJSON.objetoToJson(map), ConFunBalanceResultadosNecTO[].class));
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public List<ConFunContablesVerificacionesComprasTO> getConFunContablesVerificacionesComprasTOs(final String empresa,
			final String fechaInicio, final String fechaFin) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("empresa", empresa);
			map.put("fechaInicio", fechaInicio);
			map.put("fechaFin", fechaFin);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return Arrays.asList(restTemplate.postForObject(conexionWS + "/getConFunContablesVerificacionesComprasTOs",
					UtilsJSON.objetoToJson(map), ConFunContablesVerificacionesComprasTO[].class));
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public List<ConListaBalanceResultadosVsInventarioTO> getConListaBalanceResultadosVsInventarioTO(
			final String empresa, final String desde, final String hasta, final String sector) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("empresa", empresa);
			map.put("desde", desde);
			map.put("hasta", hasta);
			map.put("sector", sector);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return Arrays.asList(restTemplate.postForObject(conexionWS + "/getConListaBalanceResultadosVsInventarioTO",
					UtilsJSON.objetoToJson(map), ConListaBalanceResultadosVsInventarioTO[].class));
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public ConTipoTO getConTipoTO(final String empresa, final String tipCodigo) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("empresa", empresa);
			map.put("tipCodigo", tipCodigo);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return restTemplate.postForObject(conexionWS + "/getConTipoTO", UtilsJSON.objetoToJson(map),
					ConTipoTO.class);
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public List<ConBalanceGeneralComparativoTO> getFunBalanceGeneralComparativoTO(final String empresa,
			final String secCodigo, final String fechaAnterior, final String fechaActual, final Boolean ocultar) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("empresa", empresa);
			map.put("secCodigo", secCodigo);
			map.put("fechaAnterior", fechaAnterior);
			map.put("fechaActual", fechaActual);
			map.put("ocultar", ocultar);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return Arrays.asList(restTemplate.postForObject(conexionWS + "/getFunBalanceGeneralComparativoTO",
					UtilsJSON.objetoToJson(map), ConBalanceGeneralComparativoTO[].class));
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public List<ConBalanceGeneralTO> getFunBalanceGeneralTO(final String empresa, final String sector,
			final String fecha, final Boolean ocultar, final Boolean ocultarDetalle) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("empresa", empresa);
			map.put("sector", sector);
			map.put("fecha", fecha);
			map.put("ocultar", ocultar);
			map.put("ocultarDetalle", ocultarDetalle);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return Arrays.asList(restTemplate.postForObject(conexionWS + "/getFunBalanceGeneralTO",
					UtilsJSON.objetoToJson(map), ConBalanceGeneralTO[].class));
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public List<ConFunContablesVerificacionesTO> getFunContablesVerificacionesTO(final String empresa) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("empresa", empresa);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return Arrays.asList(restTemplate.postForObject(conexionWS + "/getFunContablesVerificacionesTO",
					UtilsJSON.objetoToJson(map), ConFunContablesVerificacionesTO[].class));
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public List<ConBalanceGeneralTO> getFunCuentasSobregiradasTO(final String empresa, final String secCodigo,
			final String fecha) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("empresa", empresa);
			map.put("secCodigo", secCodigo);
			map.put("fecha", fecha);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return Arrays.asList(restTemplate.postForObject(conexionWS + "/getFunCuentasSobregiradasTO",
					UtilsJSON.objetoToJson(map), ConBalanceGeneralTO[].class));
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public List<ConFunIPPTO> getFunListaIPP(final String empresa, final String fechaInicio, final String fechaFin,
			final String parametro) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("empresa", empresa);
			map.put("fechaInicio", fechaInicio);
			map.put("fechaFin", fechaFin);
			map.put("parametro", parametro);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return new ArrayList<>(Arrays.asList(restTemplate.postForObject(conexionWS + "/getFunListaIPP",
					UtilsJSON.objetoToJson(map), ConFunIPPTO[].class)));
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public List<PersonaTO> getFunPersonaTOs(final String empresa, final String busquedad) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("empresa", empresa);
			map.put("busquedad", busquedad);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return Arrays.asList(restTemplate.postForObject(conexionWS + "/getFunPersonaTOs",
					UtilsJSON.objetoToJson(map), PersonaTO[].class));
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public List<ConBalanceComprobacionTO> getListaBalanceComprobacionTO(final String empresa, final String codigoSector,
			final String fechaInicio, final String fechaFin, final Boolean ocultarDetalle) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("empresa", empresa);
			map.put("codigoSector", codigoSector);
			map.put("fechaInicio", fechaInicio);
			map.put("fechaFin", fechaFin);
			map.put("ocultarDetalle", ocultarDetalle);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return Arrays.asList(restTemplate.postForObject(conexionWS + "/getListaBalanceComprobacionTO",
					UtilsJSON.objetoToJson(map), ConBalanceComprobacionTO[].class));
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public List<ConCuentasTO> getListaBuscarConCuentas(final String empresa, final String buscar,
			final String ctaGrupo) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("empresa", empresa);
			map.put("buscar", buscar);
			map.put("ctaGrupo", ctaGrupo);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return Arrays.asList(restTemplate.postForObject(conexionWS + "/getListaBuscarConCuentasTO",
					UtilsJSON.objetoToJson(map), ConCuentasTO[].class));
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public List<ConCuentasFlujoTO> getListaBuscarConCuentasFlujo(final String empresa) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("empresa", empresa);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return Arrays.asList(restTemplate.postForObject(conexionWS + "/getListaBuscarConCuentasFlujoTO",
					UtilsJSON.objetoToJson(map), ConCuentasFlujoTO[].class));
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public List<ConCuentasFlujoTO> getListaBuscarConCuentasFlujoTO(final String empresa, final String buscar) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("empresa", empresa);
			map.put("buscar", buscar);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return Arrays.asList(restTemplate.postForObject(conexionWS + "/getListaBuscarConCuentasFlujoTO",
					UtilsJSON.objetoToJson(map), ConCuentasFlujoTO[].class));
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public List<ConContableTO> getListaConContable(final String empresa, final String perCodigo, final String tipCodigo,
			final String numContable) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("empresa", empresa);
			map.put("perCodigo", perCodigo);
			map.put("tipCodigo", tipCodigo);
			map.put("numContable", numContable);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return new ArrayList<>(Arrays.asList(restTemplate.postForObject(conexionWS + "/getListaConContableTO",
					UtilsJSON.objetoToJson(map), ConContableTO[].class)));
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public List<ConCuentasTO> getListaConCuentas(final String empresa) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("empresa", empresa);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return Arrays.asList(restTemplate.postForObject(conexionWS + "/getListaConCuentasTO",
					UtilsJSON.objetoToJson(map), ConCuentasTO[].class));
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public List<ConCuentasFlujoDetalleTO> getListaConCuentasFlujoDetalle(final String empresa) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("empresa", empresa);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return Arrays.asList(restTemplate.postForObject(conexionWS + "/getListaConCuentasFlujoDetalleTO",
					UtilsJSON.objetoToJson(map), ConCuentasFlujoDetalleTO[].class));
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public List<ConCuentasFlujoTO> getListaConCuentasFlujoTO(final String empresa) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("empresa", empresa);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return Arrays.asList(restTemplate.postForObject(conexionWS + "/getListaConCuentasFlujoTO",
					UtilsJSON.objetoToJson(map), ConCuentasFlujoTO[].class));
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public List<ConDetalleTablaTO> getListaConDetalle(final String empresa, final String periodo, final String tipo,
			final String numero) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("empresa", empresa);
			map.put("periodo", periodo);
			map.put("tipo", tipo);
			map.put("numero", numero);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return new ArrayList<>(Arrays.asList(restTemplate.postForObject(conexionWS + "/getListaConDetalleTO",
					UtilsJSON.objetoToJson(map), ConDetalleTablaTO[].class)));
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public List<ConEstructuraTO> getListaConEstructura(final String empresa) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("empresa", empresa);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return Arrays.asList(restTemplate.postForObject(conexionWS + "/getListaConEstructura",
					UtilsJSON.objetoToJson(map), ConEstructuraTO[].class));
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public List<ConEstructuraFlujoTO> getListaConEstructuraFlujo(final String empresa) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("empresa", empresa);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return Arrays.asList(restTemplate.postForObject(conexionWS + "/getListaConEstructuraFlujoTO",
					UtilsJSON.objetoToJson(map), ConEstructuraFlujoTO[].class));
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public List<ConNumeracionTO> getListaConNumeracionTO(final String empresa, final String periodo,
			final String tipo) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("empresa", empresa);
			map.put("periodo", periodo);
			map.put("tipo", tipo);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return Arrays.asList(restTemplate.postForObject(conexionWS + "/getListaConNumeracionTO",
					UtilsJSON.objetoToJson(map), ConNumeracionTO[].class));
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public List<ConTipo> getListaConTipo(final String empresa) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("empresa", empresa);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return new ArrayList<>(Arrays.asList(restTemplate.postForObject(conexionWS + "/getListaConTipo",
					UtilsJSON.objetoToJson(map), ConTipo[].class)));
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public List<ConTipoTO> getListaConTipoCartera(final String empresa) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("empresa", empresa);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return Arrays.asList(restTemplate.postForObject(conexionWS + "/getListaConTipoCarteraTO",
					UtilsJSON.objetoToJson(map), ConTipoTO[].class));
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public List<ConTipoTO> getListaConTipoCarteraAnticiposTO(final String empresa) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("empresa", empresa);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return Arrays.asList(restTemplate.postForObject(conexionWS + "/getListaConTipoCarteraAnticiposTO",
					UtilsJSON.objetoToJson(map), ConTipoTO[].class));
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public List<ConTipo> getListaConTipoReferencia(final String empresa, final String referencia) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("empresa", empresa);
			map.put("referencia", referencia);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return new ArrayList<>(Arrays.asList(restTemplate.postForObject(conexionWS + "/getListaConTipoReferencia",
					UtilsJSON.objetoToJson(map), ConTipo[].class)));
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public List<ConTipoTO> getListaConTipoRrhh(final String empresa) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("empresa", empresa);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return Arrays.asList(restTemplate.postForObject(conexionWS + "/getListaConTipoRrhhTO",
					UtilsJSON.objetoToJson(map), ConTipoTO[].class));
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public List<ConTipoTO> getListaConTipoTO(final String empresa) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("empresa", empresa);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return Arrays.asList(restTemplate.postForObject(conexionWS + "/getListaConTipoTO",
					UtilsJSON.objetoToJson(map), ConTipoTO[].class));
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public List<ConDiarioAuxiliarTO> getListaDiarioAuxiliarTO(final String empresa, final String codigoTipo,
			final String fechaInicio, final String fechaFin) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("empresa", empresa);
			map.put("codigoTipo", codigoTipo);
			map.put("fechaInicio", fechaInicio);
			map.put("fechaFin", fechaFin);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return Arrays.asList(restTemplate.postForObject(conexionWS + "/getListaDiarioAuxiliarTO",
					UtilsJSON.objetoToJson(map), ConDiarioAuxiliarTO[].class));
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public MensajeTO getListaInvConsultaConsumosPendientes(final String empCodigo, final String fechaDesde,
			final String fechaHasta) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("empCodigo", empCodigo);
			map.put("fechaDesde", fechaDesde);
			map.put("fechaHasta", fechaHasta);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return restTemplate.postForObject(conexionWS + "/getListaInvConsultaConsumosPendientes",
					UtilsJSON.objetoToJson(map), MensajeTO.class);
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public List<ConMayorAuxiliarTO> getListaMayorAuxiliarTO(final String empresa, final String codigoCuentaDesde,
			final String codigoCuentaHasta, final String fechaInicio, final String fechaFin, final String sector) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("empresa", empresa);
			map.put("codigoCuentaDesde", codigoCuentaDesde);
			map.put("codigoCuentaHasta", codigoCuentaHasta);
			map.put("fechaInicio", fechaInicio);
			map.put("fechaFin", fechaFin);
			map.put("sector", sector);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return Arrays.asList(restTemplate.postForObject(conexionWS + "/getListaMayorAuxiliarTO",
					UtilsJSON.objetoToJson(map), ConMayorAuxiliarTO[].class));
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public List<ConMayorGeneralTO> getListaMayorGeneralTO(final String empresa, final String codigoSector,
			final String codigoCuenta, final String fechaFin) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("empresa", empresa);
			map.put("codigoSector", codigoSector);
			map.put("codigoCuenta", codigoCuenta);
			map.put("fechaFin", fechaFin);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return Arrays.asList(restTemplate.postForObject(conexionWS + "/getListaMayorGeneralTO",
					UtilsJSON.objetoToJson(map), ConMayorGeneralTO[].class));
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public List<ListaConContableTO> getListConContableTO(final String empresa, final String periodo, final String tipo,
			final String busqueda, final String referencia, final String nRegistros) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("empresa", empresa);
			map.put("periodo", periodo);
			map.put("tipo", tipo);
			map.put("busqueda", busqueda);
			map.put("referencia", referencia);
			map.put("nRegistros", nRegistros);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return new ArrayList<>(Arrays.asList(restTemplate.postForObject(conexionWS + "/getListConContableTO",
					UtilsJSON.objetoToJson(map), ListaConContableTO[].class)));
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public List<ConDetalle> getListConDetalle(final ConContablePK conContablePK) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("conContablePK", conContablePK);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return new ArrayList<>(Arrays.asList(restTemplate.postForObject(conexionWS + "/getListConDetalle",
					UtilsJSON.objetoToJson(map), ConDetalle[].class)));
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public List<ConCuentasTO> getRangoCuentasTO(final String empresa, final String codigoCuentaDesde,
			final String codigoCuentaHasta, final int largoCuenta, final String usuario) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("empresa", empresa);
			map.put("codigoCuentaDesde", codigoCuentaDesde);
			map.put("codigoCuentaHasta", codigoCuentaHasta);
			map.put("largoCuenta", largoCuenta);
			map.put("usuario", usuario);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return Arrays.asList(restTemplate.postForObject(conexionWS + "/getRangoCuentasTO",
					UtilsJSON.objetoToJson(map), ConCuentasTO[].class));
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public MensajeTO insertarConContable(final ConContableTO conContableTO,
			final List<ConDetalleTO> listaConDetalleTO) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("conContableTO", conContableTO);
			map.put("listaConDetalleTO", listaConDetalleTO);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return restTemplate.postForObject(conexionWS + "/insertarConContable", UtilsJSON.objetoToJson(map),
					MensajeTO.class);
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public MensajeTO insertarConContableCierreCuentas(final ConContableTO conContableTO, final String centroProduccion,
			final String conNumeroContable) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("conContableTO", conContableTO);
			map.put("centroProduccion", centroProduccion);
			map.put("conNumeroContable", conNumeroContable);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return restTemplate.postForObject(conexionWS + "/insertarConContableCierreCuentas",
					UtilsJSON.objetoToJson(map), MensajeTO.class);
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public boolean insertarConCuenta(final ConCuentasTO conCuentasTO) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("conCuentasTO", conCuentasTO);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return restTemplate.postForObject(conexionWS + "/insertarConCuenta", UtilsJSON.objetoToJson(map),
					boolean.class);
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return false;
	}

	public boolean insertarConCuentaFlujo(final ConCuentasFlujoTO conCuentasFlujoTO) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("conCuentasFlujoTO", conCuentasFlujoTO);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return restTemplate.postForObject(conexionWS + "/insertarConCuentaFlujo", UtilsJSON.objetoToJson(map),
					boolean.class);
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return false;
	}

	public boolean insertarConCuentaFlujoDetalle(final ConCuentasFlujoDetalleTO conCuentasFlujoDetalleTO) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("conCuentasFlujoDetalleTO", conCuentasFlujoDetalleTO);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return restTemplate.postForObject(conexionWS + "/insertarConCuentaFlujoDetalle",
					UtilsJSON.objetoToJson(map), boolean.class);
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return false;
	}

	public boolean insertarConTipo(final ConTipoTO conTipoTO) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("conTipoTO", conTipoTO);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return restTemplate.postForObject(conexionWS + "/insertarConTipo", UtilsJSON.objetoToJson(map),
					boolean.class);
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return false;
	}

	public MensajeTO insertarModificarContabilizarCorrida(final String empresa, final String desde, final String hasta,
			final List<PrdContabilizarCorridaTO> listaContabilizarCorrida) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("empresa", empresa);
			map.put("desde", desde);
			map.put("hasta", hasta);
			map.put("listaContabilizarCorrida", listaContabilizarCorrida);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return restTemplate.postForObject(conexionWS + "/insertarModificarContabilizarCorrida",
					UtilsJSON.objetoToJson(map), MensajeTO.class);
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	/////////////////////////// Metodos WEB ///////////////////////////

	public MensajeTO insertarModificarContable(final ConContable conContable, final List<ConDetalle> listaConDetalle) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("conContable", conContable);
			map.put("listaConDetalle", listaConDetalle);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return restTemplate.postForObject(conexionWS + "/insertarModificarContable", UtilsJSON.objetoToJson(map),
					MensajeTO.class);
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public MensajeTO insertarModificarIPP(final String empresa, final String fechaDesde, final String fechaHasta,
			final String tipo, final Date fechaHoraBusqueda) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("empresa", empresa);
			map.put("fechaDesde", fechaDesde);
			map.put("fechaHasta", fechaHasta);
			map.put("tipo", tipo);
			map.put("fechaHoraBusqueda", fechaHoraBusqueda);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return restTemplate.postForObject(conexionWS + "/insertarModificarIPP", UtilsJSON.objetoToJson(map),
					MensajeTO.class);
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public boolean modificarConCuenta(final ConCuentasTO conCuentasTO, final String codigoCambiarLlave) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("conCuentasTO", conCuentasTO);
			map.put("codigoCambiarLlave", codigoCambiarLlave);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return restTemplate.postForObject(conexionWS + "/modificarConCuenta", UtilsJSON.objetoToJson(map),
					boolean.class);
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return false;
	}

	public boolean modificarConCuentaFlujo(final ConCuentasFlujoTO conCuentasFlujoTO, final String codigoCambiarLlave) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("conCuentasFlujoTO", conCuentasFlujoTO);
			map.put("codigoCambiarLlave", codigoCambiarLlave);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return restTemplate.postForObject(conexionWS + "/modificarConCuentaFlujo", UtilsJSON.objetoToJson(map),
					boolean.class);
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return false;
	}

	public boolean modificarConCuentaFlujoDetalle(final ConCuentasFlujoDetalleTO conCuentasFlujoDetalleTO,
			final String codigoCambiarLlave, final char formaPagoAnterior) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("conCuentasFlujoDetalleTO", conCuentasFlujoDetalleTO);
			map.put("codigoCambiarLlave", codigoCambiarLlave);
			map.put("formaPagoAnterior", formaPagoAnterior);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return restTemplate.postForObject(conexionWS + "/modificarConCuentaFlujoDetalle",
					UtilsJSON.objetoToJson(map), boolean.class);
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return false;
	}

	public boolean modificarConTipo(final ConTipoTO conTipoTO) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("conTipoTO", conTipoTO);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return restTemplate.postForObject(conexionWS + "/modificarConTipo", UtilsJSON.objetoToJson(map),
					boolean.class);
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return false;
	}

	public String restaurarConContable(final ConContablePK conContablePK) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("conContablePK", conContablePK);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return restTemplate.postForObject(conexionWS + "/restaurarConContable", UtilsJSON.objetoToJson(map),
					String.class);
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public List<String> validarChequesConciliados(final String periodo, final String conTipo, final String conNumero) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("periodo", periodo);
			map.put("conTipo", conTipo);
			map.put("conNumero", conNumero);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return restTemplate.postForObject(conexionWS + "/validarChequesConciliados", UtilsJSON.objetoToJson(map),
					List.class);
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public MensajeTO validarContables(final String periodo, final String conTipo, final String conNumero,
			final String accionUsuario, final String bandera) {
		try {

			final Map<String, Object> map = new HashMap<>();
			map.put("periodo", periodo);
			map.put("conTipo", conTipo);
			map.put("conNumero", conNumero);
			map.put("accionUsuario", accionUsuario);
			map.put("bandera", bandera);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return restTemplate.postForObject(conexionWS + "/validarContables", UtilsJSON.objetoToJson(map),
					MensajeTO.class);
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

}
