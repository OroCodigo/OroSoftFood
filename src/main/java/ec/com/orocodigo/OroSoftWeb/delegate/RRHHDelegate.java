package ec.com.orocodigo.OroSoftWeb.delegate;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
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
import ec.com.orocodigo.OroSoftUtils.contabilidad.entity.ConContable;
import ec.com.orocodigo.OroSoftUtils.contabilidad.entity.ConContablePK;
import ec.com.orocodigo.OroSoftUtils.rrhh.TO.RhAnticipoMotivoTO;
import ec.com.orocodigo.OroSoftUtils.rrhh.TO.RhAnulacionesTO;
import ec.com.orocodigo.OroSoftUtils.rrhh.TO.RhAvisoEntradaTO;
import ec.com.orocodigo.OroSoftUtils.rrhh.TO.RhBonoConceptoTO;
import ec.com.orocodigo.OroSoftUtils.rrhh.TO.RhCategoriaTO;
import ec.com.orocodigo.OroSoftUtils.rrhh.TO.RhComboBonoConceptoTO;
import ec.com.orocodigo.OroSoftUtils.rrhh.TO.RhComboCategoriaTO;
import ec.com.orocodigo.OroSoftUtils.rrhh.TO.RhComboFormaPagoBeneficioSocialTO;
import ec.com.orocodigo.OroSoftUtils.rrhh.TO.RhComboFormaPagoTO;
import ec.com.orocodigo.OroSoftUtils.rrhh.TO.RhContableTO;
import ec.com.orocodigo.OroSoftUtils.rrhh.TO.RhCtaIessTO;
import ec.com.orocodigo.OroSoftUtils.rrhh.TO.RhDetalleVacionesPagadasGozadasTO;
import ec.com.orocodigo.OroSoftUtils.rrhh.TO.RhEmpleadoDescuentosFijosTO;
import ec.com.orocodigo.OroSoftUtils.rrhh.TO.RhEmpleadoTO;
import ec.com.orocodigo.OroSoftUtils.rrhh.TO.RhFormaPagoBeneficioSocialTO;
import ec.com.orocodigo.OroSoftUtils.rrhh.TO.RhFormaPagoTO;
import ec.com.orocodigo.OroSoftUtils.rrhh.TO.RhFormulario107PeriodoFiscalTO;
import ec.com.orocodigo.OroSoftUtils.rrhh.TO.RhFunFormulario107TO;
import ec.com.orocodigo.OroSoftUtils.rrhh.TO.RhFunFormulario107_2013TO;
import ec.com.orocodigo.OroSoftUtils.rrhh.TO.RhFunListadoEmpleadosTO;
import ec.com.orocodigo.OroSoftUtils.rrhh.TO.RhFunPlantillaSueldosLotePreliminarTO;
import ec.com.orocodigo.OroSoftUtils.rrhh.TO.RhFunPlantillaSueldosLoteTO;
import ec.com.orocodigo.OroSoftUtils.rrhh.TO.RhFunUtilidadesCalcularTO;
import ec.com.orocodigo.OroSoftUtils.rrhh.TO.RhFunXiiiSueldoCalcularTO;
import ec.com.orocodigo.OroSoftUtils.rrhh.TO.RhFunXiiiSueldoConsultarTO;
import ec.com.orocodigo.OroSoftUtils.rrhh.TO.RhFunXivSueldoCalcularTO;
import ec.com.orocodigo.OroSoftUtils.rrhh.TO.RhFunXivSueldoConsultarTO;
import ec.com.orocodigo.OroSoftUtils.rrhh.TO.RhListaBonoConceptoTO;
import ec.com.orocodigo.OroSoftUtils.rrhh.TO.RhListaConsolidadoAnticiposPrestamosTO;
import ec.com.orocodigo.OroSoftUtils.rrhh.TO.RhListaConsolidadoBonosTO;
import ec.com.orocodigo.OroSoftUtils.rrhh.TO.RhListaConsolidadoRolesTO;
import ec.com.orocodigo.OroSoftUtils.rrhh.TO.RhListaDetalleAnticiposLoteTO;
import ec.com.orocodigo.OroSoftUtils.rrhh.TO.RhListaDetalleAnticiposPrestamosTO;
import ec.com.orocodigo.OroSoftUtils.rrhh.TO.RhListaDetalleAnticiposTO;
import ec.com.orocodigo.OroSoftUtils.rrhh.TO.RhListaDetalleBonosLoteTO;
import ec.com.orocodigo.OroSoftUtils.rrhh.TO.RhListaDetalleBonosTO;
import ec.com.orocodigo.OroSoftUtils.rrhh.TO.RhListaDetallePrestamosTO;
import ec.com.orocodigo.OroSoftUtils.rrhh.TO.RhListaDetalleRolesTO;
import ec.com.orocodigo.OroSoftUtils.rrhh.TO.RhListaDetalleViaticosTO;
import ec.com.orocodigo.OroSoftUtils.rrhh.TO.RhListaEmpleadoLoteTO;
import ec.com.orocodigo.OroSoftUtils.rrhh.TO.RhListaEmpleadoTO;
import ec.com.orocodigo.OroSoftUtils.rrhh.TO.RhListaFormaPagoBeneficioSocialTO;
import ec.com.orocodigo.OroSoftUtils.rrhh.TO.RhListaFormaPagoTO;
import ec.com.orocodigo.OroSoftUtils.rrhh.TO.RhListaProvisionesTO;
import ec.com.orocodigo.OroSoftUtils.rrhh.TO.RhListaRolSaldoEmpleadoDetalladoTO;
import ec.com.orocodigo.OroSoftUtils.rrhh.TO.RhListaSaldoConsolidadoAnticiposPrestamosTO;
import ec.com.orocodigo.OroSoftUtils.rrhh.TO.RhListaSaldoConsolidadoBonosTO;
import ec.com.orocodigo.OroSoftUtils.rrhh.TO.RhListaSaldoConsolidadoSueldosPorPagarTO;
import ec.com.orocodigo.OroSoftUtils.rrhh.TO.RhListaSaldoIndividualAnticiposPrestamosTO;
import ec.com.orocodigo.OroSoftUtils.rrhh.TO.RhPreavisoAnticiposPrestamosSueldoPichinchaTO;
import ec.com.orocodigo.OroSoftUtils.rrhh.TO.RhPreavisoAnticiposPrestamosSueldoTO;
import ec.com.orocodigo.OroSoftUtils.rrhh.TO.RhRolSueldoEmpleadoTO;
import ec.com.orocodigo.OroSoftUtils.rrhh.TO.RhSalarioDignoTO;
import ec.com.orocodigo.OroSoftUtils.rrhh.TO.RhUtilidadesPeriodoTO;
import ec.com.orocodigo.OroSoftUtils.rrhh.TO.RhVacacionesTO;
import ec.com.orocodigo.OroSoftUtils.rrhh.TO.RhXiiiSueldoPeriodoTO;
import ec.com.orocodigo.OroSoftUtils.rrhh.TO.RhXivSueldoPeriodoTO;
import ec.com.orocodigo.OroSoftUtils.rrhh.entity.RhAnticipo;
import ec.com.orocodigo.OroSoftUtils.rrhh.entity.RhAnticipoMotivo;
import ec.com.orocodigo.OroSoftUtils.rrhh.entity.RhBono;
import ec.com.orocodigo.OroSoftUtils.rrhh.entity.RhBonoMotivo;
import ec.com.orocodigo.OroSoftUtils.rrhh.entity.RhEmpleado;
import ec.com.orocodigo.OroSoftUtils.rrhh.entity.RhEmpleadoDescuentosFijos;
import ec.com.orocodigo.OroSoftUtils.rrhh.entity.RhEmpleadoPK;
import ec.com.orocodigo.OroSoftUtils.rrhh.entity.RhParametros;
import ec.com.orocodigo.OroSoftUtils.rrhh.entity.RhPrestamo;
import ec.com.orocodigo.OroSoftUtils.rrhh.entity.RhPrestamoMotivo;
import ec.com.orocodigo.OroSoftUtils.rrhh.entity.RhRol;
import ec.com.orocodigo.OroSoftUtils.rrhh.entity.RhRolMotivo;
import ec.com.orocodigo.OroSoftUtils.rrhh.entity.RhUtilidadMotivo;
import ec.com.orocodigo.OroSoftUtils.rrhh.entity.RhUtilidades;
import ec.com.orocodigo.OroSoftUtils.rrhh.entity.RhXiiiSueldo;
import ec.com.orocodigo.OroSoftUtils.rrhh.entity.RhXiiiSueldoMotivo;
import ec.com.orocodigo.OroSoftUtils.rrhh.entity.RhXivSueldo;
import ec.com.orocodigo.OroSoftUtils.rrhh.entity.RhXivSueldoMotivo;
import ec.com.orocodigo.OroSoftUtils.rrhh.report.ReporteAnticipoPrestamoXIIISueldo;
import ec.com.orocodigo.OroSoftUtils.rrhh.report.ReporteConsultaAnticiposLote;
import ec.com.orocodigo.OroSoftUtils.rrhh.report.ReporteConsultaBonosLote;
import ec.com.orocodigo.OroSoftUtils.rrhh.report.ReporteEmpleado;
import ec.com.orocodigo.OroSoftUtils.rrhh.report.ReportesRol;
import ec.com.orocodigo.OroSoftWeb.utils.UtilAplication;
import ec.com.orocodigo.OroSoftWeb.utils.UtilsExcepciones;

public class RRHHDelegate {

	private static RRHHDelegate grupoDelegate;

	public static RRHHDelegate getInstance() {
		if (grupoDelegate == null)
			try {
				grupoDelegate = new RRHHDelegate();
			} catch (final Exception e) {
				e.printStackTrace();
			}

		return grupoDelegate;
	}

	private final String conexionWS;

	private final RestTemplate restTemplate = new RestTemplate();

	private RRHHDelegate() throws Exception {
		final Properties propSistema = Propiedades.readPropiedades();
		conexionWS = "http://" + propSistema.getProperty("servidor.ip") + ":"
				+ propSistema.getProperty("servidor.puerto") + "/" + propSistema.getProperty("servidor.ubicacion")
				+ "/OroSoftWS/RRHHController";
	}

	public String accionRhAnticipoMotivo(final RhAnticipoMotivoTO rhAnticipoMotivoTO, final char accion) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("rhAnticipoMotivoTO", rhAnticipoMotivoTO);
			map.put("accion", accion);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return restTemplate.postForObject(conexionWS + "/accionRhAnticipoMotivo", UtilsJSON.objetoToJson(map),
					String.class);
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public String accionRhAvisosEntrada(final RhAvisoEntradaTO rhAvisoEntradaTO, final char accion) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("rhAvisoEntradaTO", rhAvisoEntradaTO);
			map.put("accion", accion);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return restTemplate.postForObject(conexionWS + "/accionRhAvisosEntrada", UtilsJSON.objetoToJson(map),
					String.class);
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public String accionRhCategoria(final RhCategoriaTO rhCategoriaTO, final char accion) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("rhCategoriaTO", rhCategoriaTO);
			map.put("accion", accion);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return restTemplate.postForObject(conexionWS + "/accionRhCategoria", map, String.class);
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public String accionRhFormaPago(final RhFormaPagoTO rhFormaPagoTO, final char accion) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("rhFormaPagoTO", rhFormaPagoTO);
			map.put("accion", accion);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return restTemplate.postForObject(conexionWS + "/accionRhFormaPago", UtilsJSON.objetoToJson(map),
					String.class);
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public String accionRhFormaPagoBeneficioSocial(final RhFormaPagoBeneficioSocialTO rhFormaPagoBeneficioSocialTO,
			final char accion) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("rhFormaPagoBeneficioSocialTO", rhFormaPagoBeneficioSocialTO);
			map.put("accion", accion);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return restTemplate.postForObject(conexionWS + "/accionRhFormaPagoBeneficioSocial",
					UtilsJSON.objetoToJson(map), String.class);
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public String accionRhUtilidadesPeriodo(final RhUtilidadesPeriodoTO rhUtilidadesPeriodoTO, final char accion) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("rhUtilidadesPeriodoTO", rhUtilidadesPeriodoTO);
			map.put("accion", accion);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return restTemplate.postForObject(conexionWS + "/accionRhUtilidadesPeriodo", UtilsJSON.objetoToJson(map),
					String.class);
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public String accionRhXiiiSueldoPeriodo(final RhXiiiSueldoPeriodoTO rhXiiiSueldoPeriodoTO,
			final String empresaCodigo, final String usuarioCodigo, final char accion) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("rhXiiiSueldoPeriodoTO", rhXiiiSueldoPeriodoTO);
			map.put("empresaCodigo", empresaCodigo);
			map.put("usuarioCodigo", usuarioCodigo);
			map.put("accion", accion);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return restTemplate.postForObject(conexionWS + "/accionRhXiiiSueldoPeriodo", UtilsJSON.objetoToJson(map),
					String.class);
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public String accionRhXivSueldoPeriodo(final RhXivSueldoPeriodoTO rhXivSueldoPeriodoTO, final String empresaCodigo,
			final String usuarioCodigo, final char accion) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("rhXivSueldoPeriodoTO", rhXivSueldoPeriodoTO);
			map.put("empresaCodigo", empresaCodigo);
			map.put("usuarioCodigo", usuarioCodigo);
			map.put("accion", accion);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return restTemplate.postForObject(conexionWS + "/accionRhXivSueldoPeriodo", UtilsJSON.objetoToJson(map),
					String.class);
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public RhCtaIessTO buscarCtaRhIess(final String empCodigo, final String empId) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("empCodigo", empCodigo);
			map.put("empId", empId);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return restTemplate.postForObject(conexionWS + "/buscarCtaRhIess", UtilsJSON.objetoToJson(map),
					RhCtaIessTO.class);
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public String eliminarImagenEmpleado(final String nombre) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("nombre", nombre);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return restTemplate.postForObject(conexionWS + "/eliminarImagenEmpleado", UtilsJSON.objetoToJson(map),
					String.class);
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public String eliminarRhAnticipoMotivo(final RhAnticipoMotivo rhAnticipoMotivo) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("rhAnticipoMotivo", rhAnticipoMotivo);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return restTemplate.postForObject(conexionWS + "/eliminarRhAnticipoMotivo", UtilsJSON.objetoToJson(map),
					String.class);
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public boolean eliminarRhBonoConcepto(final RhBonoConceptoTO rhBonoConceptoTO) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("rhBonoConceptoTO", rhBonoConceptoTO);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return restTemplate.postForObject(conexionWS + "/eliminarRhBonoConcepto", UtilsJSON.objetoToJson(map),
					boolean.class);
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return false;
	}

	public String eliminarRhBonoMotivo(final RhBonoMotivo rhBonoMotivo) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("rhBonoMotivo", rhBonoMotivo);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return restTemplate.postForObject(conexionWS + "/eliminarRhBonoMotivo", UtilsJSON.objetoToJson(map),
					String.class);
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public String eliminarRhEmpleado(final RhEmpleadoPK rhEmpleadoPK) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("rhEmpleadoPK", rhEmpleadoPK);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return restTemplate.postForObject(conexionWS + "/eliminarRhEmpleado", map, String.class);
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public String eliminarRhEmpleado(final RhEmpleadoTO rhEmpleadoTO,
			final List<RhEmpleadoDescuentosFijosTO> listaEliminar) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("rhEmpleadoTO", rhEmpleadoTO);
			map.put("listaEliminar", listaEliminar);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return restTemplate.postForObject(conexionWS + "/eliminarRhEmpleadoTO", UtilsJSON.objetoToJson(map),
					String.class);
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public String eliminarRhPrestamoMotivo(final RhPrestamoMotivo rhPrestamoMotivo) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("rhPrestamoMotivo", rhPrestamoMotivo);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return restTemplate.postForObject(conexionWS + "/eliminarRhPrestamoMotivo", UtilsJSON.objetoToJson(map),
					String.class);
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public String eliminarRhRolMotivo(final RhRolMotivo rhRolMotivo) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("rhRolMotivo", rhRolMotivo);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return restTemplate.postForObject(conexionWS + "/eliminarRhRolMotivo", UtilsJSON.objetoToJson(map),
					String.class);
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public String eliminarRhUtilidadMotivo(final RhUtilidadMotivo rhUtilidadMotivo) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("rhUtilidadMotivo", rhUtilidadMotivo);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return restTemplate.postForObject(conexionWS + "/eliminarRhUtilidadMotivo", UtilsJSON.objetoToJson(map),
					String.class);
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public String eliminarRhXiiiSueldoMotivo(final RhXiiiSueldoMotivo rhXiiiSueldoMotivo) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("rhXiiiSueldoMotivo", rhXiiiSueldoMotivo);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return restTemplate.postForObject(conexionWS + "/eliminarRhXiiiSueldoMotivo", UtilsJSON.objetoToJson(map),
					String.class);
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public String eliminarRhXivSueldoMotivo(final RhXivSueldoMotivo rhXivSueldoMotivo) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("rhXivSueldoMotivo", rhXivSueldoMotivo);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return restTemplate.postForObject(conexionWS + "/eliminarRhXivSueldoMotivo", UtilsJSON.objetoToJson(map),
					String.class);
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public String generarReporteAnticipo(final List<ReporteAnticipoPrestamoXIIISueldo> rhReporteAnticipoOprestamos) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("usuarioEmpresaReporteTO", UtilAplication.usuarioEmpresaReporteTO);
			map.put("rhReporteAnticipoOprestamos", rhReporteAnticipoOprestamos);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			final String rutaArchivo = "reportAnticipo.jrprint";
			return UtilsRESTFul.postForFile(conexionWS + "/generarReporteAnticipo", map, rutaArchivo);
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public String generarReporteConsolidadoAnticiposPrestamos(final String fechaDesde, final String fechaHasta,
			final List<RhListaConsolidadoAnticiposPrestamosTO> listaConsolidadoAnticiposPrestamosTO) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("usuarioEmpresaReporteTO", UtilAplication.usuarioEmpresaReporteTO);
			map.put("fechaDesde", fechaDesde);
			map.put("fechaHasta", fechaHasta);
			map.put("listaConsolidadoAnticiposPrestamosTO", listaConsolidadoAnticiposPrestamosTO);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			final String rutaArchivo = "reportConsolidadoAnticiposPrestamos.jrprint";
			return UtilsRESTFul.postForFile(conexionWS + "/generarReporteConsolidadoAnticiposPrestamos", map,
					rutaArchivo);
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public String generarReporteConsolidadoBonosViatico(final String fechaDesde, final String fechaHasta,
			final List<RhListaConsolidadoBonosTO> listaConsolidadoBonosViaticosTO) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("usuarioEmpresaReporteTO", UtilAplication.usuarioEmpresaReporteTO);
			map.put("fechaDesde", fechaDesde);
			map.put("fechaHasta", fechaHasta);
			map.put("listaConsolidadoBonosViaticosTO", listaConsolidadoBonosViaticosTO);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			final String rutaArchivo = "reportConsolidadoBonosViatico.jrprint";
			return UtilsRESTFul.postForFile(conexionWS + "/generarReporteConsolidadoBonosViatico", map, rutaArchivo);
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public String generarReporteConsolidadoRoles(final String fechaDesde, final String fechaHasta,
			final String empCategoria, final String nombreEmpleado,
			final List<RhListaConsolidadoRolesTO> listaConsolidadoRolesTO) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("usuarioEmpresaReporteTO", UtilAplication.usuarioEmpresaReporteTO);
			map.put("fechaDesde", fechaDesde);
			map.put("fechaHasta", fechaHasta);
			map.put("empCategoria", empCategoria);
			map.put("nombreEmpleado", nombreEmpleado);
			map.put("listaConsolidadoRolesTO", listaConsolidadoRolesTO);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			final String rutaArchivo = "reportConsolidadoRoles.jrprint";
			return UtilsRESTFul.postForFile(conexionWS + "/generarReporteConsolidadoRoles", map, rutaArchivo);
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public String generarReporteConsultaAnticiposLote(final List<ReporteConsultaAnticiposLote> lista,
			final String nombre) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("usuarioEmpresaReporteTO", UtilAplication.usuarioEmpresaReporteTO);
			map.put("lista", lista);
			map.put("nombre", nombre);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			final String rutaArchivo = "reportConsultaAnticiposLote.jrprint";
			return UtilsRESTFul.postForFile(conexionWS + "/generarReporteConsultaAnticiposLote", map, rutaArchivo);
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public String generarReporteConsultaAnticiposLoteColectivo(final List<ReporteConsultaAnticiposLote> lista) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("usuarioEmpresaReporteTO", UtilAplication.usuarioEmpresaReporteTO);
			map.put("lista", lista);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			final String rutaArchivo = "reportConsultaAnticiposLoteColectivo.jrprint";
			return UtilsRESTFul.postForFile(conexionWS + "/generarReporteConsultaAnticiposLoteColectivo", map,
					rutaArchivo);
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public String generarReporteDetalleAnticipos(final String empId, final String empCodigo, final String empCategoria,
			final String fechaDesde, final String fechaHasta,
			final List<RhListaDetalleAnticiposTO> listaDetalleAnticiposTO, final String nombre) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("usuarioEmpresaReporteTO", UtilAplication.usuarioEmpresaReporteTO);
			map.put("empId", empId);
			map.put("empCodigo", empCodigo);
			map.put("empCategoria", empCategoria);
			map.put("fechaDesde", fechaDesde);
			map.put("fechaHasta", fechaHasta);
			map.put("listaDetalleAnticiposTO", listaDetalleAnticiposTO);
			map.put("nombre", nombre);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			final String rutaArchivo = "reportDetalleAnticipos.jrprint";
			return UtilsRESTFul.postForFile(conexionWS + "/generarReporteDetalleAnticipos", map, rutaArchivo);
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public String generarReporteDetalleAnticiposPrestamos(final String empCodigo, final String fechaDesde,
			final String fechaHasta, final String empCategoria, final String empId,
			final List<RhListaDetalleAnticiposPrestamosTO> listaDetalleAnticiposPrestamosTO, final String nombre) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("usuarioEmpresaReporteTO", UtilAplication.usuarioEmpresaReporteTO);
			map.put("empCodigo", empCodigo);
			map.put("fechaDesde", fechaDesde);
			map.put("fechaHasta", fechaHasta);
			map.put("empCategoria", empCategoria);
			map.put("empId", empId);
			map.put("listaDetalleAnticiposPrestamosTO", listaDetalleAnticiposPrestamosTO);
			map.put("nombre", nombre);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			final String rutaArchivo = "reportDetalleAnticiposPrestamos.jrprint";
			return UtilsRESTFul.postForFile(conexionWS + "/generarReporteDetalleAnticiposPrestamos", map, rutaArchivo);
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public String generarReporteDetalleBonos(final String fechaDesde, final String fechaHasta,
			final String empCategoria, final String nombreEmpleado,
			final List<RhListaDetalleBonosTO> listaDetalleBonosTO) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("usuarioEmpresaReporteTO", UtilAplication.usuarioEmpresaReporteTO);
			map.put("fechaDesde", fechaDesde);
			map.put("fechaHasta", fechaHasta);
			map.put("empCategoria", empCategoria);
			map.put("nombreEmpleado", nombreEmpleado);
			map.put("listaDetalleBonosTO", listaDetalleBonosTO);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			final String rutaArchivo = "reportDetalleBonos.jrprint";
			return UtilsRESTFul.postForFile(conexionWS + "/generarReporteDetalleBonos", map, rutaArchivo);
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public String generarReporteDetalleBonosLote(final String periodo, final String tipo, final String numero,
			final List<RhListaDetalleBonosLoteTO> listaDetalleBonosLoteTO, final String nombre) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("usuarioEmpresaReporteTO", UtilAplication.usuarioEmpresaReporteTO);
			map.put("periodo", periodo);
			map.put("tipo", tipo);
			map.put("numero", numero);
			map.put("listaDetalleBonosLoteTO", listaDetalleBonosLoteTO);
			map.put("nombre", nombre);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			final String rutaArchivo = nombre + ".jrprint";
			return UtilsRESTFul.postForFile(conexionWS + "/generarReporteDetalleBonosLote", map, rutaArchivo);
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public String generarReporteDetalleBonosLoteColectivo(
			final List<ReporteConsultaBonosLote> reporteConsultaBonosLotes) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("usuarioEmpresaReporteTO", UtilAplication.usuarioEmpresaReporteTO);
			map.put("reporteConsultaBonosLotes", reporteConsultaBonosLotes);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			final String rutaArchivo = "reportComprobanteBonosPorLoteColectivo.jrprint";
			return UtilsRESTFul.postForFile(conexionWS + "/generarReporteDetalleBonosLoteColectivo", map, rutaArchivo);
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public String generarReporteDetalleBonosLoteWeb(final List<ReporteConsultaBonosLote> reporteConsultaBonosLotes) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("usuarioEmpresaReporteTO", UtilAplication.usuarioEmpresaReporteTO);
			map.put("reporteConsultaBonosLotes", reporteConsultaBonosLotes);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			final String rutaArchivo = "reportDetalleBonosLote.jrprint";
			return UtilsRESTFul.postForFile(conexionWS + "/generarReporteDetalleBonosLoteWeb", map, rutaArchivo);
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public String generarReporteDetallePrestamos(final String empCodigo, final String fechaDesde,
			final String fechaHasta, final String empCategoria, final String empId,
			final List<RhListaDetallePrestamosTO> listaDetallePrestamosTO) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("usuarioEmpresaReporteTO", UtilAplication.usuarioEmpresaReporteTO);
			map.put("empCodigo", empCodigo);
			map.put("fechaDesde", fechaDesde);
			map.put("fechaHasta", fechaHasta);
			map.put("empCategoria", empCategoria);
			map.put("empId", empId);
			map.put("listaDetallePrestamosTO", listaDetallePrestamosTO);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			final String rutaArchivo = "reportConsolidadoAnticiposPrestamos.jrprint";
			return UtilsRESTFul.postForFile(conexionWS + "/generarReporteDetallePrestamos", map, rutaArchivo);
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public String generarReporteDetalleViaticos(final String fechaDesde, final String fechaHasta,
			final String empCategoria, final String nombreEmpleado,
			final List<RhListaDetalleViaticosTO> listaDetalleViaticosTO) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("usuarioEmpresaReporteTO", UtilAplication.usuarioEmpresaReporteTO);
			map.put("fechaDesde", fechaDesde);
			map.put("fechaHasta", fechaHasta);
			map.put("empCategoria", empCategoria);
			map.put("nombreEmpleado", nombreEmpleado);
			map.put("listaDetalleViaticosTO", listaDetalleViaticosTO);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			final String rutaArchivo = "reportDetalleViaticos.jrprint";
			return UtilsRESTFul.postForFile(conexionWS + "/generarReporteDetalleViaticos", map, rutaArchivo);
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public String generarReporteEmpleado(final List<ReporteEmpleado> listReporteEmpleado) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("usuarioEmpresaReporteTO", UtilAplication.usuarioEmpresaReporteTO);
			map.put("listReporteEmpleado", listReporteEmpleado);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			final String rutaArchivo = "reportEmpleado.jrprint";
			return UtilsRESTFul.postForFile(conexionWS + "/generarReporteEmpleado", map, rutaArchivo);
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public String generarReporteListaDetalleVacaionesPagadas(final String desde, final String hasta,
			final String empSector,
			final List<RhDetalleVacionesPagadasGozadasTO> listaDetalleVacacionesPagadasGozadasTO) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("usuarioEmpresaReporteTO", UtilAplication.usuarioEmpresaReporteTO);
			map.put("desde", desde);
			map.put("hasta", hasta);
			map.put("empSector", empSector);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			map.put("listaDetalleVacacionesPagadasGozadasTO", listaDetalleVacacionesPagadasGozadasTO);
			final String rutaArchivo = "reportListaDetalleVacaionesPagadas.jrprint";
			return UtilsRESTFul.postForFile(conexionWS + "/generarReporteListaDetalleVacaionesPagadas", map,
					rutaArchivo);

		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public String generarReporteListadoRolesPago(final String fechaDesde, final String fechaHasta,
			final String empCategoria, final String nombreEmpleado,
			final List<RhListaDetalleRolesTO> listaDetalleRolesTO) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("usuarioEmpresaReporteTO", UtilAplication.usuarioEmpresaReporteTO);
			map.put("fechaDesde", fechaDesde);
			map.put("fechaHasta", fechaHasta);
			map.put("empCategoria", empCategoria);
			map.put("nombreEmpleado", nombreEmpleado);
			map.put("listaDetalleRolesTO", listaDetalleRolesTO);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			final String rutaArchivo = "reportListadoRolesPago.jrprint";
			return UtilsRESTFul.postForFile(conexionWS + "/generarReporteListadoRolesPago", map, rutaArchivo);
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public String generarReportePrestamoVista(final List<ReporteAnticipoPrestamoXIIISueldo> lista) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("usuarioEmpresaReporteTO", UtilAplication.usuarioEmpresaReporteTO);
			map.put("lista", lista);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			final String rutaArchivo = "reportPrestamoVista.jrprint";
			return UtilsRESTFul.postForFile(conexionWS + "/generarReportePrestamoVista", map, rutaArchivo);
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public String generarReporteProvisionesComprobanteContable(final String periodo, final String tipo,
			final String numero, final List<RhListaProvisionesTO> listaProvisionesTO) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("usuarioEmpresaReporteTO", UtilAplication.usuarioEmpresaReporteTO);
			map.put("periodo", periodo);
			map.put("tipo", tipo);
			map.put("numero", numero);
			map.put("listaProvisionesTO", listaProvisionesTO);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			final String rutaArchivo = "reportProvisionesComprobanteContable.jrprint";
			return UtilsRESTFul.postForFile(conexionWS + "/generarReporteProvisionesComprobanteContable", map,
					rutaArchivo);
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public String generarReporteRol(final List<ReportesRol> lista) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("usuarioEmpresaReporteTO", UtilAplication.usuarioEmpresaReporteTO);
			map.put("lista", lista);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			final String rutaArchivo = "reportRol.jrprint";
			return UtilsRESTFul.postForFile(conexionWS + "/generarReporteRol", map, rutaArchivo);
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public String generarReporteSaldoConsolidadoAnticiposPrestamos(final String fechaHasta,
			final List<RhListaSaldoConsolidadoAnticiposPrestamosTO> listaSaldoConsolidadoAnticiposPrestamosTO) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("usuarioEmpresaReporteTO", UtilAplication.usuarioEmpresaReporteTO);
			map.put("fechaHasta", fechaHasta);
			map.put("listaSaldoConsolidadoAnticiposPrestamosTO", listaSaldoConsolidadoAnticiposPrestamosTO);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			final String rutaArchivo = "reportSaldoConsolidadoAnticiposPrestamos.jrprint";
			return UtilsRESTFul.postForFile(conexionWS + "/generarReporteSaldoConsolidadoAnticiposPrestamos", map,
					rutaArchivo);
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public String generarReporteSaldoConsolidadoBonosViaticos(final String fechaHasta,
			final List<RhListaSaldoConsolidadoBonosTO> listaSaldoConsolidadoBonosViaticosTO) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("usuarioEmpresaReporteTO", UtilAplication.usuarioEmpresaReporteTO);
			map.put("fechaHasta", fechaHasta);
			map.put("listaSaldoConsolidadoBonosViaticosTO", listaSaldoConsolidadoBonosViaticosTO);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			final String rutaArchivo = "reportSaldoConsolidadoBonosViaticos.jrprint";
			return UtilsRESTFul.postForFile(conexionWS + "/generarReporteSaldoConsolidadoBonosViaticos", map,
					rutaArchivo);
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public String generarReporteSaldoConsolidadoSueldosPorPagar(final String fechaHasta,
			final List<RhListaSaldoConsolidadoSueldosPorPagarTO> listaSaldoConsolidadoSueldosPorPagarTO) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("usuarioEmpresaReporteTO", UtilAplication.usuarioEmpresaReporteTO);
			map.put("fechaHasta", fechaHasta);
			map.put("listaSaldoConsolidadoSueldosPorPagarTO", listaSaldoConsolidadoSueldosPorPagarTO);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			final String rutaArchivo = "reportSaldoConsolidadoSueldosPorPagar.jrprint";
			return UtilsRESTFul.postForFile(conexionWS + "/generarReporteSaldoConsolidadoSueldosPorPagar", map,
					rutaArchivo);
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public String generarReporteSaldoIndividualAnticiposPrestamos(final String fechaDesde, final String fechaHasta,
			final String empId,
			final List<RhListaSaldoIndividualAnticiposPrestamosTO> listaSaldoIndividualAnticiposPrestamosTO) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("usuarioEmpresaReporteTO", UtilAplication.usuarioEmpresaReporteTO);
			map.put("fechaDesde", fechaDesde);
			map.put("fechaHasta", fechaHasta);
			map.put("empId", empId);
			map.put("listaSaldoIndividualAnticiposPrestamosTO", listaSaldoIndividualAnticiposPrestamosTO);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			final String rutaArchivo = "reportSaldoIndividualAnticiposPrestamos.jrprint";
			return UtilsRESTFul.postForFile(conexionWS + "/generarReporteSaldoIndividualAnticiposPrestamos", map,
					rutaArchivo);
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public String generarReporteUtilidadesPreCalculo(final String sector, final String periodo, final String fechaDesde,
			final String fechaHasta, final String fechaMaxima,
			final List<RhFunUtilidadesCalcularTO> rhFunUtilidadesCalcularTOs) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("usuarioEmpresaReporteTO", UtilAplication.usuarioEmpresaReporteTO);
			map.put("sector", sector);
			map.put("periodo", periodo);
			map.put("fechaDesde", fechaDesde);
			map.put("fechaHasta", fechaHasta);
			map.put("fechaMaxima", fechaMaxima);
			map.put("rhFunUtilidadesCalcularTOs", rhFunUtilidadesCalcularTOs);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			final String rutaArchivo = "reportUtilidadesPreCalculo.jrprint";
			return UtilsRESTFul.postForFile(conexionWS + "/generarReporteUtilidadesPreCalculo", map, rutaArchivo);
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public String generarReporteXIIISueldo(final List<ReporteAnticipoPrestamoXIIISueldo> rhReporteXIIISueldo) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("usuarioEmpresaReporteTO", UtilAplication.usuarioEmpresaReporteTO);
			map.put("rhReporteXIIISueldo", rhReporteXIIISueldo);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			final String rutaArchivo = "reportAnticipo.jrprint";
			return UtilsRESTFul.postForFile(conexionWS + "/generarReporteXIIISueldo", map, rutaArchivo);
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public String generarReporteXiiiSueldoConsulta(final String sector, final String periodo, final String fechaDesde,
			final String fechaHasta, final String fechaMaxima,
			final List<RhFunXiiiSueldoConsultarTO> rhFunXiiiSueldoConsultarTO) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("usuarioEmpresaReporteTO", UtilAplication.usuarioEmpresaReporteTO);
			map.put("sector", sector);
			map.put("periodo", periodo);
			map.put("fechaDesde", fechaDesde);
			map.put("fechaHasta", fechaHasta);
			map.put("fechaMaxima", fechaMaxima);
			map.put("rhFunXiiiSueldoConsultarTO", rhFunXiiiSueldoConsultarTO);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			final String rutaArchivo = "reportXiiiSueldoConsulta.jrprint";
			return UtilsRESTFul.postForFile(conexionWS + "/generarReporteXiiiSueldoConsulta", map, rutaArchivo);
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public String generarReporteXivSueldoConsulta(final String sector, final String periodo, final String fechaDesde,
			final String fechaHasta, final String fechaMaxima,
			final List<RhFunXivSueldoConsultarTO> rhFunXivSueldoConsultarTO) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("usuarioEmpresaReporteTO", UtilAplication.usuarioEmpresaReporteTO);
			map.put("sector", sector);
			map.put("periodo", periodo);
			map.put("fechaDesde", fechaDesde);
			map.put("fechaHasta", fechaHasta);
			map.put("fechaMaxima", fechaMaxima);
			map.put("rhFunXivSueldoConsultarTO", rhFunXivSueldoConsultarTO);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			final String rutaArchivo = "reportXivSueldoConsulta.jrprint";
			return UtilsRESTFul.postForFile(conexionWS + "/generarReporteXivSueldoConsulta", map, rutaArchivo);
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public List<RhComboFormaPagoBeneficioSocialTO> getComboFormaPagoBeneficioSocial(final String empresa) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("empresa", empresa);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return Arrays.asList(restTemplate.postForObject(conexionWS + "/getComboFormaPagoBeneficioSocial",
					UtilsJSON.objetoToJson(map), RhComboFormaPagoBeneficioSocialTO[].class));
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public List<RhComboBonoConceptoTO> getComboRhBonoConceptoTO(final String empresa) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("empresa", empresa);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return Arrays.asList(restTemplate.postForObject(conexionWS + "/getComboRhBonoConceptoTO",
					UtilsJSON.objetoToJson(map), RhComboBonoConceptoTO[].class));
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public List<RhComboCategoriaTO> getComboRhCategoriaTO(final String empresa) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("empresa", empresa);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return Arrays.asList(restTemplate.postForObject(conexionWS + "/getComboRhCategoriaTO",
					UtilsJSON.objetoToJson(map), RhComboCategoriaTO[].class));
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public List<RhComboFormaPagoTO> getComboRhFormaPagoTO(final String empresa) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("empresa", empresa);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return Arrays.asList(restTemplate.postForObject(conexionWS + "/getComboRhFormaPagoTO",
					UtilsJSON.objetoToJson(map), RhComboFormaPagoTO[].class));
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public RetornoTO getConsolidadoIngresosTabulado(final String empresa, final String codigoSector,
			final String fechaInicio, final String fechaFin, final String usuario) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("empresa", empresa);
			map.put("codigoSector", codigoSector);
			map.put("fechaInicio", fechaInicio);
			map.put("fechaFin", fechaFin);
			map.put("usuario", usuario);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return restTemplate.postForObject(conexionWS + "/getConsolidadoIngresosTabulado",
					UtilsJSON.objetoToJson(map), RetornoTO.class);
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public RhEmpleado getEmpleado(final String empresa, final String id) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("empresa", empresa);
			map.put("id", id);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return restTemplate.postForObject(conexionWS + "/getEmpleado", UtilsJSON.objetoToJson(map),
					RhEmpleado.class);
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public List<RhEmpleadoDescuentosFijos> getEmpleadoDescuentosFijos(final String empresa, final String empleado) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("empresa", empresa);
			map.put("empleado", empleado);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return new ArrayList<>(Arrays.asList(restTemplate.postForObject(conexionWS + "/getEmpleadoDescuentosFijos",
					UtilsJSON.objetoToJson(map), RhEmpleadoDescuentosFijos[].class)));
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public List<RhFunPlantillaSueldosLoteTO> getFunPlantillaSueldosLote(final String empCodigo, final String fechaDesde,
			final String fechaHasta, final String categoria, final String sector) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("empCodigo", empCodigo);
			map.put("fechaDesde", fechaDesde);
			map.put("fechaHasta", fechaHasta);
			map.put("categoria", categoria);
			map.put("sector", sector);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return Arrays.asList(restTemplate.postForObject(conexionWS + "/getFunPlantillaSueldosLote",
					UtilsJSON.objetoToJson(map), RhFunPlantillaSueldosLoteTO[].class));
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public List<RhFunPlantillaSueldosLotePreliminarTO> getFunPlantillaSueldosLotePreliminar(final String empCodigo,
			final String fechaDesde, final String fechaHasta, final String categoria, final String sector) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("empCodigo", empCodigo);
			map.put("fechaDesde", fechaDesde);
			map.put("fechaHasta", fechaHasta);
			map.put("categoria", categoria);
			map.put("sector", sector);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return Arrays.asList(restTemplate.postForObject(conexionWS + "/getFunPlantillaSueldosLotePreliminar",
					UtilsJSON.objetoToJson(map), RhFunPlantillaSueldosLotePreliminarTO[].class));
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public List<RhListaEmpleadoTO> getListaConsultaEmpleadoTO(final String empresa, final String buscar,
			final Boolean interno, final boolean estado) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("empresa", empresa);
			map.put("buscar", buscar);
			map.put("interno", interno);
			map.put("estado", estado);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return Arrays.asList(restTemplate.postForObject(conexionWS + "/getListaConsultaEmpleadoTO",
					UtilsJSON.objetoToJson(map), RhListaEmpleadoTO[].class));
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public List<RhEmpleado> getListaEmpleado(final String empresa, final String buscar, final boolean estado) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("empresa", empresa);
			map.put("buscar", buscar);
			map.put("estado", estado);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return new ArrayList<>(Arrays.asList(restTemplate.postForObject(conexionWS + "/getListaEmpleado",
					UtilsJSON.objetoToJson(map), RhEmpleado[].class)));
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public List<RhListaEmpleadoLoteTO> getListaEmpleadoLote(final String empresa, final String categoria,
			final String sector, final String fechaHasta, final String motivo, final boolean rol) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("empresa", empresa);
			map.put("categoria", categoria);
			map.put("sector", sector);
			map.put("fechaHasta", fechaHasta);
			map.put("motivo", motivo);
			map.put("rol", rol);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return new ArrayList<>(Arrays.asList(restTemplate.postForObject(conexionWS + "/getListaEmpleadoLote",
					UtilsJSON.objetoToJson(map), RhListaEmpleadoLoteTO[].class)));
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public List<RhListaFormaPagoBeneficioSocialTO> getListaFormaPagoBeneficioSocial(final String empresa) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("empresa", empresa);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return Arrays.asList(restTemplate.postForObject(conexionWS + "/getListaFormaPagoBeneficioSocialTO",
					UtilsJSON.objetoToJson(map), RhListaFormaPagoBeneficioSocialTO[].class));
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public List<RhAnticipoMotivo> getListaRhAnticipoMotivo(final String empresa) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("empresa", empresa);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return new ArrayList<>(Arrays.asList(restTemplate.postForObject(conexionWS + "/getListaRhAnticipoMotivo",
					UtilsJSON.objetoToJson(map), RhAnticipoMotivo[].class)));
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public List<RhAnticipoMotivoTO> getListaRhAnticipoMotivoTablaTO(final String empresa) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("empresa", empresa);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return new ArrayList<>(
					Arrays.asList(restTemplate.postForObject(conexionWS + "/getListaRhAnticipoMotivoTablaTO",
							UtilsJSON.objetoToJson(map), RhAnticipoMotivoTO[].class)));
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public List<RhListaBonoConceptoTO> getListaRhBonoConceptoTO(final String empresa) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("empresa", empresa);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return Arrays.asList(restTemplate.postForObject(conexionWS + "/getListaRhBonoConceptoTO",
					UtilsJSON.objetoToJson(map), RhListaBonoConceptoTO[].class));
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public List<RhBonoMotivo> getListaRhBonoMotivo(final String empresa) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("empresa", empresa);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return new ArrayList<>(Arrays.asList(restTemplate.postForObject(conexionWS + "/getListaRhBonoMotivo",
					UtilsJSON.objetoToJson(map), RhBonoMotivo[].class)));
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public List<RhCategoriaTO> getListaRhCategoriaCuentasTO(final String empresa) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("empresa", empresa);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return Arrays.asList(restTemplate.postForObject(conexionWS + "/getListaRhCategoriaCuentasTO",
					UtilsJSON.objetoToJson(map), RhCategoriaTO[].class));
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public List<RhCategoriaTO> getListaRhCategoriaTO(final String empresa) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("empresa", empresa);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return Arrays.asList(restTemplate.postForObject(conexionWS + "/getListaRhCategoriaTO",
					UtilsJSON.objetoToJson(map), RhCategoriaTO[].class));
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public List<RhEmpleadoTO> getListaRhEmpleadoTO(final String empresa, final String buscar, final boolean estado) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("empresa", empresa);
			map.put("buscar", buscar);
			map.put("estado", estado);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return Arrays.asList(restTemplate.postForObject(conexionWS + "/getListaRhEmpleadoTO",
					UtilsJSON.objetoToJson(map), RhEmpleadoTO[].class));
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public List<RhListaFormaPagoTO> getListaRhFormaPagoTO(final String empresa) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("empresa", empresa);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return Arrays.asList(restTemplate.postForObject(conexionWS + "/getListaRhFormaPagoTO",
					UtilsJSON.objetoToJson(map), RhListaFormaPagoTO[].class));
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public List<RhPrestamoMotivo> getListaRhPrestamoMotivo(final String empresa) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("empresa", empresa);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return new ArrayList<>(Arrays.asList(restTemplate.postForObject(conexionWS + "/getListaRhPrestamoMotivo",
					UtilsJSON.objetoToJson(map), RhPrestamoMotivo[].class)));
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public List<RhRolMotivo> getListaRhRolMotivo(final String empresa) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("empresa", empresa);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return new ArrayList<>(Arrays.asList(restTemplate.postForObject(conexionWS + "/getListaRhRolMotivo",
					UtilsJSON.objetoToJson(map), RhRolMotivo[].class)));
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public List<RhUtilidadMotivo> getListaRhUtilidadMotivo(final String empresa) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("empresa", empresa);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return new ArrayList<>(Arrays.asList(restTemplate.postForObject(conexionWS + "/getListaRhUtilidadMotivo",
					UtilsJSON.objetoToJson(map), RhUtilidadMotivo[].class)));
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public List<RhXiiiSueldoMotivo> getListaRhXiiiSueldoMotivo(final String empresa) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("empresa", empresa);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return new ArrayList<>(Arrays.asList(restTemplate.postForObject(conexionWS + "/getListaRhXiiiSueldoMotivo",
					UtilsJSON.objetoToJson(map), RhXiiiSueldoMotivo[].class)));
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public List<RhXivSueldoMotivo> getListaRhXivSueldoMotivo(final String empresa) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("empresa", empresa);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return new ArrayList<>(Arrays.asList(restTemplate.postForObject(conexionWS + "/getListaRhXivSueldoMotivo",
					UtilsJSON.objetoToJson(map), RhXivSueldoMotivo[].class)));
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public List<RhAnticipo> getListRhAnticipo(final ConContablePK conContablePK) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("conContablePK", conContablePK);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return new ArrayList<>(Arrays.asList(restTemplate.postForObject(conexionWS + "/getListRhAnticipo",
					UtilsJSON.objetoToJson(map), RhAnticipo[].class)));
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public List<RhBono> getListRhBono(final ConContablePK conContablePK) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("conContablePK", conContablePK);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return new ArrayList<>(Arrays.asList(restTemplate.postForObject(conexionWS + "/getListRhBono",
					UtilsJSON.objetoToJson(map), RhBono[].class)));
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public List<RhPrestamo> getListRhPrestamo(final ConContablePK conContablePK) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("conContablePK", conContablePK);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return new ArrayList<>(Arrays.asList(restTemplate.postForObject(conexionWS + "/getListRhPrestamo",
					UtilsJSON.objetoToJson(map), RhPrestamo[].class)));
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public List<RhRol> getListRhRol(final ConContablePK conContablePK) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("conContablePK", conContablePK);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return new ArrayList<>(Arrays.asList(restTemplate.postForObject(conexionWS + "/getListRhRol",
					UtilsJSON.objetoToJson(map), RhRol[].class)));
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public List<RhUtilidades> getListRhUtilidades(final ConContablePK conContablePK) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("conContablePK", conContablePK);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return new ArrayList<>(Arrays.asList(restTemplate.postForObject(conexionWS + "/getListRhUtilidades",
					UtilsJSON.objetoToJson(map), RhUtilidades[].class)));
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public List<RhXiiiSueldo> getListRhXiiiSueldo(final ConContablePK conContablePK) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("conContablePK", conContablePK);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return new ArrayList<>(Arrays.asList(restTemplate.postForObject(conexionWS + "/getListRhXiiiSueldo",
					UtilsJSON.objetoToJson(map), RhXiiiSueldo[].class)));
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public List<RhXivSueldo> getListRhXivSueldo(final ConContablePK conContablePK) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("conContablePK", conContablePK);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return new ArrayList<>(Arrays.asList(restTemplate.postForObject(conexionWS + "/getListRhXivSueldo",
					UtilsJSON.objetoToJson(map), RhXivSueldo[].class)));
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public boolean getPermisoAccionesAnticipo(final ConContablePK conContablePK, final String fechaContable) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("conContablePK", conContablePK);
			map.put("fechaContable", fechaContable);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return restTemplate.postForObject(conexionWS + "/getPermisoAccionesAnticipo", UtilsJSON.objetoToJson(map),
					Boolean.class);
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return false;
	}

	public boolean getPermisoAccionesBono(final ConContablePK conContablePK, final String fechaContable) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("conContablePK", conContablePK);
			map.put("fechaContable", fechaContable);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return restTemplate.postForObject(conexionWS + "/getPermisoAccionesBono", UtilsJSON.objetoToJson(map),
					Boolean.class);
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return false;
	}

	public boolean getPermisoAccionesPrestamo(final ConContablePK conContablePK, final String fechaContable) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("conContablePK", conContablePK);
			map.put("fechaContable", fechaContable);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return restTemplate.postForObject(conexionWS + "/getPermisoAccionesPrestamo", UtilsJSON.objetoToJson(map),
					Boolean.class);
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return false;
	}

	public boolean getPermisoAccionesRol(final ConContablePK conContablePK, final String fechaContable) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("conContablePK", conContablePK);
			map.put("fechaContable", fechaContable);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return restTemplate.postForObject(conexionWS + "/getPermisoAccionesRol", UtilsJSON.objetoToJson(map),
					Boolean.class);
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return false;
	}

	public boolean getPermisoAccionesUtilidad(final ConContablePK conContablePK, final String fechaContable) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("conContablePK", conContablePK);
			map.put("fechaContable", fechaContable);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return restTemplate.postForObject(conexionWS + "/getPermisoAccionesUtilidad", UtilsJSON.objetoToJson(map),
					Boolean.class);
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return false;
	}

	public boolean getPermisoAccionesXiiiSueldo(final ConContablePK conContablePK, final String fechaContable) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("conContablePK", conContablePK);
			map.put("fechaContable", fechaContable);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return restTemplate.postForObject(conexionWS + "/getPermisoAccionesXiiiSueldo", UtilsJSON.objetoToJson(map),
					Boolean.class);
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return false;
	}

	public boolean getPermisoAccionesXivSueldo(final ConContablePK conContablePK, final String fechaContable) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("conContablePK", conContablePK);
			map.put("fechaContable", fechaContable);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return restTemplate.postForObject(conexionWS + "/getPermisoAccionesXivSueldo", UtilsJSON.objetoToJson(map),
					Boolean.class);
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return false;
	}

	public RetornoTO getProvisionPorFechas(final String empresa, final String codigoSector, final String fechaInicio,
			final String fechaFin, final String agrupacion) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("empresa", empresa);
			map.put("codigoSector", codigoSector);
			map.put("fechaInicio", fechaInicio);
			map.put("fechaFin", fechaFin);
			map.put("agrupacion", agrupacion);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return restTemplate.postForObject(conexionWS + "/getProvisionPorFechas", UtilsJSON.objetoToJson(map),
					RetornoTO.class);
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public List<RhAnulacionesTO> getRhAnulacionesTO(final String empresa, final String periodo, final String tipo,
			final String numero) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("empresa", empresa);
			map.put("periodo", periodo);
			map.put("tipo", tipo);
			map.put("numero", numero);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return Arrays.asList(restTemplate.postForObject(conexionWS + "/getRhAnulacionesTO",
					UtilsJSON.objetoToJson(map), RhAnulacionesTO[].class));
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public RhCategoriaTO getRhCategoriaTO(final String empCodigo, final String catNombre) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("empCodigo", empCodigo);
			map.put("catNombre", catNombre);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return restTemplate.postForObject(conexionWS + "/getRhCategoriaTO", UtilsJSON.objetoToJson(map),
					RhCategoriaTO.class);
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public List<RhUtilidadesPeriodoTO> getRhComboUtilidadesPeriodoTO(final String empresa) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("empresa", empresa);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return Arrays.asList(restTemplate.postForObject(conexionWS + "/getRhComboUtilidadesPeriodoTO",
					UtilsJSON.objetoToJson(map), RhUtilidadesPeriodoTO[].class));
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public List<RhXiiiSueldoPeriodoTO> getRhComboXiiiSueldoPeriodoTO() {
		try {
			return Arrays.asList(restTemplate.postForObject(conexionWS + "/getRhComboXiiiSueldoPeriodoTO",
					UtilAplication.getSisInfoTO(), RhXiiiSueldoPeriodoTO[].class));
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public List<RhXivSueldoPeriodoTO> getRhComboXivSueldoPeriodoTO() {
		try {
			return Arrays.asList(restTemplate.postForObject(conexionWS + "/getRhComboXivSueldoPeriodoTO",
					UtilAplication.getSisInfoTO(), RhXivSueldoPeriodoTO[].class));
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public List<RhListaConsolidadoAnticiposPrestamosTO> getRhConsolidadoAnticiposPrestamosTO(final String empCodigo,
			final String fechaDesde, final String fechaHasta, final String empCategoria, final String empId) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("empCodigo", empCodigo);
			map.put("fechaDesde", fechaDesde);
			map.put("fechaHasta", fechaHasta);
			map.put("empCategoria", empCategoria);
			map.put("empId", empId);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return Arrays.asList(restTemplate.postForObject(conexionWS + "/getRhConsolidadoAnticiposPrestamosTO",
					UtilsJSON.objetoToJson(map), RhListaConsolidadoAnticiposPrestamosTO[].class));
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public List<RhListaConsolidadoBonosTO> getRhConsolidadoBonosTO(final String empCodigo, final String fechaDesde,
			final String fechaHasta) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("empCodigo", empCodigo);
			map.put("fechaDesde", fechaDesde);
			map.put("fechaHasta", fechaHasta);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return Arrays.asList(restTemplate.postForObject(conexionWS + "/getRhConsolidadoBonosTO",
					UtilsJSON.objetoToJson(map), RhListaConsolidadoBonosTO[].class));
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public List<RhListaConsolidadoRolesTO> getRhConsolidadoRolesTO(final String empCodigo, final String fechaDesde,
			final String fechaHasta, final String sector, final String empCategoria, final String empId) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("empCodigo", empCodigo);
			map.put("fechaDesde", fechaDesde);
			map.put("fechaHasta", fechaHasta);
			map.put("sector", sector);
			map.put("empCategoria", empCategoria);
			map.put("empId", empId);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return Arrays.asList(restTemplate.postForObject(conexionWS + "/getRhConsolidadoRolesTO",
					UtilsJSON.objetoToJson(map), RhListaConsolidadoRolesTO[].class));
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public List<RhListaDetalleAnticiposTO> getRhDetalleAnticiposFiltradoTO(final String empCodigo,
			final String fechaDesde, final String fechaHasta, final String empCategoria, final String empId,
			final String formaPago, final String parametro) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("empCodigo", empCodigo);
			map.put("fechaDesde", fechaDesde);
			map.put("fechaHasta", fechaHasta);
			map.put("empCategoria", empCategoria);
			map.put("empId", empId);
			map.put("formaPago", formaPago);
			map.put("parametro", parametro);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return Arrays.asList(restTemplate.postForObject(conexionWS + "/getRhDetalleAnticiposFiltradoTO",
					UtilsJSON.objetoToJson(map), RhListaDetalleAnticiposTO[].class));
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public List<RhListaDetalleAnticiposLoteTO> getRhDetalleAnticiposLoteTO(final String empresa, final String periodo,
			final String tipo, final String numero) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("empresa", empresa);
			map.put("periodo", periodo);
			map.put("tipo", tipo);
			map.put("numero", numero);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return Arrays.asList(restTemplate.postForObject(conexionWS + "/getRhDetalleAnticiposLoteTO",
					UtilsJSON.objetoToJson(map), RhListaDetalleAnticiposLoteTO[].class));
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public List<RhListaDetalleAnticiposPrestamosTO> getRhDetalleAnticiposPrestamosTO(final String empCodigo,
			final String fechaDesde, final String fechaHasta, final String empCategoria, final String empId,
			final String parametro) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("empCodigo", empCodigo);
			map.put("fechaDesde", fechaDesde);
			map.put("fechaHasta", fechaHasta);
			map.put("empCategoria", empCategoria);
			map.put("empId", empId);
			map.put("parametro", parametro);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return Arrays.asList(restTemplate.postForObject(conexionWS + "/getRhDetalleAnticiposPrestamosTO",
					UtilsJSON.objetoToJson(map), RhListaDetalleAnticiposPrestamosTO[].class));
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public List<RhListaDetalleAnticiposTO> getRhDetalleAnticiposTO(final String empCodigo, final String fechaDesde,
			final String fechaHasta, final String empCategoria, final String empId, final String formaPago) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("empCodigo", empCodigo);
			map.put("fechaDesde", fechaDesde);
			map.put("fechaHasta", fechaHasta);
			map.put("empCategoria", empCategoria);
			map.put("empId", empId);
			map.put("formaPago", formaPago);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return Arrays.asList(restTemplate.postForObject(conexionWS + "/getRhDetalleAnticiposTO",
					UtilsJSON.objetoToJson(map), RhListaDetalleAnticiposTO[].class));
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public List<RhListaDetalleBonosLoteTO> getRhDetalleBonosLoteTO(final String empresa, final String periodo,
			final String tipo, final String numero) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("empresa", empresa);
			map.put("periodo", periodo);
			map.put("tipo", tipo);
			map.put("numero", numero);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return Arrays.asList(restTemplate.postForObject(conexionWS + "/getRhDetalleBonosLoteTO",
					UtilsJSON.objetoToJson(map), RhListaDetalleBonosLoteTO[].class));
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public List<RhListaDetalleBonosTO> getRhDetalleBonosTO(final String empCodigo, final String fechaDesde,
			final String fechaHasta, final String empCategoria, final String empId, final String estadoDeducible) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("empCodigo", empCodigo);
			map.put("fechaDesde", fechaDesde);
			map.put("fechaHasta", fechaHasta);
			map.put("empCategoria", empCategoria);
			map.put("empId", empId);
			map.put("estadoDeducible", estadoDeducible);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return Arrays.asList(restTemplate.postForObject(conexionWS + "/getRhDetalleBonosTO",
					UtilsJSON.objetoToJson(map), RhListaDetalleBonosTO[].class));
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public List<RhListaDetallePrestamosTO> getRhDetallePrestamosTO(final String empCodigo, final String fechaDesde,
			final String fechaHasta, final String empCategoria, final String empId) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("empCodigo", empCodigo);
			map.put("fechaDesde", fechaDesde);
			map.put("fechaHasta", fechaHasta);
			map.put("empCategoria", empCategoria);
			map.put("empId", empId);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return Arrays.asList(restTemplate.postForObject(conexionWS + "/getRhDetallePrestamosTO",
					UtilsJSON.objetoToJson(map), RhListaDetallePrestamosTO[].class));
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public List<RhListaDetalleRolesTO> getRhDetalleRolesTO(final String empCodigo, final String fechaDesde,
			final String fechaHasta, final String sector, final String empCategoria, final String empId) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("empCodigo", empCodigo);
			map.put("fechaDesde", fechaDesde);
			map.put("fechaHasta", fechaHasta);
			map.put("empCategoria", empCategoria);
			map.put("sector", sector);
			map.put("empId", empId);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return Arrays.asList(restTemplate.postForObject(conexionWS + "/getRhDetalleRolesTO",
					UtilsJSON.objetoToJson(map), RhListaDetalleRolesTO[].class));
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public List<RhDetalleVacionesPagadasGozadasTO> getRhDetalleVacacionesPagadasGozadasTO(final String empCodigo,
			final String empId, final String sector, final String fechaDesde, final String fechaHasta,
			final String tipo) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("empCodigo", empCodigo);
			map.put("empId", empId);
			map.put("sector", sector);
			map.put("fechaDesde", fechaDesde);
			map.put("fechaHasta", fechaHasta);
			map.put("tipo", tipo);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return Arrays.asList(restTemplate.postForObject(conexionWS + "/getRhDetalleVacacionesPagadasGozadasTO",
					UtilsJSON.objetoToJson(map), RhDetalleVacionesPagadasGozadasTO[].class));
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public List<RhListaDetalleViaticosTO> getRhDetalleViaticosTO(final String empCodigo, final String fechaDesde,
			final String fechaHasta, final String empCategoria, final String empId) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("empCodigo", empCodigo);
			map.put("fechaDesde", fechaDesde);
			map.put("fechaHasta", fechaHasta);
			map.put("empCategoria", empCategoria);
			map.put("empId", empId);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return Arrays.asList(restTemplate.postForObject(conexionWS + "/getRhDetalleViaticosTO",
					UtilsJSON.objetoToJson(map), RhListaDetalleViaticosTO[].class));
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public List<RhEmpleadoDescuentosFijosTO> getRhEmpleadoDescuentosFijosTO(final String empresa,
			final String empresaID) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("empresa", empresa);
			map.put("empresaID", empresaID);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return new ArrayList<>(
					Arrays.asList(restTemplate.postForObject(conexionWS + "/getRhEmpleadoDescuentosFijosTO",
							UtilsJSON.objetoToJson(map), RhEmpleadoDescuentosFijosTO[].class)));
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public boolean getRhEmpleadoRetencion(final String empCodigo, final String empId) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("empCodigo", empCodigo);
			map.put("empId", empId);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return restTemplate.postForObject(conexionWS + "/getRhEmpleadoRetencion", UtilsJSON.objetoToJson(map),
					boolean.class);
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return false;
	}

	public List<RhFormulario107PeriodoFiscalTO> getRhFormulario107PeriodoFiscalComboTO() {
		try {
			return Arrays.asList(restTemplate.postForObject(conexionWS + "/getRhFunConsultarUtilidades",
					UtilAplication.getSisInfoTO(), RhFormulario107PeriodoFiscalTO[].class));
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public List<RhFunUtilidadesCalcularTO> getRhFunCalcularUtilidades(final String empresa, final String sector,
			final String desde, final String hasta, final Integer totalDias, final Integer totalCargas,
			final BigDecimal totalPagar) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("empresa", empresa);
			map.put("sector", sector);
			map.put("desde", desde);
			map.put("hasta", hasta);
			map.put("totalDias", totalDias);
			map.put("totalCargas", totalCargas);
			map.put("totalPagar", totalPagar);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return Arrays.asList(restTemplate.postForObject(conexionWS + "/getRhFunCalcularUtilidades",
					UtilsJSON.objetoToJson(map), RhFunUtilidadesCalcularTO[].class));
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public List<RhFunXiiiSueldoCalcularTO> getRhFunCalcularXiiiSueldo(final String empresa, final String sector,
			final String desde, final String hasta) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("empresa", empresa);
			map.put("sector", sector);
			map.put("desde", desde);
			map.put("hasta", hasta);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return Arrays.asList(restTemplate.postForObject(conexionWS + "/getRhFunCalcularXiiiSueldo",
					UtilsJSON.objetoToJson(map), RhFunXiiiSueldoCalcularTO[].class));
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public List<RhFunXivSueldoCalcularTO> getRhFunCalcularXivSueldo(final String empresa, final String sector,
			final String desde, final String hasta) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("empresa", empresa);
			map.put("sector", sector);
			map.put("desde", desde);
			map.put("hasta", hasta);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return Arrays.asList(restTemplate.postForObject(conexionWS + "/getRhFunCalcularXivSueldo",
					UtilsJSON.objetoToJson(map), RhFunXivSueldoCalcularTO[].class));
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public List<RhFunXiiiSueldoConsultarTO> getRhFunConsultarXiiiSueldo(final String empresa, final String sector,
			final String desde, final String hasta) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("empresa", empresa);
			map.put("sector", sector);
			map.put("desde", desde);
			map.put("hasta", hasta);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return Arrays.asList(restTemplate.postForObject(conexionWS + "/getRhFunConsultarXiiiSueldo",
					UtilsJSON.objetoToJson(map), RhFunXiiiSueldoConsultarTO[].class));
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public List<RhFunXivSueldoConsultarTO> getRhFunConsultarXivSueldo(final String empresa, final String sector,
			final String desde, final String hasta) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("empresa", empresa);
			map.put("sector", sector);
			map.put("desde", desde);
			map.put("hasta", hasta);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return Arrays.asList(restTemplate.postForObject(conexionWS + "/getRhFunConsultarXivSueldo",
					UtilsJSON.objetoToJson(map), RhFunXivSueldoConsultarTO[].class));
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public List<RhFunFormulario107TO> getRhFunFormulario107(final String empresa, final String desde,
			final String hasta, final Character status) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("empresa", empresa);
			map.put("desde", desde);
			map.put("hasta", hasta);
			map.put("status", status);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return Arrays.asList(restTemplate.postForObject(conexionWS + "/getRhFunFormulario107",
					UtilsJSON.objetoToJson(map), RhFunFormulario107TO[].class));
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public List<RhFunFormulario107_2013TO> getRhFunFormulario107_2013(final String empresa, final String desde,
			final String hasta, final Character status) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("empresa", empresa);
			map.put("desde", desde);
			map.put("hasta", hasta);
			map.put("status", status);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return Arrays.asList(restTemplate.postForObject(conexionWS + "/getRhFunFormulario107_2013",
					UtilsJSON.objetoToJson(map), RhFunFormulario107_2013TO[].class));
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public List<RhFunListadoEmpleadosTO> getRhFunListadoEmpleadosTO(final String empresa, final String provincia,
			final String canton, final String sector, final String categoria, final boolean estado) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("empresa", empresa);
			map.put("provincia", provincia);
			map.put("canton", canton);
			map.put("sector", sector);
			map.put("categoria", categoria);
			map.put("estado", estado);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return Arrays.asList(restTemplate.postForObject(conexionWS + "/getRhFunListadoEmpleadosTO",
					UtilsJSON.objetoToJson(map), RhFunListadoEmpleadosTO[].class));
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public List<RhPreavisoAnticiposPrestamosSueldoTO> getRhFunPreavisoAnticiposBolivariano(final String empresa,
			final String fecha, final String cuenta, final String tipoPreAviso, final String servicio) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("empresa", empresa);
			map.put("fecha", fecha);
			map.put("cuenta", cuenta);
			map.put("tipoPreAviso", tipoPreAviso);
			map.put("servicio", servicio);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return Arrays.asList(restTemplate.postForObject(conexionWS + "/getRhFunPreavisoAnticiposBolivariano",
					UtilsJSON.objetoToJson(map), RhPreavisoAnticiposPrestamosSueldoTO[].class));
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public List<RhPreavisoAnticiposPrestamosSueldoPichinchaTO> getRhFunPreavisoAnticiposPichincha(final String empresa,
			final String fecha, final String cuenta, final String tipo) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("empresa", empresa);
			map.put("fecha", fecha);
			map.put("cuenta", cuenta);
			map.put("tipo", tipo);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return Arrays.asList(restTemplate.postForObject(conexionWS + "/getRhFunPreavisoAnticiposPichincha",
					UtilsJSON.objetoToJson(map), RhPreavisoAnticiposPrestamosSueldoPichinchaTO[].class));
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public List<RhPreavisoAnticiposPrestamosSueldoTO> getRhFunPreavisoPrestamos(final String empresa,
			final String fecha, final String cuenta) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("empresa", empresa);
			map.put("fecha", fecha);
			map.put("cuenta", cuenta);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return Arrays.asList(restTemplate.postForObject(conexionWS + "/getRhFunPreavisoPrestamos",
					UtilsJSON.objetoToJson(map), RhPreavisoAnticiposPrestamosSueldoTO[].class));
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public List<RhPreavisoAnticiposPrestamosSueldoTO> getRhFunPreavisoSueldos(final String empresa, final String fecha,
			final String cuenta) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("empresa", empresa);
			map.put("fecha", fecha);
			map.put("cuenta", cuenta);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return Arrays.asList(restTemplate.postForObject(conexionWS + "/getRhFunPreavisoAnticipos",
					UtilsJSON.objetoToJson(map), RhPreavisoAnticiposPrestamosSueldoTO[].class));
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public List<RhPreavisoAnticiposPrestamosSueldoTO> getRhFunPreavisoVacaciones(final String empresa,
			final String fecha, final String cuenta) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("empresa", empresa);
			map.put("fecha", fecha);
			map.put("cuenta", cuenta);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return Arrays.asList(restTemplate.postForObject(conexionWS + "/getRhFunPreavisoVacaciones",
					UtilsJSON.objetoToJson(map), RhPreavisoAnticiposPrestamosSueldoTO[].class));
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public List<RhPreavisoAnticiposPrestamosSueldoTO> getRhFunPreavisoXiiis(final String empresa, final String fecha,
			final String cuenta) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("empresa", empresa);
			map.put("fecha", fecha);
			map.put("cuenta", cuenta);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return Arrays.asList(restTemplate.postForObject(conexionWS + "/getRhFunPreavisoXiiis",
					UtilsJSON.objetoToJson(map), RhPreavisoAnticiposPrestamosSueldoTO[].class));
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public List<RhPreavisoAnticiposPrestamosSueldoTO> getRhFunPreavisoXivs(final String empresa, final String fecha,
			final String cuenta) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("empresa", empresa);
			map.put("fecha", fecha);
			map.put("cuenta", cuenta);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return Arrays.asList(restTemplate.postForObject(conexionWS + "/getRhFunPreavisoXivs",
					UtilsJSON.objetoToJson(map), RhPreavisoAnticiposPrestamosSueldoTO[].class));
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public List<RhListaDetalleBonosTO> getRhListaDetalleBonosFiltradoTO(final String empCodigo, final String fechaDesde,
			final String fechaHasta, final String empCategoria, final String empId, final String estadoDeducible,
			final String parametro) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("empCodigo", empCodigo);
			map.put("fechaDesde", fechaDesde);
			map.put("fechaHasta", fechaHasta);
			map.put("empCategoria", empCategoria);
			map.put("empId", empId);
			map.put("estadoDeducible", estadoDeducible);
			map.put("parametro", parametro);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return Arrays.asList(restTemplate.postForObject(conexionWS + "/getRhListaDetalleBonosFiltradoTO",
					UtilsJSON.objetoToJson(map), RhListaDetalleBonosTO[].class));
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public List<RhListaProvisionesTO> getRhListaProvisionesComprobanteContableTO(final String empresa,
			final String periodo, final String tipo, final String numero) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("empresa", empresa);
			map.put("periodo", periodo);
			map.put("tipo", tipo);
			map.put("numero", numero);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return Arrays.asList(restTemplate.postForObject(conexionWS + "/getRhListaProvisionesComprobanteContableTO",
					UtilsJSON.objetoToJson(map), RhListaProvisionesTO[].class));
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public List<RhListaProvisionesTO> getRhListaProvisionesTO(final String empresa, final String periodo,
			final String sector, final String status) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("empresa", empresa);
			map.put("periodo", periodo);
			map.put("sector", sector);
			map.put("status", status);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return Arrays.asList(restTemplate.postForObject(conexionWS + "/getRhListaProvisionesTO",
					UtilsJSON.objetoToJson(map), RhListaProvisionesTO[].class));
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	/////////////////////////// Metodos WEB ///////////////////////////
	public RhParametros getRhParametros(final String fechaHasta) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("fechaHasta", fechaHasta);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return restTemplate.postForObject(conexionWS + "/getRhParametros", UtilsJSON.objetoToJson(map),
					RhParametros.class);
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public List<RhListaRolSaldoEmpleadoDetalladoTO> getRhRolSaldoEmpleadoDetallado(final String empCodigo,
			final String empId, final String fechaDesde, final String fechaHasta) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("empCodigo", empCodigo);
			map.put("empId", empId);
			map.put("fechaDesde", fechaDesde);
			map.put("fechaHasta", fechaHasta);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return Arrays.asList(restTemplate.postForObject(conexionWS + "/getRhRolSaldoEmpleadoDetallado",
					UtilsJSON.objetoToJson(map), RhListaRolSaldoEmpleadoDetalladoTO[].class));
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public BigDecimal getRhRolSaldoPrestamo(final String empCodigo, final String empId, final String fechaDesde,
			final String fechaHasta) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("empCodigo", empCodigo);
			map.put("empId", empId);
			map.put("fechaDesde", fechaDesde);
			map.put("fechaHasta", fechaHasta);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return restTemplate.postForObject(conexionWS + "/getRhRolSaldoPrestamo", UtilsJSON.objetoToJson(map),
					BigDecimal.class);
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public RhRolSueldoEmpleadoTO getRhRolSueldoEmpleadoTO(final String empCodigo, final String empId) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("empCodigo", empCodigo);
			map.put("empId", empId);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return restTemplate.postForObject(conexionWS + "/getRhRolSueldoEmpleadoTO", UtilsJSON.objetoToJson(map),
					RhRolSueldoEmpleadoTO.class);
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public List<RhListaSaldoConsolidadoAnticiposPrestamosTO> getRhSaldoConsolidadoAnticiposPrestamosTO(
			final String empCodigo, final String fechaHasta) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("empCodigo", empCodigo);
			map.put("fechaHasta", fechaHasta);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return Arrays.asList(restTemplate.postForObject(conexionWS + "/getRhSaldoConsolidadoAnticiposPrestamosTO",
					UtilsJSON.objetoToJson(map), RhListaSaldoConsolidadoAnticiposPrestamosTO[].class));
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public List<RhListaSaldoConsolidadoBonosTO> getRhSaldoConsolidadoBonosTO(final String empCodigo,
			final String fechaHasta) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("empCodigo", empCodigo);
			map.put("fechaHasta", fechaHasta);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return Arrays.asList(restTemplate.postForObject(conexionWS + "/getRhSaldoConsolidadoBonosTO",
					UtilsJSON.objetoToJson(map), RhListaSaldoConsolidadoBonosTO[].class));
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public List<RhListaSaldoConsolidadoSueldosPorPagarTO> getRhSaldoConsolidadoSueldosPorPagarTO(final String empCodigo,
			final String fechaHasta) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("empCodigo", empCodigo);
			map.put("fechaHasta", fechaHasta);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return Arrays.asList(restTemplate.postForObject(conexionWS + "/getRhSaldoConsolidadoSueldosPorPagarTO",
					UtilsJSON.objetoToJson(map), RhListaSaldoConsolidadoSueldosPorPagarTO[].class));
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public List<RhListaSaldoIndividualAnticiposPrestamosTO> getRhSaldoIndividualAnticiposPrestamosTO(
			final String empCodigo, final String fechaDesde, final String fechaHasta, final String empId,
			final String tipo) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("empCodigo", empCodigo);
			map.put("fechaDesde", fechaDesde);
			map.put("fechaHasta", fechaHasta);
			map.put("empId", empId);
			map.put("tipo", tipo);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return Arrays.asList(restTemplate.postForObject(conexionWS + "/getRhSaldoIndividualAnticiposPrestamosTO",
					UtilsJSON.objetoToJson(map), RhListaSaldoIndividualAnticiposPrestamosTO[].class));
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public BigDecimal getRhValorImpuestoRenta(final String empCodigo, final String empId, final String fechaHasta,
			final Integer diasLaborados, final BigDecimal rolIngresos) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("empCodigo", empCodigo);
			map.put("empId", empId);
			map.put("fechaHasta", fechaHasta);
			map.put("diasLaborados", diasLaborados);
			map.put("rolIngresos", rolIngresos);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return restTemplate.postForObject(conexionWS + "/getRhValorImpuestoRenta", UtilsJSON.objetoToJson(map),
					BigDecimal.class);
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public boolean getSwInactivaEmpleado(final String empresa, final String empleado) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("empresa", empresa);
			map.put("empleado", empleado);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return restTemplate.postForObject(conexionWS + "/getSwInactivaEmpleado", UtilsJSON.objetoToJson(map),
					boolean.class);
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return false;
	}

	public String guardarImagenEmpleado(final byte[] imagen, final String nombre) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("imagen", imagen);
			map.put("nombre", nombre);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return restTemplate.postForObject(conexionWS + "/guardarImagenEmpleado", UtilsJSON.objetoToJson(map),
					String.class);
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public MensajeTO insertarModificarRhAnticipo(final ConContable conContable,
			final List<RhAnticipo> listaRhAnticipo) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("conContable", conContable);
			map.put("listaRhAnticipo", listaRhAnticipo);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return restTemplate.postForObject(conexionWS + "/insertarModificarRhAnticipo", UtilsJSON.objetoToJson(map),
					MensajeTO.class);
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public MensajeTO insertarModificarRhBono(final ConContable conContable, final List<RhBono> listaRhBono) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("conContable", conContable);
			map.put("listaRhBono", listaRhBono);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return restTemplate.postForObject(conexionWS + "/insertarModificarRhBono", UtilsJSON.objetoToJson(map),
					MensajeTO.class);
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public MensajeTO insertarModificarRhEmpleado(final RhEmpleado rhEmpleado,
			final List<RhEmpleadoDescuentosFijos> listEmpleadoDescuentosFijos) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("rhEmpleado", rhEmpleado);
			map.put("listEmpleadoDescuentosFijos", listEmpleadoDescuentosFijos);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return restTemplate.postForObject(conexionWS + "/insertarModificarRhEmpleado", map, MensajeTO.class);
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public MensajeTO insertarModificarRhPrestamo(final ConContable conContable, final RhPrestamo rhPrestamo) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("conContable", conContable);
			map.put("rhPrestamo", rhPrestamo);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return restTemplate.postForObject(conexionWS + "/insertarModificarRhPrestamo", UtilsJSON.objetoToJson(map),
					MensajeTO.class);
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public MensajeTO insertarModificarRhProvisiones(final ConContable conContable, final List<RhRol> listaRhRol) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("conContable", conContable);
			map.put("listaRhRol", listaRhRol);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return restTemplate.postForObject(conexionWS + "/insertarModificarRhProvisiones",
					UtilsJSON.objetoToJson(map), MensajeTO.class);
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public MensajeTO insertarModificarRhRol(final ConContable conContable, final List<RhRol> listaRhRol) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("conContable", conContable);
			map.put("listaRhRol", listaRhRol);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return restTemplate.postForObject(conexionWS + "/insertarModificarRhRol", UtilsJSON.objetoToJson(map),
					MensajeTO.class);
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public MensajeTO insertarModificarRhUtilidades(final ConContable conContable,
			final List<RhUtilidades> listaRhUtilidades) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("conContable", conContable);
			map.put("listaRhUtilidades", listaRhUtilidades);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return restTemplate.postForObject(conexionWS + "/insertarModificarRhUtilidades",
					UtilsJSON.objetoToJson(map), MensajeTO.class);
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public MensajeTO insertarModificarRhXiiiSueldo(final ConContable conContable,
			final List<RhXiiiSueldo> listaRhXiiiSueldo) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("conContable", conContable);
			map.put("listaRhXiiiSueldo", listaRhXiiiSueldo);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return restTemplate.postForObject(conexionWS + "/insertarModificarRhXiiiSueldo",
					UtilsJSON.objetoToJson(map), MensajeTO.class);
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public MensajeTO insertarModificarRhXivSueldo(final ConContable conContable,
			final List<RhXivSueldo> listaRhXivSueldo) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("conContable", conContable);
			map.put("listaRhXivSueldo", listaRhXivSueldo);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return restTemplate.postForObject(conexionWS + "/insertarModificarRhXivSueldo", UtilsJSON.objetoToJson(map),
					MensajeTO.class);
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public MensajeTO insertarRhAnticipoMotivo(final RhAnticipoMotivo rhAnticipoMotivo) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("rhAnticipoMotivo", rhAnticipoMotivo);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return restTemplate.postForObject(conexionWS + "/insertarRhAnticipoMotivo", UtilsJSON.objetoToJson(map),
					MensajeTO.class);
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public boolean insertarRhBonoConcepto(final RhBonoConceptoTO rhBonoConceptoTO) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("rhBonoConceptoTO", rhBonoConceptoTO);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return restTemplate.postForObject(conexionWS + "/insertarRhBonoConcepto", UtilsJSON.objetoToJson(map),
					boolean.class);
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return false;
	}

	public MensajeTO insertarRhBonoMotivo(final RhBonoMotivo rhBonoMotivo) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("rhBonoMotivo", rhBonoMotivo);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return restTemplate.postForObject(conexionWS + "/insertarRhBonoMotivo", UtilsJSON.objetoToJson(map),
					MensajeTO.class);
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public String insertarRhEmpleado(final RhEmpleadoTO rhEmpleadoTO,
			final List<RhEmpleadoDescuentosFijosTO> ListarhEmpleadoDescuentosFijosTO) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("rhEmpleadoTO", rhEmpleadoTO);
			map.put("ListarhEmpleadoDescuentosFijosTO", ListarhEmpleadoDescuentosFijosTO);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return restTemplate.postForObject(conexionWS + "/insertarRhEmpleado", UtilsJSON.objetoToJson(map),
					String.class);
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public RhContableTO insertarRhListaProvisionesConContable(final String empresa, final String periodo,
			final String sector, final String usuario) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("empresa", empresa);
			map.put("periodo", periodo);
			map.put("sector", sector);
			map.put("usuario", usuario);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return restTemplate.postForObject(conexionWS + "/insertarRhListaProvisionesConContable",
					UtilsJSON.objetoToJson(map), RhContableTO.class);
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public MensajeTO insertarRhPrestamoMotivo(final RhPrestamoMotivo rhPrestamoMotivo) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("rhPrestamoMotivo", rhPrestamoMotivo);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return restTemplate.postForObject(conexionWS + "/insertarRhPrestamoMotivo", UtilsJSON.objetoToJson(map),
					MensajeTO.class);
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public String insertarRhRolMotivo(final RhRolMotivo rhRolMotivo) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("rhRolMotivo", rhRolMotivo);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return restTemplate.postForObject(conexionWS + "/insertarRhRolMotivo", UtilsJSON.objetoToJson(map),
					String.class);
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public RhContableTO insertarRhSalarioDignoContable(final RhSalarioDignoTO rhSalarioDignoTO) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("rhSalarioDignoTO", rhSalarioDignoTO);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return restTemplate.postForObject(conexionWS + "/insertarRhSalarioDignoContable",
					UtilsJSON.objetoToJson(map), RhContableTO.class);
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public MensajeTO insertarRhUtilidadMotivo(final RhUtilidadMotivo rhUtilidadMotivo) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("rhUtilidadMotivo", rhUtilidadMotivo);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return restTemplate.postForObject(conexionWS + "/insertarRhUtilidadMotivo", UtilsJSON.objetoToJson(map),
					MensajeTO.class);
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public RhContableTO insertarRhVacaciones(final RhVacacionesTO rhVacacionesTO) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("rhVacacionesTO", rhVacacionesTO);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return restTemplate.postForObject(conexionWS + "/insertarRhVacacionesConContable",
					UtilsJSON.objetoToJson(map), RhContableTO.class);
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public MensajeTO insertarRhXiiiSueldoMotivo(final RhXiiiSueldoMotivo rhXiiiSueldoMotivo) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("rhXiiiSueldoMotivo", rhXiiiSueldoMotivo);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return restTemplate.postForObject(conexionWS + "/insertarRhXiiiSueldoMotivo", UtilsJSON.objetoToJson(map),
					MensajeTO.class);
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public MensajeTO insertarRhXivSueldoMotivo(final RhXivSueldoMotivo rhXivSueldoMotivo) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("rhXivSueldoMotivo", rhXivSueldoMotivo);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return restTemplate.postForObject(conexionWS + "/insertarRhXivSueldoMotivo", UtilsJSON.objetoToJson(map),
					MensajeTO.class);
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public String modificarRhAnticipoMotivo(final RhAnticipoMotivo rhAnticipoMotivo) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("rhAnticipoMotivo", rhAnticipoMotivo);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return restTemplate.postForObject(conexionWS + "/modificarRhAnticipoMotivo", UtilsJSON.objetoToJson(map),
					String.class);
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public boolean modificarRhBonoConcepto(final RhBonoConceptoTO rhBonoConceptoTO) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("rhBonoConceptoTO", rhBonoConceptoTO);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return restTemplate.postForObject(conexionWS + "/modificarRhBonoConcepto", UtilsJSON.objetoToJson(map),
					boolean.class);
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return false;
	}

	public String modificarRhBonoMotivo(final RhBonoMotivo rhBonoMotivo) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("rhBonoMotivo", rhBonoMotivo);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return restTemplate.postForObject(conexionWS + "/modificarRhBonoMotivo", UtilsJSON.objetoToJson(map),
					String.class);
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public String modificarRhEmpleado(final RhEmpleadoTO rhEmpleadoTO,
			final List<RhEmpleadoDescuentosFijosTO> listaModificar,
			final List<RhEmpleadoDescuentosFijosTO> listaEliminar) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("rhEmpleadoTO", rhEmpleadoTO);
			map.put("listaModificar", listaModificar);
			map.put("listaEliminar", listaEliminar);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return restTemplate.postForObject(conexionWS + "/modificarRhEmpleado", UtilsJSON.objetoToJson(map),
					String.class);
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public String modificarRhPrestamoMotivo(final RhPrestamoMotivo rhPrestamoMotivo) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("rhPrestamoMotivo", rhPrestamoMotivo);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return restTemplate.postForObject(conexionWS + "/modificarRhPrestamoMotivo", UtilsJSON.objetoToJson(map),
					String.class);
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public String modificarRhRolMotivo(final RhRolMotivo rhRolMotivo) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("rhRolMotivo", rhRolMotivo);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return restTemplate.postForObject(conexionWS + "/modificarRhRolMotivo", UtilsJSON.objetoToJson(map),
					String.class);
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public String modificarRhUtilidadMotivo(final RhUtilidadMotivo rhUtilidadMotivo) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("rhUtilidadMotivo", rhUtilidadMotivo);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return restTemplate.postForObject(conexionWS + "/modificarRhUtilidadMotivo", UtilsJSON.objetoToJson(map),
					String.class);
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public String modificarRhXiiiSueldoMotivo(final RhXiiiSueldoMotivo rhXiiiSueldoMotivo) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("rhXiiiSueldoMotivo", rhXiiiSueldoMotivo);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return restTemplate.postForObject(conexionWS + "/modificarRhXiiiSueldoMotivo", UtilsJSON.objetoToJson(map),
					String.class);
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public String modificarRhXivSueldoMotivo(final RhXivSueldoMotivo rhXivSueldoMotivo) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("rhXivSueldoMotivo", rhXivSueldoMotivo);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return restTemplate.postForObject(conexionWS + "/modificarRhXivSueldoMotivo", UtilsJSON.objetoToJson(map),
					String.class);
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public byte[] obtenerImagenEmpleado(final String nombre) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("nombre", nombre);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return restTemplate.postForObject(conexionWS + "/obtenerImagenEmpleado", UtilsJSON.objetoToJson(map),
					byte[].class);
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public String obtenerRutaImagenEmpleado(final String nombre) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("nombre", nombre);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return restTemplate.postForObject(conexionWS + "/obtenerRutaImagenEmpleado", UtilsJSON.objetoToJson(map),
					String.class);
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

}
