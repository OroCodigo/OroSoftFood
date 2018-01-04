package ec.com.orocodigo.OroSoftWeb.controller.inventario;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import ec.com.orocodigo.OroSoftUtils.MensajeTO;
import ec.com.orocodigo.OroSoftUtils.UtilsAplicacion;
import ec.com.orocodigo.OroSoftUtils.UtilsValidacion;
import ec.com.orocodigo.OroSoftUtils.anexos.TO.AnxNumeracionLineaTO;
import ec.com.orocodigo.OroSoftUtils.inventario.TO.InvFunListadoProductosTO;
import ec.com.orocodigo.OroSoftUtils.inventario.TO.InvProductoCategoriaTO;
import ec.com.orocodigo.OroSoftUtils.inventario.TO.InvVentasDetalleTO;
import ec.com.orocodigo.OroSoftUtils.inventario.TO.InvVentasTO;
import ec.com.orocodigo.OroSoftUtils.inventario.entity.InvMenuComida;
import ec.com.orocodigo.OroSoftUtils.sistema.entity.SisEmpresa;
import ec.com.orocodigo.OroSoftUtils.sri.util.TipoComprobanteEnum;
import ec.com.orocodigo.OroSoftWeb.TO.NumeracionTipoDocumentoVenta;
import ec.com.orocodigo.OroSoftWeb.TO.TotalesFacturaTO;
import ec.com.orocodigo.OroSoftWeb.delegate.AnexosDelegate;
import ec.com.orocodigo.OroSoftWeb.delegate.InventarioDelegate;
import ec.com.orocodigo.OroSoftWeb.delegate.SistemaDelegate;
import ec.com.orocodigo.OroSoftWeb.utils.UtilAplication;
import ec.com.orocodigo.OroSoftWeb.utils.UtilsExcepciones;
import ec.com.orocodigo.OroSoftWeb.utils.UtilsMensaje;

@Controller
@Scope("session")
public class VentaRestaurantBean {
	private List<SisEmpresa> listEmpresas;
	private String empresa;
	private BigDecimal montoRecibido;
	private BigDecimal montoCambio;

	private String numeroFactura;
	private String numeroAuxiliarFactura;
	private String accion;
	private String numeroAutorizacion;

	private List<InvMenuComida> listInvMenuComida;

	private List<InvProductoCategoriaTO> listInvProductoCategoriaTO;

	List<InvFunListadoProductosTO> listaProductos;
	private String categoria;

	private List<InvVentasDetalleTO> listaInvVentasDetalleTO;
	private InvVentasDetalleTO invVentasDetalleTO;

	private TotalesFacturaTO totalesFacturaTO;
	private BigDecimal ivaVigente;
	private BigDecimal montoMaximoConsumidorFinal;
	private String rucCedula;

	private boolean isComprobanteElectronica;
	private boolean isNumeroAmbienteProduccion;

	private AnxNumeracionLineaTO anxNumeracionLineaTO;

	private final BigDecimal CERO = new BigDecimal("0.00");
	private List<NumeracionTipoDocumentoVenta> listaNumeracionTipoDocumentoVenta = new ArrayList();

	private InvVentasTO invVentasTO;
	private List<String> lista;

	public VentaRestaurantBean() {
	}

	@PostConstruct
	public void init() {
		limpiar();
	}

	public void limpiar() {
		listEmpresas = SistemaDelegate.getInstance().getEmpresasPorUsuarioItem();
		empresa = null;

		listInvMenuComida = InventarioDelegate.getInstance().getMenuComidaTO("ORC");
		listaInvVentasDetalleTO = new ArrayList<InvVentasDetalleTO>();
		invVentasDetalleTO = new InvVentasDetalleTO();

		totalesFacturaTO = new TotalesFacturaTO();

		accion = "";

		if (numeroFactura != null)
			try {
				// nombreReporte = anxTipoComprobanteComboTO.getTcRutaReporte();
				listaNumeracionTipoDocumentoVenta = ultimoSecuencial("18");

				if (!isComprobanteElectronica) {
					anxNumeracionLineaTO = AnexosDelegate.getInstance().getNumeroAutorizacion(empresa,
							numeroFactura == null ? "" : numeroFactura.toString().toString(), "18",
							"'" + LocalDate.now().toString() + "'");

					if (anxNumeracionLineaTO == null) {
						numeroAutorizacion = "";
						numeroFactura = "";
					} else
						numeroAutorizacion = anxNumeracionLineaTO.getNumeroAutorizacion().trim();

				} else {
					numeroAutorizacion = "";
					anxNumeracionLineaTO = new AnxNumeracionLineaTO();
					anxNumeracionLineaTO.setFormatoPOS(false);
					anxNumeracionLineaTO.setNumeroAutorizacion(numeroAutorizacion);
					anxNumeracionLineaTO.setNumeroLineas(999999);
				}

			} catch (final Exception e) {
				UtilsExcepciones.enviarError(e, getClass().getName());
			}
	}

	public void buscarCategorias(String codigoMenuComida) {
		listInvProductoCategoriaTO = InventarioDelegate.getInstance().getInvProductoCategoria("ORC", codigoMenuComida);
		listaProductos = new ArrayList<InvFunListadoProductosTO>();
	}

	public void buscarProductos(String codigoCategoria) {
		categoria = codigoCategoria;
		UtilAplication.presentaMensaje(FacesMessage.SEVERITY_INFO, categoria);
		listaProductos = InventarioDelegate.getInstance().getInvFunListadoProductosTO("ORC", categoria, null);
	}

	public void agregarProducto(InvFunListadoProductosTO producto) {
		invVentasDetalleTO = new InvVentasDetalleTO();
		invVentasDetalleTO.setVtIva(false);
		if (producto.getPrdIva().compareTo("GRAVA") == 0)
			invVentasDetalleTO.setVtIva(true);
		invVentasDetalleTO.setBodCodigo("10");
		invVentasDetalleTO.setBodEmpresa("ORC");
		invVentasDetalleTO.setDetCantidad(new BigDecimal("1.00"));
		invVentasDetalleTO.setDetPrecio(producto.getPrdPrecio1());
		invVentasDetalleTO.setProCodigoPrincipal(producto.getPrdCodigoPrincipal());
		invVentasDetalleTO.setProEmpresa("ORC");
		invVentasDetalleTO.setProNombre(producto.getPrdNombre());
		invVentasDetalleTO.setSecEmpresa("ORC");
		invVentasDetalleTO.setSecCodigo("ORC");
		listaInvVentasDetalleTO.add(invVentasDetalleTO);
		calcularTotales(listaInvVentasDetalleTO);
	}

	public void eliminarFila(InvVentasDetalleTO ivd) {
		if (listaInvVentasDetalleTO.size() > 1) {
			int idx = listaInvVentasDetalleTO.indexOf(ivd);
			listaInvVentasDetalleTO.remove(idx);
			if (idx >= listaInvVentasDetalleTO.size())
				idx = listaInvVentasDetalleTO.size() - 1;
		}
		calcularTotales(listaInvVentasDetalleTO);
	}

	public void calcularTotales(List<InvVentasDetalleTO> listaInvVentasDetalleTO) {
		totalesFacturaTO.setSubtotal_12(new BigDecimal("0.00"));
		totalesFacturaTO.setSubtotal_0(new BigDecimal("0.00"));
		totalesFacturaTO.setSubtotal(new BigDecimal("0.00"));
		totalesFacturaTO.setIva(new BigDecimal("0.00"));
		totalesFacturaTO.setTotal(new BigDecimal("0.00"));

		for (InvVentasDetalleTO ivdto : listaInvVentasDetalleTO) {
			if (ivdto.getVtIva())
				totalesFacturaTO.setSubtotal_12(totalesFacturaTO.getSubtotal_12().add(ivdto.getDetPrecio()));
			else
				totalesFacturaTO.setSubtotal_0(totalesFacturaTO.getSubtotal_0().add(ivdto.getDetPrecio()));
		}
		totalesFacturaTO.setSubtotal(totalesFacturaTO.getSubtotal_0().add(totalesFacturaTO.getSubtotal_12()));
		totalesFacturaTO.setIva(totalesFacturaTO.getSubtotal_12().multiply(new BigDecimal("0.12")));
		totalesFacturaTO.setTotal(UtilsValidacion.redondeoDecimalBigDecimal(
				totalesFacturaTO.getSubtotal().add(totalesFacturaTO.getIva()), 2, RoundingMode.HALF_UP));

	}

	public void calcularCambio() {
		montoCambio = montoRecibido.subtract(totalesFacturaTO.getTotal());
	}

	public void guardar() {
		List<NumeracionTipoDocumentoVenta> listaNumeracionTipoDocumentoVentaAux = new ArrayList();
		boolean puedeContinuar = false;
		try {

			final String codigoFactura = InventarioDelegate.getInstance().getConteoNumeroFacturaVenta(empresa, "18",
					numeroFactura);
			if (codigoFactura == null)
				puedeContinuar = true;
			else {
				listaNumeracionTipoDocumentoVentaAux = ultimoSecuencial("18");
				numeroFactura(listaNumeracionTipoDocumentoVentaAux, "AUX_NUMERO_FACTURA");
				if (numeroAuxiliarFactura.equals(numeroFactura))
					puedeContinuar = true;
				else {
					UtilAplication.presentaMensaje(FacesMessage.SEVERITY_INFO,
							"El numero de la Venta a Cambiado Se Guardará la venta con el Nº  "
									+ numeroAuxiliarFactura);
					puedeContinuar = true;
					numeroFactura = numeroAuxiliarFactura;
				}
			}
		} catch (final Exception e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		if (puedeContinuar) {
			accion = "NUEVO";
			if (!validaMontoMaximoConsumidorFinal()) {
				UtilAplication.presentaMensaje(FacesMessage.SEVERITY_INFO,
						"Se necesita un Número de Identificacion valido, CÉDULA O RUC.");
			} else if (numeroAutorizacion.trim().compareToIgnoreCase("CADUCADO") == 0) {
				UtilAplication.presentaMensaje(FacesMessage.SEVERITY_INFO,
						"El Número de Documento que ingreso esta CADUCADO.");
			} else if (numeroAutorizacion.trim().compareToIgnoreCase("ANULADO") == 0) {
				UtilAplication.presentaMensaje(FacesMessage.SEVERITY_INFO,
						"El Número de Documento que ingreso esta ANULADO.");
			} else if (!documentoCorrecto(numeroFactura)) {
				UtilAplication.presentaMensaje(FacesMessage.SEVERITY_INFO,
						"Número de documento mal ingresado\nIngrese con este formato: 001-001-000000001");
			} else {
				boolean verificarComplemento = false;
				boolean puedeSeguir = false;
				String mensajeComplemento = "";
				if ("18".equals("04") || "18".equals("05")) {
					if (numeroFactura.equals("999-999-999999999")) {
						verificarComplemento = false;
						anxNumeracionLineaTO = null;
					} else
						verificarComplemento = true;
				} else
					anxNumeracionLineaTO = null;
				puedeSeguir = true;
				if (!"18".equals("00"))
					try {
						anxNumeracionLineaTO = AnexosDelegate.getInstance().getNumeroAutorizacion(empresa,
								numeroFactura == null ? "" : numeroFactura, "18",
								"'" + LocalDate.now().toString() + "'");
					} catch (final Exception e) {
						UtilsExcepciones.enviarError(e, getClass().getName());
					}
				puedeSeguir = true;
				// if (anxNumeracionLineaTO != null)
				// if (calculosGeneralesVentas.filasUnidadBulto() <=
				// anxNumeracionLineaTO.getNumeroLineas())
				// puedeSeguir = true;
				// else
				// puedeSeguir = false;

				if (!puedeSeguir) {
					UtilAplication.presentaMensaje(FacesMessage.SEVERITY_INFO,
							"El número de líneas es mayor al número de productos ingresados. Corrija el error.");
				} else if (codigoVentaMotivo.trim().isEmpty() || jtfCliente.getText().trim().isEmpty()
						|| codigoTipoComprobante.trim().isEmpty()) {
					UtilAplication.presentaMensaje(FacesMessage.SEVERITY_INFO,
							"Faltan ingresar algunos datos. Intente de nuevo.");
				} else {
					String mensaje = "";
					MensajeTO mensajeTO = new MensajeTO();
					llenarObjetoTO();
					if (llenarDetalleTO()) {
						buscarSectorValorMaximo();
						llenarObjetoTO();
						try {
							if (anxVentaTO != null) {
								if (anxVentaTO.getVenBase0().compareTo(invVentasTO.getVtaSubtotalBase0()) == 0
										&& anxVentaTO.getVenBaseImponible()
												.compareTo(invVentasTO.getVtaSubtotalBaseImponible()) == 0) {
									if ((sisUsuarioEmpresaTO.getEmpActividad().equals("COMERCIAL")
											|| sisUsuarioEmpresaTO.getEmpActividad().equals("COMISARIATO"))
											&& !invVentasTO.getVtaFormaPago().equals("POR PAGAR")) {
										formularioFormaDePagoCambio();
										if (valorRetornado != null)
											if (valorRetornado.compareTo(cero) > 0) {
												if (accion.equals("NUEVO")) {
													invVentasTO.setVtaPagadoEfectivo(valorEfectivo);
													invVentasTO.setVtaPagadoDineroElectronico(valorDineroElectronico);
													invVentasTO.setVtaPagadoOtro(valorOtros);
													invVentasTO.setVtaPagadoTarjetaCredito(valorTarjetaCredito);
													mensajeTO = InventarioDelegate.getInstance().insertarInvVentasTO(
															invVentasTO, listaInvVentasDetalleTO, anxVentaTO,
															codigoTipoComprobanteComplemento,
															jftfNumeroComplemento.getText(), isComprobanteElectronica);
													mensaje = mensajeTO.getMensaje().substring(0,
															mensajeTO.getMensaje().lastIndexOf("</html>") + 7);
												}
												if (mensaje.charAt(0) == 'T')
													mostrarMensajes(mensaje, mensajeTO);
												else if (mensaje.charAt(0) == 'M') {
													UtilsMensaje.showDialogError(mensajeTO.getMensaje().substring(1));
													limpiar();
												} else {
													if (mensaje.equals("CONFIGURAR")) {
														UtilAplication.presentaMensaje(FacesMessage.SEVERITY_INFO,
																"Falta Configurar Parametros Para Generarar Comprobantes Electronicos");
														mensajeValidacionControles = new ArrayList();
													} else {
														if (mensajeTO.getListaErrores1().isEmpty())
															UtilsMensaje.showDialogError(
																	mensajeTO.getMensaje().substring(1));
														else
															UtilsMensaje.showDialogError(
																	mensajeTO.getMensaje().substring(1),
																	mensajeTO.getListaErrores1());
														if (mensajeTO.getMensaje().equals(
																"F<html>El Número de Documento que ingresó ya existe...\nIntente de nuevo o contacte con el administrador</html>")) {
															actualizarSecuencias(codigoTipoComprobante,
																	jftfNumeroDocumento.getValue() == null ? ""
																			: jftfNumeroDocumento.getValue()
																					.toString());
															escogerTipoDocumento();
														}
													}
													jtfCliente.requestFocus();
												}
											} else
												UtilsMensaje.showDialogInformation(
														"Para guardar la Venta, necesita ingresar el VALOR DEL CAMBIO.");

									} else {
										formularioFormaDePagoCambio();
										if (valorRetornado != null)
											if (valorRetornado.compareTo(cero) > 0) {
												if (accion.equals("NUEVO")) {
													invVentasTO.setVtaPagadoEfectivo(valorEfectivo);
													invVentasTO.setVtaPagadoDineroElectronico(valorDineroElectronico);
													invVentasTO.setVtaPagadoOtro(valorOtros);
													invVentasTO.setVtaPagadoTarjetaCredito(valorTarjetaCredito);
													mensajeTO = InventarioDelegate.getInstance().insertarInvVentasTO(
															invVentasTO, listaInvVentasDetalleTO, anxVentaTO,
															codigoTipoComprobanteComplemento,
															jftfNumeroComplemento.getText(), isComprobanteElectronica);
													mensaje = mensajeTO.getMensaje().substring(0,
															mensajeTO.getMensaje().lastIndexOf("</html>") + 7);
												}

												if (mensaje.charAt(0) == 'T')
													mostrarMensajes(mensaje, mensajeTO);
												else if (mensaje.charAt(0) == 'M') {
													UtilsMensaje.showDialogError(mensajeTO.getMensaje().substring(1));
													limpiar();
												} else {
													if (mensaje.equals("CONFIGURAR")) {
														UtilAplication.presentaMensaje(FacesMessage.SEVERITY_INFO,
																"Falta Configurar Parametros Para Generarar Comprobantes Electronicos");
													} else {
														if (mensajeTO.getListaErrores1().isEmpty())
															UtilAplication.presentaMensaje(FacesMessage.SEVERITY_INFO,
																	mensajeTO.getMensaje().substring(1));
														else
															UtilAplication.presentaMensaje(FacesMessage.SEVERITY_INFO,
																	mensajeTO.getMensaje().substring(1)
																			+ mensajeTO.getListaErrores1());
														if (mensajeTO.getMensaje().equals(
																"F<html>El Número de Documento que ingresó ya existe...\nIntente de nuevo o contacte con el administrador</html>")) {
															actualizarSecuencias("18",
																	numeroFactura == null ? "" : numeroFactura);
														}
													}
												}
											} else
												UtilAplication.presentaMensaje(FacesMessage.SEVERITY_INFO,
														"Para guardar la Venta, necesita ingresar el VALOR DEL CAMBIO.");

									}
								} else {
									UtilAplication.presentaMensaje(FacesMessage.SEVERITY_INFO,
											"Los valores en la Venta son diferentes a la de la Retención.\nActualice la información");
								}
							} else if ((sisUsuarioEmpresaTO.getEmpActividad().equals("COMERCIAL")
									|| sisUsuarioEmpresaTO.getEmpActividad().equals("COMISARIATO"))
									&& !invVentasTO.getVtaFormaPago().equals("POR PAGAR")) {
								formularioFormaDePagoCambio();
								if (valorRetornado != null)
									if (valorRetornado.compareTo(cero) > 0) {
										if (accion.equals("NUEVO")) {
											invVentasTO.setVtaPagadoDineroElectronico(valorDineroElectronico);
											invVentasTO.setVtaPagadoEfectivo(valorEfectivo);
											invVentasTO.setVtaPagadoOtro(valorOtros);
											invVentasTO.setVtaPagadoTarjetaCredito(valorTarjetaCredito);

											mensajeTO = InventarioDelegate.getInstance().insertarInvVentasTO(
													invVentasTO, listaInvVentasDetalleTO, anxVentaTO,
													codigoTipoComprobanteComplemento, jftfNumeroComplemento.getText(),
													isComprobanteElectronica);
											mensaje = mensajeTO.getMensaje().substring(0,
													mensajeTO.getMensaje().lastIndexOf("</html>") + 7);

										}
										if (mensaje.charAt(0) == 'T')
											mostrarMensajes(mensaje, mensajeTO);
										else if (mensaje.charAt(0) == 'M') {
											UtilsMensaje.showDialogError(mensajeTO.getMensaje().substring(1));
											limpiar();
										} else {
											if (mensaje.equals("CONFIGURAR")) {
												UtilAplication.presentaMensaje(FacesMessage.SEVERITY_INFO,
														"Falta Configurar Parametros Para Generarar Comprobantes Electronicos");
											}
											if (mensaje.equals("Error"))
												UtilAplication.presentaMensaje(FacesMessage.SEVERITY_INFO,
														"no se puede guardar Web service En mantenimiento ");
											else {
												if (mensajeTO.getListaErrores1().isEmpty())
													UtilAplication.presentaMensaje(FacesMessage.SEVERITY_INFO,
															mensajeTO.getMensaje().substring(1));
												else
													UtilAplication.presentaMensaje(FacesMessage.SEVERITY_INFO,
															mensajeTO.getMensaje().substring(1),
															mensajeTO.getListaErrores1());
												if (mensajeTO.getMensaje().equals(
														"F<html>El Número de Documento que ingresó ya existe...\nIntente de nuevo o contacte con el administrador</html>")) {
													actualizarSecuencias(codigoTipoComprobante,
															jftfNumeroDocumento.getValue() == null ? ""
																	: jftfNumeroDocumento.getValue().toString());
													escogerTipoDocumento();
												}
											}
											jtfCliente.requestFocus();
										}
									} else
										UtilsMensaje.showDialogInformation(
												"Para guardar la Venta, necesita ingresar el VALOR DEL CAMBIO.");
							} else {

								formularioFormaDePagoCambio();

								if (valorRetornado != null)
									if (valorRetornado.compareTo(cero) > 0) {
										if (accion.equals("NUEVO")) {
											invVentasTO.setVtaPagadoDineroElectronico(valorDineroElectronico);
											invVentasTO.setVtaPagadoEfectivo(valorEfectivo);
											invVentasTO.setVtaPagadoOtro(valorOtros);
											invVentasTO.setVtaPagadoTarjetaCredito(valorTarjetaCredito);
											mensajeTO = InventarioDelegate.getInstance().insertarInvVentasTO(
													invVentasTO, listaInvVentasDetalleTO, anxVentaTO,
													codigoTipoComprobanteComplemento, jftfNumeroComplemento.getText(),
													isComprobanteElectronica);
											mensaje = mensajeTO.getMensaje().substring(0,
													mensajeTO.getMensaje().lastIndexOf("</html>") + 7);

										}
										if (mensaje.charAt(0) == 'T')
											mostrarMensajes(mensaje, mensajeTO);
										else if (mensaje.charAt(0) == 'M') {
											UtilsMensaje.showDialogError(mensajeTO.getMensaje().substring(1));
											limpiar();
										} else {
											if (mensaje.equals("CONFIGURAR")) {
												UtilsMensaje.showDialogInformation(
														"Faltan ingresar algunos datos...\nIntente de nuevo.");
												mensajeValidacionControles = new ArrayList();
											} else {
												if (mensajeTO.getListaErrores1().isEmpty())
													UtilsMensaje.showDialogError(mensajeTO.getMensaje().substring(1));
												else
													UtilsMensaje.showDialogError(mensajeTO.getMensaje().substring(1),
															mensajeTO.getListaErrores1());
												if (mensajeTO.getMensaje().equals(
														"F<html>El Número de Documento que ingresó ya existe...\nIntente de nuevo o contacte con el administrador</html>")) {
													actualizarSecuencias(codigoTipoComprobante,
															jftfNumeroDocumento.getValue() == null ? ""
																	: jftfNumeroDocumento.getValue().toString());
													escogerTipoDocumento();
												}
											}
											jtfCliente.requestFocus();
										}
									} else
										UtilsMensaje.showDialogInformation(
												"Para guardar la Venta, necesita ingresar el VALOR DEL CAMBIO.");

							}
						} catch (final Exception e) {
							UtilsExcepciones.enviarError(e, getClass().getName());
							requestFocus();
						}
					}
				}
			}
		}
	}

	private List<NumeracionTipoDocumentoVenta> ultimoSecuencial(final String codigoTipoDocumento) {
		final List<NumeracionTipoDocumentoVenta> listaNumeracionTipoDocumentoVentaAux = new ArrayList();
		try {
			if (sisUsuarioEmpresaTO.getPerSecuencialPermitido() != null) {
				ultimoSecuencial = AnexosDelegate.getInstance().getUltimaNumeracionComprobante(
						sisUsuarioEmpresaTO.getEmpCodigo(), codigoTipoDocumento,
						sisUsuarioEmpresaTO.getPerSecuencialPermitido());
				if (ultimoSecuencial == null)
					ultimoSecuencial = sisUsuarioEmpresaTO.getPerSecuencialPermitido() + "-000000000";
				if (ultimoSecuencial != null && !"".equals(ultimoSecuencial)) {
					final String separado = ultimoSecuencial.substring(0, ultimoSecuencial.lastIndexOf("-") + 1);
					final int numero = Integer
							.parseInt(ultimoSecuencial.substring(ultimoSecuencial.lastIndexOf("-") + 2));
					listaNumeracionTipoDocumentoVentaAux
							.add(new NumeracionTipoDocumentoVenta(codigoTipoDocumento, separado, numero));
				}
			}
		} catch (final Exception e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
			requestFocus();
		}

		return listaNumeracionTipoDocumentoVentaAux;
	}

	private boolean numeroFactura(final List<NumeracionTipoDocumentoVenta> listaNumeracionTipoDocumentoVenta,
			final String boolenVisualizaNumeroFactura) {
		boolean crear = false;
		for (int i = 0; i < listaNumeracionTipoDocumentoVenta.size(); i++)
			if (listaNumeracionTipoDocumentoVenta.get(i).getCodigoTipoDocumento().equals("18")) {
				if (listaNumeracionTipoDocumentoVenta.get(i).getSecuenciaNumeroFactura() != null) {
					listaNumeracionTipoDocumentoVenta.set(i,
							new NumeracionTipoDocumentoVenta(
									listaNumeracionTipoDocumentoVenta.get(i).getCodigoTipoDocumento(),
									listaNumeracionTipoDocumentoVenta.get(i).getParteEstatica(),
									listaNumeracionTipoDocumentoVenta.get(i).getSecuenciaNumeroFactura()));

					final String numero = String
							.valueOf(listaNumeracionTipoDocumentoVenta.get(i).getSecuenciaNumeroFactura() + 1);
					final int numeroCerosAponer = 9 - numero.length();
					String rellenarConCeros = "";
					if (numeroCerosAponer != 0)
						for (int j = 0; j < numeroCerosAponer; j++)
							rellenarConCeros = rellenarConCeros + "0";
					if (boolenVisualizaNumeroFactura.equals("AUX_NUMERO_FACTURA"))
						numeroAuxiliarFactura = listaNumeracionTipoDocumentoVenta.get(i).getParteEstatica()
								+ rellenarConCeros + numero;
					else
						numeroAuxiliarFactura = listaNumeracionTipoDocumentoVenta.get(i).getParteEstatica()
								+ rellenarConCeros + numero;
				}
				i = listaNumeracionTipoDocumentoVenta.size();
				crear = false;
			} else
				crear = true;
		return crear;
	}

	private boolean validaMontoMaximoConsumidorFinal() {
		boolean ban = false;
		if (!(totalesFacturaTO.getTotal().compareTo(montoMaximoConsumidorFinal) > -1 && !"18".equals("00")
				&& (rucCedula == null || rucCedula.trim().equals("9999999999999"))))
			ban = true;
		return ban;
	}

	private void verificarComprobanteElectronico(final String codigotipoDocumento) {
		try {
			String ultimoSecuencial = numeroFactura;

			if (codigotipoDocumento.equals("18")
					|| codigotipoDocumento.equals(TipoComprobanteEnum.NOTA_DE_DEBITO.getCode())
					|| codigotipoDocumento.equals(TipoComprobanteEnum.NOTA_DE_CREDITO.getCode())) {

				final AnxNumeracionLineaTO anxNumeracionLineaTO = AnexosDelegate.getInstance().getNumeroAutorizacion(
						empresa, ultimoSecuencial, codigotipoDocumento,
						"'" + UtilsValidacion.fecha(new Date().toString(), "dd-MM-yyyy", "yyyy-MM-dd") + "'");

				if (anxNumeracionLineaTO != null) {

					isNumeroAmbienteProduccion = anxNumeracionLineaTO.isNumeroAmbienteProduccion();
					isComprobanteElectronica = anxNumeracionLineaTO.isNumeroDocumentoElectronico();
				} else {

					isComprobanteElectronica = false;
					isNumeroAmbienteProduccion = false;
				}

				if (isComprobanteElectronica) {
					setTitle(titulo + " Electronicas (Ambiente "
							+ (isNumeroAmbienteProduccion ? "Produccción )" : "Prueba )"));
					sisPckeystoreTO = SistemaDelegate.getInstance().getSisPckeystoreTO();
					if (sisPckeystoreTO != null) {
						setTitle(titulo + " Electronicas (Ambiente"
								+ (isComprobanteElectronica ? "Produccción)" : "Prueba)"));
						urlWebServicesTO = AnexosDelegate.getInstance().getAnxUrlWebServicesTO();
					}
				} else if ("18".equals(sisUsuarioEmpresaTO.getPerDocumentoPermitido())
						&& sisUsuarioEmpresaTO.getPerSecuencialPermitido() != null) {
					ultimoSecuencial = AnexosDelegate.getInstance().getUltimaNumeracionComprobante(
							sisUsuarioEmpresaTO.getEmpCodigo(),
							"" + sisUsuarioEmpresaTO.getPerDocumentoPermitido() + "",
							sisUsuarioEmpresaTO.getPerSecuencialPermitido());
					if (ultimoSecuencial != null) {
						final String separado = ultimoSecuencial.substring(0, ultimoSecuencial.lastIndexOf("-") + 1);
						final int numero = Integer
								.parseInt(ultimoSecuencial.substring(ultimoSecuencial.lastIndexOf("-") + 2));
						listaNumeracionTipoDocumentoVenta.add(new NumeracionTipoDocumentoVenta(
								sisUsuarioEmpresaTO.getPerDocumentoPermitido(), separado, numero));
					}
				}
			}
		} catch (final Exception e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
			requestFocus();
		}
	}

	private void obtenerIva(String fecha) {
		try {
			fecha = fecha == null ? null : UtilsValidacion.fecha(fecha, "dd-MM-yyyy", "yyyy-MM-dd");
			ivaVigente = new BigDecimal("0.00");
			ivaVigente = AnexosDelegate.getInstance().getValorAnxPorcentajeIvaTO(fecha);
			ivaVigente = ivaVigente == null ? CERO : ivaVigente;
			totalesFacturaTO.setIvaLabel("IVA " + ivaVigente + "%:");
			montoMaximoConsumidorFinal = AnexosDelegate.getInstance().getValorAnxMontoMaximoConsumidorFinalTO(fecha);
		} catch (final Exception e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
	}

	private void actualizarSecuencias(final String tipoDocumento, final String numeroDocumento) {
		try {
			if (!"00".equals("18"))
				if (listaNumeracionTipoDocumentoVenta.size() > 0) {
					boolean crear = false;
					for (int i = 0; i < listaNumeracionTipoDocumentoVenta.size(); i++)
						if (listaNumeracionTipoDocumentoVenta.get(i).getCodigoTipoDocumento().equals(tipoDocumento)) {
							final String separado = numeroDocumento.trim().substring(0,
									numeroDocumento.trim().lastIndexOf("-") + 1);
							final int numero = Integer.parseInt(
									numeroDocumento.trim().substring(numeroDocumento.trim().lastIndexOf("-") + 2));
							listaNumeracionTipoDocumentoVenta.set(i,
									new NumeracionTipoDocumentoVenta(
											listaNumeracionTipoDocumentoVenta.get(i).getCodigoTipoDocumento(), separado,
											numero));
							i = listaNumeracionTipoDocumentoVenta.size();
							crear = false;
						} else
							crear = true;
					if (crear) {
						final String separado = numeroDocumento.trim().substring(0,
								numeroDocumento.trim().lastIndexOf("-") + 1);
						final int numero = Integer.parseInt(
								numeroDocumento.trim().substring(numeroDocumento.trim().lastIndexOf("-") + 2));
						listaNumeracionTipoDocumentoVenta
								.add(new NumeracionTipoDocumentoVenta(tipoDocumento, separado, numero));
					}
				} else {
					final String separado = numeroDocumento.trim().substring(0,
							numeroDocumento.trim().lastIndexOf("-") + 1);
					final int numero = Integer
							.parseInt(numeroDocumento.trim().substring(numeroDocumento.trim().lastIndexOf("-") + 2));
					listaNumeracionTipoDocumentoVenta
							.add(new NumeracionTipoDocumentoVenta(tipoDocumento, separado, numero));
				}
		} catch (final Exception e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
	}

	private void llenarObjetoTO() {
		invVentasTO = new InvVentasTO();
		invVentasTO.setVtaEmpresa(empresa);
		invVentasTO.setVtaPeriodo(LocalDate.now().toString());
		invVentasTO.setVtaMotivo("001");
		invVentasTO.setVtaNumero("numeroVenta");
		invVentasTO.setVtaNumeroAlterno("0000000");
		invVentasTO.setVtaDocumentoTipo("18");
		invVentasTO.setVtaDocumentoNumero(numeroFactura);
		invVentasTO.setVtaFecha(LocalDate.now().toString());
		invVentasTO.setVtaFechaVencimiento(LocalDate.now().toString());

		invVentasTO.setVtaIvaVigente(ivaVigente);
		invVentasTO.setVtaObservaciones("");
		// Informacion adicional facturacion electronica
		// llenarInformacionAdicional();
		invVentasTO.setVtaInformacionAdicional("");
		invVentasTO.setVtaPendiente(false);
		invVentasTO.setVtaRevisado(false);
		invVentasTO.setVtaAnulado(false);
		lista = UtilsValidacion.separar(
				tipoFormaPago.trim().equals("POR PAGAR") ? " | " + tipoFormaPago.trim() : tipoFormaPago.trim(), "|");
		invVentasTO.setVtaFormaPago(lista.isEmpty() ? "" : lista.get(1));

		invVentasTO.setVtaBase0(parcialCeroTotal);/// <<--jtfParcialCero
		invVentasTO.setVtaBaseImponible(parcialImponibleTotal);// <<--jtfParcialImponible
		invVentasTO.setVtaBaseNoObjeto(cero);//
		invVentasTO.setVtaBaseExenta(cero);//

		invVentasTO.setVtaRecargoBase0(recargoBaseCero);
		invVentasTO.setVtaRecargoBaseImponible(recargoBaseImponible);
		invVentasTO.setVtaDescuentoBase0(descuentoBaseCero);
		invVentasTO.setVtaDescuentoBaseImponible(descuentoBaseImponible);
		invVentasTO.setVtaDescuentoBaseNoObjeto(cero);
		invVentasTO.setVtaDescuentoBaseExenta(cero);

		invVentasTO.setVtaSubtotalBase0(subtotalBaseCero);// <<---jtfSubtotalCero
		invVentasTO.setVtaSubtotalBaseImponible(subtotalBaseImponible);// <<---
																		// jtfSubtotalImponible
		invVentasTO.setVtaSubtotalBaseExenta(cero);// <<--- jtfSubtotalImponible
		invVentasTO.setVtaSubtotalBaseNoObjeto(cero);// <<---
														// jtfSubtotalImponible
		invVentasTO.setVtaMontoIva(ivaTotal);// <<-- jtfIva

		invVentasTO.setVtaTotal(jtfTotal.getText().trim().isEmpty() ? cero : new BigDecimal(jtfTotal.getText()));
		invVentasTO.setCliEmpresa(invVentasTO.getVtaEmpresa());
		invVentasTO.setCliCodigo(jtfCliente.getText().trim());
		invVentasTO.setSecEmpresa(invVentasTO.getVtaEmpresa());
		invVentasTO.setSecCodigo(codigoSector);
		invVentasTO.setConEmpresa(sisUsuarioEmpresaTO.getEmpCodigo());
		invVentasTO.setConPeriodo(null);
		invVentasTO.setConTipo(null);
		invVentasTO.setConNumero(null);
		invVentasTO.setVendEmpresa(sisUsuarioEmpresaTO.getEmpCodigo());
		invVentasTO.setVendCodigo(codigoVendedor);

		invVentasTO.setUsrEmpresa(invVentasTO.getVtaEmpresa());
		invVentasTO.setUsrCodigo(sisUsuarioEmpresaTO.getUsrCodigo());
	}

	public static boolean documentoCorrecto(final String documento) {

		System.out.println(documento);

		boolean primeroComp = false;
		boolean segundoComp = false;
		boolean terceroComp = false;

		final int primero = Integer.parseInt(documento.substring(0, 3).equals("___") ? "0" : documento.substring(0, 3));
		final int segundo = Integer.parseInt(documento.substring(4, 7).equals("___") ? "0" : documento.substring(4, 7));
		final int tercero = Integer.parseInt(documento.substring(8).equals("_________") ? "0" : documento.substring(8));

		if (primero > 0) {
			primeroComp = true;
			if (segundo > 0) {
				segundoComp = true;
				if (tercero > 0)
					terceroComp = true;
			}
		}

		System.out.println(primeroComp);
		System.out.println(segundoComp);
		System.out.println(terceroComp);

		return primeroComp && segundoComp && terceroComp;
	}

	private boolean llenarDetalleTO() {
		boolean comprobarDetalle = false;
		listaInvVentasDetalleTO = new ArrayList();

		for (int i = 0; i < jtblDetalle.getRowCount(); i++)
			if (new BigDecimal(jtblDetalle.getValueAt(i, buscarColumna("Total")).toString())
					.compareTo(UtilsAplicacion.cero) > 0) {
				if (new BigDecimal(jtblDetalle.getValueAt(i, buscarColumna("Precio")).toString())
						.compareTo(UtilsAplicacion.cero) > 0) {
					final InvVentasDetalleTO invVentasDetalleTO = new InvVentasDetalleTO();

					invVentasDetalleTO.setDetSecuencia(0);
					invVentasDetalleTO.setDetOrden(i + 1);
					invVentasDetalleTO.setDetPendiente(buscarColumna("Pendiente") == -1 ? false
							: jtblDetalle.getValueAt(i, buscarColumna("Pendiente")).toString().trim().isEmpty() ? false
									: Boolean
											.valueOf(jtblDetalle.getValueAt(i, buscarColumna("Pendiente")).toString()));
					invVentasDetalleTO.setDetCantidad(
							new BigDecimal(jtblDetalle.getValueAt(i, buscarColumna("Cantidad")).toString()));
					invVentasDetalleTO.setDetPrecio(
							new BigDecimal(jtblDetalle.getValueAt(i, buscarColumna("Precio")).toString()));
					invVentasDetalleTO.setDetPorcentajeRecargo(new BigDecimal(
							jtblDetalle.getValueAt(i, buscarColumna("Recargo")).toString().isEmpty() ? "0"
									: jtblDetalle.getValueAt(i, buscarColumna("Recargo")).toString()));
					invVentasDetalleTO.setDetPorcentajeDescuento(new BigDecimal(
							jtblDetalle.getValueAt(i, buscarColumna("Descuento")).toString().isEmpty() ? "0"
									: jtblDetalle.getValueAt(i, buscarColumna("Descuento")).toString()));
					invVentasDetalleTO.setBodEmpresa(invVentasTO.getVtaEmpresa());
					invVentasDetalleTO.setProEmpresa(invVentasTO.getVtaEmpresa());
					invVentasDetalleTO
							.setProCodigoPrincipal(jtblDetalle.getValueAt(i, buscarColumna("Producto")).toString());
					invVentasDetalleTO
							.setProNombre(jtblDetalle.getValueAt(i, buscarColumna("Nombre")).toString().trim());
					invVentasDetalleTO.setSecEmpresa(invVentasTO.getVtaEmpresa());

					if (!sisUsuarioEmpresaTO.getEmpActividad().equals("COMERCIAL")
							&& !sisUsuarioEmpresaTO.getEmpActividad().equals("COMISARIATO")) {
						invVentasDetalleTO.setBodCodigo(jtblDetalle.getValueAt(i, buscarColumna("Bodega")).toString());
						invVentasDetalleTO.setSecCodigo(jtblDetalle.getValueAt(i, buscarColumna("CP")).toString());
						invVentasDetalleTO
								.setPisNumero(!jtblDetalle.getValueAt(i, buscarColumna("CC")).toString().isEmpty()
										? jtblDetalle.getValueAt(i, buscarColumna("CC")).toString() : null);
						invVentasDetalleTO.setPisSector(jtblDetalle.getValueAt(i, buscarColumna("CP")).toString());
					} else {
						invVentasDetalleTO.setBodCodigo(Bodega);
						invVentasDetalleTO.setSecCodigo(CP);
						invVentasDetalleTO.setPisSector(CP);
						invVentasDetalleTO.setPisNumero(null);

					}

					invVentasDetalleTO.setPisEmpresa(invVentasTO.getVtaEmpresa());

					invVentasDetalleTO.setVtaEmpresa(invVentasTO.getVtaEmpresa());
					invVentasDetalleTO.setVtaPeriodo(invVentasTO.getVtaPeriodo());
					invVentasDetalleTO.setVtaMotivo(invVentasTO.getVtaMotivo());
					invVentasDetalleTO.setVtaNumero(invVentasTO.getVtaNumero());

					invVentasDetalleTO
							.setProEstadoIva(jtblDetalle.getValueAt(i, buscarColumna("EstadoIVA")).toString());
					invVentasDetalleTO.setProTipo(jtblDetalle.getValueAt(i, buscarColumna("ProductoTipo")).toString());

					/* campos para el xml de la Faxctura Electronica */
					invVentasDetalleTO
							.setVtIva(Boolean.valueOf(jtblDetalle.getValueAt(i, buscarColumna("IVA?")).toString()));
					invVentasDetalleTO.setVtCodigoPorcentaje(
							Boolean.valueOf(jtblDetalle.getValueAt(i, buscarColumna("IVA?")).toString()) ? "2" : "0");
					invVentasDetalleTO.setProMedida(jtblDetalle.getValueAt(i, buscarColumna("Medida")).toString());

					////////////////////////////////////////////////
					listaInvVentasDetalleTO.add(invVentasDetalleTO);
					comprobarDetalle = true;
				} else if (UtilsMensaje.showOptionDialogQuestion(
						"El precio del producto " + jtblDetalle.getValueAt(i, buscarColumna("Nombre")).toString()
								+ " de la línea " + (i + 1) + " tiene un valor de 0 (cero).\nDesea continuar?")) {
					final InvVentasDetalleTO invVentasDetalleTO = new InvVentasDetalleTO();
					invVentasDetalleTO.setDetSecuencia(0);
					invVentasDetalleTO.setDetOrden(i + 1);
					invVentasDetalleTO.setDetPendiente(buscarColumna("Pendiente") == -1 ? false
							: jtblDetalle.getValueAt(i, buscarColumna("Pendiente")).toString().trim().isEmpty() ? false
									: Boolean
											.valueOf(jtblDetalle.getValueAt(i, buscarColumna("Pendiente")).toString()));
					invVentasDetalleTO.setDetCantidad(
							new BigDecimal(jtblDetalle.getValueAt(i, buscarColumna("Cantidad")).toString()));
					invVentasDetalleTO.setDetPrecio(
							new BigDecimal(jtblDetalle.getValueAt(i, buscarColumna("Precio")).toString()));
					invVentasDetalleTO.setDetPorcentajeRecargo(new BigDecimal(
							jtblDetalle.getValueAt(i, buscarColumna("Recargo")).toString().isEmpty() ? "0"
									: jtblDetalle.getValueAt(i, buscarColumna("Recargo")).toString()));
					invVentasDetalleTO.setDetPorcentajeDescuento(new BigDecimal(
							jtblDetalle.getValueAt(i, buscarColumna("Descuento")).toString().isEmpty() ? "0"
									: jtblDetalle.getValueAt(i, buscarColumna("Descuento")).toString()));
					invVentasDetalleTO.setBodEmpresa(invVentasTO.getVtaEmpresa());
					if (!sisUsuarioEmpresaTO.getEmpActividad().equals("COMERCIAL")
							&& !sisUsuarioEmpresaTO.getEmpActividad().equals("COMISARIATO")) {
						invVentasDetalleTO.setBodCodigo(jtblDetalle.getValueAt(i, buscarColumna("Bodega")).toString());
						invVentasDetalleTO.setSecCodigo(jtblDetalle.getValueAt(i, buscarColumna("CP")).toString());
						invVentasDetalleTO.setPisSector(jtblDetalle.getValueAt(i, buscarColumna("CP")).toString());
						invVentasDetalleTO
								.setPisNumero(!jtblDetalle.getValueAt(i, buscarColumna("CC")).toString().isEmpty()
										? jtblDetalle.getValueAt(i, buscarColumna("CC")).toString() : null);

					} else {
						invVentasDetalleTO.setBodCodigo(Bodega);
						invVentasDetalleTO.setSecCodigo(CP);
						invVentasDetalleTO.setPisSector(CP);
						invVentasDetalleTO.setPisNumero(null);
					}
					invVentasDetalleTO.setProEmpresa(invVentasTO.getVtaEmpresa());
					invVentasDetalleTO
							.setProCodigoPrincipal(jtblDetalle.getValueAt(i, buscarColumna("Producto")).toString());
					invVentasDetalleTO
							.setProNombre(jtblDetalle.getValueAt(i, buscarColumna("Nombre")).toString().trim());
					invVentasDetalleTO.setSecEmpresa(invVentasTO.getVtaEmpresa());
					invVentasDetalleTO.setPisEmpresa(invVentasTO.getVtaEmpresa());

					invVentasDetalleTO.setVtaEmpresa(invVentasTO.getVtaEmpresa());
					invVentasDetalleTO.setVtaPeriodo(invVentasTO.getVtaPeriodo());
					invVentasDetalleTO.setVtaMotivo(invVentasTO.getVtaMotivo());
					invVentasDetalleTO.setVtaNumero(invVentasTO.getVtaNumero());
					/* campor para el xml de la Faxctura Electronica */
					invVentasDetalleTO.setProMedida(jtblDetalle.getValueAt(i, buscarColumna("Medida")).toString());
					invVentasDetalleTO
							.setVtIva(Boolean.valueOf(jtblDetalle.getValueAt(i, buscarColumna("IVA?")).toString()));
					invVentasDetalleTO.setVtCodigoPorcentaje(
							Boolean.valueOf(jtblDetalle.getValueAt(i, buscarColumna("IVA?")).toString()) ? "2" : "0");
					invVentasDetalleTO
							.setProEstadoIva(jtblDetalle.getValueAt(i, buscarColumna("EstadoIVA")).toString());
					invVentasDetalleTO.setProTipo(jtblDetalle.getValueAt(i, buscarColumna("ProductoTipo")).toString());
					listaInvVentasDetalleTO.add(invVentasDetalleTO);
					comprobarDetalle = true;
				} else {
					i = jtblDetalle.getRowCount();
					jtblDetalle.requestFocus();
					jtblDetalle.changeSelection(0, 0, false, false);
					comprobarDetalle = false;
				}
			} else {
				UtilsMensaje.showDialogInformation("Uno de los subtotales de la línea " + (i + 1)
						+ " tiene valor de cero (0). Revise la información.");
				i = jtblDetalle.getRowCount();
				jtblDetalle.requestFocus();
				jtblDetalle.changeSelection(0, 0, false, false);
				comprobarDetalle = false;
			}
	}else

	{
		i = jtblDetalle.getRowCount();
		UtilsMensaje.showDialogInformation("Detalle mal ingresado. Revise la información");
		jtblDetalle.requestFocus();
		jtblDetalle.changeSelection(0, 0, false, false);
		comprobarDetalle = false;
	}return comprobarDetalle;
	}

	// GETTERS Y SETTERS

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

	public List<InvFunListadoProductosTO> getListaProductos() {
		return listaProductos;
	}

	public void setListaProductos(List<InvFunListadoProductosTO> listaProductos) {
		this.listaProductos = listaProductos;
	}

	public List<InvMenuComida> getListInvMenuComida() {
		return listInvMenuComida;
	}

	public void setListInvMenuComida(List<InvMenuComida> listInvMenuComida) {
		this.listInvMenuComida = listInvMenuComida;
	}

	public List<InvProductoCategoriaTO> getListInvProductoCategoriaTO() {
		return listInvProductoCategoriaTO;
	}

	public void setListInvProductoCategoriaTO(List<InvProductoCategoriaTO> listInvProductoCategoriaTO) {
		this.listInvProductoCategoriaTO = listInvProductoCategoriaTO;
	}

	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}

	public List<InvVentasDetalleTO> getListaInvVentasDetalleTO() {
		return listaInvVentasDetalleTO;
	}

	public void setListaInvVentasDetalleTO(List<InvVentasDetalleTO> listaInvVentasDetalleTO) {
		this.listaInvVentasDetalleTO = listaInvVentasDetalleTO;
	}

	public InvVentasDetalleTO getInvVentasDetalleTO() {
		return invVentasDetalleTO;
	}

	public void setInvVentasDetalleTO(InvVentasDetalleTO invVentasDetalleTO) {
		this.invVentasDetalleTO = invVentasDetalleTO;
	}

	public TotalesFacturaTO getTotalesFacturaTO() {
		return totalesFacturaTO;
	}

	public void setTotalesFacturaTO(TotalesFacturaTO totalesFacturaTO) {
		this.totalesFacturaTO = totalesFacturaTO;
	}

	public BigDecimal getMontoRecibido() {
		return montoRecibido;
	}

	public void setMontoRecibido(BigDecimal montoRecibido) {
		this.montoRecibido = montoRecibido;
	}

	public BigDecimal getMontoCambio() {
		return montoCambio;
	}

	public void setMontoCambio(BigDecimal montoCambio) {
		this.montoCambio = montoCambio;
	}

	public String getNumeroFactura() {
		return numeroFactura;
	}

	public void setNumeroFactura(String numeroFactura) {
		this.numeroFactura = numeroFactura;
	}

	public String getNumeroAuxiliarFactura() {
		return numeroAuxiliarFactura;
	}

	public void setNumeroAuxiliarFactura(String numeroAuxiliarFactura) {
		this.numeroAuxiliarFactura = numeroAuxiliarFactura;
	}

	public InvVentasTO getInvVentasTO() {
		return invVentasTO;
	}

	public void setInvVentasTO(InvVentasTO invVentasTO) {
		this.invVentasTO = invVentasTO;
	}

	public List<String> getLista() {
		return lista;
	}

	public void setLista(List<String> lista) {
		this.lista = lista;
	}

}
