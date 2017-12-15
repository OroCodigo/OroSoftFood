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
import ec.com.orocodigo.OroSoftUtils.UtilsAplicacion;
import ec.com.orocodigo.OroSoftUtils.UtilsJSON;
import ec.com.orocodigo.OroSoftUtils.UtilsRESTFul;
import ec.com.orocodigo.OroSoftUtils.produccion.TO.ListaLiquidacionTO;
import ec.com.orocodigo.OroSoftUtils.produccion.TO.ListaPreLiquidacionTO;
import ec.com.orocodigo.OroSoftUtils.produccion.TO.MultiplePiscinaCorrida;
import ec.com.orocodigo.OroSoftUtils.produccion.TO.PiscinaGrameajeTO;
import ec.com.orocodigo.OroSoftUtils.produccion.TO.PrdComboCorridaTO;
import ec.com.orocodigo.OroSoftUtils.produccion.TO.PrdComboPiscinaTO;
import ec.com.orocodigo.OroSoftUtils.produccion.TO.PrdConsumosTO;
import ec.com.orocodigo.OroSoftUtils.produccion.TO.PrdContabilizarCorridaTO;
import ec.com.orocodigo.OroSoftUtils.produccion.TO.PrdCorridaTO;
import ec.com.orocodigo.OroSoftUtils.produccion.TO.PrdCostoDetalleFechaTO;
import ec.com.orocodigo.OroSoftUtils.produccion.TO.PrdEstadoCCCVT;
import ec.com.orocodigo.OroSoftUtils.produccion.TO.PrdFunCostosPorFechaSimpleTO;
import ec.com.orocodigo.OroSoftUtils.produccion.TO.PrdGrameajeTO;
import ec.com.orocodigo.OroSoftUtils.produccion.TO.PrdLiquidacionCabeceraTO;
import ec.com.orocodigo.OroSoftUtils.produccion.TO.PrdLiquidacionDetalleTO;
import ec.com.orocodigo.OroSoftUtils.produccion.TO.PrdLiquidacionMotivoComboTO;
import ec.com.orocodigo.OroSoftUtils.produccion.TO.PrdLiquidacionMotivoTO;
import ec.com.orocodigo.OroSoftUtils.produccion.TO.PrdLiquidacionMotivoTablaTO;
import ec.com.orocodigo.OroSoftUtils.produccion.TO.PrdLiquidacionProductoTO;
import ec.com.orocodigo.OroSoftUtils.produccion.TO.PrdLiquidacionProductoTablaTO;
import ec.com.orocodigo.OroSoftUtils.produccion.TO.PrdLiquidacionTO;
import ec.com.orocodigo.OroSoftUtils.produccion.TO.PrdLiquidacionTallaTO;
import ec.com.orocodigo.OroSoftUtils.produccion.TO.PrdLiquidacionTallaTablaTO;
import ec.com.orocodigo.OroSoftUtils.produccion.TO.PrdListaCorridaTO;
import ec.com.orocodigo.OroSoftUtils.produccion.TO.PrdListaCostosDetalleCorridaTO;
import ec.com.orocodigo.OroSoftUtils.produccion.TO.PrdListaDetalleLiquidacionTO;
import ec.com.orocodigo.OroSoftUtils.produccion.TO.PrdListaFunAnalisisPesosTO;
import ec.com.orocodigo.OroSoftUtils.produccion.TO.PrdListaPiscinaComboTO;
import ec.com.orocodigo.OroSoftUtils.produccion.TO.PrdListaPiscinaTO;
import ec.com.orocodigo.OroSoftUtils.produccion.TO.PrdListaSectorConHectareajeTO;
import ec.com.orocodigo.OroSoftUtils.produccion.TO.PrdListaSectorTO;
import ec.com.orocodigo.OroSoftUtils.produccion.TO.PrdListaSobrevivenciaTO;
import ec.com.orocodigo.OroSoftUtils.produccion.TO.PrdListadoGrameajeTO;
import ec.com.orocodigo.OroSoftUtils.produccion.TO.PrdPiscinaTO;
import ec.com.orocodigo.OroSoftUtils.produccion.TO.PrdProyeccionTO;
import ec.com.orocodigo.OroSoftUtils.produccion.TO.PrdResumenCorridaTO;
import ec.com.orocodigo.OroSoftUtils.produccion.TO.PrdResumenFinancieroTO;
import ec.com.orocodigo.OroSoftUtils.produccion.TO.PrdSectorTO;
import ec.com.orocodigo.OroSoftUtils.produccion.TO.PrdSobrevivenciaTO;
import ec.com.orocodigo.OroSoftUtils.produccion.entity.PrdCorrida;
import ec.com.orocodigo.OroSoftUtils.produccion.entity.PrdCorridaDetalle;
import ec.com.orocodigo.OroSoftUtils.produccion.entity.PrdGrameaje;
import ec.com.orocodigo.OroSoftUtils.produccion.entity.PrdLiquidacion;
import ec.com.orocodigo.OroSoftUtils.produccion.entity.PrdLiquidacionDetalle;
import ec.com.orocodigo.OroSoftUtils.produccion.entity.PrdLiquidacionMotivo;
import ec.com.orocodigo.OroSoftUtils.produccion.entity.PrdLiquidacionPK;
import ec.com.orocodigo.OroSoftUtils.produccion.entity.PrdPiscina;
import ec.com.orocodigo.OroSoftUtils.produccion.entity.PrdPreLiquidacion;
import ec.com.orocodigo.OroSoftUtils.produccion.entity.PrdPreLiquidacionDetalle;
import ec.com.orocodigo.OroSoftUtils.produccion.entity.PrdPreLiquidacionMotivo;
import ec.com.orocodigo.OroSoftUtils.produccion.entity.PrdPreLiquidacionPK;
import ec.com.orocodigo.OroSoftUtils.produccion.entity.PrdPresupuestoPesca;
import ec.com.orocodigo.OroSoftUtils.produccion.entity.PrdPresupuestoPescaDetalle;
import ec.com.orocodigo.OroSoftUtils.produccion.entity.PrdPresupuestoPescaMotivo;
import ec.com.orocodigo.OroSoftUtils.produccion.entity.PrdPresupuestoPescaPK;
import ec.com.orocodigo.OroSoftUtils.produccion.entity.PrdProducto;
import ec.com.orocodigo.OroSoftUtils.produccion.entity.PrdSector;
import ec.com.orocodigo.OroSoftUtils.produccion.entity.PrdTalla;
import ec.com.orocodigo.OroSoftUtils.produccion.report.ReporteLiquidacionPesca;
import ec.com.orocodigo.OroSoftUtils.produccion.report.ReportePrdFunCostosPorFechaSimpleTO;
import ec.com.orocodigo.OroSoftUtils.produccion.report.ReporteResumenFinanciero;
import ec.com.orocodigo.OroSoftWeb.utils.UtilsExcepciones;

public class ProduccionDelegate {

	private static ProduccionDelegate produccionDelegate;

	public static ProduccionDelegate getInstance() {
		if (produccionDelegate == null)
			try {
				produccionDelegate = new ProduccionDelegate();
			} catch (final Exception e) {
				e.printStackTrace();
			}
		return produccionDelegate;
	}

	private final String conexionWS;

	private final RestTemplate restTemplate = new RestTemplate();

	private ProduccionDelegate() throws Exception {
		final Properties propSistema = Propiedades.readPropiedades();
		conexionWS = "http://" + propSistema.getProperty("servidor.ip") + ":"
				+ propSistema.getProperty("servidor.puerto") + "/" + propSistema.getProperty("servidor.ubicacion")
				+ "/OroSoftWS/produccionController";
	}

	public String anularRestaurarPrdLiquidacion(final PrdLiquidacionPK liquidacionPK, final boolean anularRestaurar) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("liquidacionPK", liquidacionPK);
			map.put("anularRestaurar", anularRestaurar);
			map.put("sisInfoTO", UtilsAplicacion.getSisInfoTO());
			return restTemplate.postForObject(conexionWS + "/anularRestaurarPrdLiquidacion",
					UtilsJSON.objetoToJson(map), String.class);
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public String anularRestaurarPrdPreLiquidacion(final PrdPreLiquidacionPK liquidacionPK,
			final boolean anularRestaurar) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("liquidacionPK", liquidacionPK);
			map.put("anularRestaurar", anularRestaurar);
			map.put("sisInfoTO", UtilsAplicacion.getSisInfoTO());
			return restTemplate.postForObject(conexionWS + "/anularRestaurarPrdPreLiquidacion",
					UtilsJSON.objetoToJson(map), String.class);
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public String anularRestaurarPresupuestoPesca(final PrdPresupuestoPescaPK presupuestoPescaPK,
			final boolean anularRestaurar) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("presupuestoPescaPK", presupuestoPescaPK);
			map.put("anularRestaurar", anularRestaurar);
			map.put("sisInfoTO", UtilsAplicacion.getSisInfoTO());
			return restTemplate.postForObject(conexionWS + "/anularRestaurarPresupuestoPesca",
					UtilsJSON.objetoToJson(map), String.class);
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public String consultarFechaMaxMin(final String empresa, final String tipoResumen) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("empresa", empresa);
			map.put("tipoResumen", tipoResumen);
			map.put("sisInfoTO", UtilsAplicacion.getSisInfoTO());
			return restTemplate.postForObject(conexionWS + "/consultarFechaMaxMin", UtilsJSON.objetoToJson(map),
					String.class, map);
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public String desmayorizarPrdLiquidacion(final PrdLiquidacionPK liquidacionPK) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("liquidacionPK", liquidacionPK);
			map.put("sisInfoTO", UtilsAplicacion.getSisInfoTO());
			return restTemplate.postForObject(conexionWS + "/desmayorizarPrdLiquidacion", UtilsJSON.objetoToJson(map),
					String.class);
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public String desmayorizarPrdPreLiquidacion(final PrdPreLiquidacionPK liquidacionPK) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("liquidacionPK", liquidacionPK);
			map.put("sisInfoTO", UtilsAplicacion.getSisInfoTO());
			return restTemplate.postForObject(conexionWS + "/desmayorizarPrdPreLiquidacion",
					UtilsJSON.objetoToJson(map), String.class);
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public String desmayorizarPresupuestoPesca(final PrdPresupuestoPescaPK presupuestoPescaPK) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("presupuestoPescaPK", presupuestoPescaPK);
			map.put("sisInfoTO", UtilsAplicacion.getSisInfoTO());
			return restTemplate.postForObject(conexionWS + "/desmayorizarPresupuestoPesca", UtilsJSON.objetoToJson(map),
					String.class);
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public String eliminarGrameaje(final PrdGrameaje prdGrameaje) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("prdGrameaje", prdGrameaje);
			map.put("sisInfoTO", UtilsAplicacion.getSisInfoTO());
			return restTemplate.postForObject(conexionWS + "/eliminarGrameaje", UtilsJSON.objetoToJson(map),
					String.class);
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public String eliminarPrdCorrida(final PrdCorrida prdCorrida) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("prdCorrida", prdCorrida);
			map.put("sisInfoTO", UtilsAplicacion.getSisInfoTO());
			return restTemplate.postForObject(conexionWS + "/eliminarPrdCorrida", UtilsJSON.objetoToJson(map),
					String.class);
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public String eliminarPrdGrameaje(final PrdGrameajeTO prdGrameajeTO) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("prdGrameajeTO", prdGrameajeTO);
			map.put("sisInfoTO", UtilsAplicacion.getSisInfoTO());
			return restTemplate.postForObject(conexionWS + "/eliminarPrdGrameaje", UtilsJSON.objetoToJson(map),
					String.class);
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public String eliminarPrdLiquidacionMotivo(final PrdLiquidacionMotivo prdLiquidacionMotivo) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("prdLiquidacionMotivo", prdLiquidacionMotivo);
			map.put("sisInfoTO", UtilsAplicacion.getSisInfoTO());
			return restTemplate.postForObject(conexionWS + "/eliminarPrdLiquidacionMotivo", UtilsJSON.objetoToJson(map),
					String.class);
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public String eliminarPrdLiquidacionMotivoTO(final PrdLiquidacionMotivoTO prdLiquidacionMotivoTO) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("prdLiquidacionMotivoTO", prdLiquidacionMotivoTO);
			map.put("sisInfoTO", UtilsAplicacion.getSisInfoTO());
			return restTemplate.postForObject(conexionWS + "/eliminarPrdLiquidacionMotivoTO",
					UtilsJSON.objetoToJson(map), String.class);
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public String eliminarPrdLiquidacionProducto(final PrdProducto prdLiquidacionProducto) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("prdLiquidacionProducto", prdLiquidacionProducto);
			map.put("sisInfoTO", UtilsAplicacion.getSisInfoTO());
			return restTemplate.postForObject(conexionWS + "/eliminarPrdLiquidacionProducto",
					UtilsJSON.objetoToJson(map), String.class);
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public String eliminarPrdLiquidacionProductoTO(final PrdLiquidacionProductoTO prdLiquidacionProductoTO) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("prdLiquidacionProductoTO", prdLiquidacionProductoTO);
			map.put("sisInfoTO", UtilsAplicacion.getSisInfoTO());
			return restTemplate.postForObject(conexionWS + "/eliminarPrdLiquidacionProductoTO",
					UtilsJSON.objetoToJson(map), String.class);
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public String eliminarPrdLiquidacionTalla(final PrdTalla prdLiquidacionTalla) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("prdLiquidacionTalla", prdLiquidacionTalla);
			map.put("sisInfoTO", UtilsAplicacion.getSisInfoTO());
			return restTemplate.postForObject(conexionWS + "/eliminarPrdLiquidacionTalla", UtilsJSON.objetoToJson(map),
					String.class);
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public String eliminarPrdLiquidacionTallaTO(final PrdLiquidacionTallaTO prdLiquidacionTallaTO) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("prdLiquidacionTallaTO", prdLiquidacionTallaTO);
			map.put("sisInfoTO", UtilsAplicacion.getSisInfoTO());
			return restTemplate.postForObject(conexionWS + "/eliminarPrdLiquidacionTallaTO",
					UtilsJSON.objetoToJson(map), String.class);
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public String eliminarPrdPiscina(final PrdPiscina prdPiscina) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("prdPiscina", prdPiscina);
			map.put("sisInfoTO", UtilsAplicacion.getSisInfoTO());
			return restTemplate.postForObject(conexionWS + "/eliminarPrdPiscina", UtilsJSON.objetoToJson(map),
					String.class);
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public boolean eliminarPrdPiscina(final PrdPiscinaTO prdPiscinaTO) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("prdPiscinaTO", prdPiscinaTO);
			map.put("sisInfoTO", UtilsAplicacion.getSisInfoTO());
			return restTemplate.postForObject(conexionWS + "/eliminarPrdPiscinaTO", UtilsJSON.objetoToJson(map),
					boolean.class);
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return false;
	}

	public String eliminarPrdPreLiquidacionMotivo(final PrdPreLiquidacionMotivo prdPreLiquidacionMotivo) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("prdPreLiquidacionMotivo", prdPreLiquidacionMotivo);
			map.put("sisInfoTO", UtilsAplicacion.getSisInfoTO());
			return restTemplate.postForObject(conexionWS + "/eliminarPrdPreLiquidacionMotivo",
					UtilsJSON.objetoToJson(map), String.class);
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public String eliminarPrdPresupuestoPescaMotivo(final PrdPresupuestoPescaMotivo prdPresupuestoPescaMotivo) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("prdPresupuestoPescaMotivo", prdPresupuestoPescaMotivo);
			map.put("sisInfoTO", UtilsAplicacion.getSisInfoTO());
			return restTemplate.postForObject(conexionWS + "/eliminarPrdPresupuestoPescaMotivo",
					UtilsJSON.objetoToJson(map), String.class);
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public String eliminarPrdSector(final PrdSector prdSector) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("prdSector", prdSector);
			map.put("sisInfoTO", UtilsAplicacion.getSisInfoTO());
			return restTemplate.postForObject(conexionWS + "/eliminarPrdSector", UtilsJSON.objetoToJson(map),
					String.class);
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public boolean eliminarPrdSector(final PrdSectorTO prdSectorTO) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("prdSectorTO", prdSectorTO);
			map.put("sisInfoTO", UtilsAplicacion.getSisInfoTO());
			return restTemplate.postForObject(conexionWS + "/eliminarPrdSectorTO", UtilsJSON.objetoToJson(map),
					boolean.class);
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return false;
	}

	public boolean eliminarPrdSobrevivencia(final PrdSobrevivenciaTO prdSobrevivenciaTO) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("prdSobrevivenciaTO", prdSobrevivenciaTO);
			map.put("sisInfoTO", UtilsAplicacion.getSisInfoTO());
			return restTemplate.postForObject(conexionWS + "/eliminarPrdSobrevivencia", UtilsJSON.objetoToJson(map),
					boolean.class);
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return false;
	}

	public String generarReporteConsumoFecha(final String secCodigo, final String fechaDesde, final String fechaHasta,
			final List<PrdConsumosTO> prdConsumosTO) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("usuarioEmpresaReporteTO", UtilsAplicacion.usuarioEmpresaReporteTO);
			map.put("secCodigo", secCodigo);
			map.put("fechaDesde", fechaDesde);
			map.put("fechaHasta", fechaHasta);
			map.put("prdConsumosTO", prdConsumosTO);
			map.put("sisInfoTO", UtilsAplicacion.getSisInfoTO());

			final String rutaArchivo = "reportConsumoFecha.jrprint";
			return UtilsRESTFul.postForFile(conexionWS + "/generarReporteConsumoFecha", map, rutaArchivo);

		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public String generarReporteConsumoPiscina(final String codigoSector, final String codigoPiscina,
			final String numeroCorrida, final String hectareas, final Integer numLarvas, final String fechaDesde,
			final String fechaHasta, final List<PrdConsumosTO> prdConsumosTO) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("usuarioEmpresaReporteTO", UtilsAplicacion.usuarioEmpresaReporteTO);
			map.put("codigoSector", codigoSector);
			map.put("codigoPiscina", codigoPiscina);
			map.put("numeroCorrida", numeroCorrida);
			map.put("hectareas", hectareas);
			map.put("numLarvas", numLarvas);
			map.put("fechaDesde", fechaDesde);
			map.put("fechaHasta", fechaHasta);
			map.put("prdConsumosTO", prdConsumosTO);
			map.put("sisInfoTO", UtilsAplicacion.getSisInfoTO());
			final String rutaArchivo = "reportConsumoPiscina.jrprint";
			return UtilsRESTFul.postForFile(conexionWS + "/generarReporteConsumoPiscina", map, rutaArchivo);

		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public String generarReporteEconomicoPorFechas(final String fechaDesde, final String fechaHasta,
			final List<PrdCostoDetalleFechaTO> listaPrdCostoDetalleFechaTO) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("usuarioEmpresaReporteTO", UtilsAplicacion.usuarioEmpresaReporteTO);
			map.put("fechaDesde", fechaDesde);
			map.put("fechaHasta", fechaHasta);
			map.put("listaPrdCostoDetalleFechaTO", listaPrdCostoDetalleFechaTO);
			map.put("sisInfoTO", UtilsAplicacion.getSisInfoTO());
			final String rutaArchivo = "reportEconomicoPorFechas.jrprint";
			return UtilsRESTFul.postForFile(conexionWS + "/generarReporteEconomicoPorFechas", map, rutaArchivo);

		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public String generarReporteEconomicoPorPiscinas(final String codigoSector, final String codigoPiscina,
			final String codigoCorrida, final String hectareas, final Integer numLarvas, final String fechaDesde,
			final String fechaHasta, final List<PrdListaCostosDetalleCorridaTO> prdListaCostosDetalleCorridaTO) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("usuarioEmpresaReporteTO", UtilsAplicacion.usuarioEmpresaReporteTO);
			map.put("codigoSector", codigoSector);
			map.put("codigoPiscina", codigoPiscina);
			map.put("codigoCorrida", codigoCorrida);
			map.put("hectareas", hectareas);
			map.put("numLarvas", numLarvas);
			map.put("fechaDesde", fechaDesde);
			map.put("fechaHasta", fechaHasta);
			map.put("prdListaCostosDetalleCorridaTO", prdListaCostosDetalleCorridaTO);
			map.put("sisInfoTO", UtilsAplicacion.getSisInfoTO());
			final String rutaArchivo = "reportEconomicoPorPiscinas.jrprint";
			return UtilsRESTFul.postForFile(conexionWS + "/generarReporteEconomicoPorPiscinas", map, rutaArchivo);

		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public String generarReporteLiquidacionPesca(final List<ReporteLiquidacionPesca> reporteLiquidacionPescas) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("usuarioEmpresaReporteTO", UtilsAplicacion.usuarioEmpresaReporteTO);
			map.put("reporteLiquidacionPescas", reporteLiquidacionPescas);
			map.put("sisInfoTO", UtilsAplicacion.getSisInfoTO());
			final String rutaArchivo = "reportLiquidacionPesca.jrprint";
			return UtilsRESTFul.postForFile(conexionWS + "/generarReporteLiquidacionPesca", map, rutaArchivo);
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public String generarReporteListadoFunAnalisisPesos(final String codigoSector, final String fechaHasta,
			final List<PrdListaFunAnalisisPesosTO> prdListaFunAnalisisPesosTO) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("usuarioEmpresaReporteTO", UtilsAplicacion.usuarioEmpresaReporteTO);
			map.put("codigoSector", codigoSector);
			map.put("fechaHasta", fechaHasta);
			map.put("prdListaFunAnalisisPesosTO", prdListaFunAnalisisPesosTO);
			map.put("sisInfoTO", UtilsAplicacion.getSisInfoTO());
			final String rutaArchivo = "reportListadoFunAnalisisPesos.jrprint";
			return UtilsRESTFul.postForFile(conexionWS + "/generarReporteListadoFunAnalisisPesos", map, rutaArchivo);

		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public String generarReporteListadoGramaje(final String codigoSector, final String codigoPiscina,
			final String codigoCorrida, final String fechaDesde, final String fechaHasta,
			final List<PrdListadoGrameajeTO> prdListadoGrameajeTO) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("usuarioEmpresaReporteTO", UtilsAplicacion.usuarioEmpresaReporteTO);
			map.put("codigoSector", codigoSector);
			map.put("codigoPiscina", codigoPiscina);
			map.put("codigoCorrida", codigoCorrida);
			map.put("fechaDesde", fechaDesde);
			map.put("fechaHasta", fechaHasta);
			map.put("prdListadoGrameajeTO", prdListadoGrameajeTO);
			map.put("sisInfoTO", UtilsAplicacion.getSisInfoTO());
			final String rutaArchivo = "reportListadoGramaje.jrprint";
			return UtilsRESTFul.postForFile(conexionWS + "/generarReporteListadoGramaje", map, rutaArchivo);
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public String generarReportePrdFunCostosPorFechaSimpleTO(
			final List<ReportePrdFunCostosPorFechaSimpleTO> reportePrdFunCostosPorFechaSimpleTOs,
			final String tituloReporte, String rutaArchivo) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("usuarioEmpresaReporteTO", UtilsAplicacion.usuarioEmpresaReporteTO);
			map.put("reportePrdFunCostosPorFechaSimpleTOs", reportePrdFunCostosPorFechaSimpleTOs);
			map.put("tituloReporte", tituloReporte);
			map.put("sisInfoTO", UtilsAplicacion.getSisInfoTO());
			rutaArchivo = rutaArchivo == null || rutaArchivo.isEmpty() ? "reportPrdFunCostosPorFechaSimpleTO.jrprint"
					: rutaArchivo;
			return UtilsRESTFul.postForFile(conexionWS + "/generarReportePrdFunCostosPorFechaSimpleTO", map,
					rutaArchivo);

		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public String generarReportePrdResumenCorrida(final List<PrdResumenCorridaTO> prdResumenCorrida,
			final String tituloReporte, String rutaArchivo) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("usuarioEmpresaReporteTO", UtilsAplicacion.usuarioEmpresaReporteTO);
			map.put("prdResumenCorrida", prdResumenCorrida);
			map.put("tituloReporte", tituloReporte);
			map.put("sisInfoTO", UtilsAplicacion.getSisInfoTO());
			rutaArchivo = rutaArchivo == null || rutaArchivo.isEmpty() ? "reportPrdResumenCorrida.jrprint"
					: rutaArchivo;
			return UtilsRESTFul.postForFile(conexionWS + "/generarReportePrdResumenCorrida", map, rutaArchivo);

		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public String generarReporteResumenFinanciero(final List<ReporteResumenFinanciero> reporteResumenFinanciero,
			final String tituloReporte, String rutaArchivo) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("usuarioEmpresaReporteTO", UtilsAplicacion.usuarioEmpresaReporteTO);
			map.put("reporteResumenFinanciero", reporteResumenFinanciero);
			map.put("tituloReporte", tituloReporte);
			map.put("sisInfoTO", UtilsAplicacion.getSisInfoTO());
			rutaArchivo = rutaArchivo == null || rutaArchivo.isEmpty() ? "reportResumenFinanciero.jrprint"
					: rutaArchivo;
			return UtilsRESTFul.postForFile(conexionWS + "/generarReporteResumenFinanciero", map, rutaArchivo);

		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public String generarReporteResumenPesca(final List<PrdResumenCorridaTO> listaPrdListaResumenCorridaTO) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("usuarioEmpresaReporteTO", UtilsAplicacion.usuarioEmpresaReporteTO);
			map.put("listaPrdListaResumenCorridaTO", listaPrdListaResumenCorridaTO);
			map.put("sisInfoTO", UtilsAplicacion.getSisInfoTO());
			final String rutaArchivo = "reportResumenPesca.jrprint";
			return UtilsRESTFul.postForFile(conexionWS + "/generarResumenPesca", map, rutaArchivo);

		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public String generarReporteResumenPescaEconomico(final Object[][] datos) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("usuarioEmpresaReporteTO", UtilsAplicacion.usuarioEmpresaReporteTO);
			map.put("datos", datos);
			map.put("sisInfoTO", UtilsAplicacion.getSisInfoTO());
			final String rutaArchivo = "reportResumenPescaEconomico.jrprint";
			return UtilsRESTFul.postForFile(conexionWS + "/generarReporteResumenPescaEconomico", map, rutaArchivo);

		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public String generarReporteResumenSiembra(final List<PrdResumenCorridaTO> listaPrdListaResumenCorridaTO) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("usuarioEmpresaReporteTO", UtilsAplicacion.usuarioEmpresaReporteTO);
			map.put("listaPrdListaResumenCorridaTO", listaPrdListaResumenCorridaTO);
			map.put("sisInfoTO", UtilsAplicacion.getSisInfoTO());
			final String rutaArchivo = "reportResumenSiembraAux.jrprint";
			return UtilsRESTFul.postForFile(conexionWS + "/generarReporteResumenSiembraAux", map, rutaArchivo);

		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;

	}

	public String generarReporteResumenSiembra(final Object[][] datos) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("usuarioEmpresaReporteTO", UtilsAplicacion.usuarioEmpresaReporteTO);
			map.put("datos", datos);
			map.put("sisInfoTO", UtilsAplicacion.getSisInfoTO());
			final String rutaArchivo = "reportResumenSiembra.jrprint";
			return UtilsRESTFul.postForFile(conexionWS + "/generarReporteResumenSiembra", map, rutaArchivo);

		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public List<PrdComboCorridaTO> getComboPrdCorridaTO(final String empresa, final String sector,
			final String piscina) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("empresa", empresa);
			map.put("sector", sector);
			map.put("piscina", piscina);
			map.put("sisInfoTO", UtilsAplicacion.getSisInfoTO());
			return Arrays.asList(restTemplate.postForObject(conexionWS + "/getComboPrdCorridaTO",
					UtilsJSON.objetoToJson(map), PrdComboCorridaTO[].class));
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public List<PrdComboPiscinaTO> getComboPrdPiscinaTO(final String empresa, final String sector) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("empresa", empresa);
			map.put("sector", sector);
			map.put("sisInfoTO", UtilsAplicacion.getSisInfoTO());
			return Arrays.asList(restTemplate.postForObject(conexionWS + "/getComboPrdPiscinaTO",
					UtilsJSON.objetoToJson(map), PrdComboPiscinaTO[].class));
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public RetornoTO getConsultaGrameajePromedioPorPiscina(final String empresa, final String sector) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("empresa", empresa);
			map.put("sector", sector);
			map.put("sisInfoTO", UtilsAplicacion.getSisInfoTO());
			return restTemplate.postForObject(conexionWS + "/getConsultaGrameajePromedioPorPiscina",
					UtilsJSON.objetoToJson(map), RetornoTO.class);
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public RetornoTO getConsumoPorFechaDesglosado(final String empresa, final String codigoSector,
			final String fechaInicio, final String fechaFin, final String usuario) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("empresa", empresa);
			map.put("codigoSector", codigoSector);
			map.put("fechaInicio", fechaInicio);
			map.put("fechaFin", fechaFin);
			map.put("usuario", usuario);
			map.put("sisInfoTO", UtilsAplicacion.getSisInfoTO());
			return restTemplate.postForObject(conexionWS + "/getConsumoPorFechaDesglosado", UtilsJSON.objetoToJson(map),
					RetornoTO.class);
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public RetornoTO getConsumoPorPiscinaPeriodo(final String empresa, final String codigoSector,
			final String numeroPiscina, final String fechaInicio, final String fechaFin, final String periodo,
			final String usuario) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("empresa", empresa);
			map.put("codigoSector", codigoSector);
			map.put("numeroPiscina", numeroPiscina);
			map.put("fechaInicio", fechaInicio);
			map.put("fechaFin", fechaFin);
			map.put("periodo", periodo);
			map.put("usuario", usuario);
			map.put("sisInfoTO", UtilsAplicacion.getSisInfoTO());
			return restTemplate.postForObject(conexionWS + "/getConsumoPorPiscinaPeriodo", UtilsJSON.objetoToJson(map),
					RetornoTO.class);
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public List<PrdCorridaDetalle> getCorridaDetalleDestinoPorCorrida(final String empresa, final String sector,
			final String piscina, final String corrida) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("empresa", empresa);
			map.put("sector", sector);
			map.put("piscina", piscina);
			map.put("corrida", corrida);
			map.put("sisInfoTO", UtilsAplicacion.getSisInfoTO());
			return new ArrayList<>(
					Arrays.asList(restTemplate.postForObject(conexionWS + "/getCorridaDetalleDestinoPorCorrida",
							UtilsJSON.objetoToJson(map), PrdCorridaDetalle[].class)));
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public List<PrdCorridaDetalle> getCorridaDetalleOrigenPorCorrida(final String empresa, final String sector,
			final String piscina, final String corrida) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("empresa", empresa);
			map.put("sector", sector);
			map.put("piscina", piscina);
			map.put("corrida", corrida);
			map.put("sisInfoTO", UtilsAplicacion.getSisInfoTO());
			return new ArrayList<>(
					Arrays.asList(restTemplate.postForObject(conexionWS + "/getCorridaDetalleOrigenPorCorrida",
							UtilsJSON.objetoToJson(map), PrdCorridaDetalle[].class)));
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public List<PrdCorrida> getCorridaPorNumero(final String empresa, final String sector, final String piscina,
			final String numero) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("empresa", empresa);
			map.put("sector", sector);
			map.put("piscina", piscina);
			map.put("numero", numero);
			map.put("sisInfoTO", UtilsAplicacion.getSisInfoTO());
			return new ArrayList<>(Arrays.asList(restTemplate.postForObject(conexionWS + "/getCorridaPorNumero",
					UtilsJSON.objetoToJson(map), PrdCorrida[].class)));
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public List<MultiplePiscinaCorrida> getCostoDetallePersonalizado(final String empresa, final String sector,
			final String fecha) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("empresa", empresa);
			map.put("sector", sector);
			map.put("fecha", fecha);
			map.put("sisInfoTO", UtilsAplicacion.getSisInfoTO());
			return Arrays.asList(restTemplate.postForObject(conexionWS + "/getCostoDetallePersonalizado",
					UtilsJSON.objetoToJson(map), MultiplePiscinaCorrida[].class));
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public RetornoTO getCostoPorFechaProrrateado(final String empresa, final String codigoSector,
			final String fechaInicio, final String fechaFin, final String usuario, final String agrupadoPor) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("empresa", empresa);
			map.put("codigoSector", codigoSector);
			map.put("fechaInicio", fechaInicio);
			map.put("fechaFin", fechaFin);
			map.put("usuario", usuario);
			map.put("agrupadoPor", agrupadoPor);
			map.put("sisInfoTO", UtilsAplicacion.getSisInfoTO());
			return restTemplate.postForObject(conexionWS + "/getCostoPorFechaProrrateado", UtilsJSON.objetoToJson(map),
					RetornoTO.class);
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public RetornoTO getCostoPorPiscinaSemanal(final String empresa, final String codigoSector,
			final String numeroPiscina, final String fechaInicio, final String fechaFin, final String usuario,
			final String agrupadoPor) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("empresa", empresa);
			map.put("codigoSector", codigoSector);
			map.put("numeroPiscina", numeroPiscina);
			map.put("fechaInicio", fechaInicio);
			map.put("fechaFin", fechaFin);
			map.put("usuario", usuario);
			map.put("agrupadoPor", agrupadoPor);
			map.put("sisInfoTO", UtilsAplicacion.getSisInfoTO());
			return restTemplate.postForObject(conexionWS + "/getCostoPorPiscinaSemanal", UtilsJSON.objetoToJson(map),
					RetornoTO.class);
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public PrdEstadoCCCVT getEstadoCCCVT(final String empresa, final String motivoTipo, final String numero) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("empresa", empresa);
			map.put("motivoTipo", motivoTipo);
			map.put("numero", numero);
			map.put("sisInfoTO", UtilsAplicacion.getSisInfoTO());
			return restTemplate.postForObject(conexionWS + "/getEstadoCCCVT", UtilsJSON.objetoToJson(map),
					PrdEstadoCCCVT.class);
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public List<PrdListaFunAnalisisPesosTO> getFunAnalisisPesos(final String empresa, final String sector,
			final String fecha) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("empresa", empresa);
			map.put("sector", sector);
			map.put("fecha", fecha);
			map.put("sisInfoTO", UtilsAplicacion.getSisInfoTO());
			return Arrays.asList(restTemplate.postForObject(conexionWS + "/getFunAnalisisPesos",
					UtilsJSON.objetoToJson(map), PrdListaFunAnalisisPesosTO[].class));
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public List<String> getFunFechaSemanas(final String empresa, final String sector, final String fecha) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("empresa", empresa);
			map.put("sector", sector);
			map.put("fecha", fecha);
			map.put("sisInfoTO", UtilsAplicacion.getSisInfoTO());
			return Arrays.asList(restTemplate.postForObject(conexionWS + "/getFunFechaSemanas",
					UtilsJSON.objetoToJson(map), String[].class));
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public boolean getIsFechaGrameajeSuperior(final String empresa, final String sector, final String numPiscina,
			final String fechaDesde, final String fechaHasta, final String fecha) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("empresa", empresa);
			map.put("sector", sector);
			map.put("numPiscina", numPiscina);
			map.put("fechaDesde", fechaDesde);
			map.put("fechaHasta", fechaHasta);
			map.put("fecha", fecha);
			map.put("sisInfoTO", UtilsAplicacion.getSisInfoTO());
			return restTemplate.postForObject(conexionWS + "/getIsFechaGrameajeSuperior", UtilsJSON.objetoToJson(map),
					boolean.class);
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return false;
	}

	public List<PrdContabilizarCorridaTO> getListaContabilizarCorridaTO(final String empresa, final String desde,
			final String hasta) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("empresa", empresa);
			map.put("desde", desde);
			map.put("hasta", hasta);
			map.put("sisInfoTO", UtilsAplicacion.getSisInfoTO());
			return new ArrayList<>(
					Arrays.asList(restTemplate.postForObject(conexionWS + "/getListaContabilizarCorridaTO",
							UtilsJSON.objetoToJson(map), PrdContabilizarCorridaTO[].class)));
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public List<PrdCorrida> getListaCorridasPorEmpresaSectorPiscina(final String empresa, final String sector,
			final String piscina) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("empresa", empresa);
			map.put("sector", sector);
			map.put("piscina", piscina);
			map.put("sisInfoTO", UtilsAplicacion.getSisInfoTO());
			return new ArrayList<>(
					Arrays.asList(restTemplate.postForObject(conexionWS + "/getListaCorridasPorEmpresaSectorPiscina",
							UtilsJSON.objetoToJson(map), PrdCorrida[].class)));
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public List<PrdListaCorridaTO> getListaCorridaTO(final String empresa, final String sector, final String piscina) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("empresa", empresa);
			map.put("sector", sector);
			map.put("piscina", piscina);
			map.put("sisInfoTO", UtilsAplicacion.getSisInfoTO());
			return Arrays.asList(restTemplate.postForObject(conexionWS + "/getListaCorridaTO",
					UtilsJSON.objetoToJson(map), PrdListaCorridaTO[].class));
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public List<PrdListaCorridaTO> getListaCorridaTO(final String empresa, final String sector, final String piscina,
			final String corrida) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("empresa", empresa);
			map.put("sector", sector);
			map.put("piscina", piscina);
			map.put("corrida", corrida);
			map.put("sisInfoTO", UtilsAplicacion.getSisInfoTO());
			return Arrays.asList(restTemplate.postForObject(conexionWS + "/getListaCorridaTO",
					UtilsJSON.objetoToJson(map), PrdListaCorridaTO[].class));
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public List<PrdGrameaje> getListaGrameajes(final String empresa, final String sector, final String fecha) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("empresa", empresa);
			map.put("sector", sector);
			map.put("fecha", fecha);
			map.put("sisInfoTO", UtilsAplicacion.getSisInfoTO());
			return new ArrayList<>(Arrays.asList(restTemplate.postForObject(conexionWS + "/getListaGrameajes",
					UtilsJSON.objetoToJson(map), PrdGrameaje[].class)));
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public List<PrdLiquidacionMotivoComboTO> getListaLiquidacionMotivoComboTO(final String empresa,
			final boolean filtrarInactivos) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("empresa", empresa);
			map.put("filtrarInactivos", filtrarInactivos);
			map.put("sisInfoTO", UtilsAplicacion.getSisInfoTO());
			return Arrays.asList(restTemplate.postForObject(conexionWS + "/getListaLiquidacionMotivoComboTO",
					UtilsJSON.objetoToJson(map), PrdLiquidacionMotivoComboTO[].class));
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public List<PrdListaPiscinaComboTO> getListaPiscinaActivoTO(final String empresa, final boolean activo) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("empresa", empresa);
			map.put("activo", activo);
			map.put("sisInfoTO", UtilsAplicacion.getSisInfoTO());
			return Arrays.asList(restTemplate.postForObject(conexionWS + "/getListaPiscinaActivoTO",
					UtilsJSON.objetoToJson(map), PrdListaPiscinaComboTO[].class));
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public List<PrdPiscina> getListaPiscinaPor_Empresa_Sector_Activa(final String empresa, final String sector,
			final boolean activa) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("empresa", empresa);
			map.put("sector", sector);
			map.put("activa", activa);
			map.put("sisInfoTO", UtilsAplicacion.getSisInfoTO());
			return new ArrayList<>(
					Arrays.asList(restTemplate.postForObject(conexionWS + "/getListaPiscinaPor_Empresa_Sector_Activa",
							UtilsJSON.objetoToJson(map), PrdPiscina[].class)));
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public List<PiscinaGrameajeTO> getListaPiscinasGrameaje(final String empresa, final String sector,
			final String fecha) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("empresa", empresa);
			map.put("sector", sector);
			map.put("fecha", fecha);
			map.put("sisInfoTO", UtilsAplicacion.getSisInfoTO());
			return Arrays.asList(restTemplate.postForObject(conexionWS + "/getListaPiscinasGrameaje",
					UtilsJSON.objetoToJson(map), PiscinaGrameajeTO[].class));
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public List<PrdPiscina> getListaPiscinasPorEmpresaSector(final String empresa, final String sector) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("empresa", empresa);
			map.put("sector", sector);
			map.put("sisInfoTO", UtilsAplicacion.getSisInfoTO());
			return new ArrayList<>(
					Arrays.asList(restTemplate.postForObject(conexionWS + "/getListaPiscinasPorEmpresaSector",
							UtilsJSON.objetoToJson(map), PrdPiscina[].class)));
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public List<PrdListaPiscinaTO> getListaPiscinaTO(final String empresa, final String sector) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("empresa", empresa);
			map.put("sector", sector);
			map.put("sisInfoTO", UtilsAplicacion.getSisInfoTO());
			return Arrays.asList(restTemplate.postForObject(conexionWS + "/getListaPiscinaTO",
					UtilsJSON.objetoToJson(map), PrdListaPiscinaTO[].class));
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public List<PrdListaPiscinaTO> getListaPiscinaTOBusqueda(final String empresa, final String sector,
			final String fecha) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("empresa", empresa);
			map.put("sector", sector);
			map.put("fecha", fecha);
			map.put("sisInfoTO", UtilsAplicacion.getSisInfoTO());
			return Arrays.asList(restTemplate.postForObject(conexionWS + "/getListaPiscinaTOBusqueda",
					UtilsJSON.objetoToJson(map), PrdListaPiscinaTO[].class));
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public List<ListaLiquidacionTO> getListaPrdConsultaLiquidacion(final String empresa, final String sector,
			final String piscina, final String busqueda) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("empresa", empresa);
			map.put("sector", sector);
			map.put("piscina", piscina);
			map.put("busqueda", busqueda);
			map.put("sisInfoTO", UtilsAplicacion.getSisInfoTO());
			return Arrays.asList(restTemplate.postForObject(conexionWS + "/getListaPrdConsultaLiquidacion",
					UtilsJSON.objetoToJson(map), ListaLiquidacionTO[].class));
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public List<ListaPreLiquidacionTO> getListaPrdConsultaPreLiquidacion(final String empresa, final String sector,
			final String piscina, final String busqueda) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("empresa", empresa);
			map.put("sector", sector);
			map.put("piscina", piscina);
			map.put("busqueda", busqueda);
			map.put("sisInfoTO", UtilsAplicacion.getSisInfoTO());
			return Arrays.asList(restTemplate.postForObject(conexionWS + "/getListaPrdConsultaPreLiquidacion",
					UtilsJSON.objetoToJson(map), ListaPreLiquidacionTO[].class));
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public List<PrdListaDetalleLiquidacionTO> getListaPrdLiquidacionDetalleTO(final String empresa, final String motivo,
			final String numero) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("empresa", empresa);
			map.put("motivo", motivo);
			map.put("numero", numero);
			map.put("sisInfoTO", UtilsAplicacion.getSisInfoTO());
			return Arrays.asList(restTemplate.postForObject(conexionWS + "/getListaPrdLiquidacionDetalleTO",
					UtilsJSON.objetoToJson(map), PrdListaDetalleLiquidacionTO[].class));
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public List<PrdLiquidacionMotivo> getListaPrdLiquidacionMotivo(final String empresa) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("empresa", empresa);
			map.put("sisInfoTO", UtilsAplicacion.getSisInfoTO());
			return new ArrayList<>(
					Arrays.asList(restTemplate.postForObject(conexionWS + "/getListaPrdLiquidacionMotivo",
							UtilsJSON.objetoToJson(map), PrdLiquidacionMotivo[].class)));
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public List<PrdLiquidacionMotivoTablaTO> getListaPrdLiquidacionMotivoTablaTO(final String empresa) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("empresa", empresa);
			map.put("sisInfoTO", UtilsAplicacion.getSisInfoTO());
			return Arrays.asList(restTemplate.postForObject(conexionWS + "/getListaPrdLiquidacionMotivoTablaTO",
					UtilsJSON.objetoToJson(map), PrdLiquidacionMotivoTablaTO[].class));
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public List<PrdProducto> getListaPrdLiquidacionProducto(final String empresa) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("empresa", empresa);
			map.put("sisInfoTO", UtilsAplicacion.getSisInfoTO());
			return new ArrayList<>(Arrays.asList(restTemplate.postForObject(
					conexionWS + "/getListaPrdLiquidacionProducto", UtilsJSON.objetoToJson(map), PrdProducto[].class)));
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public List<PrdLiquidacionProductoTablaTO> getListaPrdLiquidacionProductoTablaTO(final String empresa) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("empresa", empresa);
			map.put("sisInfoTO", UtilsAplicacion.getSisInfoTO());
			return Arrays.asList(restTemplate.postForObject(conexionWS + "/getListaPrdLiquidacionProductoTablaTO",
					UtilsJSON.objetoToJson(map), PrdLiquidacionProductoTablaTO[].class));
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public List<PrdLiquidacionProductoTablaTO> getListaPrdLiquidacionProductoTablaTO(final String empresa,
			final String codigo) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("empresa", empresa);
			map.put("codigo", codigo);
			map.put("sisInfoTO", UtilsAplicacion.getSisInfoTO());
			return Arrays.asList(restTemplate.postForObject(conexionWS + "/getListaPrdLiquidacionProductoTablaTO",
					UtilsJSON.objetoToJson(map), PrdLiquidacionProductoTablaTO[].class));
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public List<PrdTalla> getListaPrdLiquidacionTalla(final String empresa, final boolean presupuestoPesca) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("empresa", empresa);
			map.put("presupuestoPesca", presupuestoPesca);
			map.put("sisInfoTO", UtilsAplicacion.getSisInfoTO());
			return new ArrayList<>(Arrays.asList(restTemplate.postForObject(conexionWS + "/getListaPrdLiquidacionTalla",
					UtilsJSON.objetoToJson(map), PrdTalla[].class)));
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public List<PrdLiquidacionTallaTablaTO> getListaPrdLiquidacionTallaTablaTO(final String empresa) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("empresa", empresa);
			map.put("sisInfoTO", UtilsAplicacion.getSisInfoTO());
			return Arrays.asList(restTemplate.postForObject(conexionWS + "/getListaPrdLiquidacionTallaTablaTO",
					UtilsJSON.objetoToJson(map), PrdLiquidacionTallaTablaTO[].class));
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public List<PrdLiquidacionTallaTablaTO> getListaPrdLiquidacionTallaTablaTO(final String empresa,
			final String codigo) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("empresa", empresa);
			map.put("codigo", codigo);
			map.put("sisInfoTO", UtilsAplicacion.getSisInfoTO());
			return Arrays.asList(restTemplate.postForObject(conexionWS + "/getListaPrdLiquidacionTallaTablaTO",
					UtilsJSON.objetoToJson(map), PrdLiquidacionTallaTablaTO[].class));
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public List<PrdPreLiquidacionMotivo> getListaPrdPreLiquidacionMotivo(final String empresa) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("empresa", empresa);
			map.put("sisInfoTO", UtilsAplicacion.getSisInfoTO());
			return new ArrayList<>(
					Arrays.asList(restTemplate.postForObject(conexionWS + "/getListaPrdPreLiquidacionMotivo",
							UtilsJSON.objetoToJson(map), PrdPreLiquidacionMotivo[].class)));
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public List<PrdPresupuestoPesca> getListaPrdPresupuestoPesca(final String empresa) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("empresa", empresa);
			map.put("sisInfoTO", UtilsAplicacion.getSisInfoTO());
			return Arrays.asList(restTemplate.postForObject(conexionWS + "/getListaPrdPresupuestoPesca",
					UtilsJSON.objetoToJson(map), PrdPresupuestoPesca[].class));
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public List<PrdPresupuestoPescaMotivo> getListaPrdPresupuestoPescaMotivo(final String empresa) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("empresa", empresa);
			map.put("sisInfoTO", UtilsAplicacion.getSisInfoTO());
			return new ArrayList<>(
					Arrays.asList(restTemplate.postForObject(conexionWS + "/getListaPrdPresupuestoPescaMotivo",
							UtilsJSON.objetoToJson(map), PrdPresupuestoPescaMotivo[].class)));
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public List<PrdProyeccionTO> getListaPrdProyeccion(final String empresa, final String sector, final Date fecha) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("empresa", empresa);
			map.put("sector", sector);
			map.put("fecha", fecha);
			map.put("sisInfoTO", UtilsAplicacion.getSisInfoTO());
			return new ArrayList<>(Arrays.asList(restTemplate.postForObject(conexionWS + "/getListaPrdProyeccion",
					UtilsJSON.objetoToJson(map), PrdProyeccionTO[].class)));
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public List<PrdResumenCorridaTO> getListaResumenCorridaTO(final String empresa, final String sector,
			final String desde, final String hasta, final String tipoResumen) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("empresa", empresa);
			map.put("sector", sector);
			map.put("desde", desde);
			map.put("hasta", hasta);
			map.put("tipoResumen", tipoResumen);
			map.put("sisInfoTO", UtilsAplicacion.getSisInfoTO());
			return Arrays.asList(restTemplate.postForObject(conexionWS + "/getListaResumenCorridaTO",
					UtilsJSON.objetoToJson(map), PrdResumenCorridaTO[].class));
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public List<PrdSector> getListaSector(final String empresa, final Boolean activo) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("empresa", empresa);
			map.put("activo", activo);
			map.put("sisInfoTO", UtilsAplicacion.getSisInfoTO());
			return new ArrayList<>(Arrays.asList(restTemplate.postForObject(conexionWS + "/getListaSectorPorEmpresa",
					UtilsJSON.objetoToJson(map), PrdSector[].class)));
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public List<PrdListaSectorConHectareajeTO> getListaSectorConHectareaje(final String empresa, final String fecha)
			throws Exception {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("empresa", empresa);
			map.put("fecha", fecha);
			map.put("sisInfoTO", UtilsAplicacion.getSisInfoTO());
			return Arrays.asList(restTemplate.postForObject(conexionWS + "/getListaSectorConHectareaje",
					UtilsJSON.objetoToJson(map), PrdListaSectorConHectareajeTO[].class));
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	/////////////////////////// Metodos WEB ///////////////////////////

	public List<PrdSector> getListaSectorPorEmpresa(final String empresa, final Boolean activo) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("empresa", empresa);
			map.put("activo", activo);
			map.put("sisInfoTO", UtilsAplicacion.getSisInfoTO());
			return new ArrayList<>(Arrays.asList(restTemplate.postForObject(conexionWS + "/getListaSectorPorEmpresa",
					UtilsJSON.objetoToJson(map), PrdSector[].class)));
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public List<PrdListaSectorTO> getListaSectorTO(final String empresa, final Boolean activo) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("empresa", empresa);
			map.put("activo", activo);
			map.put("sisInfoTO", UtilsAplicacion.getSisInfoTO());
			return Arrays.asList(restTemplate.postForObject(conexionWS + "/getListaSectorTO",
					UtilsJSON.objetoToJson(map), PrdListaSectorTO[].class));
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public List<PrdListaSobrevivenciaTO> getListaSobrevivenciaTO(final String empresa, final String sector) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("empresa", empresa);
			map.put("sector", sector);
			map.put("sisInfoTO", UtilsAplicacion.getSisInfoTO());
			return Arrays.asList(restTemplate.postForObject(conexionWS + "/getListaSobrevivenciaTO",
					UtilsJSON.objetoToJson(map), PrdListaSobrevivenciaTO[].class));
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public List<PrdConsumosTO> getPrdConsumosFechaTO(final String empresa, final String sector, final String fechaDesde,
			final String fechaHasta) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("empresa", empresa);
			map.put("sector", sector);
			map.put("fechaDesde", fechaDesde);
			map.put("fechaHasta", fechaHasta);
			map.put("sisInfoTO", UtilsAplicacion.getSisInfoTO());
			return Arrays.asList(restTemplate.postForObject(conexionWS + "/getPrdConsumosFechaTO",
					UtilsJSON.objetoToJson(map), PrdConsumosTO[].class));
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public List<PrdConsumosTO> getPrdConsumosPiscinaTO(final String empresa, final String sector, final String piscina,
			final String fechaDesde, final String fechaHasta) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("empresa", empresa);
			map.put("sector", sector);
			map.put("piscina", piscina);
			map.put("fechaDesde", fechaDesde);
			map.put("fechaHasta", fechaHasta);
			map.put("sisInfoTO", UtilsAplicacion.getSisInfoTO());
			return Arrays.asList(restTemplate.postForObject(conexionWS + "/getPrdConsumosPiscinaTO",
					UtilsJSON.objetoToJson(map), PrdConsumosTO[].class));
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public PrdCorridaTO getPrdCorridaTO(final String empresa, final String sector, final String piscina,
			final String fecha) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("empresa", empresa);
			map.put("sector", sector);
			map.put("piscina", piscina);
			map.put("fecha", fecha);
			map.put("sisInfoTO", UtilsAplicacion.getSisInfoTO());
			return restTemplate.postForObject(conexionWS + "/getPrdCorridaTO", UtilsJSON.objetoToJson(map),
					PrdCorridaTO.class);
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public List<PrdFunCostosPorFechaSimpleTO> getPrdFunCostosPorFechaSimpleTO(final String codigoSector,
			final String fechaInicio, final String fechaFin, final String infEmpresea) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("codigoSector", codigoSector);
			map.put("fechaInicio", fechaInicio);
			map.put("fechaFin", fechaFin);
			map.put("infEmpresea", infEmpresea);
			map.put("sisInfoTO", UtilsAplicacion.getSisInfoTO());
			return Arrays.asList(restTemplate.postForObject(conexionWS + "/getPrdFunCostosPorFechaSimpleTO",
					UtilsJSON.objetoToJson(map), PrdFunCostosPorFechaSimpleTO[].class));
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public PrdGrameaje getPrdGrameaje(final String empresa, final String sector, final String piscina) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("empresa", empresa);
			map.put("sector", sector);
			map.put("piscina", piscina);
			map.put("sisInfoTO", UtilsAplicacion.getSisInfoTO());
			return restTemplate.postForObject(conexionWS + "/getPrdGrameaje", UtilsJSON.objetoToJson(map),
					PrdGrameaje.class);
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public PrdGrameajeTO getPrdGrameajeTO(final String empresa, final String sector, final String piscina,
			final String fecha) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("empresa", empresa);
			map.put("sector", sector);
			map.put("piscina", piscina);
			map.put("fecha", fecha);
			map.put("sisInfoTO", UtilsAplicacion.getSisInfoTO());
			return restTemplate.postForObject(conexionWS + "/getPrdGrameajeTOAux", UtilsJSON.objetoToJson(map),
					PrdGrameajeTO.class);
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public PrdGrameajeTO getPrdGrameajeTO(final String empresa, final String sector, final String piscina,
			final String desde, final String hasta) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("empresa", empresa);
			map.put("sector", sector);
			map.put("piscina", piscina);
			map.put("desde", desde);
			map.put("hasta", hasta);
			map.put("sisInfoTO", UtilsAplicacion.getSisInfoTO());
			return restTemplate.postForObject(conexionWS + "/getPrdGrameajeTO", UtilsJSON.objetoToJson(map),
					PrdGrameajeTO.class);
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public PrdLiquidacion getPrdLiquidacion(final PrdLiquidacionPK liquidacionPK) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("liquidacionPK", liquidacionPK);
			map.put("sisInfoTO", UtilsAplicacion.getSisInfoTO());
			return restTemplate.postForObject(conexionWS + "/getPrdLiquidacion", UtilsJSON.objetoToJson(map),
					PrdLiquidacion.class);
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public PrdLiquidacionCabeceraTO getPrdLiquidacionCabeceraTO(final String empresa, final String motivo,
			final String numeroLiquidacion) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("empresa", empresa);
			map.put("motivo", motivo);
			map.put("numeroLiquidacion", numeroLiquidacion);
			map.put("sisInfoTO", UtilsAplicacion.getSisInfoTO());
			return restTemplate.postForObject(conexionWS + "/getPrdLiquidacionCabeceraTO", UtilsJSON.objetoToJson(map),
					PrdLiquidacionCabeceraTO.class);
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public List<PrdListaCostosDetalleCorridaTO> getPrdListaCostosDetalleCorridaTO(final String empresa,
			final String sector, final String piscina, final String desde, final String hasta,
			final String agrupadoPor) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("empresa", empresa);
			map.put("sector", sector);
			map.put("piscina", piscina);
			map.put("desde", desde);
			map.put("hasta", hasta);
			map.put("agrupadoPor", agrupadoPor);
			map.put("sisInfoTO", UtilsAplicacion.getSisInfoTO());
			return Arrays.asList(restTemplate.postForObject(conexionWS + "/getPrdListaCostosDetalleCorridaTO",
					UtilsJSON.objetoToJson(map), PrdListaCostosDetalleCorridaTO[].class));
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public List<PrdCostoDetalleFechaTO> getPrdListadoCostoDetalleFechaTO(final String empresa, final String secCodigo,
			final String desde, final String hasta, final String agrupadoPor) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("empresa", empresa);
			map.put("secCodigo", secCodigo);
			map.put("desde", desde);
			map.put("hasta", hasta);
			map.put("agrupadoPor", agrupadoPor);
			map.put("sisInfoTO", UtilsAplicacion.getSisInfoTO());
			return Arrays.asList(restTemplate.postForObject(conexionWS + "/getPrdListadoCostoDetalleFechaTO",
					UtilsJSON.objetoToJson(map), PrdCostoDetalleFechaTO[].class));
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public List<PrdListadoGrameajeTO> getPrdListadoGrameajeTO(final String empresa, final String sector,
			final String piscina, final String desde, final String hasta) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("empresa", empresa);
			map.put("sector", sector);
			map.put("piscina", piscina);
			map.put("desde", desde);
			map.put("hasta", hasta);
			map.put("sisInfoTO", UtilsAplicacion.getSisInfoTO());
			return Arrays.asList(restTemplate.postForObject(conexionWS + "/getPrdListadoGrameajeTO",
					UtilsJSON.objetoToJson(map), PrdListadoGrameajeTO[].class));
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public PrdPreLiquidacion getPrdPreLiquidacion(final PrdPreLiquidacionPK liquidacionPK) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("liquidacionPK", liquidacionPK);
			map.put("sisInfoTO", UtilsAplicacion.getSisInfoTO());
			return restTemplate.postForObject(conexionWS + "/getPrdPreLiquidacion", UtilsJSON.objetoToJson(map),
					PrdPreLiquidacion.class);
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public PrdPresupuestoPesca getPrdPresupuestoPesca(final PrdPresupuestoPescaPK presupuestoPescaPK) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("presupuestoPescaPK", presupuestoPescaPK);
			map.put("sisInfoTO", UtilsAplicacion.getSisInfoTO());
			return restTemplate.postForObject(conexionWS + "/getPrdPresupuestoPesca", UtilsJSON.objetoToJson(map),
					PrdPresupuestoPesca.class);
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public List<PrdResumenFinancieroTO> getPrdResumenFinancieroTO(final String empresa, final String secCodigo,
			final String desde, final String hasta) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("empresa", empresa);
			map.put("secCodigo", secCodigo);
			map.put("desde", desde);
			map.put("hasta", hasta);
			map.put("sisInfoTO", UtilsAplicacion.getSisInfoTO());
			return Arrays.asList(restTemplate.postForObject(conexionWS + "/getPrdResumenFinancieroTO",
					UtilsJSON.objetoToJson(map), PrdResumenFinancieroTO[].class));
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public RetornoTO getResumenPesca(final String empresa, final String codigoSector, final String fechaInicio,
			final String fechaFin, final String usuario) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("empresa", empresa);
			map.put("codigoSector", codigoSector);
			map.put("fechaInicio", fechaInicio);
			map.put("fechaFin", fechaFin);
			map.put("usuario", usuario);
			map.put("sisInfoTO", UtilsAplicacion.getSisInfoTO());
			return restTemplate.postForObject(conexionWS + "/getResumenPesca" + "", UtilsJSON.objetoToJson(map),
					RetornoTO.class);
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public RetornoTO getResumenSiembra(final String empresa, final String codigoSector, final String fechaInicio,
			final String fechaFin, final String usuario) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("empresa", empresa);
			map.put("codigoSector", codigoSector);
			map.put("fechaInicio", fechaInicio);
			map.put("fechaFin", fechaFin);
			map.put("usuario", usuario);
			map.put("sisInfoTO", UtilsAplicacion.getSisInfoTO());
			return restTemplate.postForObject(conexionWS + "/getResumenSiembra" + "", UtilsJSON.objetoToJson(map),
					RetornoTO.class);
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public boolean insertarGrameajeListado(final List<PrdGrameaje> listaGrameajes) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("listaGrameajes", listaGrameajes);
			map.put("sisInfoTO", UtilsAplicacion.getSisInfoTO());
			return restTemplate.postForObject(conexionWS + "/insertarGrameajeListado", UtilsJSON.objetoToJson(map),
					boolean.class);
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return false;
	}

	public MensajeTO insertarModificarPrdLiquidacion(final PrdLiquidacion prdLiquidacion,
			final List<PrdLiquidacionDetalle> listaPrdLiquidacionDetalle) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("prdLiquidacion", prdLiquidacion);
			map.put("listaPrdLiquidacionDetalle", listaPrdLiquidacionDetalle);
			map.put("sisInfoTO", UtilsAplicacion.getSisInfoTO());
			return restTemplate.postForObject(conexionWS + "/insertarModificarPrdLiquidacion",
					UtilsJSON.objetoToJson(map), MensajeTO.class);
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public MensajeTO insertarModificarPrdPreLiquidacion(final PrdPreLiquidacion liquidacion,
			final List<PrdPreLiquidacionDetalle> listaLiquidacionDetalle) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("liquidacion", liquidacion);
			map.put("listaLiquidacionDetalle", listaLiquidacionDetalle);
			map.put("sisInfoTO", UtilsAplicacion.getSisInfoTO());
			return restTemplate.postForObject(conexionWS + "/insertarModificarPrdPreLiquidacion",
					UtilsJSON.objetoToJson(map), MensajeTO.class);
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public MensajeTO insertarModificarPrdPresupuestoPesca(final PrdPresupuestoPesca prdPresupuestoPesca,
			final List<PrdPresupuestoPescaDetalle> listaPrdPresupuestoPescaDetalles) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("prdPresupuestoPesca", prdPresupuestoPesca);
			map.put("listaPrdPresupuestoPescaDetalles", listaPrdPresupuestoPescaDetalles);
			map.put("sisInfoTO", UtilsAplicacion.getSisInfoTO());
			return restTemplate.postForObject(conexionWS + "/insertarModificarPrdPresupuestoPesca",
					UtilsJSON.objetoToJson(map), MensajeTO.class);
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public String insertarPrdCorrida(final PrdCorrida prdCorrida, final List<PrdCorridaDetalle> corridaDetalleList) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("prdCorrida", prdCorrida);
			map.put("corridaDetalleList", corridaDetalleList);
			map.put("sisInfoTO", UtilsAplicacion.getSisInfoTO());
			return restTemplate.postForObject(conexionWS + "/insertarPrdCorrida", UtilsJSON.objetoToJson(map),
					String.class);
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public boolean insertarPrdGrameaje(final PrdGrameajeTO prdGrameajeTO) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("prdGrameajeTO", prdGrameajeTO);
			map.put("sisInfoTO", UtilsAplicacion.getSisInfoTO());
			return restTemplate.postForObject(conexionWS + "/insertarPrdGrameaje", UtilsJSON.objetoToJson(map),
					boolean.class);
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return false;
	}

	public String insertarPrdLiquidacionMotivo(final PrdLiquidacionMotivo prdLiquidacionMotivo) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("prdLiquidacionMotivo", prdLiquidacionMotivo);
			map.put("sisInfoTO", UtilsAplicacion.getSisInfoTO());
			return restTemplate.postForObject(conexionWS + "/insertarPrdLiquidacionMotivo", UtilsJSON.objetoToJson(map),
					String.class);
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public String insertarPrdLiquidacionMotivoTO(final PrdLiquidacionMotivoTO prdLiquidacionMotivoTO) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("prdLiquidacionMotivoTO", prdLiquidacionMotivoTO);
			map.put("sisInfoTO", UtilsAplicacion.getSisInfoTO());
			return restTemplate.postForObject(conexionWS + "/insertarPrdLiquidacionMotivoTO",
					UtilsJSON.objetoToJson(map), String.class);
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public String insertarPrdLiquidacionProducto(final PrdProducto prdLiquidacionProducto) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("prdLiquidacionProducto", prdLiquidacionProducto);
			map.put("sisInfoTO", UtilsAplicacion.getSisInfoTO());
			return restTemplate.postForObject(conexionWS + "/insertarPrdLiquidacionProducto",
					UtilsJSON.objetoToJson(map), String.class);
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public String insertarPrdLiquidacionProductoTO(final PrdLiquidacionProductoTO prdLiquidacionProductoTO) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("prdLiquidacionProductoTO", prdLiquidacionProductoTO);
			map.put("sisInfoTO", UtilsAplicacion.getSisInfoTO());
			return restTemplate.postForObject(conexionWS + "/insertarPrdLiquidacionProductoTO",
					UtilsJSON.objetoToJson(map), String.class);
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public String insertarPrdLiquidacionTalla(final PrdTalla prdLiquidacionTalla) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("prdLiquidacionTalla", prdLiquidacionTalla);
			map.put("sisInfoTO", UtilsAplicacion.getSisInfoTO());
			return restTemplate.postForObject(conexionWS + "/insertarPrdLiquidacionTalla", UtilsJSON.objetoToJson(map),
					String.class);
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public String insertarPrdLiquidacionTallaTO(final PrdLiquidacionTallaTO prdLiquidacionTallaTO) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("prdLiquidacionTallaTO", prdLiquidacionTallaTO);
			map.put("sisInfoTO", UtilsAplicacion.getSisInfoTO());
			return restTemplate.postForObject(conexionWS + "/insertarPrdLiquidacionTallaTO",
					UtilsJSON.objetoToJson(map), String.class);
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public String insertarPrdLiquidacionTO(final PrdLiquidacionTO prdLiquidacionTO,
			final List<PrdLiquidacionDetalleTO> listaPrdLiquidacionDetalleTO) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("prdLiquidacionTO", prdLiquidacionTO);
			map.put("listaPrdLiquidacionDetalleTO", listaPrdLiquidacionDetalleTO);
			map.put("sisInfoTO", UtilsAplicacion.getSisInfoTO());
			System.out.println("delegate  prdLiquidacionTO" + prdLiquidacionTO.getLiqFecha());
			return restTemplate.postForObject(conexionWS + "/insertarPrdLiquidacionTO", UtilsJSON.objetoToJson(map),
					String.class);
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public String insertarPrdPiscina(final PrdPiscina prdPiscina) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("prdPiscina", prdPiscina);
			map.put("sisInfoTO", UtilsAplicacion.getSisInfoTO());
			return restTemplate.postForObject(conexionWS + "/insertarPrdPiscina", UtilsJSON.objetoToJson(map),
					String.class);
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public boolean insertarPrdPiscina(final PrdPiscinaTO prdPiscinaTO) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("prdPiscinaTO", prdPiscinaTO);
			map.put("sisInfoTO", UtilsAplicacion.getSisInfoTO());
			return restTemplate.postForObject(conexionWS + "/insertarPrdPiscinaTO", UtilsJSON.objetoToJson(map),
					boolean.class);
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return false;
	}

	public String insertarPrdPreLiquidacionMotivo(final PrdPreLiquidacionMotivo prdPreLiquidacionMotivo) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("prdPreLiquidacionMotivo", prdPreLiquidacionMotivo);
			map.put("sisInfoTO", UtilsAplicacion.getSisInfoTO());
			return restTemplate.postForObject(conexionWS + "/insertarPrdPreLiquidacionMotivo",
					UtilsJSON.objetoToJson(map), String.class);
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public String insertarPrdPresupuestoPescaMotivo(final PrdPresupuestoPescaMotivo prdPresupuestoPescaMotivo) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("prdPresupuestoPescaMotivo", prdPresupuestoPescaMotivo);
			map.put("sisInfoTO", UtilsAplicacion.getSisInfoTO());
			return restTemplate.postForObject(conexionWS + "/insertarPrdPresupuestoPescaMotivo",
					UtilsJSON.objetoToJson(map), String.class);
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public String insertarPrdSector(final PrdSector prdSector) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("prdSector", prdSector);
			map.put("sisInfoTO", UtilsAplicacion.getSisInfoTO());
			return restTemplate.postForObject(conexionWS + "/insertarPrdSector", UtilsJSON.objetoToJson(map),
					String.class);
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public boolean insertarPrdSector(final PrdSectorTO prdSectorTO) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("prdSectorTO", prdSectorTO);
			map.put("sisInfoTO", UtilsAplicacion.getSisInfoTO());
			return restTemplate.postForObject(conexionWS + "/insertarPrdSectorTO", UtilsJSON.objetoToJson(map),
					boolean.class);
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return false;
	}

	public boolean insertarPrdSobrevivencia(final PrdSobrevivenciaTO prdSobrevivenciaTO) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("prdSobrevivenciaTO", prdSobrevivenciaTO);
			map.put("sisInfoTO", UtilsAplicacion.getSisInfoTO());
			return restTemplate.postForObject(conexionWS + "/insertarPrdSobrevivencia", UtilsJSON.objetoToJson(map),
					boolean.class);
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return false;
	}

	public RetornoTO modificarCorridaActivo(final String empresa, final String sector, final String hasta,
			final String proceso, final String agrupadoPor) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("empresa", empresa);
			map.put("sector", sector);
			map.put("hasta", hasta);
			map.put("proceso", proceso);
			map.put("agrupadoPor", agrupadoPor);
			map.put("sisInfoTO", UtilsAplicacion.getSisInfoTO());
			return restTemplate.postForObject(conexionWS + "/modificarCorridaActivo", UtilsJSON.objetoToJson(map),
					RetornoTO.class);
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public RetornoTO modificarCorridaActivoSeleccionMultiple(final String empresa,
			final List<MultiplePiscinaCorrida> multiplePiscinaCorrida, final String proceso, final String agrupadoPor) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("empresa", empresa);
			map.put("multiplePiscinaCorrida", multiplePiscinaCorrida);
			map.put("proceso", proceso);
			map.put("agrupadoPor", agrupadoPor);
			map.put("sisInfoTO", UtilsAplicacion.getSisInfoTO());
			return restTemplate.postForObject(conexionWS + "/modificarCorridaActivoSeleccionMultiple",
					UtilsJSON.objetoToJson(map), RetornoTO.class);
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public String modificarPrdCorrida(final PrdCorrida prdCorrida, final List<PrdCorridaDetalle> corridaDetalleList) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("prdCorrida", prdCorrida);
			map.put("corridaDetalleList", corridaDetalleList);
			map.put("sisInfoTO", UtilsAplicacion.getSisInfoTO());
			return restTemplate.postForObject(conexionWS + "/modificarPrdCorrida", UtilsJSON.objetoToJson(map),
					String.class);
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public String modificarPrdLiquidacionMotivo(final PrdLiquidacionMotivo prdLiquidacionMotivo) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("prdLiquidacionMotivo", prdLiquidacionMotivo);
			map.put("sisInfoTO", UtilsAplicacion.getSisInfoTO());
			return restTemplate.postForObject(conexionWS + "/modificarPrdLiquidacionMotivo",
					UtilsJSON.objetoToJson(map), String.class);
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public String modificarPrdLiquidacionMotivoTO(final PrdLiquidacionMotivoTO prdLiquidacionMotivoTO) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("prdLiquidacionMotivoTO", prdLiquidacionMotivoTO);
			map.put("sisInfoTO", UtilsAplicacion.getSisInfoTO());
			return restTemplate.postForObject(conexionWS + "/modificarPrdLiquidacionMotivoTO",
					UtilsJSON.objetoToJson(map), String.class);
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public String modificarPrdLiquidacionProducto(final PrdProducto prdLiquidacionProducto) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("prdLiquidacionProducto", prdLiquidacionProducto);
			map.put("sisInfoTO", UtilsAplicacion.getSisInfoTO());
			return restTemplate.postForObject(conexionWS + "/modificarPrdLiquidacionProducto",
					UtilsJSON.objetoToJson(map), String.class);
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public String modificarPrdLiquidacionProductoTO(final PrdLiquidacionProductoTO prdLiquidacionProductoTO) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("prdLiquidacionProductoTO", prdLiquidacionProductoTO);
			map.put("sisInfoTO", UtilsAplicacion.getSisInfoTO());
			return restTemplate.postForObject(conexionWS + "/modificarPrdLiquidacionProductoTO",
					UtilsJSON.objetoToJson(map), String.class);
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public String modificarPrdLiquidacionTalla(final PrdTalla prdLiquidacionTalla) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("prdLiquidacionTalla", prdLiquidacionTalla);
			map.put("sisInfoTO", UtilsAplicacion.getSisInfoTO());
			return restTemplate.postForObject(conexionWS + "/modificarPrdLiquidacionTalla", UtilsJSON.objetoToJson(map),
					String.class);
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public String modificarPrdLiquidacionTallaTO(final PrdLiquidacionTallaTO prdLiquidacionTallaTO) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("prdLiquidacionTallaTO", prdLiquidacionTallaTO);
			map.put("sisInfoTO", UtilsAplicacion.getSisInfoTO());
			return restTemplate.postForObject(conexionWS + "/modificarPrdLiquidacionTallaTO",
					UtilsJSON.objetoToJson(map), String.class);
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public String modificarPrdLiquidacionTO(final PrdLiquidacionTO prdLiquidacionTO,
			final List<PrdLiquidacionDetalleTO> listaPrdLiquidacionDetalleTO,
			final List<PrdLiquidacionDetalleTO> listaPrdLiquidacionEliminarDetalleTO) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("prdLiquidacionTO", prdLiquidacionTO);
			map.put("listaPrdLiquidacionDetalleTO", listaPrdLiquidacionDetalleTO);
			map.put("listaPrdLiquidacionEliminarDetalleTO", listaPrdLiquidacionEliminarDetalleTO);
			map.put("sisInfoTO", UtilsAplicacion.getSisInfoTO());
			return restTemplate.postForObject(conexionWS + "/modificarPrdLiquidacionTO", UtilsJSON.objetoToJson(map),
					String.class);
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public String modificarPrdPiscina(final PrdPiscina prdPiscina) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("prdPiscina", prdPiscina);
			map.put("sisInfoTO", UtilsAplicacion.getSisInfoTO());
			return restTemplate.postForObject(conexionWS + "/modificarPrdPiscina", UtilsJSON.objetoToJson(map),
					String.class);
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public boolean modificarPrdPiscina(final PrdPiscinaTO prdPiscinaTO) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("prdPiscinaTO", prdPiscinaTO);
			map.put("sisInfoTO", UtilsAplicacion.getSisInfoTO());
			return restTemplate.postForObject(conexionWS + "/modificarPrdPiscinaTO", UtilsJSON.objetoToJson(map),
					boolean.class);
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return false;
	}

	public String modificarPrdPreLiquidacionMotivo(final PrdPreLiquidacionMotivo prdPreLiquidacionMotivo) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("prdPreLiquidacionMotivo", prdPreLiquidacionMotivo);
			map.put("sisInfoTO", UtilsAplicacion.getSisInfoTO());
			return restTemplate.postForObject(conexionWS + "/modificarPrdPreLiquidacionMotivo",
					UtilsJSON.objetoToJson(map), String.class);
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public String modificarPrdPresupuestoPescaMotivo(final PrdPresupuestoPescaMotivo prdPresupuestoPescaMotivo) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("prdPresupuestoPescaMotivo", prdPresupuestoPescaMotivo);
			map.put("sisInfoTO", UtilsAplicacion.getSisInfoTO());
			return restTemplate.postForObject(conexionWS + "/modificarPrdPresupuestoPescaMotivo",
					UtilsJSON.objetoToJson(map), String.class);
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public String modificarPrdSector(final PrdSector prdSector) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("prdSector", prdSector);
			map.put("sisInfoTO", UtilsAplicacion.getSisInfoTO());
			return restTemplate.postForObject(conexionWS + "/modificarPrdSector", UtilsJSON.objetoToJson(map),
					String.class);
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public boolean modificarPrdSector(final PrdSectorTO prdSectorTO) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("prdSectorTO", prdSectorTO);
			map.put("sisInfoTO", UtilsAplicacion.getSisInfoTO());
			return restTemplate.postForObject(conexionWS + "/modificarPrdSectorTO", UtilsJSON.objetoToJson(map),
					boolean.class);
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return false;
	}

	public boolean modificarPrdSobrevivencia(final PrdSobrevivenciaTO prdSobrevivenciaTO) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("prdSobrevivenciaTO", prdSobrevivenciaTO);
			map.put("sisInfoTO", UtilsAplicacion.getSisInfoTO());
			return restTemplate.postForObject(conexionWS + "/modificarPrdSobrevivencia", UtilsJSON.objetoToJson(map),
					boolean.class);
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return false;
	}

	public PrdPiscina obtenerPorEmpresaSectorPiscina(final String empresa, final String sector,
			final String numPiscina) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("empresa", empresa);
			map.put("sector", sector);
			map.put("numPiscina", numPiscina);
			map.put("sisInfoTO", UtilsAplicacion.getSisInfoTO());
			return restTemplate.postForObject(conexionWS + "/obtenerPorEmpresaSectorPiscina",
					UtilsJSON.objetoToJson(map), PrdPiscina.class);
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

}
