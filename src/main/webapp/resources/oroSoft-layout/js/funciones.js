PrimeFaces.locales['es'] = {
	closeText : 'Cerrar',
	prevText : 'Anterior',
	nextText : 'Siguiente',
	monthNames : [ 'Enero', 'Febrero', 'Marzo', 'Abril', 'Mayo', 'Junio',
			'Julio', 'Agosto', 'Septiembre', 'Octubre', 'Noviembre',
			'Diciembre' ],
	monthNamesShort : [ 'Ene', 'Feb', 'Mar', 'Abr', 'May', 'Jun', 'Jul', 'Ago',
			'Sep', 'Oct', 'Nov', 'Dic' ],
	dayNames : [ 'Domingo', 'Lunes', 'Martes', 'Miércoles', 'Jueves',
			'Viernes', 'Sábado' ],
	dayNamesShort : [ 'Dom', 'Lun', 'Mar', 'Mie', 'Jue', 'Vie', 'Sab' ],
	dayNamesMin : [ 'D', 'L', 'M', 'X', 'J', 'V', 'S' ],
	weekHeader : 'Semana',
	firstDay : 1,
	isRTL : false,
	showMonthAfterYear : false,
	yearSuffix : '',
	timeOnlyTitle : 'Sólo hora',
	timeText : 'Tiempo',
	hourText : 'Hora',
	minuteText : 'Minuto',
	secondText : 'Segundo',
	currentText : 'Fecha actual',
	ampm : false,
	month : 'Mes',
	week : 'Semana',
	day : 'Día',
	allDayText : 'Todo el día'
};

function estiloFila(id) {
	var vec = id.split(':');
	var fila = obtenerNumero(vec);
	var tblData = vec.slice(0, vec.length - 2).join(':') + '_data';
	document.getElementById(tblData).getElementsByTagName('tr')[fila].classList
			.add("class", 'ui-state-highlight');
}

function sinEstiloFila(id) {
	var vec = id.split(':');
	var fila = obtenerNumero(vec);
	var tblData = vec.slice(0, vec.length - 2).join(':') + '_data';
	document.getElementById(tblData).getElementsByTagName('tr')[fila].classList
			.remove('ui-state-highlight');
}

function obtenerNumero(vec) {
	for (i = 0; i < vec.length; i++) {
		var a = Number.parseInt(vec[i]);
		if (a.toString() != 'NaN') {
			return a;
		}
	}
	return -1;
}

function teclaAbajoArriba(keyCode, id) {
	var vec = id.split(':');
	var tabla = vec.slice(0, vec.length - 2).join(':');
	var idComponenteUltimo = vec[vec.length - 1];
	var p = parseInt(vec[vec.length - 2]);
	var n = document.getElementById(tabla + '_data').childNodes.length;
	if (keyCode == 38) {
		if (p === 0)
			p = n - 1;
		else
			p = p - 1;
		document.getElementById(tabla + ':' + p + ':' + idComponenteUltimo)
				.focus();
	} else if (keyCode == 40) {
		if ((p + 1) == n)
			p = 0;
		else
			p = p + 1;

		document.getElementById(tabla + ':' + p + ':' + idComponenteUltimo)
				.focus();
	} else if (keyCode == 115) {
		document.getElementById(tabla + ':' + p + ':btnEliminar').click();
	} else if (keyCode == 113) {
		document.getElementById(tabla + ':' + p + ':btnAgregarArriba').click();
	}
	return false;
}

function comprobarMostrarDialogo(xhr, status, args, id) {
	if (args.abrir) {
		mostrarDialogo(id, '');
	}
}

function mostrarDialogo(idPanel, idFocus) {
	if (idPanel !== '') {
		PF(idPanel).show();
	}
	if (idFocus !== '') {
		setTimeout(function() {
			var $fo = $('#' + idFocus);
			$fo.focus();
		}, 100);
	}
	return false;
}

function comprobarOcultarDialogo(xhr, status, args, id) {
	if (args.cerrar) {
		ocultarDialogo(id, '');
	}
}

function ocultarDialogo(idPanel, idFocus) {
	if (idPanel !== '') {
		PF(idPanel).hide();
	}
	if (idFocus !== '') {
		setTimeout(function() {
			var $fo = $('#' + idFocus);
			$fo.focus();
		}, 100);
	}
	return false;
}

function clickIngresar() {
	$('#btnSubmit').click();
}

function clickCerrar() {
	$('#btnCerrarBusqueda').click();
}

function mostarPanelMostrar(id) {
	$panelPrincipal = $('#' + id + 'panelPrincipal');
	$panelInsertar = $('#' + id + 'panelMostrar');
	$panelPrincipal.slideToggle();
	$panelInsertar.slideToggle();
	return false;
}

function mostarPanelInsertar(id) {
	$panelPrincipal = $('#' + id + 'panelPrincipal');
	$panelInsertar = $('#' + id + 'panelInsertar');
	$panelPrincipal.slideToggle();
	$panelInsertar.slideToggle();
	return false;
}

function mostarPanelImportar(id) {
	$panelPrincipal = $('#' + id + 'panelPrincipal');
	$panelImportar = $('#' + id + 'panelImportar');
	$panelPrincipal.slideToggle();
	$panelImportar.slideToggle();
	return false;
}

function mostarPanelEditar(id) {
	$panelPrincipal = $('#' + id + 'panelPrincipal');
	$panelEditar = $('#' + id + 'panelEditar');
	$panelPrincipal.slideToggle();
	$panelEditar.slideToggle();
	return false;
}

function mostarPanelEditarMapa(id) {
	$panelPrincipal = $('#' + id + 'panelPrincipal');
	$panelEditar = $('#' + id + 'panelEditar');
	$panelPrincipal.slideToggle();
	$panelEditar.slideToggle();
	$('.gmapModificar').css('visibility', 'visible');
	return false;
}

function mostarPanelEliminar(id) {
	$panelPrincipal = $('#' + id + 'panelPrincipal');
	$panelEliminar = $('#' + id + 'panelEliminar');
	$panelPrincipal.slideToggle();
	$panelEliminar.slideToggle();
	return false;
}

function mostarPanelBusqueda(id) {
	$panelBusqueda = $('#' + id + 'panelBusqueda');
	$panelBusqueda.slideToggle();
	return false;
}

function mostarPanel(idPanelPrincipal, idPanel) {
	$panelPrincipal = $('#' + idPanelPrincipal);
	$panel = $('#' + idPanel);
	$panelPrincipal.slideToggle();
	$panel.slideToggle();
	return false;
}

function mostrarPanel(idPanel, idFocus) {
	$panel = $('#' + idPanel);
	$panel.slideDown('slow');
	if (idFocus !== '') {
		$fo = $('#' + idFocus);
		$fo.focus();
		setTimeout(function() {
			$fo = $('#' + idFocus);
			$fo.focus();
		}, 300);
	}
	return false;
}

function ocultarPanel(idPanel, idFocus) {
	$panel = $('#' + idPanel);
	$panel.hide();
	if (idFocus !== '') {
		$fo = $('#' + idFocus);
		$fo.focus();
		setTimeout(function() {
			$fo = $('#' + idFocus);
			$fo.focus();
		}, 300);
	}
	return false;
}

function comprobarInsertar(xhr, status, args, id) {
	if (!args.validationFailed && args.cerrar) {
		mostarPanelInsertar(id);
	}
}

function comprobarEditar(xhr, status, args, id) {
	if (!args.validationFailed && args.cerrar) {
		mostarPanelEditar(id);
	}
}

var producto;

function focusProducto() {
	if (producto !== undefined) {
		producto.focus();
		setTimeout(function() {
			producto.focus();
		}, 300);
	}
}

function obtenerFilaProducto(registroProducto) {
	console.log(registroProducto);
	producto = document.getElementById(registroProducto);
	console.log(producto);
}

function animacionLabel(id) {
	document.getElementById(id).parentNode.nextSibling.classList
			.add("labelAnimacion");
}

function quitarAnimacionLabel(id) {
	console.log(document.getElementById(id).value.localeCompare());
	if (document.getElementById(id).value.localeCompare("__-__-____") === 0)
		document.getElementById(id).parentNode.nextSibling.classList
				.remove("labelAnimacion");
}

var rolJson;
function mostrarRol(rolJson) {
	this.rolJson = rolJson;

	var setText = function(val, text, remove, dec) {
		if (val === 0) {
			if (remove.length == 1) {
				$('#formRol\\:' + remove).remove();
				$('#formRol\\:' + text).remove();
			} else {
				remove.forEach(function(elem) {
					$('#' + elem).remove();
				});
			}
		} else
			$('#formRol\\:' + text).text(dec === true ? val : decimales(val));
	};
	var redondeoTotales = function(numero) {
		return redondeo(numero, 2);
	};
	var decimales = function(val) {
		return completarDecimales(val, 2);
	};

	$('#formRol\\:txtNombre').text(
			rolJson.rhEmpleado.empApellidos + ' '
					+ rolJson.rhEmpleado.empNombres);
	// Informacion
	var fechaDesde = new Date(rolJson.rolDesde);
	var fechaHasta = new Date(rolJson.rolHasta);
	$('#formRol\\:txtDesde').text(formatDate(fechaDesde));
	$('#formRol\\:txtHasta').text(formatDate(fechaHasta));
	$('#formRol\\:txtLaborados').text(rolJson.rolDiasLaboradosReales);
	setText(rolJson.rolDiasFaltasReales, 'txtFaltas', [ 'lblFaltas' ], true);
	setText(rolJson.rolDiasDescansoReales, 'txtDescanso', [ 'lblDescanso' ],
			true);
	setText(rolJson.rolDiasPagadosReales, 'txtPagados', [ 'lblPagados' ], true);
	$('#formRol\\:txtSueldo').text(decimales(rolJson.empSueldo));

	// Ingresos
	setText(rolJson.rolSaldoAnterior, 'txtSaldoAnterior',
			[ 'lblSaldoAnterior' ]);
	setText(rolJson.rolIngresos, 'txtIngresos', [ 'lblIngresos' ]);
	var horasExtras = rolJson.rolBonos + rolJson.rolHorasExtras
			+ rolJson.rolBonosnd;
	setText(horasExtras, 'txtHorasExtras', [ 'lblHorasExtras' ]);
	var bonoFijo = rolJson.rolBonoFijo + rolJson.rolBonoFijoNd;
	setText(bonoFijo, 'txtBonoFijo', [ 'lblBonoFijo' ]);
	var otrosIngresos = rolJson.rolOtrosIngresos + rolJson.rolOtrosIngresosNd;
	setText(otrosIngresos, 'txtOtrosIngresos', [ 'lblOtrosIngresos' ]);
	setText(rolJson.rolFondoReserva, 'txtFondoReservaI', [ 'lblFondoReservaI' ]);

	// Descuentos
	setText(rolJson.rolAnticipos, 'txtAnticipos', [ 'lblAnticipos' ]);
	setText(rolJson.rolIess, 'txtIess', [ 'lblIess' ]);
	setText(rolJson.rolPrestamos, 'txtPrestamos', [ 'lblPrestamos' ]);
	setText(rolJson.rolRetencionFuente, 'txtImpuestoRenta',
			[ 'lblImpuestoRenta' ]);
	setText(rolJson.rolDescuentoPermisoMedico, 'txtPermisoMedico',
			[ 'lblPermisoMedico' ]);
	var fondoReserva = rolJson.rhEmpleado.empCancelarFondoReservaMensualmente ? 0
			: rolJson.rolFondoReserva;
	setText(fondoReserva, 'txtFondoReservaD', [ 'lblFondoReservaD' ]);

	// Liquidacion
	var xiii = rolJson.empCancelarXiiiSueldoMensualmente ? rolJson.rolXiii
			: rolJson.rolLiqXiii;
	setText(xiii, 'txtXIIISueldo', [ 'lblXIIISueldo' ]);
	var xiv = rolJson.empCancelarXivSueldoMensualmente ? rolJson.rolXiv
			: rolJson.rolLiqXiv;
	setText(xiv, 'txtXIVSueldo', [ 'lblXIVSueldo' ]);
	setText(rolJson.rolLiqVacaciones, 'txtVacaciones', [ 'lblVacaciones' ]);
	setText(rolJson.rolLiqSalarioDigno, 'txtSalarioDigno',
			[ 'lblSalarioDigno' ]);
	setText(rolJson.rolLiqDesahucio, 'txtDesahucio', [ 'lblDesahucio' ]);
	setText(rolJson.rolLiqDesahucioIntempestivo, 'txtDesahucioIntem',
			[ 'lblDesahucioIntem' ]);
	setText(rolJson.rolLiqBonificacion, 'txtBonificacion',
			[ 'lblBonificacion' ]);

	// Totales
	var totalIngresos = rolJson.rolSaldoAnterior + rolJson.rolIngresos
			+ horasExtras + bonoFijo + otrosIngresos + rolJson.rolFondoReserva;
	var totalDescuentos = rolJson.rolAnticipos
			+ (rolJson.rhEmpleado.empSalarioNeto ? 0 : rolJson.rolIess)
			+ rolJson.rolPrestamos + rolJson.rolRetencionFuente
			+ rolJson.rolDescuentoPermisoMedico + fondoReserva;
	var totalLiquidacion = xiii + xiv + rolJson.rolLiqVacaciones
			+ rolJson.rolLiqSalarioDigno + rolJson.rolLiqDesahucio
			+ rolJson.rolLiqDesahucioIntempestivo + rolJson.rolLiqBonificacion;
	var totalPagar = totalIngresos - totalDescuentos + totalLiquidacion;

	setText(redondeoTotales(totalPagar), 'txtTotalPagar', [ 'totalPagar' ]);
	setText(redondeoTotales(totalIngresos), 'txtTotalIngresos', [
			'totalIngresos', 'ingresos' ]);
	setText(redondeoTotales(totalDescuentos), 'txtTotalDescuentos', [
			'totalDescuentos', 'descuentos' ]);
	setText(redondeoTotales(totalLiquidacion), 'txtTotalLiquidacion', [
			'totalLiquidacion', 'liquidacion' ]);
}

function formatDate(fecha) {
	var dd = fecha.getDate();
	var mm = fecha.getMonth() + 1;
	var yyyy = fecha.getFullYear();
	if (dd < 10) {
		dd = '0' + dd;
	}
	if (mm < 10) {
		mm = '0' + mm;
	}
	return yyyy + '-' + mm + '-' + dd;
}

function redondeo(numero, decimales) {
	var flotante = parseFloat(numero);
	return Math.round(flotante * Math.pow(10, decimales))
			/ Math.pow(10, decimales);
}

function completarDecimales(val, dec) {
	val = String(val);
	var split = val.split('.');
	var entero = split[0];
	var decimales = split[1];
	if (split.length === 1) {
		decimales = '0';
	}
	for (i = decimales.length; i < dec; i++) {
		decimales += '0';
	}
	return entero + '.' + decimales;
}

function retencion() {
	var bnRetencion = document
			.getElementById('empleado:tabView:bcbRetencion_input').checked;
	if (!bnRetencion) {
		$('#divRetencion').addClass('DispNone');
		$('#divConvenio').addClass('DispNone');
		$('#divComboConvenio').addClass('DispNone');
	} else {
		$('#divRetencion').removeClass('DispNone');
		$('#divConvenio').removeClass('DispNone');
		$('#divComboConvenio').removeClass('DispNone');
	}
}

function discapacidad() {
	var valDiscapacidad = $('#empleado\\:tabView\\:comboTipoDiscapacidad_label')
			.text();
	if (valDiscapacidad === 'SIN DISCAPACIDAD') {
		$('#lblPorcentajeDiscapacidad').addClass('DispNone');
		$('#txtPorcentajeDiscapacidad').addClass('DispNone');
		$('#lblTipoIdentidadDiscapacidad').addClass('DispNone');
		$('#txtTipoIdentidadDiscapacidad').addClass('DispNone');
		$('#lblIdentidadDiscapacidad').addClass('DispNone');
		$('#txtIdentidadDiscapacidad').addClass('DispNone');
	} else {
		$('#lblPorcentajeDiscapacidad').removeClass('DispNone');
		$('#txtPorcentajeDiscapacidad').removeClass('DispNone');
		$('#lblTipoIdentidadDiscapacidad').removeClass('DispNone');
		$('#txtTipoIdentidadDiscapacidad').removeClass('DispNone');
		$('#lblIdentidadDiscapacidad').removeClass('DispNone');
		$('#txtIdentidadDiscapacidad').removeClass('DispNone');
	}
}

function banco() {
	var valBanco = $('#empleado\\:tabView\\:comboBanco_label').text();
	if (valBanco === 'Escoja un Banco') {
		$('#empleado\\:tabView\\:labelComboCtaTipo').addClass('DispNone');
		$('#empleado\\:tabView\\:comboCtaTipo').addClass('DispNone');
		$('#empleado\\:tabView\\:labelNumeroCuenta').addClass('DispNone');
		$('#empleado\\:tabView\\:numeroCuenta').addClass('DispNone');
	} else {
		$('#empleado\\:tabView\\:labelComboCtaTipo').removeClass('DispNone');
		$('#empleado\\:tabView\\:comboCtaTipo').removeClass('DispNone');
		$('#empleado\\:tabView\\:labelNumeroCuenta').removeClass('DispNone');
		$('#empleado\\:tabView\\:numeroCuenta').removeClass('DispNone');
	}
}

function validarEmpleado() {

	var x = document.getElementsByClassName('ui-growl-error');

	for (i = 0; i < x.length; i++) {
		x[i].remove(i);
	}

	var cedula = document.getElementById('empleado:tabView:txtCedula');
	var residencia = document
			.getElementById('empleado:tabView:comboResidencia');
	var primerIngreso = document
			.getElementById('empleado:tabView:primerIngreso').childNodes[0];
	var primeraSalida = document
			.getElementById('empleado:tabView:primeraSalida').childNodes[0];
	var fechaAfiliacion = document
			.getElementById('empleado:tabView:fechaAfiliacion').childNodes[0];
	var codigoAfiliacion = document
			.getElementById('empleado:tabView:codigoAfiliacion');
	var codigoCargo = document.getElementById('empleado:tabView:codigoCargo');
	var apellidos = document.getElementById('empleado:tabView:apellidos');
	var nombres = document.getElementById('empleado:tabView:nombres');
	var genero = document.getElementById('empleado:tabView:comboGenero');
	var fechaNacimiento = document
			.getElementById('empleado:tabView:fechaNacimiento').childNodes[0];
	var estadoCivil = document
			.getElementById('empleado:tabView:comboEstadoCivil');
	var cargasFamiliares = document
			.getElementById('empleado:tabView:cargasFamiliares');
	var provincia = document.getElementById('empleado:tabView:comboProvincia');
	var canton = document.getElementById('empleado:tabView:comboCanton');
	var ciudad = document.getElementById('empleado:tabView:ciudad');
	var domicilio = document.getElementById('empleado:tabView:domicilio');
	var numero = document.getElementById('empleado:tabView:numero');
	var telefono = document.getElementById('empleado:tabView:telefono');
	var sueldo = document.getElementById('empleado:tabView:sueldo');
	var cargo = document.getElementById('empleado:tabView:cargo');
	var bonoFijo = document.getElementById('empleado:tabView:bonoFijo');
	var bonoFijoND = document.getElementById('empleado:tabView:bonoFijoND');
	var otrosIngresos = document
			.getElementById('empleado:tabView:otrosIngresos');
	var otrosIngresosND = document
			.getElementById('empleado:tabView:otrosIngresosND');
	var retencion = document
			.getElementById('empleado:tabView:bcbRetencion_input').checked;
	var educacion = document.getElementById('empleado:tabView:educacion');
	var discapacidad = document.getElementById('empleado:tabView:discapacidad');
	var alimentacion = document.getElementById('empleado:tabView:alimentacion');
	var terceraEdad = document.getElementById('empleado:tabView:terceraEdad');
	var salud = document.getElementById('empleado:tabView:salud');
	var sueldoOtraCompania = document
			.getElementById('empleado:tabView:sueldoOtraCompania');
	var vivienda = document.getElementById('empleado:tabView:vivienda');
	var otrasDeducciones = document
			.getElementById('empleado:tabView:otrasDeducciones');
	var vestuario = document.getElementById('empleado:tabView:vestuario');
	var utilidades = document.getElementById('empleado:tabView:utilidades');
	var selectDiscapacidad = document
			.getElementById("empleado:tabView:comboTipoDiscapacidad_input");
	var porcentaje = document
			.getElementById('empleado:tabView:porcentajeDiscapacidad');
	var banco = document.getElementById('empleado:tabView:comboBanco');
	var ctaTipo = document.getElementById('empleado:tabView:comboCtaTipo');
	var numeroCuenta = document.getElementById('empleado:tabView:numeroCuenta');

	var mensaje = "";
	if (cedula.value.localeCompare("") == 0) {
		cedula.classList.add("error");
		mensaje = " Cedula,";
	} else {
		cedula.classList.remove("error");
	}
	if (residencia.childNodes[1].childNodes[0].value.localeCompare("") == 0) {
		residencia.classList.add("error");
		mensaje += " Residencia,";
	} else {
		residencia.classList.remove("error");
	}
	if (primerIngreso.value.localeCompare("") != 0
			&& primeraSalida.value.localeCompare("") == 0) {
		primeraSalida.classList.add("error");
		mensaje += " Primera Salida,";
	} else {
		primeraSalida.classList.remove("error");
	}
	if (primeraSalida.value.localeCompare("") != 0
			&& primerIngreso.value.localeCompare("") == 0) {
		primerIngreso.classList.add("error");
		mensaje += " Primer Ingreso,";
	} else {
		primerIngreso.classList.remove("error");
	}

	if (banco.childNodes[1].childNodes[0].value.localeCompare("") != 0
			&& ctaTipo.childNodes[1].childNodes[0].value.localeCompare("") == 0) {
		ctaTipo.classList.add("error");
		mensaje += " Cuenta Tipo,";
	} else {
		ctaTipo.classList.remove("error");
	}

	if (banco.childNodes[1].childNodes[0].value.localeCompare("") != 0
			&& numeroCuenta.value.localeCompare("") == 0) {
		numeroCuenta.classList.add("error");
		mensaje += " Cuenta Numero,";
	} else {
		numeroCuenta.classList.remove("error");
	}

	if (fechaAfiliacion.value.localeCompare("") == 0) {
		fechaAfiliacion.classList.add("error");
		mensaje += " Fecha Afiliación,";
	} else {
		fechaAfiliacion.classList.remove("error");
	}
	if (codigoAfiliacion.value.localeCompare("") == 0) {
		codigoAfiliacion.classList.add("error");
		mensaje += " Codigo Afiliación,";
	} else {
		codigoAfiliacion.classList.remove("error");
	}
	if (codigoCargo.value.localeCompare("") == 0) {
		codigoCargo.classList.add("error");
		mensaje += " Codigo Cargo,";
	} else {
		codigoCargo.classList.remove("error");
	}
	if (apellidos.value.localeCompare("") == 0) {
		apellidos.classList.add("error");
		mensaje += " Apellidos,";
	} else {
		apellidos.classList.remove("error");
	}
	if (nombres.value.localeCompare("") == 0) {
		nombres.classList.add("error");
		mensaje += " Nombres,";
	} else {
		nombres.classList.remove("error");
	}
	if (genero.childNodes[1].childNodes[0].value.localeCompare("") == 0) {
		genero.classList.add("error");
		mensaje += " Genero,";
	} else {
		genero.classList.remove("error");
	}
	if (fechaNacimiento.value.localeCompare("") == 0) {
		fechaNacimiento.classList.add("error");
		mensaje += " Fecha Nacimiento,";
	} else {
		fechaNacimiento.classList.remove("error");
	}
	if (estadoCivil.childNodes[1].childNodes[0].value.localeCompare("") == 0) {
		estadoCivil.classList.add("error");
		mensaje += " Estadi Civil,";
	} else {
		estadoCivil.classList.remove("error");
	}
	if (cargasFamiliares.value.localeCompare("") == 0) {
		cargasFamiliares.classList.add("error");
		mensaje += " Cargas Familiares,";
	} else {
		cargasFamiliares.classList.remove("error");
	}
	if (provincia.childNodes[1].childNodes[0].value.localeCompare("") == 0) {
		provincia.classList.add("error");
		mensaje += " Provincia,";
	} else {
		provincia.classList.remove("error");
	}
	if (canton.childNodes[1].childNodes[0].value.localeCompare("") == 0) {
		canton.classList.add("error");
		mensaje += " Canton,";
	} else {
		canton.classList.remove("error");
	}
	if (domicilio.value.localeCompare("") == 0) {
		domicilio.classList.add("error");
		mensaje += " Domicilio,";
	} else {
		domicilio.classList.remove("error");
	}
	if (ciudad.value.localeCompare("") == 0) {
		ciudad.classList.add("error");
		mensaje += " Numero,";
	} else {
		ciudad.classList.remove("error");
	}
	if (numero.value.localeCompare("") == 0) {
		numero.classList.add("error");
		mensaje += " Numero,";
	} else {
		numero.classList.remove("error");
	}
	if (telefono.value.localeCompare("") == 0) {
		telefono.classList.add("error");
		mensaje += " Telefono,";
	} else {
		telefono.classList.remove("error");
	}
	if (sueldo.value.localeCompare("") == 0) {
		sueldo.classList.add("error");
		mensaje += " Sueldo,";
	} else {
		sueldo.classList.remove("error");
	}
	if (cargo.value.localeCompare("") == 0) {
		cargo.classList.add("error");
		mensaje += " Cargo,";
	} else {
		cargo.classList.remove("error");
	}
	if (bonoFijo.value.localeCompare("") == 0) {
		bonoFijo.classList.add("error");
		mensaje += " Bono Fijo,";
	} else {
		bonoFijo.classList.remove("error");
	}
	if (bonoFijoND.value.localeCompare("") == 0) {
		bonoFijoND.classList.add("error");
		mensaje += " Bono Fijo ND,";
	} else {
		bonoFijoND.classList.remove("error");
	}
	if (otrosIngresos.value.localeCompare("") == 0) {
		otrosIngresos.classList.add("error");
		mensaje += " Otros Ingresos,";
	} else {
		otrosIngresos.classList.remove("error");
	}
	if (otrosIngresosND.value.localeCompare("") == 0) {
		otrosIngresosND.classList.add("error");
		mensaje += " Otros Ingresos ND,";
	} else {
		otrosIngresosND.classList.remove("error");
	}
	if (retencion == true && educacion.value.localeCompare("") == 0) {
		educacion.classList.add("error");
		mensaje += " Educacion,";
	} else {
		educacion.classList.remove("error");
	}
	if (retencion == true && discapacidad.value.localeCompare("") == 0) {
		discapacidad.classList.add("error");
		mensaje += " Discapacidad,";
	} else {
		discapacidad.classList.remove("error");
	}
	if (retencion == true && alimentacion.value.localeCompare("") == 0) {
		alimentacion.classList.add("error");
		mensaje += " Alimentación,";
	} else {
		alimentacion.classList.remove("error");
	}
	if (retencion == true && terceraEdad.value.localeCompare("") == 0) {
		terceraEdad.classList.add("error");
		mensaje += " Rebaja Tercera Edad,";
	} else {
		terceraEdad.classList.remove("error");
	}
	if (retencion == true && salud.value.localeCompare("") == 0) {
		salud.classList.add("error");
		mensaje += " Salud,";
	} else {
		salud.classList.remove("error");
	}
	if (retencion == true && sueldoOtraCompania.value.localeCompare("") == 0) {
		sueldoOtraCompania.classList.add("error");
		mensaje += " Sueldo Otras Compañias,";
	} else {
		sueldoOtraCompania.classList.remove("error");
	}
	if (retencion == true && vivienda.value.localeCompare("") == 0) {
		vivienda.classList.add("error");
		mensaje += " Vivienda,";
	} else {
		vivienda.classList.remove("error");
	}
	if (retencion == true && otrasDeducciones.value.localeCompare("") == 0) {
		otrasDeducciones.classList.add("error");
		mensaje += " IESS Otras Compañias,";
	} else {
		otrasDeducciones.classList.remove("error");
	}
	if (retencion == true && vestuario.value.localeCompare("") == 0) {
		vestuario.classList.add("error");
		mensaje += " Vestuario,";
	} else {
		vestuario.classList.remove("error");
	}
	if (retencion == true && utilidades.value.localeCompare("") == 0) {
		utilidades.classList.add("error");
		mensaje += " Utilidades,";
	} else {
		utilidades.classList.remove("error");
	}
	if (selectDiscapacidad.options[selectDiscapacidad.selectedIndex].text
			.localeCompare("SIN DISCAPACIDAD") != 0
			&& porcentaje.value.localeCompare("") == 0) {
		porcentaje.classList.add("error");
		mensaje += " Porcentaje Discapacidad,";
	} else {
		porcentaje.classList.remove("error");
	}
	if (mensaje.localeCompare("") == 0) {
		return true;
	} else {
		PF('mensaje').renderMessage({
			"summary" : "Existen problemas en los siguientes campos: ",
			"detail" : mensaje,
			"severity" : "error"
		});
		return false;
	}
}

function tablaContable() {
	var x = document.getElementById('formTabla:tablaDetalle_data').childNodes;

	x.forEach(function(elem) {
		let
		dc = elem.childNodes[5].childNodes[1].childNodes[0].data;
		let
		debito = elem.childNodes[6].childNodes[1].childNodes[0];
		let
		credito = elem.childNodes[7].childNodes[1].childNodes[0];
		if (dc.localeCompare("D") == 0) {
			credito.value = "0.00";
		} else {
			debito.value = "0.00";
		}
	});
}

function mayorAuxiliar(idTabla) {
	var tabla = document.getElementById(idTabla);
	var columnasTablas = document
			.getElementById('formTabla:tablaDiarioAuxiliarCuentas_head').childNodes[0].childNodes.length - 1;
	var estDetalle = document.getElementById('estDetalle').childNodes[0].data;
	var x = parseInt(estDetalle);
	var cuenta;
	var icono;
	console.log(estDetalle);
	console.log(x);
	for (i = 0; i < tabla.childNodes.length; i++) {
		icono = tabla.childNodes[i].childNodes[columnasTablas].childNodes[1];
		if (tabla.childNodes[i].childNodes[0].childNodes[1].childNodes[0] === undefined) {
			cuenta = 0;
		} else
			cuenta = tabla.childNodes[i].childNodes[0].childNodes[1].childNodes[0].length;

		if (cuenta != estDetalle)
			icono.classList.add("desabilitar_a");
		else
			icono.setAttribute("target", "_blank");

	}
}

function tablaAcciones(idTabla, columnaComparar) {
	var tabla = document.getElementById(idTabla + '_data');
	var columnasTablas = document.getElementById(idTabla + '_head').childNodes[0].childNodes.length - 1;
	var campo;
	var icono;
	for (i = 0; i < tabla.childNodes.length; i++) {
		icono = tabla.childNodes[i].childNodes[columnasTablas].childNodes[1];
		if (tabla.childNodes[i].childNodes[columnaComparar].childNodes[1].childNodes[0] === undefined) {
			campo = 0;
		} else
			campo = tabla.childNodes[i].childNodes[columnaComparar].childNodes[1].childNodes[0].length;

		if (campo == 0)
			icono.classList.add("desabilitar_a");
		else
			icono.setAttribute("target", "_blank");
	}
}

function focusGained(id) {
	$('#' + id.replace('_focus', '').split(':').join('\\:')).addClass(
			'focusCombo');
}

function focusLost(id) {
	$('#' + id.replace('_focus', '').split(':').join('\\:')).removeClass(
			'focusCombo');
}