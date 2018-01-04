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

import ec.com.orocodigo.OroSoftDesktop.util.FormatoDocumentoCorrecto;
import ec.com.orocodigo.OroSoftUtils.MensajeTO;
import ec.com.orocodigo.OroSoftUtils.UtilsDate;
import ec.com.orocodigo.OroSoftUtils.UtilsValidacion;
import ec.com.orocodigo.OroSoftUtils.anexos.TO.AnxNumeracionLineaTO;
import ec.com.orocodigo.OroSoftUtils.anexos.TO.AnxTipoComprobanteComboTO;
import ec.com.orocodigo.OroSoftUtils.inventario.TO.InvFunListadoProductosTO;
import ec.com.orocodigo.OroSoftUtils.inventario.TO.InvProductoCategoriaTO;
import ec.com.orocodigo.OroSoftUtils.inventario.TO.InvVentasDetalleTO;
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
				final AnxTipoComprobanteComboTO anxTipoComprobanteComboTO = anexosBBTipoComprobanteCombo1
						.getListaAnxTipoComprobanteComboTO().get(jcboTipoDocumento.getSelectedIndex());
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
			} else if (UtilsDate.fechaFormatoDate(fechaVencimiento, "dd-MM-yyyy").getTime() < UtilsDate
					.fechaFormatoDate(jftfFechaEmision.getText(), "dd-MM-yyyy").getTime()) {
				UtilAplication.presentaMensaje(FacesMessage.SEVERITY_INFO,
						"La Fecha de Vencimiento no puede ser menor que la Fecha de Emisión");
			} else if (!FormatoDocumentoCorrecto.DocumentoCorrecto(jftfNumeroDocumento.getText().toString())) {
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
				if (anxNumeracionLineaTO != null) {
					if (calculosGeneralesVentas.filasUnidadBulto() <= anxNumeracionLineaTO.getNumeroLineas())
						puedeSeguir = true;
					else
						puedeSeguir = false;
				}
				if (!"18".equals("00"))
					try {
						anxNumeracionLineaTO = AnexosDelegate.getInstance().getNumeroAutorizacion(empresa,
								numeroFactura == null ? "" : numeroFactura, "18",
								"'" + LocalDate.now().toString() + "'");
					} catch (final Exception e) {
						UtilsExcepciones.enviarError(e, getClass().getName());
					}
				puedeSeguir = true;
				if (anxNumeracionLineaTO != null)
					if (calculosGeneralesVentas.filasUnidadBulto() <= anxNumeracionLineaTO.getNumeroLineas())
						puedeSeguir = true;
					else
						puedeSeguir = false;

				if (!puedeSeguir) {
					UtilAplication.presentaMensaje(FacesMessage.SEVERITY_INFO,
							"El número de líneas es mayor al número de productos ingresados. Corrija el error.");
					jtblDetalle.requestFocus();
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

}
