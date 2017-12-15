package ec.com.orocodigo.OroSoftWeb.delegate;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
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
import ec.com.orocodigo.OroSoftUtils.anexos.TO.AnxCompraDetalleTO;
import ec.com.orocodigo.OroSoftUtils.anexos.TO.AnxCompraFormaPagoTO;
import ec.com.orocodigo.OroSoftUtils.anexos.TO.AnxCompraReembolsoTO;
import ec.com.orocodigo.OroSoftUtils.anexos.TO.AnxCompraTO;
import ec.com.orocodigo.OroSoftUtils.anexos.TO.AnxVentaTO;
import ec.com.orocodigo.OroSoftUtils.inventario.TO.*;
import ec.com.orocodigo.OroSoftUtils.inventario.entity.InvBodega;
import ec.com.orocodigo.OroSoftUtils.inventario.entity.InvCliente;
import ec.com.orocodigo.OroSoftUtils.inventario.entity.InvClienteDetalleVentaAutomatica;
import ec.com.orocodigo.OroSoftUtils.inventario.entity.InvComprasMotivoAnulacion;
import ec.com.orocodigo.OroSoftUtils.inventario.entity.InvConsumos;
import ec.com.orocodigo.OroSoftUtils.inventario.entity.InvConsumosDetalle;
import ec.com.orocodigo.OroSoftUtils.inventario.entity.InvConsumosMotivo;
import ec.com.orocodigo.OroSoftUtils.inventario.entity.InvConsumosMotivoAnulacion;
import ec.com.orocodigo.OroSoftUtils.inventario.entity.InvMenuComida;
import ec.com.orocodigo.OroSoftUtils.inventario.entity.InvProducto;
import ec.com.orocodigo.OroSoftUtils.inventario.entity.InvProveedor;
import ec.com.orocodigo.OroSoftUtils.inventario.entity.InvTransferenciasMotivoAnulacion;
import ec.com.orocodigo.OroSoftUtils.inventario.entity.InvVentasMotivoAnulacion;
import ec.com.orocodigo.OroSoftUtils.inventario.report.DatoFunListaProductosImpresionPlaca;
import ec.com.orocodigo.OroSoftUtils.inventario.report.ReporteCompraDetalle;
import ec.com.orocodigo.OroSoftUtils.inventario.report.ReporteConsumoDetalle;
import ec.com.orocodigo.OroSoftUtils.inventario.report.ReporteProformaDetalle;
import ec.com.orocodigo.OroSoftUtils.inventario.report.ReporteTransferenciaDetalle;
import ec.com.orocodigo.OroSoftUtils.inventario.report.ReporteVentaDetalle;
import ec.com.orocodigo.OroSoftWeb.utils.UtilAplication;
import ec.com.orocodigo.OroSoftWeb.utils.UtilsExcepciones;

public class InventarioDelegate {

	private static InventarioDelegate inventarioDelegate;

	public static InventarioDelegate getInstance() {
		if (inventarioDelegate == null)
			try {
				inventarioDelegate = new InventarioDelegate();
			} catch (final Exception e) {
				e.printStackTrace();
			}
		return inventarioDelegate;
	}

	private final String conexionWS;

	private final RestTemplate restTemplate = new RestTemplate();

	private InventarioDelegate() throws Exception {
		final Properties propSistema = Propiedades.readPropiedades();
		conexionWS = "http://" + propSistema.getProperty("servidor.ip") + ":"
				+ propSistema.getProperty("servidor.puerto") + "/" + propSistema.getProperty("servidor.ubicacion")
				+ "/OroSoftWS/inventarioController";
	}

	public String accionInvClienteCategoria(final InvClienteCategoriaTO invClienteCategoriaTO, final char accion) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("invClienteCategoriaTO", invClienteCategoriaTO);
			map.put("accion", accion);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return restTemplate.postForObject(conexionWS + "/accionInvClienteCategoria", UtilsJSON.objetoToJson(map),
					String.class);
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public String accionInvComprasPagosForma(final InvComprasFormaPagoTO invComprasFormaPagoTO, final char accion) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("invComprasFormaPagoTO", invComprasFormaPagoTO);
			map.put("accion", accion);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return restTemplate.postForObject(conexionWS + "/accionInvComprasPagosForma", UtilsJSON.objetoToJson(map),
					String.class);
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public String accionInvConsumosMotivo(final InvConsumosMotivoTO invConsumosMotivoTO, final char accion) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("invConsumosMotivoTO", invConsumosMotivoTO);
			map.put("accion", accion);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return restTemplate.postForObject(conexionWS + "/accionInvConsumosMotivo", UtilsJSON.objetoToJson(map),
					String.class);
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public String accionInvProductoCategoria(final InvProductoCategoriaTO invProductoCategoriaTO, final char accion) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("invProductoCategoriaTO", invProductoCategoriaTO);
			map.put("accion", accion);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return restTemplate.postForObject(conexionWS + "/accionInvProductoCategoria", UtilsJSON.objetoToJson(map),
					String.class);
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public String accionInvProductoMarcaTO(final InvProductoMarcaTO invProductoMarcaTO, final char accion) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("invProductoMarcaTO", invProductoMarcaTO);
			map.put("accion", accion);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return restTemplate.postForObject(conexionWS + "/accionInvProductoMarcaTO", UtilsJSON.objetoToJson(map),
					String.class);
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public String accionInvProductoMedida(final InvProductoMedidaTO invProductoMedidaTO, final char accion) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("invProductoMedidaTO", invProductoMedidaTO);
			map.put("accion", accion);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return restTemplate.postForObject(conexionWS + "/accionInvProductoMedida", UtilsJSON.objetoToJson(map),
					String.class);
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public String accionInvProductoPresentacionCajasTO(
			final InvProductoPresentacionCajasTO invProductoPresentacionCajasTO, final char accion) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("invProductoPresentacionCajasTO", invProductoPresentacionCajasTO);
			map.put("accion", accion);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return restTemplate.postForObject(conexionWS + "/accionInvProductoPresentacionCajasTO",
					UtilsJSON.objetoToJson(map), String.class);
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public String accionInvProductoPresentacionUnidadesTO(
			final InvProductoPresentacionUnidadesTO invProductoPresentacionUnidadesTO, final char accion) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("invProductoPresentacionUnidadesTO", invProductoPresentacionUnidadesTO);
			map.put("accion", accion);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return restTemplate.postForObject(conexionWS + "/accionInvProductoPresentacionUnidadesTO",
					UtilsJSON.objetoToJson(map), String.class);
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public String accionInvProductoTipo(final InvProductoTipoTO invProductoTipoTO, final char accion) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("invProductoTipoTO", invProductoTipoTO);
			map.put("accion", accion);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return restTemplate.postForObject(conexionWS + "/accionInvProductoTipo", UtilsJSON.objetoToJson(map),
					String.class);
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public String accionInvProveedorCategoria(final InvProveedorCategoriaTO invProveedorCategoriaTO,
			final char accion) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("invProveedorCategoriaTO", invProveedorCategoriaTO);
			map.put("accion", accion);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return restTemplate.postForObject(conexionWS + "/accionInvProveedorCategoria", UtilsJSON.objetoToJson(map),
					String.class);
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public String accionInvTransferenciasMotivo(final InvTransferenciaMotivoTO invTransferenciaMotivoTO,
			final char accion) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("invTransferenciaMotivoTO", invTransferenciaMotivoTO);
			map.put("accion", accion);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return restTemplate.postForObject(conexionWS + "/accionInvTransferenciaMotivo", UtilsJSON.objetoToJson(map),
					String.class);
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public String accionInvVendedorTO(final InvVendedorTO invVendedorTO, final char accion) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("invVendedorTO", invVendedorTO);
			map.put("accion", accion);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return restTemplate.postForObject(conexionWS + "/accionInvVendedorTO", UtilsJSON.objetoToJson(map),
					String.class);
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public String accionInvVentasPagosForma(final InvVentasFormaPagoTO invVentasFormaPagoTO, final char accion) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("invVentasFormaPagoTO", invVentasFormaPagoTO);
			map.put("accion", accion);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return restTemplate.postForObject(conexionWS + "/accionInvVentasPagosForma", UtilsJSON.objetoToJson(map),
					String.class);
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public String anularConsumo(final String empresa, final String periodo, final String motivo, final String numero) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("empresa", empresa);
			map.put("periodo", periodo);
			map.put("motivo", motivo);
			map.put("numero", numero);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return restTemplate.postForObject(conexionWS + "/anularConsumo", UtilsJSON.objetoToJson(map), String.class);
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public Boolean buscarConteoCliente(final String empCodigo, final String codigoCliente) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("empCodigo", empCodigo);
			map.put("codigoCliente", codigoCliente);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return restTemplate.postForObject(conexionWS + "/buscarConteoCliente", UtilsJSON.objetoToJson(map),
					Boolean.class);
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public Boolean buscarConteoProveedor(final String empCodigo, final String codigoProveedor) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("empCodigo", empCodigo);
			map.put("codigoProveedor", codigoProveedor);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return restTemplate.postForObject(conexionWS + "/buscarConteoProveedor", UtilsJSON.objetoToJson(map),
					Boolean.class);
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public InvProductoDAOTO buscarInvProductoDAOTO(final String empresa, final String codigoProducto) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("empresa", empresa);
			map.put("codigoProducto", codigoProducto);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return restTemplate.postForObject(conexionWS + "/buscarInvProductoDAOTO", UtilsJSON.objetoToJson(map),
					InvProductoDAOTO.class);
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public Boolean comprobarEliminarInvProductoTipo(final String empresa, final String codigo) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("empresa", empresa);
			map.put("codigo", codigo);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return restTemplate.postForObject(conexionWS + "/comprobarEliminarInvProductoTipo",
					UtilsJSON.objetoToJson(map), Boolean.class);
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public boolean comprobarInvAplicaRetencionIva(final String empresa, final String codigo) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("empresa", empresa);
			map.put("codigo", codigo);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return restTemplate.postForObject(conexionWS + "/comprobarInvAplicaRetencionIva",
					UtilsJSON.objetoToJson(map), boolean.class);
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return false;
	}
	// </editor-fold>

	public String desmayorizarConsumo(final String empresa, final String periodo, final String motivo,
			final String numero) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("empresa", empresa);
			map.put("periodo", periodo);
			map.put("motivo", motivo);
			map.put("numero", numero);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return restTemplate.postForObject(conexionWS + "/desmayorizarConsumo", UtilsJSON.objetoToJson(map),
					String.class);
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public String eliminarBodegaTO(final InvBodegaTO invBodegaTO) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("invBodegaTO", invBodegaTO);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return restTemplate.postForObject(conexionWS + "/eliminarBodegaTO", UtilsJSON.objetoToJson(map),
					String.class);
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public String eliminarInvClienteTO(final InvClienteTO invClienteTO) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("invClienteTO", invClienteTO);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return restTemplate.postForObject(conexionWS + "/eliminarInvClienteTO", UtilsJSON.objetoToJson(map),
					String.class);
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public String eliminarInvComprasMotivoTO(final InvComprasMotivoTO invCompraMotivoTO) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("invCompraMotivoTO", invCompraMotivoTO);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return restTemplate.postForObject(conexionWS + "/eliminarInvComprasMotivoTO", UtilsJSON.objetoToJson(map),
					String.class);
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public String eliminarInvProductoTO(final InvProductoTO invProductoTO) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("invProductoTO", invProductoTO);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return restTemplate.postForObject(conexionWS + "/eliminarInvProductoTO", UtilsJSON.objetoToJson(map),
					String.class);
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public String eliminarInvProformaMotivoTO(final InvProformaMotivoTO invProformaMotivoTO) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("invProformaMotivoTO", invProformaMotivoTO);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return restTemplate.postForObject(conexionWS + "/eliminarInvProformaMotivoTO", UtilsJSON.objetoToJson(map),
					String.class);
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public String eliminarInvProveedorTO(final InvProveedorTO invProveedorTO) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("invProveedorTO", invProveedorTO);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return restTemplate.postForObject(conexionWS + "/eliminarInvProveedorTO", UtilsJSON.objetoToJson(map),
					String.class);
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public String eliminarInvVentaMotivoTO(final InvVentaMotivoTO invVentaMotivoTO) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("invVentaMotivoTO", invVentaMotivoTO);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return restTemplate.postForObject(conexionWS + "/eliminarInvVentaMotivoTO", UtilsJSON.objetoToJson(map),
					String.class);
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public String generarReporteCompraDetalle(final List<ReporteCompraDetalle> reporteCompraDetalles) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("usuarioEmpresaReporteTO", UtilAplication.usuarioEmpresaReporteTO);
			map.put("reporteCompraDetalles", reporteCompraDetalles);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			final String rutaArchivo = "reporteCompraDetalles.jrprint";
			return UtilsRESTFul.postForFile(conexionWS + "/generarReporteCompraDetalle", map, rutaArchivo);
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public String generarReporteCompraDetalleImprimir(final List<ReporteCompraDetalle> reporteCompraDetalles,
			final String cmFormaImprimir) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("usuarioEmpresaReporteTO", UtilAplication.usuarioEmpresaReporteTO);
			map.put("reporteCompraDetalles", reporteCompraDetalles);
			map.put("cmFormaImprimir", cmFormaImprimir);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			final String rutaArchivo = "reporteCompraDetalleImprimir.jrprint";
			return UtilsRESTFul.postForFile(conexionWS + "/generarReporteCompraDetalleImprimir", map, rutaArchivo);
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public String generarReporteConsolidadoCompraProducto(final String fechaDesde, final String fechaHasta,
			final String bodega, final String proveedor,
			final List<InvFunComprasConsolidandoProductosTO> listInvFunComprasConsolidandoProductosTO) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("usuarioEmpresaReporteTO", UtilAplication.usuarioEmpresaReporteTO);
			map.put("fechaDesde", fechaDesde);
			map.put("fechaHasta", fechaHasta);
			map.put("bodega", bodega);
			map.put("proveedor", proveedor);
			map.put("listInvFunComprasConsolidandoProductosTO", listInvFunComprasConsolidandoProductosTO);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			final String rutaArchivo = "reportConsolidadoCompraProducto.jrprint";
			return UtilsRESTFul.postForFile(conexionWS + "/generarReporteConsolidadoCompraProducto", map, rutaArchivo);
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public String generarReporteConsolidadoConsumoProducto(final String fechaDesde, final String fechaHasta,
			final String bodega, final String proveedor,
			final List<InvFunConsumosConsolidandoProductosTO> listInvFunConsumosConsolidandoProductosTO) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("usuarioEmpresaReporteTO", UtilAplication.usuarioEmpresaReporteTO);
			map.put("fechaDesde", fechaDesde);
			map.put("fechaHasta", fechaHasta);
			map.put("bodega", bodega);
			map.put("proveedor", proveedor);
			map.put("listInvFunConsumosConsolidandoProductosTO", listInvFunConsumosConsolidandoProductosTO);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			final String rutaArchivo = "reportConsolidadoCompraProducto.jrprint";
			return UtilsRESTFul.postForFile(conexionWS + "/generarReporteConsolidadoConsumoProducto", map, rutaArchivo);
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public String generarReporteConsolidadoVentaCliente(final String fechaDesde, final String fechaHasta,
			final String sector,
			final List<InvFunVentasConsolidandoClientesTO> listInvFunVentasConsolidandoClientesTO) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("usuarioEmpresaReporteTO", UtilAplication.usuarioEmpresaReporteTO);
			map.put("fechaDesde", fechaDesde);
			map.put("fechaHasta", fechaHasta);
			map.put("sector", sector);
			map.put("listInvFunVentasConsolidandoClientesTO", listInvFunVentasConsolidandoClientesTO);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			final String rutaArchivo = "reportConsolidadoVentaCliente.jrprint";
			return UtilsRESTFul.postForFile(conexionWS + "/generarReporteConsolidadoVentaCliente", map, rutaArchivo);
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public String generarReporteConsolidadoVentaProducto(final String fechaDesde, final String fechaHasta,
			final String bodega, final String cliente,
			final List<InvFunVentasConsolidandoProductosTO> listInvFunVentasConsolidandoProductosTO) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("usuarioEmpresaReporteTO", UtilAplication.usuarioEmpresaReporteTO);
			map.put("fechaDesde", fechaDesde);
			map.put("fechaHasta", fechaHasta);
			map.put("bodega", bodega);
			map.put("cliente", cliente);
			map.put("listInvFunVentasConsolidandoProductosTO", listInvFunVentasConsolidandoProductosTO);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			final String rutaArchivo = "reportConsolidadoVentaProducto.jrprint";
			return UtilsRESTFul.postForFile(conexionWS + "/generarReporteConsolidadoVentaProducto", map, rutaArchivo);
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public String generarReporteConsumoDetalle(final boolean ordenado,
			final List<ReporteConsumoDetalle> reporteConsumoDetalles) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("usuarioEmpresaReporteTO", UtilAplication.usuarioEmpresaReporteTO);
			map.put("ordenado", ordenado);
			map.put("reporteConsumoDetalles", reporteConsumoDetalles);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			final String rutaArchivo = "reportConsumoDetalle.jrprint";
			return UtilsRESTFul.postForFile(conexionWS + "/generarReporteConsumoDetalle", map, rutaArchivo);
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public String generarReporteImpresionPlaca(
			final LinkedList<DatoFunListaProductosImpresionPlaca> listProductosImpresionPlaca) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("usuarioEmpresaReporteTO", UtilAplication.usuarioEmpresaReporteTO);
			map.put("listProductosImpresionPlaca", listProductosImpresionPlaca);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			final String rutaArchivo = "reportImpresionPlaca.jrprint";
			return UtilsRESTFul.postForFile(conexionWS + "/generarReporteImpresionPlaca", map, rutaArchivo);
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public String generarReporteInvFunVentasVsCosto(final String fechaDesde, final String fechaHasta,
			final String bodega, final String clienteId, final List<InvFunVentasVsCostoTO> invFunVentasVsCostoTO) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("usuarioEmpresaReporteTO", UtilAplication.usuarioEmpresaReporteTO);
			map.put("fechaDesde", fechaDesde);
			map.put("fechaHasta", fechaHasta);
			map.put("bodega", bodega);
			map.put("clienteId", clienteId);
			map.put("invFunVentasVsCostoTO", invFunVentasVsCostoTO);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			final String rutaArchivo = "reportInvFunVentasVsCosto.jrprint";
			return UtilsRESTFul.postForFile(conexionWS + "/generarReporteInvFunVentasVsCosto", map, rutaArchivo);
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public String generarReporteInvKardex(final String nombreProducto, final String fechaDesde, final String fechaHasta,
			final List<InvKardexTO> listInvKardexTO, final boolean banderaCosto) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("usuarioEmpresaReporteTO", UtilAplication.usuarioEmpresaReporteTO);
			map.put("nombreProducto", nombreProducto);
			map.put("fechaDesde", fechaDesde);
			map.put("fechaHasta", fechaHasta);
			map.put("listInvKardexTO", listInvKardexTO);
			map.put("banderaCosto", banderaCosto);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			final String rutaArchivo = "reportInvKardex.jrprint";
			return UtilsRESTFul.postForFile(conexionWS + "/generarReporteInvKardex", map, rutaArchivo);
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public String generarReporteInvProductosConError(final List<InvProductosConErrorTO> listInvProductosConErrorTO) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("usuarioEmpresaReporteTO", UtilAplication.usuarioEmpresaReporteTO);
			map.put("listInvProductosConErrorTO", listInvProductosConErrorTO);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			final String rutaArchivo = "reportReporteInvProductosConError.jrprint";
			return UtilsRESTFul.postForFile(conexionWS + "/generarReporteInvProductosConError", map, rutaArchivo);
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public String generarReporteListadoCompras(final String fechaDesde, final String fechaHasta, final String motivo,
			final String proveedorId, final String documento, final List<InvFunComprasTO> listInvFunComprasTO) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("usuarioEmpresaReporteTO", UtilAplication.usuarioEmpresaReporteTO);
			map.put("fechaDesde", fechaDesde);
			map.put("fechaHasta", fechaHasta);
			map.put("motivo", motivo);
			map.put("proveedorId", proveedorId);
			map.put("documento", documento);
			map.put("listInvFunComprasTO", listInvFunComprasTO);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			final String rutaArchivo = "reportListadoCompras.jrprint";
			return UtilsRESTFul.postForFile(conexionWS + "/generarReporteListadoCompras", map, rutaArchivo);
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public String generarReporteListadoConsumos(final String fechaDesde, final String fechaHasta,
			final List<InvFunConsumosTO> listInvFunConsumosTO) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("usuarioEmpresaReporteTO", UtilAplication.usuarioEmpresaReporteTO);
			map.put("fechaDesde", fechaDesde);
			map.put("fechaHasta", fechaHasta);
			map.put("listInvFunConsumosTO", listInvFunConsumosTO);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			final String rutaArchivo = "reportListadoConsumos.jrprint";
			return UtilsRESTFul.postForFile(conexionWS + "/generarReporteListadoConsumos", map, rutaArchivo);
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public String generarReporteListadoVentas(final String fechaDesde, final String fechaHasta, final String motivo,
			final String cliente, final String documento, final List<InvFunVentasTO> listInvFunVentasTO) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("usuarioEmpresaReporteTO", UtilAplication.usuarioEmpresaReporteTO);
			map.put("fechaDesde", fechaDesde);
			map.put("fechaHasta", fechaHasta);
			map.put("motivo", motivo);
			map.put("cliente", cliente);
			map.put("documento", documento);
			map.put("listInvFunVentasTO", listInvFunVentasTO);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			final String rutaArchivo = "reportListadoVentas.jrprint";
			return UtilsRESTFul.postForFile(conexionWS + "/generarReporteListadoVentas", map, rutaArchivo);
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public String generarReporteListaProductos(final String bodega,
			final List<InvListaProductosTO> listInvListaProductosTO) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("usuarioEmpresaReporteTO", UtilAplication.usuarioEmpresaReporteTO);
			map.put("bodega", bodega);
			map.put("listInvListaProductosTO", listInvListaProductosTO);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			final String rutaArchivo = "reportListaProductos.jrprint";
			return UtilsRESTFul.postForFile(conexionWS + "/generarReporteListaProductos", map, rutaArchivo);
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public String generarReporteProformaDetalleImpresion(final List<ReporteProformaDetalle> lista,
			final String nombreReporte) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("usuarioEmpresaReporteTO", UtilAplication.usuarioEmpresaReporteTO);
			map.put("lista", lista);
			map.put("nombreReporte", nombreReporte);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			final String rutaArchivo = "reportProformaDetalleImpresion.jrprint";
			return UtilsRESTFul.postForFile(conexionWS + "/generarReporteProformaDetalleImpresion", map, rutaArchivo);
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public String generarReporteSaldoBodega(final String bodega, final String fechaHasta,
			final List<SaldoBodegaTO> listSaldoBodegaTO) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("usuarioEmpresaReporteTO", UtilAplication.usuarioEmpresaReporteTO);
			map.put("bodega", bodega);
			map.put("fechaHasta", fechaHasta);
			map.put("listSaldoBodegaTO", listSaldoBodegaTO);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			final String rutaArchivo = "reportSaldoBodega.jrprint";
			return UtilsRESTFul.postForFile(conexionWS + "/generarReporteSaldoBodega", map, rutaArchivo);
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public String generarReporteSaldoBodegaComprobacion(final String bodega, final String fechaDesde,
			final String fechaHasta, final List<SaldoBodegaComprobacionTO> lista) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("usuarioEmpresaReporteTO", UtilAplication.usuarioEmpresaReporteTO);
			map.put("bodega", bodega);
			map.put("fechaDesde", fechaDesde);
			map.put("fechaHasta", fechaHasta);
			map.put("lista", lista);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			final String rutaArchivo = "reportSaldoBodegaComprobacion.jrprint";
			return UtilsRESTFul.postForFile(conexionWS + "/generarReporteSaldoBodegaComprobacion", map, rutaArchivo);
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public String generarReporteTrasferencias(final List<ReporteTransferenciaDetalle> listReporteTransferenciaDetalle) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("usuarioEmpresaReporteTO", UtilAplication.usuarioEmpresaReporteTO);
			map.put("listReporteTransferenciaDetalle", listReporteTransferenciaDetalle);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			final String rutaArchivo = "reportTrasferencias.jrprint";
			return UtilsRESTFul.postForFile(conexionWS + "/generarReporteTrasferencias", map, rutaArchivo);
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public String generarReporteVentaDetalleImpresion(final List<ReporteVentaDetalle> lista, final String nombreCliente,
			final String nombreReporte) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("usuarioEmpresaReporteTO", UtilAplication.usuarioEmpresaReporteTO);
			map.put("lista", lista);
			map.put("nombreCliente", nombreCliente);
			map.put("nombreReporte", nombreReporte);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			final String rutaArchivo = "reportVentaDetalleImpresion.jrprint";
			return UtilsRESTFul.postForFile(conexionWS + "/generarReporteVentaDetalleImpresion", map, rutaArchivo);
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public List<InvBodegaTO> getBodegaTO(final String empresa, final String codigo) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("empresa", empresa);
			map.put("codigo", codigo);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return Arrays.asList(restTemplate.postForObject(conexionWS + "/getBodegaTO", UtilsJSON.objetoToJson(map),
					InvBodegaTO[].class));
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public InvProveedorTO getBuscaCedulaProveedorTO(final String empresa, final String cedRuc) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("empresa", empresa);
			map.put("cedRuc", cedRuc);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return restTemplate.postForObject(conexionWS + "/getBuscaCedulaProveedorTO", UtilsJSON.objetoToJson(map),
					InvProveedorTO.class);
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public BigDecimal getCantidad3(final String empresa, final String codProducto) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("empresa", empresa);
			map.put("codProducto", codProducto);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return restTemplate.postForObject(conexionWS + "/getCantidad3", UtilsJSON.objetoToJson(map),
					BigDecimal.class);
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public boolean getClienteRepetido(final String empresa, final String codigo, final String id, final String nombre,
			final String razonSocial) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("empresa", empresa);
			map.put("codigo", codigo);
			map.put("id", id);
			map.put("nombre", nombre);
			map.put("razonSocial", razonSocial);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return restTemplate.postForObject(conexionWS + "/getClienteRepetido", UtilsJSON.objetoToJson(map),
					boolean.class);
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return false;
	}

	public List<InvClienteTO> getClienteTO(final String empresa, final String codigo) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("empresa", empresa);
			map.put("codigo", codigo);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return Arrays.asList(restTemplate.postForObject(conexionWS + "/getClienteTO", UtilsJSON.objetoToJson(map),

					InvClienteTO[].class));
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public List<InvComboFormaPagoTO> getComboFormaPagoCompra(final String empresa) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("empresa", empresa);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return Arrays.asList(restTemplate.postForObject(conexionWS + "/getComboFormaPagoCompra",
					UtilsJSON.objetoToJson(map), InvComboFormaPagoTO[].class));
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public List<InvComboFormaPagoTO> getComboFormaPagoVenta(final String empresa) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("empresa", empresa);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return Arrays.asList(restTemplate.postForObject(conexionWS + "/getComboFormaPagoVenta",
					UtilsJSON.objetoToJson(map), InvComboFormaPagoTO[].class));
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public List<InvVendedorComboListadoTO> getComboinvListaVendedorTOs(final String empresa) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("empresa", empresa);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return Arrays.asList(restTemplate.postForObject(conexionWS + "/getComboinvListaVendedorTOs",
					UtilsJSON.objetoToJson(map), InvVendedorComboListadoTO[].class));
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public Object[] getCompra(final String empresa, final String perCodigo, final String motCodigo,
			final String compNumero) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("empresa", empresa);
			map.put("perCodigo", perCodigo);
			map.put("motCodigo", motCodigo);
			map.put("compNumero", compNumero);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return restTemplate.postForObject(conexionWS + "/getCompra", UtilsJSON.objetoToJson(map), Object[].class,
					map);
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public RetornoTO getComprasPorPeriodo(final String empresa, final String codigoSector, final String fechaInicio,
			final String fechaFin, final boolean chk) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("empresa", empresa);
			map.put("codigoSector", codigoSector);
			map.put("fechaInicio", fechaInicio);
			map.put("fechaFin", fechaFin);
			map.put("chk", chk);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return restTemplate.postForObject(conexionWS + "/getComprasPorPeriodo", UtilsJSON.objetoToJson(map),
					RetornoTO.class);
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public InvComprasTO getComprasTO(final String empresa, final String periodo, final String motivo,
			final String numeroCompra) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("empresa", empresa);
			map.put("periodo", periodo);
			map.put("motivo", motivo);
			map.put("numeroCompra", numeroCompra);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return restTemplate.postForObject(conexionWS + "/getComprasTO", UtilsJSON.objetoToJson(map),
					InvComprasTO.class);
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public InvCompraTotalesTO getCompraTotalesTO(final String empresa, final String comPeriodo, final String comMotivo,
			final String ComNumero) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("empresa", empresa);
			map.put("comPeriodo", comPeriodo);
			map.put("comMotivo", comMotivo);
			map.put("ComNumero", ComNumero);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return restTemplate.postForObject(conexionWS + "/getCompraTotalesTO", UtilsJSON.objetoToJson(map),
					InvCompraTotalesTO.class);
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public String getConteoNumeroFacturaCompra(final String empresaCodigo, final String provCodigo,
			final String compDocumentoTipo, final String compDocumentoNumero) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("empresaCodigo", empresaCodigo);
			map.put("provCodigo", provCodigo);
			map.put("compDocumentoTipo", compDocumentoTipo);
			map.put("compDocumentoNumero", compDocumentoNumero);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return restTemplate.postForObject(conexionWS + "/getConteoNumeroFacturaCompra", UtilsJSON.objetoToJson(map),
					String.class);
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public String getConteoNumeroFacturaVenta(final String empresaCodigo, final String compDocumentoTipo,
			final String compDocumentoNumero) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("empresaCodigo", empresaCodigo);
			map.put("compDocumentoTipo", compDocumentoTipo);
			map.put("compDocumentoNumero", compDocumentoNumero);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return restTemplate.postForObject(conexionWS + "/getConteoNumeroFacturaVenta", UtilsJSON.objetoToJson(map),
					String.class);
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public InvEstadoCCCVT getEstadoCCCVT(final String empresa, final String periodo, final String motivo,
			final String numero, final String proceso) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("empresa", empresa);
			map.put("periodo", periodo);
			map.put("motivo", motivo);
			map.put("numero", numero);
			map.put("proceso", proceso);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return restTemplate.postForObject(conexionWS + "/getEstadoCCCVT", UtilsJSON.objetoToJson(map),
					InvEstadoCCCVT.class);
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public List<InvListaConsultaCompraTO> getFunComprasListado(final String empresa, final String fechaDesde,
			final String fechaHasta, final String status) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("empresa", empresa);
			map.put("fechaDesde", fechaDesde);
			map.put("fechaHasta", fechaHasta);
			map.put("status", status);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return Arrays.asList(restTemplate.postForObject(conexionWS + "/getFunComprasListado",
					UtilsJSON.objetoToJson(map), InvListaConsultaCompraTO[].class));
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public List<InvListaConsultaConsumosTO> getFunConsumosListado(final String empresa, final String fechaDesde,
			final String fechaHasta, final String status) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("empresa", empresa);
			map.put("fechaDesde", fechaDesde);
			map.put("fechaHasta", fechaHasta);
			map.put("status", status);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return Arrays.asList(restTemplate.postForObject(conexionWS + "/getFunConsumosListado",
					UtilsJSON.objetoToJson(map), InvListaConsultaConsumosTO[].class));
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public List<InvListaConsultaTransferenciaTO> getFunListadoTransferencias(final String empresa,
			final String fechaDesde, final String fechaHasta, final String status) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("empresa", empresa);
			map.put("fechaDesde", fechaDesde);
			map.put("fechaHasta", fechaHasta);
			map.put("status", status);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return Arrays.asList(restTemplate.postForObject(conexionWS + "/getFunListadoTransferencias",
					UtilsJSON.objetoToJson(map), InvListaConsultaTransferenciaTO[].class));
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public List<InvListaConsultaVentaTO> getFunVentasListado(final String empresa, final String fechaDesde,
			final String fechaHasta, final String status) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("empresa", empresa);
			map.put("fechaDesde", fechaDesde);
			map.put("fechaHasta", fechaHasta);
			map.put("status", status);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return Arrays.asList(restTemplate.postForObject(conexionWS + "/getFunVentasListado",
					UtilsJSON.objetoToJson(map), InvListaConsultaVentaTO[].class));
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public List<InvClienteCategoriaTO> getInvClienteCategoriaTO(final String empresa) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("empresa", empresa);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return Arrays.asList(restTemplate.postForObject(conexionWS + "/getInvClienteCategoriaTO",
					UtilsJSON.objetoToJson(map), InvClienteCategoriaTO[].class));
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public List<InvComboBodegaTO> getInvComboBodegaTO(final String empresa) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("empresa", empresa);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return Arrays.asList(restTemplate.postForObject(conexionWS + "/getInvComboBodegaTO",
					UtilsJSON.objetoToJson(map), InvComboBodegaTO[].class));
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public InvCompraCabeceraTO getInvCompraCabeceraTO(final String empresa, final String codigoPeriodo,
			final String motivo, final String numeroCompra) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("empresa", empresa);
			map.put("codigoPeriodo", codigoPeriodo);
			map.put("motivo", motivo);
			map.put("numeroCompra", numeroCompra);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return restTemplate.postForObject(conexionWS + "/getInvCompraCabeceraTO", UtilsJSON.objetoToJson(map),
					InvCompraCabeceraTO.class);
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public InvComprasMotivoTO getInvComprasMotivoTO(final String empresa, final String cmCodigo) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("empresa", empresa);
			map.put("cmCodigo", cmCodigo);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return restTemplate.postForObject(conexionWS + "/getInvComprasMotivoTO", UtilsJSON.objetoToJson(map),
					InvComprasMotivoTO.class, map);
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public InvComprasRecepcionTO getInvComprasRecepcionTO(final String empresa, final String periodo,
			final String motivo, final String numero) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("empresa", empresa);
			map.put("periodo", periodo);
			map.put("motivo", motivo);
			map.put("numero", numero);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return restTemplate.postForObject(conexionWS + "/getInvComprasRecepcionTO", UtilsJSON.objetoToJson(map),
					InvComprasRecepcionTO.class);
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public InvConsumosTO getInvConsumoCabeceraTO(final String empresa, final String codigoPeriodo, final String motivo,
			final String numeroConsumo) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("empresa", empresa);
			map.put("codigoPeriodo", codigoPeriodo);
			map.put("motivo", motivo);
			map.put("numeroConsumo", numeroConsumo);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return restTemplate.postForObject(conexionWS + "/getInvConsumoCabeceraTO", UtilsJSON.objetoToJson(map),
					InvConsumosTO.class);
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public InvConsumosMotivoTO getInvConsumoMotivoTO(final String empresa, final String cmCodigo) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("empresa", empresa);
			map.put("cmCodigo", cmCodigo);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return restTemplate.postForObject(conexionWS + "/getInvConsumoMotivoTO", UtilsJSON.objetoToJson(map),
					InvConsumosMotivoTO.class);
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public InvConsumos getInvConsumos(final String empresa, final String periodo, final String motivo,
			final String numero) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("empresa", empresa);
			map.put("periodo", periodo);
			map.put("motivo", motivo);
			map.put("numero", numero);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return restTemplate.postForObject(conexionWS + "/getInvConsumos", UtilsJSON.objetoToJson(map),
					InvConsumos.class);
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public List<InvFunComprasConsolidandoProductosTO> getInvFunComprasConsolidandoProductosTO(final String empresa,
			final String desde, final String hasta, final String sector, final String motivo, final String proveedor) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("empresa", empresa);
			map.put("desde", desde);
			map.put("hasta", hasta);
			map.put("sector", sector);
			map.put("motivo", motivo);
			map.put("proveedor", proveedor);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return Arrays.asList(restTemplate.postForObject(conexionWS + "/getInvFunComprasConsolidandoProductosTO",
					UtilsJSON.objetoToJson(map), InvFunComprasConsolidandoProductosTO[].class));
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public List<InvFunComprasTO> getInvFunComprasTO(final String empresa, final String desde, final String hasta,
			final String motivo, final String proveedor, final String documento) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("empresa", empresa);
			map.put("desde", desde);
			map.put("hasta", hasta);
			map.put("motivo", motivo);
			map.put("proveedor", proveedor);
			map.put("documento", documento);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return Arrays.asList(restTemplate.postForObject(conexionWS + "/getInvFunComprasTO",
					UtilsJSON.objetoToJson(map), InvFunComprasTO[].class));
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public List<InvFunConsumosConsolidandoProductosTO> getInvFunConsumosConsolidandoProductosTO(final String empresa,
			final String desde, final String hasta, final String sector, final String bodega) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("empresa", empresa);
			map.put("desde", desde);
			map.put("hasta", hasta);
			map.put("sector", sector);
			map.put("bodega", bodega);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return Arrays.asList(restTemplate.postForObject(conexionWS + "/getInvFunConsumosConsolidandoProductosTO",
					UtilsJSON.objetoToJson(map), InvFunConsumosConsolidandoProductosTO[].class, map));
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public List<InvFunConsumosTO> getInvFunConsumosTO(final String empresa, final String desde, final String hasta,
			final String motivo) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("empresa", empresa);
			map.put("desde", desde);
			map.put("hasta", hasta);
			map.put("motivo", motivo);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return Arrays.asList(restTemplate.postForObject(conexionWS + "/getInvFunConsumosTO",
					UtilsJSON.objetoToJson(map), InvFunConsumosTO[].class));
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public List<InvFunListadoClientesTO> getInvFunListadoClientesTO(final String empresa, final String categoria) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("empresa", empresa);
			map.put("categoria", categoria);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return Arrays.asList(restTemplate.postForObject(conexionWS + "/getInvFunListadoClientesTO",
					UtilsJSON.objetoToJson(map), InvFunListadoClientesTO[].class));
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public List<InvFunListadoProductosTO> getInvFunListadoProductosTO(final String empresa, final String categoria,
			final String busqueda) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("empresa", empresa);
			map.put("categoria", categoria);
			map.put("busqueda", busqueda);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return Arrays.asList(restTemplate.postForObject(conexionWS + "/getInvFunListadoProductosTO",
					UtilsJSON.objetoToJson(map), InvFunListadoProductosTO[].class));
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public List<InvFunListadoProveedoresTO> getInvFunListadoProveedoresTO(final String empresa,
			final String categoria) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("empresa", empresa);
			map.put("categoria", categoria);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return Arrays.asList(restTemplate.postForObject(conexionWS + "/getInvFunListadoProveedoresTO",
					UtilsJSON.objetoToJson(map), InvFunListadoProveedoresTO[].class));
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public List<InvFunListaProductosImpresionPlacasTO> getInvFunListaProductosImpresionPlacas1TO(final String empresa,
			final String producto, final boolean estado) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("empresa", empresa);
			map.put("producto", producto);
			map.put("estado", estado);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return Arrays.asList(restTemplate.postForObject(conexionWS + "/getInvFunListaProductosImpresionPlacasTO",
					UtilsJSON.objetoToJson(map), InvFunListaProductosImpresionPlacasTO[].class));
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public RetornoTO getInvFunListaProductosSaldosGeneralTO(final String empresa, final String producto,
			final String fecha, final boolean estado, final String usuario) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("empresa", empresa);
			map.put("producto", producto);
			map.put("fecha", fecha);
			map.put("estado", estado);
			map.put("usuario", usuario);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return restTemplate.postForObject(conexionWS + "/getInvFunListaProductosSaldosGeneralTO",
					UtilsJSON.objetoToJson(map), RetornoTO.class);
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public List<SaldoBodegaComprobacionTO> getInvFunSaldoBodegaComprobacionCantidadTO(final String empresa,
			final String bodega, final String desde, final String hasta) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("empresa", empresa);
			map.put("bodega", bodega);
			map.put("desde", desde);
			map.put("hasta", hasta);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return Arrays
					.asList(restTemplate.postForObject(conexionWS + "/getInvFunSaldoBodegaComprobacionCantidadesTO",
							UtilsJSON.objetoToJson(map), SaldoBodegaComprobacionTO[].class));
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public List<SaldoBodegaComprobacionTO> getInvFunSaldoBodegaComprobacionTO(final String empresa, final String bodega,
			final String desde, final String hasta) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("empresa", empresa);
			map.put("bodega", bodega);
			map.put("desde", desde);
			map.put("hasta", hasta);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());

			return Arrays.asList(restTemplate.postForObject(conexionWS + "/getInvFunSaldoBodegaComprobacionTO",
					UtilsJSON.objetoToJson(map), SaldoBodegaComprobacionTO[].class));
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public List<InvFunUltimasComprasTO> getInvFunUltimasComprasTOs(final String empresa, final String producto) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("empresa", empresa);
			map.put("producto", producto);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return Arrays.asList(restTemplate.postForObject(conexionWS + "/getInvFunUltimasComprasTOs",
					UtilsJSON.objetoToJson(map), InvFunUltimasComprasTO[].class));
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public List<InvFunVentasConsolidandoClientesTO> getInvFunVentasConsolidandoClientesTO(final String empresa,
			final String sector, final String desde, final String hasta) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("empresa", empresa);
			map.put("sector", sector);
			map.put("desde", desde);
			map.put("hasta", hasta);

			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return Arrays.asList(restTemplate.postForObject(conexionWS + "/getInvFunVentasConsolidandoClientesTO",
					UtilsJSON.objetoToJson(map), InvFunVentasConsolidandoClientesTO[].class));
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public List<InvFunVentasConsolidandoProductosTO> getInvFunVentasConsolidandoProductosTO(final String empresa,
			final String desde, final String hasta, final String sector, final String motivo, final String cliente) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("empresa", empresa);
			map.put("desde", desde);
			map.put("hasta", hasta);
			map.put("sector", sector);
			map.put("motivo", motivo);
			map.put("cliente", cliente);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return Arrays.asList(restTemplate.postForObject(conexionWS + "/getInvFunVentasConsolidandoProductosTO",
					UtilsJSON.objetoToJson(map), InvFunVentasConsolidandoProductosTO[].class));
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public List<InvFunVentasTO> getInvFunVentasTO(final String empresa, final String desde, final String hasta,
			final String motivo, final String cliente, final String documento) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("empresa", empresa);
			map.put("desde", desde);
			map.put("hasta", hasta);
			map.put("motivo", motivo);
			map.put("cliente", cliente);
			map.put("documento", documento);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return Arrays.asList(restTemplate.postForObject(conexionWS + "/getInvFunVentasTO",
					UtilsJSON.objetoToJson(map), InvFunVentasTO[].class));
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public List<InvFunVentasVsCostoTO> getInvFunVentasVsCostoTO(final String empresa, final String desde,
			final String hasta, final String bodega, final String cliente) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("empresa", empresa);
			map.put("desde", desde);
			map.put("hasta", hasta);
			map.put("bodega", bodega);
			map.put("cliente", cliente);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return Arrays.asList(restTemplate.postForObject(conexionWS + "/getInvFunVentasVsCostoTO",
					UtilsJSON.objetoToJson(map), InvFunVentasVsCostoTO[].class));
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public List<InvListaComprasFormaPagoTO> getInvListaComprasPagosFormaTO(final String empresa) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("empresa", empresa);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return Arrays.asList(restTemplate.postForObject(conexionWS + "/getInvListaComprasFormaPagoTO",
					UtilsJSON.objetoToJson(map), InvListaComprasFormaPagoTO[].class, map));
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public List<InvListaConsumosMotivoTO> getInvListaConsumosMotivoTO(final String empresa) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("empresa", empresa);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return Arrays.asList(restTemplate.postForObject(conexionWS + "/getInvListaConsumosMotivoTO",
					UtilsJSON.objetoToJson(map), InvListaConsumosMotivoTO[].class));
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public List<InvListaTransferenciaMotivoTO> getInvListaTransferenciaMotivoTO(final String empresa) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("empresa", empresa);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return Arrays.asList(restTemplate.postForObject(conexionWS + "/getInvListaTransferenciaMotivoTO",
					UtilsJSON.objetoToJson(map), InvListaTransferenciaMotivoTO[].class));
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public List<InvListaVentasFormaPagoTO> getInvListaVentasPagosFormaTO(final String empresa) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("empresa", empresa);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return Arrays.asList(restTemplate.postForObject(conexionWS + "/getInvListaVentasFormaPagoTO",
					UtilsJSON.objetoToJson(map), InvListaVentasFormaPagoTO[].class));
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public List<InvProductoMarcaComboListadoTO> getInvMarcaComboListadoTO(final String empresa) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("empresa", empresa);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return Arrays.asList(restTemplate.postForObject(conexionWS + "/getInvMarcaComboListadoTO",
					UtilsJSON.objetoToJson(map), InvProductoMarcaComboListadoTO[].class));
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public List<InvProductoCategoriaTO> getInvProductoCategoria(final String empresa, String menuComida) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("empresa", empresa);
			map.put("menuComida", menuComida);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return new ArrayList<>(Arrays.asList(restTemplate.postForObject(conexionWS + "/getInvProductoCategoriaTO",
					UtilsJSON.objetoToJson(map), InvProductoCategoriaTO[].class)));
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public List<InvProductoMedidaTO> getInvProductoMedidaTO(final String empresa) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("empresa", empresa);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return Arrays.asList(restTemplate.postForObject(conexionWS + "/getInvProductoMedidaTO",
					UtilsJSON.objetoToJson(map), InvProductoMedidaTO[].class));
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public List<InvListaProductosCompraSustentoConceptoTO> getInvProductoSustentoConcepto(final String empresa,
			final List<InvListaProductosCompraSustentoConceptoTO> invListaProductosCompraTOs) {
		try {

			final Map<String, Object> map = new HashMap<>();
			map.put("empresa", empresa);
			map.put("invListaProductosCompraTOs", invListaProductosCompraTOs);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return Arrays.asList(restTemplate.postForObject(conexionWS + "/getInvProductoSustentoConcepto",
					UtilsJSON.objetoToJson(map), InvListaProductosCompraSustentoConceptoTO[].class));
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public List<InvProductoTipoComboTO> getInvProductoTipoComboListadoTO(final String empresa, final String accion) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("empresa", empresa);
			map.put("accion", accion);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return Arrays.asList(restTemplate.postForObject(conexionWS + "/getInvProductoTipoComboListadoTO",
					UtilsJSON.objetoToJson(map), InvProductoTipoComboTO[].class));
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public InvProformaCabeceraTO getInvProformaCabeceraTO(final String empresa, final String codigoPeriodo,
			final String motivo, final String numeroProforma) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("empresa", empresa);
			map.put("codigoPeriodo", codigoPeriodo);
			map.put("motivo", motivo);
			map.put("numeroProforma", numeroProforma);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return restTemplate.postForObject(conexionWS + "/getInvProformaCabeceraTO", UtilsJSON.objetoToJson(map),
					InvProformaCabeceraTO.class);
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public InvProformaMotivoTO getInvProformasMotivoTO(final String empresa, final String pmCodigo) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("empresa", empresa);
			map.put("pmCodigo", pmCodigo);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return restTemplate.postForObject(conexionWS + "/getInvProformasMotivoTO", UtilsJSON.objetoToJson(map),
					InvProformaMotivoTO.class);
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public List<InvProveedorCategoriaTO> getInvProveedorCategoria(final String empresa) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("empresa", empresa);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return Arrays.asList(restTemplate.postForObject(conexionWS + "/getInvProveedorCategoriaTO",
					UtilsJSON.objetoToJson(map), InvProveedorCategoriaTO[].class));
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public List<InvSecuenciaComprobanteTO> getInvSecuenciaComprobanteTO(final String empresa, final String fechaDesde,
			final String fechaHasta) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("empresa", empresa);
			map.put("fechaDesde", fechaDesde);
			map.put("fechaHasta", fechaHasta);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return Arrays.asList(restTemplate.postForObject(conexionWS + "/getInvSecuenciaComprobanteTO",
					UtilsJSON.objetoToJson(map), InvSecuenciaComprobanteTO[].class));
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public InvTransferenciaMotivoTO getInvTransferenciaMotivoTO(final String empresa, final String tmCodigo) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("empresa", empresa);
			map.put("tmCodigo", tmCodigo);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return restTemplate.postForObject(conexionWS + "/getInvTransferenciaMotivoTO", UtilsJSON.objetoToJson(map),
					InvTransferenciaMotivoTO.class);
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public InvTransferenciasTO getInvTransferenciasCabeceraTO(final String empresa, final String codigoPeriodo,
			final String motivo, final String numeroTransferencia) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("empresa", empresa);
			map.put("codigoPeriodo", codigoPeriodo);
			map.put("motivo", motivo);
			map.put("numeroTransferencia", numeroTransferencia);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return restTemplate.postForObject(conexionWS + "/getInvTransferenciasCabeceraTO",
					UtilsJSON.objetoToJson(map), InvTransferenciasTO.class);
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public InvVentaCabeceraTO getInvVentaCabeceraTO(final String empresa, final String codigoPeriodo,
			final String motivo, final String numeroVenta) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("empresa", empresa);
			map.put("codigoPeriodo", codigoPeriodo);
			map.put("motivo", motivo);
			map.put("numeroVenta", numeroVenta);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return restTemplate.postForObject(conexionWS + "/getInvVentaCabeceraTO", UtilsJSON.objetoToJson(map),
					InvVentaCabeceraTO.class);
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public InvVentaRetencionesTO getInvVentaRetencionesTO(final String codigoEmpresa, final String facturaNumero) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("codigoEmpresa", codigoEmpresa);
			map.put("facturaNumero", facturaNumero);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return restTemplate.postForObject(conexionWS + "/getInvVentaRetencionesTO", UtilsJSON.objetoToJson(map),
					InvVentaRetencionesTO.class);
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public InvVentaMotivoTO getInvVentasMotivoTO(final String empresa, final String vmCodigo) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("empresa", empresa);
			map.put("vmCodigo", vmCodigo);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return restTemplate.postForObject(conexionWS + "/getInvVentasMotivoTO", UtilsJSON.objetoToJson(map),
					InvVentaMotivoTO.class);
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public List<InvBodega> getListaBodegas(final String empresa, final boolean inactivo) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("empresa", empresa);
			map.put("inactivo", inactivo);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return Arrays.asList(restTemplate.postForObject(conexionWS + "/getListaBodegas",
					UtilsJSON.objetoToJson(map), InvBodega[].class));
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public List<InvListaBodegasTO> getListaBodegasTO(final String empresa, final boolean inactivo) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("empresa", empresa);
			map.put("inactivo", inactivo);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return Arrays.asList(restTemplate.postForObject(conexionWS + "/getListaBodegasTO",
					UtilsJSON.objetoToJson(map), InvListaBodegasTO[].class));
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public List<InvCategoriaClienteComboTO> getListaCategoriaClienteComboTO(final String empresa) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("empresa", empresa);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return Arrays.asList(restTemplate.postForObject(conexionWS + "/getListaCategoriaClienteComboTO",
					UtilsJSON.objetoToJson(map), InvCategoriaClienteComboTO[].class));
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public List<InvCategoriaComboProductoTO> getListaCategoriaComboTO(final String empresa) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("empresa", empresa);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return Arrays.asList(restTemplate.postForObject(conexionWS + "/getListaCategoriaComboTO",
					UtilsJSON.objetoToJson(map), InvCategoriaComboProductoTO[].class));
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public List<InvCategoriaProveedorComboTO> getListaCategoriaProveedorComboTO(final String empresa) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("empresa", empresa);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return Arrays.asList(restTemplate.postForObject(conexionWS + "/getListaCategoriaProveedorComboTO",
					UtilsJSON.objetoToJson(map), InvCategoriaProveedorComboTO[].class));
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public List<InvListaClienteTO> getListaClienteTO(final String empresa, final String busqueda,
			final boolean activo_Cliente) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("empresa", empresa);
			map.put("busqueda", busqueda);
			map.put("activo_Cliente", activo_Cliente);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return Arrays.asList(restTemplate.postForObject(conexionWS + "/getListaClienteTO",
					UtilsJSON.objetoToJson(map), InvListaClienteTO[].class));
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public List<InvCompraMotivoComboTO> getListaCompraMotivoComboto(final String empresa,
			final Boolean filtrarInactivos) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("empresa", empresa);
			map.put("filtrarInactivos", filtrarInactivos);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return Arrays.asList(restTemplate.postForObject(conexionWS + "/getListaCompraMotivoComboTO",
					UtilsJSON.objetoToJson(map), InvCompraMotivoComboTO[].class));
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public List<InvConsumosMotivo> getListaConsumosMotivo(final String empresa, final boolean filtrarInactivos) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("empresa", empresa);
			map.put("filtrarInactivos", filtrarInactivos);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return Arrays.asList(restTemplate.postForObject(conexionWS + "/getListaConsumosMotivo",
					UtilsJSON.objetoToJson(map), InvConsumosMotivo[].class));
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public List<InvConsumosMotivoComboTO> getListaConsumosMotivoComboTO(final String empresa,
			final boolean filtrarInactivos) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("empresa", empresa);
			map.put("filtrarInactivos", filtrarInactivos);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return Arrays.asList(restTemplate.postForObject(conexionWS + "/getListaConsumosMotivoComboTO",
					UtilsJSON.objetoToJson(map), InvConsumosMotivoComboTO[].class));
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public List<InvProductosConErrorTO> getListadoProductosConError(final String empresa) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("empresa", empresa);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return Arrays.asList(restTemplate.postForObject(conexionWS + "/getListadoProductosConError",
					UtilsJSON.objetoToJson(map), InvProductosConErrorTO[].class));
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public List<InvClienteDetalleVentaAutomatica> getListaInvClienteDetalleVentaAutomatica(final String empresa,
			final String cliente) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("empresa", empresa);
			map.put("cliente", cliente);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return new ArrayList<>(
					Arrays.asList(restTemplate.postForObject(conexionWS + "/getListaInvClienteDetalleVentaAutomatica",
							UtilsJSON.objetoToJson(map), InvClienteDetalleVentaAutomatica[].class, map)));
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public List<InvListaDetalleComprasTO> getListaInvCompraDetalleTO(final String empresa, final String periodo,
			final String motivo, final String numeroCompra) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("empresa", empresa);
			map.put("periodo", periodo);
			map.put("motivo", motivo);
			map.put("numeroCompra", numeroCompra);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return Arrays.asList(restTemplate.postForObject(conexionWS + "/getListaInvCompraDetalleTO",
					UtilsJSON.objetoToJson(map), InvListaDetalleComprasTO[].class));
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public List<InvCompraMotivoTablaTO> getListaInvComprasMotivoTablaTO(final String empresa) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("empresa", empresa);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return Arrays.asList(restTemplate.postForObject(conexionWS + "/getListaInvComprasMotivoTablaTO",
					UtilsJSON.objetoToJson(map), InvCompraMotivoTablaTO[].class));
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public List<InvListaConsultaCompraTO> getListaInvConsultaCompra(final String empresa, final String periodo,
			final String motivo, final String busqueda, final String nRegistros) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("empresa", empresa);
			map.put("periodo", periodo);
			map.put("motivo", motivo);
			map.put("busqueda", busqueda);
			map.put("nRegistros", nRegistros);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return Arrays.asList(restTemplate.postForObject(conexionWS + "/getListaInvConsultaCompra",
					UtilsJSON.objetoToJson(map), InvListaConsultaCompraTO[].class));
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public List<InvListaConsultaConsumosTO> getListaInvConsultaConsumos(final String empresa, final String periodo,
			final String motivo, final String busqueda, final String nRegistros) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("empresa", empresa);
			map.put("periodo", periodo);
			map.put("motivo", motivo);
			map.put("busqueda", busqueda);
			map.put("nRegistros", nRegistros);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return Arrays.asList(restTemplate.postForObject(conexionWS + "/getListaInvConsultaConsumos",
					UtilsJSON.objetoToJson(map), InvListaConsultaConsumosTO[].class));
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public List<InvListaConsultaProformaTO> getListaInvConsultaProforma(final String empresa, final String periodo,
			final String motivo, final String busqueda) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("empresa", empresa);
			map.put("periodo", periodo);
			map.put("motivo", motivo);
			map.put("busqueda", busqueda);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return Arrays.asList(restTemplate.postForObject(conexionWS + "/getListaInvConsultaProforma",
					UtilsJSON.objetoToJson(map), InvListaConsultaProformaTO[].class));
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public List<InvListaConsultaTransferenciaTO> getListaInvConsultaTransferencias(final String empresa,
			final String periodo, final String motivo, final String busqueda, final String nRegistros) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("empresa", empresa);
			map.put("periodo", periodo);
			map.put("motivo", motivo);
			map.put("busqueda", busqueda);
			map.put("nRegistros", nRegistros);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return Arrays.asList(restTemplate.postForObject(conexionWS + "/getListaInvConsultaTransferencias",
					UtilsJSON.objetoToJson(map), InvListaConsultaTransferenciaTO[].class));
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public List<InvListaConsultaVentaTO> getListaInvConsultaVenta(final String empresa, final String periodo,
			final String motivo, final String busqueda, final String nRegistros) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("empresa", empresa);
			map.put("periodo", periodo);
			map.put("motivo", motivo);
			map.put("busqueda", busqueda);
			map.put("nRegistros", nRegistros);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return Arrays.asList(restTemplate.postForObject(conexionWS + "/getListaInvConsultaVenta",
					UtilsJSON.objetoToJson(map), InvListaConsultaVentaTO[].class));
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public List<InvListaDetalleConsumoTO> getListaInvConsumoDetalleTO(final String empresa, final String periodo,
			final String motivo, final String numeroConsumo) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("empresa", empresa);
			map.put("periodo", periodo);
			map.put("motivo", motivo);
			map.put("numeroConsumo", numeroConsumo);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return Arrays.asList(restTemplate.postForObject(conexionWS + "/getListaInvConsumoDetalleTO",
					UtilsJSON.objetoToJson(map), InvListaDetalleConsumoTO[].class, map));
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public List<InvConsumosImportarExportarTO> getListaInvConsumosImportarExportarTO(final String empresa,
			final String desde, final String hasta) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("empresa", empresa);
			map.put("desde", desde);
			map.put("hasta", hasta);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return Arrays.asList(restTemplate.postForObject(conexionWS + "/getListaInvConsumosImportarExportarTO",
					UtilsJSON.objetoToJson(map), InvConsumosImportarExportarTO[].class));
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public List<InvKardexTO> getListaInvKardexTO(final String empresa, final String bodega, final String producto,
			final String desde, final String hasta, final String promedio) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("empresa", empresa);
			map.put("bodega", bodega);
			map.put("producto", producto);
			map.put("desde", desde);
			map.put("hasta", hasta);
			map.put("promedio", promedio);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return Arrays.asList(restTemplate.postForObject(conexionWS + "/getListaInvKardexTO",
					UtilsJSON.objetoToJson(map), InvKardexTO[].class));
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public List<InvMedidaComboTO> getListaInvMedidaTablaTO(final String empresa) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("empresa", empresa);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return Arrays.asList(restTemplate.postForObject(conexionWS + "/getListaInvMedidaTablaTO",
					UtilsJSON.objetoToJson(map), InvMedidaComboTO[].class));
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public List<InvNumeracionCompraTO> getListaInvNumeracionCompraTO(final String empresa, final String periodo,
			final String motivo) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("empresa", empresa);
			map.put("periodo", periodo);
			map.put("motivo", motivo);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return Arrays.asList(restTemplate.postForObject(conexionWS + "/getListaInvNumeracionCompraTO",
					UtilsJSON.objetoToJson(map), InvNumeracionCompraTO[].class));
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public List<InvNumeracionConsumoTO> getListaInvNumeracionConsumoTO(final String empresa, final String periodo,
			final String motivo) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("empresa", empresa);
			map.put("periodo", periodo);
			map.put("motivo", motivo);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return Arrays.asList(restTemplate.postForObject(conexionWS + "/getListaInvNumeracionConsumoTO",
					UtilsJSON.objetoToJson(map), InvNumeracionConsumoTO[].class));
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public List<InvNumeracionVentaTO> getListaInvNumeracionVentaTO(final String empresa, final String periodo,
			final String motivo) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("empresa", empresa);
			map.put("periodo", periodo);
			map.put("motivo", motivo);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return Arrays.asList(restTemplate.postForObject(conexionWS + "/getListaInvNumeracionVentaTO",
					UtilsJSON.objetoToJson(map), InvNumeracionVentaTO[].class));
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public List<InvProformaMotivoTablaTO> getListaInvProformaMotivoTablaTO(final String empresa) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("empresa", empresa);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return Arrays.asList(restTemplate.postForObject(conexionWS + "/getListaInvProformaMotivoTablaTO",
					UtilsJSON.objetoToJson(map), InvProformaMotivoTablaTO[].class));
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public List<InvListaDetalleProformasTO> getListaInvProformasDetalleTO(final String empresa, final String periodo,
			final String motivo, final String numeroProformas) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("empresa", empresa);
			map.put("periodo", periodo);
			map.put("motivo", motivo);
			map.put("numeroProformas", numeroProformas);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return Arrays.asList(restTemplate.postForObject(conexionWS + "/getListaInvProformasDetalleTO",
					UtilsJSON.objetoToJson(map), InvListaDetalleProformasTO[].class));
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public List<InvListaDetalleTransferenciaTO> getListaInvTransferenciasDetalleTO(final String empresa,
			final String periodo, final String motivo, final String numeroTransferencia) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("empresa", empresa);
			map.put("periodo", periodo);
			map.put("motivo", motivo);
			map.put("numeroTransferencia", numeroTransferencia);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return Arrays.asList(restTemplate.postForObject(conexionWS + "/getListaInvTransferenciasDetalleTO",
					UtilsJSON.objetoToJson(map), InvListaDetalleTransferenciaTO[].class));
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public List<InvVentaMotivoTablaTO> getListaInvVentaMotivoTablaTO(final String empresa) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("empresa", empresa);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return Arrays.asList(restTemplate.postForObject(conexionWS + "/getListaInvVentasMotivoTablaTO",
					UtilsJSON.objetoToJson(map), InvVentaMotivoTablaTO[].class));
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public List<InvListaDetalleVentasTO> getListaInvVentasDetalleTO(final String empresa, final String periodo,
			final String motivo, final String numeroVentas) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("empresa", empresa);
			map.put("periodo", periodo);
			map.put("motivo", motivo);
			map.put("numeroVentas", numeroVentas);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return Arrays.asList(restTemplate.postForObject(conexionWS + "/getListaInvVentasDetalleTO",
					UtilsJSON.objetoToJson(map), InvListaDetalleVentasTO[].class));
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public List<InvProductoPresentacionCajasComboListadoTO> getListaPresentacionCajaComboTO(final String empresa) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("empresa", empresa);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return Arrays.asList(restTemplate.postForObject(conexionWS + "/getListaPresentacionCajaComboTO",
					UtilsJSON.objetoToJson(map), InvProductoPresentacionCajasComboListadoTO[].class));
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public List<InvProductoPresentacionUnidadesComboListadoTO> getListaPresentacionUnidadComboTO(final String empresa) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("empresa", empresa);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return Arrays.asList(restTemplate.postForObject(conexionWS + "/getListaPresentacionUnidadComboTO",
					UtilsJSON.objetoToJson(map), InvProductoPresentacionUnidadesComboListadoTO[].class));
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public List<InvListaProductosCambiarPrecioCantidadTO> getListaProductosCambiarPrecioCantidadTO(final String empresa,
			final String busqueda, final String bodega, final String fecha) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("empresa", empresa);
			map.put("busqueda", busqueda);
			map.put("bodega", bodega);
			map.put("fecha", fecha);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return Arrays.asList(restTemplate.postForObject(conexionWS + "/getListaProductosCambiarPrecioCantidadTO",
					UtilsJSON.objetoToJson(map), InvListaProductosCambiarPrecioCantidadTO[].class));
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public List<InvListaProductosCambiarPrecioTO> getListaProductosCambiarPrecioTO(final String empresa,
			final String busqueda, final String bodega, final String fecha) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("empresa", empresa);
			map.put("busqueda", busqueda);
			map.put("bodega", bodega);
			map.put("fecha", fecha);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return Arrays.asList(restTemplate.postForObject(conexionWS + "/getListaProductosCambiarPrecioTO",
					UtilsJSON.objetoToJson(map), InvListaProductosCambiarPrecioTO[].class));
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public List<InvListaProductosTO> getListaProductosTO(final String empresa, final String busqueda,
			final String bodega, final String categoria, String fecha, final boolean incluirInactivos,
			final boolean limite) {
		try {
			if (fecha.trim().isEmpty())
				fecha = null;
			else
				fecha = "'" + fecha + "'";
			final Map<String, Object> map = new HashMap<>();
			map.put("empresa", empresa);
			map.put("busqueda", busqueda);
			map.put("bodega", bodega);
			map.put("categoria", categoria);
			map.put("fecha", fecha);
			map.put("incluirInactivos", incluirInactivos);
			map.put("limite", limite);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return Arrays.asList(restTemplate.postForObject(conexionWS + "/getListaProductosTO",
					UtilsJSON.objetoToJson(map), InvListaProductosTO[].class, map));
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public List<InvListaProductosTO> getListaProductosTO(final String empresa, final String busqueda,
			final String bodega, final String categoria, final String fecha, final boolean incluirInactivos,
			final boolean limite, final boolean codigo) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("empresa", empresa);
			map.put("busqueda", busqueda);
			map.put("bodega", bodega);
			map.put("categoria", categoria);
			map.put("fecha", fecha.trim().isEmpty() ? null : "'" + fecha + "'");
			map.put("incluirInactivos", incluirInactivos);
			map.put("limite", limite);
			map.put("codigo", codigo);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return new ArrayList<>(Arrays.asList(restTemplate.postForObject(conexionWS + "/getListaProductosTO",
					UtilsJSON.objetoToJson(map), InvListaProductosTO[].class, map)));
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public List<InvProformaMotivoComboTO> getListaProformaMotivoComboto(final String empresa,
			final boolean filtrarInactivos) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("empresa", empresa);
			map.put("filtrarInactivos", filtrarInactivos);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return Arrays.asList(restTemplate.postForObject(conexionWS + "/getListaProformaMotivoComboTO",
					UtilsJSON.objetoToJson(map), InvProformaMotivoComboTO[].class));
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public List<InvListaProveedoresTO> getListaProveedoresTO(final String empresa, final String busqueda,
			final boolean activoProveedor) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("empresa", empresa);
			map.put("busqueda", busqueda);
			map.put("activoProveedor", activoProveedor);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return Arrays.asList(restTemplate.postForObject(conexionWS + "/getListaProveedoresTO",
					UtilsJSON.objetoToJson(map), InvListaProveedoresTO[].class));
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public List<SaldoBodegaTO> getListaSaldoBodegaTO(final String empresa, final String bodega, final String hasta) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("empresa", empresa);
			map.put("bodega", bodega);
			map.put("hasta", hasta);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return Arrays.asList(restTemplate.postForObject(conexionWS + "/getListaSaldoBodegaTO",
					UtilsJSON.objetoToJson(map), SaldoBodegaTO[].class));
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public List<InvTransferenciaMotivoComboTO> getListaTransferenciasMotivoComboTO(final String empresa,
			final boolean filtrarInactivos) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("empresa", empresa);
			map.put("filtrarInactivos", filtrarInactivos);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return Arrays.asList(restTemplate.postForObject(conexionWS + "/getListaTransferenciaMotivoComboTO",
					UtilsJSON.objetoToJson(map), InvTransferenciaMotivoComboTO[].class));
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public List<InvVentaMotivoComboTO> getListaVentaMotivoComboTO(final String empresa,
			final Boolean filtrarInactivos) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("empresa", empresa);
			map.put("filtrarInactivos", filtrarInactivos);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return Arrays.asList(restTemplate.postForObject(conexionWS + "/getListaVentaMotivoComboTO",
					UtilsJSON.objetoToJson(map), InvVentaMotivoComboTO[].class, map));
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public Boolean getPrecioFijoCategoriaProducto(final String empresa, final String codigoCategoria) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("empresa", empresa);
			map.put("codigoCategoria", codigoCategoria);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return restTemplate.postForObject(conexionWS + "/getPrecioFijoCategoriaProducto",
					UtilsJSON.objetoToJson(map), Boolean.class);
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public BigDecimal getPrecioProductoPorCantidad(final String empresa, final String codProducto,
			final BigDecimal cantidad) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("empresa", empresa);
			map.put("codProducto", codProducto);
			map.put("cantidad", cantidad);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return restTemplate.postForObject(conexionWS + "/getPrecioProductoPorCantidad", UtilsJSON.objetoToJson(map),
					BigDecimal.class);
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public InvProducto getProducto(final String empresa, final String codigo) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("empresa", empresa);
			map.put("codigo", codigo);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return restTemplate.postForObject(conexionWS + "/getProducto", UtilsJSON.objetoToJson(map),
					InvProducto.class, map);
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public List<InvProductoTO> getProductoTO(final String empresa, final String codigo) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("empresa", empresa);
			map.put("codigo", codigo);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return Arrays.asList(restTemplate.postForObject(conexionWS + "/getProductoTO", UtilsJSON.objetoToJson(map),
					InvProductoTO[].class));
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public List<InvProveedorTO> getProveedorTO(final String empresa, final String codigo) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("empresa", empresa);
			map.put("codigo", codigo);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return Arrays.asList(restTemplate.postForObject(conexionWS + "/getProveedorTO", UtilsJSON.objetoToJson(map),
					InvProveedorTO[].class));
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public Boolean getPuedeEliminarProducto(final String empresa, final String producto) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("empresa", empresa);
			map.put("producto", producto);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return restTemplate.postForObject(conexionWS + "/getPuedeEliminarProducto", UtilsJSON.objetoToJson(map),
					Boolean.class);
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public Object[] getVenta(final String empresa, final String perCodigo, final String motCodigo,
			final String compNumero) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("empresa", empresa);
			map.put("perCodigo", perCodigo);
			map.put("motCodigo", motCodigo);
			map.put("compNumero", compNumero);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return restTemplate.postForObject(conexionWS + "/getVenta", UtilsJSON.objetoToJson(map), Object[].class);
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public String insertarBodegaTO(final InvBodegaTO invBodegaTO) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("invBodegaTO", invBodegaTO);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return restTemplate.postForObject(conexionWS + "/insertarBodegaTO", UtilsJSON.objetoToJson(map),
					String.class);
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public String insertarClienteTO(final InvClienteTO invClienteTO) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("invClienteTO", invClienteTO);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return restTemplate.postForObject(conexionWS + "/insertarClienteTO", UtilsJSON.objetoToJson(map),
					String.class);
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public String insertarInvComprasMotivoTO(final InvComprasMotivoTO invCompraMotivoTO) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("invCompraMotivoTO", invCompraMotivoTO);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return restTemplate.postForObject(conexionWS + "/insertarInvComprasMotivoTO", UtilsJSON.objetoToJson(map),
					String.class);
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public MensajeTO insertarInvComprasTO(final InvComprasTO invComprasTO,
			final List<InvComprasDetalleTO> listaInvComprasDetalleTO, final AnxCompraTO anxCompraTO,
			final List<AnxCompraDetalleTO> anxCompraDetalleTO, final List<AnxCompraReembolsoTO> anxCompraReembolsoTO,
			final List<AnxCompraFormaPagoTO> anxCompraFormaPagoTO) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("invComprasTO", invComprasTO);
			map.put("listaInvComprasDetalleTO", listaInvComprasDetalleTO);
			map.put("anxCompraTO", anxCompraTO);
			map.put("anxCompraDetalleTO", anxCompraDetalleTO);
			map.put("anxCompraReembolsoTO", anxCompraReembolsoTO);
			map.put("anxCompraFormaPagoTO", anxCompraFormaPagoTO);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return restTemplate.postForObject(conexionWS + "/insertarInvComprasTO", UtilsJSON.objetoToJson(map),
					MensajeTO.class);
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public MensajeTO insertarInvConsumosTO(final InvConsumosTO invConsumosTO,
			final List<InvConsumosDetalleTO> listaInvConsumosDetalleTO) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("invConsumosTO", invConsumosTO);
			map.put("listaInvConsumosDetalleTO", listaInvConsumosDetalleTO);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return restTemplate.postForObject(conexionWS + "/insertarInvConsumosTO", UtilsJSON.objetoToJson(map),
					MensajeTO.class);
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public MensajeTO insertarInvContableComprasTO(final String empresa, final String periodo, final String motivo,
			final String compraNumero, final String codigoUsuario, final boolean recontabilizar, final String conNumero,
			final boolean recontabilizarSinPendiente, final String tipCodigo) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("empresa", empresa);
			map.put("periodo", periodo);
			map.put("motivo", motivo);
			map.put("compraNumero", compraNumero);
			map.put("codigoUsuario", codigoUsuario);
			map.put("recontabilizar", recontabilizar);
			map.put("conNumero", conNumero);
			map.put("recontabilizarSinPendiente", recontabilizarSinPendiente);
			map.put("tipCodigo", tipCodigo);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return restTemplate.postForObject(conexionWS + "/insertarInvContableComprasTO", UtilsJSON.objetoToJson(map),
					MensajeTO.class);
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public MensajeTO insertarInvContableVentasTO(final String empresa, final String periodo, final String motivo,
			final String ventaNumero, final String codigoUsuario, final boolean recontabilizar, final String conNumero,
			final String tipCodigo) {
		// MensajeTO mensajeTO1 = new MensajeTO();
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("empresa", empresa);
			map.put("periodo", periodo);
			map.put("motivo", motivo);
			map.put("ventaNumero", ventaNumero);
			map.put("codigoUsuario", codigoUsuario);
			map.put("recontabilizar", recontabilizar);
			map.put("conNumero", conNumero);
			map.put("tipCodigo", tipCodigo);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return restTemplate.postForObject(conexionWS + "/insertarInvContableVentasTO", UtilsJSON.objetoToJson(map),
					MensajeTO.class);
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public String insertarInvProformaMotivoTO(final InvProformaMotivoTO invProformaMotivoTO) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("invProformaMotivoTO", invProformaMotivoTO);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return restTemplate.postForObject(conexionWS + "/insertarInvProformaMotivoTO", UtilsJSON.objetoToJson(map),
					String.class);
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public MensajeTO insertarInvProformasTO(final InvProformasTO invProformasTO,
			final List<InvProformasDetalleTO> listaInvProformasDetalleTO) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("invProformasTO", invProformasTO);
			map.put("listaInvProformasDetalleTO", listaInvProformasDetalleTO);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return restTemplate.postForObject(conexionWS + "/insertarInvProformasTO", UtilsJSON.objetoToJson(map),
					MensajeTO.class);
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public MensajeTO insertarInvTransferenciaTO(final InvTransferenciasTO invTransferenciasTO,
			final List<InvTransferenciasDetalleTO> listaInvTransferenciasDetalleTO) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("invTransferenciasTO", invTransferenciasTO);
			map.put("listaInvTransferenciasDetalleTO", listaInvTransferenciasDetalleTO);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return restTemplate.postForObject(conexionWS + "/insertarInvTransferenciaTO", UtilsJSON.objetoToJson(map),
					MensajeTO.class);
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public String insertarInvVentasMotivoTO(final InvVentaMotivoTO invVentaMotivoTO) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("invVentaMotivoTO", invVentaMotivoTO);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return restTemplate.postForObject(conexionWS + "/insertarInvVentasMotivoTO", UtilsJSON.objetoToJson(map),
					String.class);
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public MensajeTO insertarInvVentasTO(final InvVentasTO invVentasTO,
			final List<InvVentasDetalleTO> listaInvVentasDetalleTO, final AnxVentaTO anxVentasTO,
			final String tipoDocumentoComplemento, final String numeroDocumentoComplemento,
			final Boolean isComprobanteElectronica) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("invVentasTO", invVentasTO);
			map.put("listaInvVentasDetalleTO", listaInvVentasDetalleTO);
			map.put("anxVentasTO", anxVentasTO);
			map.put("tipoDocumentoComplemento", tipoDocumentoComplemento);
			map.put("numeroDocumentoComplemento", numeroDocumentoComplemento);
			map.put("isComprobanteElectronica", isComprobanteElectronica);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return restTemplate.postForObject(conexionWS + "/insertarInvVentasTO", UtilsJSON.objetoToJson(map),
					MensajeTO.class);
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public String insertarModificarComprasRecepcionTO(final InvComprasRecepcionTO invComprasRecepcionTO) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("invComprasRecepcionTO", invComprasRecepcionTO);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return restTemplate.postForObject(conexionWS + "/insertarModificarComprasRecepcionTO",
					UtilsJSON.objetoToJson(map), String.class);
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public MensajeTO insertarModificarInvConsumos(final InvConsumos invConsumos,
			final List<InvConsumosDetalle> listaInvConsumosDetalle) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("invConsumos", invConsumos);
			map.put("listaInvConsumosDetalle", listaInvConsumosDetalle);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return restTemplate.postForObject(conexionWS + "/insertarModificarInvConsumos", UtilsJSON.objetoToJson(map),
					MensajeTO.class);
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public String insertarProductoTO(final InvProductoTO invProductoTO) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("invProductoTO", invProductoTO);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());

			return restTemplate.postForObject(conexionWS + "/insertarProductoTO", UtilsJSON.objetoToJson(map),
					String.class);
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public String insertarProveedorTO(final InvProveedorTO invProveedorTO) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("invProveedorTO", invProveedorTO);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return restTemplate.postForObject(conexionWS + "/insertarProveedorTO", UtilsJSON.objetoToJson(map),
					String.class);
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public MensajeTO invCambiarPrecioCantidadProducto(final String empresa, final String usuario,
			final List<InvListaProductosCambiarPrecioCantidadTO> invListaProductosCambiarPrecioCantidadTOs) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("empresa", empresa);
			map.put("usuario", usuario);
			map.put("invListaProductosCambiarPrecioCantidadTOs", invListaProductosCambiarPrecioCantidadTOs);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return restTemplate.postForObject(conexionWS + "/invCambiarPrecioCantidadProducto",
					UtilsJSON.objetoToJson(map), MensajeTO.class);
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public MensajeTO invCambiarPrecioProducto(final String empresa, final String usuario,
			final List<InvListaProductosCambiarPrecioTO> invListaProductosCambiarPrecioTOs) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("empresa", empresa);
			map.put("usuario", usuario);
			map.put("invListaProductosCambiarPrecioTOs", invListaProductosCambiarPrecioTOs);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return restTemplate.postForObject(conexionWS + "/invCambiarPrecioProducto", UtilsJSON.objetoToJson(map),
					MensajeTO.class);
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public List<InvListadoCobrosTO> invListadoCobrosTO(final String empresa, final String periodo, final String motivo,
			final String numero) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("empresa", empresa);
			map.put("periodo", periodo);
			map.put("motivo", motivo);
			map.put("numero", numero);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return Arrays.asList(restTemplate.postForObject(conexionWS + "/invListadoCobrosTO",
					UtilsJSON.objetoToJson(map), InvListadoCobrosTO[].class));
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public List<InvListadoPagosTO> invListadoPagosTO(final String empresa, final String periodo, final String motivo,
			final String numero) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("empresa", empresa);
			map.put("periodo", periodo);
			map.put("motivo", motivo);
			map.put("numero", numero);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return Arrays.asList(restTemplate.postForObject(conexionWS + "/invListadoPagosTO",
					UtilsJSON.objetoToJson(map), InvListadoPagosTO[].class));
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public List<InvProductoSincronizarTO> invProductoSincronizar(final String empresaOrigen,
			final String empresaDestino, final String usuario) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("empresaOrigen", empresaOrigen);
			map.put("empresaDestino", empresaDestino);
			map.put("usuario", usuario);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return Arrays.asList(restTemplate.postForObject(conexionWS + "/invProductoSincronizar",
					UtilsJSON.objetoToJson(map), InvProductoSincronizarTO[].class));
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public String modificarBodegaTO(final InvBodegaTO invBodegaTO) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("invBodegaTO", invBodegaTO);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return restTemplate.postForObject(conexionWS + "/modificarBodegaTO", UtilsJSON.objetoToJson(map),
					String.class);
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public String modificarClienteTO(final InvClienteTO invClienteTO, final String codigoAnterior) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("invClienteTO", invClienteTO);
			map.put("codigoAnterior", codigoAnterior);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return restTemplate.postForObject(conexionWS + "/modificarClienteTO", UtilsJSON.objetoToJson(map),
					String.class);
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public String modificarInvComprasMotivoTO(final InvComprasMotivoTO invCompraMotivoTO) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("invCompraMotivoTO", invCompraMotivoTO);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return restTemplate.postForObject(conexionWS + "/modificarInvComprasMotivoTO", UtilsJSON.objetoToJson(map),
					String.class);
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public MensajeTO modificarInvComprasTO(final InvComprasTO invComprasTO,
			final List<InvComprasDetalleTO> listaInvComprasDetalleTO,
			final List<InvComprasDetalleTO> listaInvComprasEliminarDetalleTO, final AnxCompraTO anxCompraTO,
			final List<AnxCompraDetalleTO> anxCompraDetalleTO,
			final List<AnxCompraDetalleTO> anxCompraDetalleEliminarTO,
			final List<AnxCompraReembolsoTO> anxCompraReembolsoTO,
			final List<AnxCompraReembolsoTO> anxCompraReembolsoEliminarTO,
			final List<AnxCompraFormaPagoTO> anxCompraFormaPagoTO,
			final List<AnxCompraFormaPagoTO> anxCompraFormaPagoEliminarTO, final boolean desmayorizar,
			final boolean quitarAnulado, final InvComprasMotivoAnulacion invComprasMotivoAnulacion) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("invComprasTO", invComprasTO);
			map.put("listaInvComprasDetalleTO", listaInvComprasDetalleTO);
			map.put("listaInvComprasEliminarDetalleTO", listaInvComprasEliminarDetalleTO);
			map.put("anxCompraTO", anxCompraTO);
			map.put("anxCompraDetalleTO", anxCompraDetalleTO);
			map.put("anxCompraDetalleEliminarTO", anxCompraDetalleEliminarTO);
			map.put("anxCompraReembolsoTO", anxCompraReembolsoTO);
			map.put("anxCompraReembolsoEliminarTO", anxCompraReembolsoEliminarTO);
			map.put("anxCompraFormaPagoTO", anxCompraFormaPagoTO);
			map.put("anxCompraFormaPagoEliminarTO", anxCompraFormaPagoEliminarTO);
			map.put("desmayorizar", desmayorizar);
			map.put("quitarAnulado", quitarAnulado);
			map.put("invComprasMotivoAnulacion", invComprasMotivoAnulacion);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return restTemplate.postForObject(conexionWS + "/modificarInvComprasTO", UtilsJSON.objetoToJson(map),
					MensajeTO.class);
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public MensajeTO modificarInvConsumosTO(final InvConsumosTO invConsumosTO,
			final List<InvConsumosDetalleTO> listaInvConsumosDetalleTO,
			final List<InvConsumosDetalleTO> listaInvConsumosEliminarDetalleTO, final boolean desmayorizar,
			final InvConsumosMotivoAnulacion invConsumosMotivoAnulacion) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("invConsumosTO", invConsumosTO);
			map.put("listaInvConsumosDetalleTO", listaInvConsumosDetalleTO);
			map.put("listaInvConsumosEliminarDetalleTO", listaInvConsumosEliminarDetalleTO);
			map.put("desmayorizar", desmayorizar);
			map.put("invConsumosMotivoAnulacion", invConsumosMotivoAnulacion);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return restTemplate.postForObject(conexionWS + "/modificarInvConsumosTO", UtilsJSON.objetoToJson(map),
					MensajeTO.class);
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	/////////////////////////// Metodos WEB ///////////////////////////

	public String modificarInvProformaMotivoTO(final InvProformaMotivoTO invProformaMotivoTO) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("invProformaMotivoTO", invProformaMotivoTO);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return restTemplate.postForObject(conexionWS + "/modificarInvProformaMotivoTO", UtilsJSON.objetoToJson(map),
					String.class);
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public MensajeTO modificarInvProformasTO(final InvProformasTO invProformasTO,
			final List<InvProformasDetalleTO> listaInvProformasDetalleTO,
			final List<InvProformasDetalleTO> listaInvProfromasEliminarDetalleTO, final boolean quitarAnulado) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("invProformasTO", invProformasTO);
			map.put("listaInvProformasDetalleTO", listaInvProformasDetalleTO);
			map.put("listaInvProfromasEliminarDetalleTO", listaInvProfromasEliminarDetalleTO);
			map.put("quitarAnulado", quitarAnulado);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return restTemplate.postForObject(conexionWS + "/modificarInvProformasTO", UtilsJSON.objetoToJson(map),
					MensajeTO.class);
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public MensajeTO modificarInvTransferenciasTO(final InvTransferenciasTO invTransferenciasTO,
			final List<InvTransferenciasDetalleTO> listaInvTransferenciasDetalleTO,
			final List<InvTransferenciasDetalleTO> listaInvTransferenciasEliminarDetalleTO, final boolean desmayorizar,
			final InvTransferenciasMotivoAnulacion invTransferenciasMotivoAnulacion) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("invTransferenciasTO", invTransferenciasTO);
			map.put("listaInvTransferenciasDetalleTO", listaInvTransferenciasDetalleTO);
			map.put("listaInvTransferenciasEliminarDetalleTO", listaInvTransferenciasEliminarDetalleTO);
			map.put("desmayorizar", desmayorizar);
			map.put("invTransferenciasMotivoAnulacion", invTransferenciasMotivoAnulacion);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return restTemplate.postForObject(conexionWS + "/modificarInvTransferenciasTO", UtilsJSON.objetoToJson(map),
					MensajeTO.class);
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public String modificarInvVentasMotivoTO(final InvVentaMotivoTO invVentaMotivoTO) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("invVentaMotivoTO", invVentaMotivoTO);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return restTemplate.postForObject(conexionWS + "/modificarInvVentasMotivoTO", UtilsJSON.objetoToJson(map),
					String.class);
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public MensajeTO modificarInvVentasTO(final InvVentasTO invVentasTO,
			final List<InvVentasDetalleTO> listaInvVentasDetalleTO,
			final List<InvVentasDetalleTO> listaInvVentasEliminarDetalleTO, final boolean desmayorizar,
			final AnxVentaTO anxVentasTO, final boolean quitarAnulado, final String tipoDocumentoComplemento,
			final String numeroDocumentoComplemento, final InvVentasMotivoAnulacion invVentasMotivoAnulacion) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("invVentasTO", invVentasTO);
			map.put("listaInvVentasDetalleTO", listaInvVentasDetalleTO);
			map.put("listaInvVentasEliminarDetalleTO", listaInvVentasEliminarDetalleTO);
			map.put("desmayorizar", desmayorizar);
			map.put("anxVentasTO", anxVentasTO);
			map.put("quitarAnulado", quitarAnulado);
			map.put("tipoDocumentoComplemento", tipoDocumentoComplemento);
			map.put("numeroDocumentoComplemento", numeroDocumentoComplemento);
			map.put("invVentasMotivoAnulacion", invVentasMotivoAnulacion);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return restTemplate.postForObject(conexionWS + "/modificarInvVentasTO", UtilsJSON.objetoToJson(map),
					MensajeTO.class);
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public String modificarProductoTO(final InvProductoTO invProductoTO, final String codigoCambiarLlave) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("invProductoTO", invProductoTO);
			map.put("codigoCambiarLlave", codigoCambiarLlave);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return restTemplate.postForObject(conexionWS + "/modificarProductoTO", UtilsJSON.objetoToJson(map),
					String.class);
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public String modificarProveedorTO(final InvProveedorTO invProveedorTO, final String codigoAnterior) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("invProveedorTO", invProveedorTO);
			map.put("codigoAnterior", codigoAnterior);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return restTemplate.postForObject(conexionWS + "/modificarProveedorTO", UtilsJSON.objetoToJson(map),
					String.class);
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public InvCliente obtenerInvClientePorCedulaRuc(final String empresa, final String cedulaRuc) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("empresa", empresa);
			map.put("cedulaRuc", cedulaRuc);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return restTemplate.postForObject(conexionWS + "/obtenerInvClientePorCedulaRuc",
					UtilsJSON.objetoToJson(map), InvCliente.class, map);
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public InvProveedor obtenerInvProveedorPorCedulaRuc(final String empresa, final String ruc) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("empresa", empresa);
			map.put("ruc", ruc);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return restTemplate.postForObject(conexionWS + "/obtenerInvProveedorPorCedulaRuc",
					UtilsJSON.objetoToJson(map), InvProveedor.class, map);
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public boolean productoRepetidoCodigoBarra(final String empresa, final String barras) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("empresa", empresa);
			map.put("barras", barras);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return restTemplate.postForObject(conexionWS + "/productoRepetidoCodigoBarra", UtilsJSON.objetoToJson(map),
					boolean.class);
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return false;
	}

	public String restaurarConsumo(final String empresa, final String periodo, final String motivo,
			final String numero) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("empresa", empresa);
			map.put("periodo", periodo);
			map.put("motivo", motivo);
			map.put("numero", numero);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return restTemplate.postForObject(conexionWS + "/restaurarConsumo", UtilsJSON.objetoToJson(map),
					String.class);
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public MensajeTO validarInvContableComprasDetalleTO(final String empresa, final String periodo, final String motivo,
			final String compraNumero) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("empresa", empresa);
			map.put("periodo", periodo);
			map.put("motivo", motivo);
			map.put("compraNumero", compraNumero);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return restTemplate.postForObject(conexionWS + "/validarInvContableComprasDetalleTO",
					UtilsJSON.objetoToJson(map), MensajeTO.class);
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public List<InvMenuComida> getMenuComidaTO(String empresa) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("empresa", empresa);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return new ArrayList<>(Arrays.asList(restTemplate.postForObject(conexionWS + "/getMenuComidaTO",
					UtilsJSON.objetoToJson(map), InvMenuComida[].class)));
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public MensajeTO insertarMenuComida(final InvMenuComida invMenuComida) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("invMenuComida", invMenuComida);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return restTemplate.postForObject(conexionWS + "/insertarMenuComida", UtilsJSON.objetoToJson(map),
					MensajeTO.class);
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public String modificarMenuComida(final InvMenuComida invMenuComida) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("invMenuComida", invMenuComida);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return restTemplate.postForObject(conexionWS + "/modificarMenuComida", UtilsJSON.objetoToJson(map),
					String.class);
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public String eliminarMenuComida(final InvMenuComida invMenuComida) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("invMenuComida", invMenuComida);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return restTemplate.postForObject(conexionWS + "/eliminarMenuComida", UtilsJSON.objetoToJson(map),
					String.class);
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

}
