package ec.com.orocodigo.OroSoftWeb.delegate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import ec.com.orocodigo.OroSoftUtils.Propiedades;
import ec.com.orocodigo.OroSoftUtils.UtilsJSON;
import ec.com.orocodigo.OroSoftUtils.sistema.TO.SisComboPeriodoTO;
import ec.com.orocodigo.OroSoftUtils.sistema.TO.SisConsultaUsuarioGrupoTO;
import ec.com.orocodigo.OroSoftUtils.sistema.TO.SisEmpresaTO;
import ec.com.orocodigo.OroSoftUtils.sistema.TO.SisErrorTO;
import ec.com.orocodigo.OroSoftUtils.sistema.TO.SisGrupoTO;
import ec.com.orocodigo.OroSoftUtils.sistema.TO.SisInfoTO;
import ec.com.orocodigo.OroSoftUtils.sistema.TO.SisListaEmpresaTO;
import ec.com.orocodigo.OroSoftUtils.sistema.TO.SisListaPeriodoTO;
import ec.com.orocodigo.OroSoftUtils.sistema.TO.SisListaSusTablaTO;
import ec.com.orocodigo.OroSoftUtils.sistema.TO.SisListaUsuarioTO;
import ec.com.orocodigo.OroSoftUtils.sistema.TO.SisLoginTO;
import ec.com.orocodigo.OroSoftUtils.sistema.TO.SisMenu;
import ec.com.orocodigo.OroSoftUtils.sistema.TO.SisPckeystoreTO;
import ec.com.orocodigo.OroSoftUtils.sistema.TO.SisPeriodoTO;
import ec.com.orocodigo.OroSoftUtils.sistema.TO.SisPermisoTO;
import ec.com.orocodigo.OroSoftUtils.sistema.TO.SisReporteTO;
import ec.com.orocodigo.OroSoftUtils.sistema.TO.SisSucesoTO;
import ec.com.orocodigo.OroSoftUtils.sistema.TO.SisUsuarioTO;
import ec.com.orocodigo.OroSoftUtils.sistema.entity.SisEmpresa;
import ec.com.orocodigo.OroSoftUtils.sistema.entity.SisPeriodo;
import ec.com.orocodigo.OroSoftUtils.sistema.entity.SisUsuario;
import ec.com.orocodigo.OroSoftWeb.utils.UtilAplication;
import ec.com.orocodigo.OroSoftWeb.utils.UtilsExcepciones;

public class SistemaDelegate {

	private static SistemaDelegate sistemaDelegate;

	public static SistemaDelegate getInstance() {
		if (sistemaDelegate == null)
			try {
				sistemaDelegate = new SistemaDelegate();
			} catch (final Exception e) {
				e.printStackTrace();
			}
		return sistemaDelegate;
	}

	private final String conexionWS;

	private final RestTemplate restTemplate = new RestTemplate();

	private SistemaDelegate() throws Exception {
		final Properties propSistema = Propiedades.readPropiedades();
		conexionWS = "http://" + propSistema.getProperty("servidor.ip") + ":"
				+ propSistema.getProperty("servidor.puerto") + "/" + propSistema.getProperty("servidor.ubicacion")
				+ "/OroSoftWS/sistemaController";
	}

	public boolean accionSisGrupoTO(final SisGrupoTO sisGrupoTO, final char accion) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("sisGrupoTO", sisGrupoTO);
			map.put("accion", accion);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return restTemplate.postForObject(conexionWS + "/accionSisGrupoTO", UtilsJSON.objetoToJson(map),
					boolean.class);
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return false;
	}

	public SisUsuario buscarUsuario(final String empCodigo, final String usrCodigo) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("empCodigo", empCodigo);
			map.put("usrCodigo", usrCodigo);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return restTemplate.postForObject(conexionWS + "/buscarUsuario", UtilsJSON.objetoToJson(map),
					SisUsuario.class);
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public SisUsuario buscarUsuarioPorNick(final String usrNick) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("usrNick", usrNick);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return restTemplate.postForObject(conexionWS + "/buscarUsuarioPorNick", UtilsJSON.objetoToJson(map),
					SisUsuario.class);
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public boolean comprobarSisPcs() {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("mac", UtilAplication.getSisInfoTO().getMac());
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return restTemplate.postForObject(conexionWS + "/comprobarSisPcs", UtilsJSON.objetoToJson(map),
					boolean.class);
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return false;
	}

	public String eliminarImagenEmpresa(final String nombre) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("nombre", nombre);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return restTemplate.postForObject(conexionWS + "/eliminarImagenEmpresa", UtilsJSON.objetoToJson(map),
					String.class);
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public boolean eliminarSisPeriodoTO(final SisPeriodoTO sisPeriodoTO) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("sisPeriodoTO", sisPeriodoTO);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return restTemplate.postForObject(conexionWS + "/eliminarSisPeriodoTO", UtilsJSON.objetoToJson(map),
					boolean.class);
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return false;
	}

	public boolean eliminarSisUsuarioTO(final SisUsuarioTO sisUsuarioTO) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("sisUsuarioTO", sisUsuarioTO);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return restTemplate.postForObject(conexionWS + "/eliminarSisUsuarioTO", UtilsJSON.objetoToJson(map),
					boolean.class);
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return false;
	}

	public void enviarError(final SisErrorTO sisErrorTO) {
		try {
			restTemplate.postForObject(conexionWS + "/enviarError", sisErrorTO, Void.class);
		} catch (final RestClientException e) {
			e.printStackTrace();
		}
	}

	public String estadoPeriodo(final String empresa, final String fecha) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("empresa", empresa);
			map.put("fecha", fecha);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return restTemplate.postForObject(conexionWS + "/estadoPeriodo", UtilsJSON.objetoToJson(map), String.class);
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public List<SisEmpresa> getEmpresasPorUsuarioItem() {
		try {
			String usuario = UtilAplication.getSisInfoTO().getUsuario();
			String[] url = ((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest())
					.getRequestURI().split("/");
			String item = url[url.length - 1];
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("usuario", usuario);
			map.put("item", item);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			List<SisEmpresa> listEmpresas = new ArrayList<SisEmpresa>(Arrays.asList(restTemplate.postForObject(
					conexionWS + "/getEmpresasPorUsuarioItem", UtilsJSON.objetoToJson(map), SisEmpresa[].class)));
			if (listEmpresas == null || listEmpresas.isEmpty()) {
				throw new AccessDeniedException("Acceso denegado - vista: " + item + " usuario: " + usuario);
			}
			return listEmpresas;
		} catch (RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public SisLoginTO getAccesoTO(final String nick, final String password, final String empresa) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("nick", nick);
			map.put("password", password);
			map.put("empresa", empresa);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return restTemplate.postForObject(conexionWS + "/getSisAccesoTO", UtilsJSON.objetoToJson(map),
					SisLoginTO.class);
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public Integer getColumnasEstadosFinancieros(final String empCodigo) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("empCodigo", empCodigo);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return restTemplate.postForObject(conexionWS + "/getColumnasEstadosFinancieros",
					UtilsJSON.objetoToJson(map), Integer.class);

		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public List<SisListaEmpresaTO> getListaLoginEmpresaTO(final String usrCodigo) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("usrCodigo", usrCodigo);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return Arrays.asList(restTemplate.postForObject(conexionWS + "/getListaLoginEmpresaTO",
					UtilsJSON.objetoToJson(map), SisListaEmpresaTO[].class));
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public List<String> getListaPermisoModulo(final String empCodigo, final String grupoCodigo) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("empCodigo", empCodigo);
			map.put("grupoCodigo", grupoCodigo);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return Arrays.asList(restTemplate.postForObject(conexionWS + "/getListaPermisoModulo",
					UtilsJSON.objetoToJson(map), String[].class));
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public List<SisPermisoTO> getListaPermisoTO(final String grupoCodigo, final String empCodigo) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("grupoCodigo", grupoCodigo);
			map.put("empCodigo", empCodigo);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return Arrays.asList(restTemplate.postForObject(conexionWS + "/getListaPermisoTO",
					UtilsJSON.objetoToJson(map), SisPermisoTO[].class));
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public List<SisMenu> getListaPermisoTO(final String empCodigo, final String grupoCodigo, final String perModulo) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("empCodigo", empCodigo);
			map.put("grupoCodigo", grupoCodigo);
			map.put("perModulo", perModulo);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return Arrays.asList(restTemplate.postForObject(conexionWS + "/getListaLoginPermisoTO",
					UtilsJSON.objetoToJson(map), SisMenu[].class));
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public List<SisEmpresaTO> getListaSisEmpresa(final String usrCodigo, final String empresa) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("usrCodigo", usrCodigo);
			map.put("empresa", empresa);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return Arrays.asList(restTemplate.postForObject(conexionWS + "/getListaSisEmpresaTO",
					UtilsJSON.objetoToJson(map), SisEmpresaTO[].class));
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public List<SisEmpresaTO> getListaSisEmpresaWeb(final String usrCodigo) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("usrCodigo", usrCodigo);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return Arrays.asList(restTemplate.postForObject(conexionWS + "/getListaSisEmpresaTO",
					UtilsJSON.objetoToJson(map), SisEmpresaTO[].class));
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public List<SisGrupoTO> getListaSisGrupo(final String empresa) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("empresa", empresa);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return Arrays.asList(restTemplate.postForObject(conexionWS + "/getListaSisGrupo",
					UtilsJSON.objetoToJson(map), SisGrupoTO[].class));
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public List<SisPeriodo> getListaSisPeriodo(final String empresa) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("empresa", empresa);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return Arrays.asList(restTemplate.postForObject(conexionWS + "/getListaSisPeriodos",
					UtilsJSON.objetoToJson(map), SisPeriodo[].class));
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public List<SisListaPeriodoTO> getListaSisPeriodoTO(final String empresa) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("empresa", empresa);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return new ArrayList<>(Arrays.asList(restTemplate.postForObject(conexionWS + "/getListaSisPeriodosTO",
					UtilsJSON.objetoToJson(map), SisListaPeriodoTO[].class)));
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public List<SisSucesoTO> getListaSisSucesoTO(final String desde, final String hasta, final String usuario,
			final String suceso, final String cadenaGeneral, final String empresa) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("desde", desde);
			map.put("hasta", hasta);
			map.put("usuario", usuario);
			map.put("suceso", suceso);
			map.put("cadenaGeneral", cadenaGeneral);
			map.put("empresa", empresa);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return Arrays.asList(restTemplate.postForObject(conexionWS + "/getListaSisSucesoTO",
					UtilsJSON.objetoToJson(map), SisSucesoTO[].class));

		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public List<SisListaSusTablaTO> getListaSisSusTablaTOs(final String empresa) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("empresa", empresa);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return Arrays.asList(restTemplate.postForObject(conexionWS + "/getListaSisSusTablaTOs",
					UtilsJSON.objetoToJson(map), SisListaSusTablaTO[].class));
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public List<SisConsultaUsuarioGrupoTO> getListaUsuario(final String parametro) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("parametro", parametro);
			map.put("infEmpresa", UtilAplication.getSisInfoTO().getEmpresa());
			// map.put("infUsuario",
			// UtilAplication.getSisInfoTO().getInfUsuario());
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return Arrays.asList(restTemplate.postForObject(conexionWS + "/getListaUsuario",
					UtilsJSON.objetoToJson(map), SisConsultaUsuarioGrupoTO[].class));
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public List<SisListaUsuarioTO> getListaUsuariosTO(final String empresa) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("empresa", empresa);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return Arrays.asList(restTemplate.postForObject(conexionWS + "/getListaSisUsuario",
					UtilsJSON.objetoToJson(map), SisListaUsuarioTO[].class));
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public List<SisMenu> getMenuWeb(final String usuario, final boolean produccion) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("usuario", usuario);
			map.put("produccion", produccion);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			final List<SisMenu> menus = new ArrayList<>(Arrays.asList(restTemplate
					.postForObject(conexionWS + "/getMenuWeb", UtilsJSON.objetoToJson(map), SisMenu[].class)));
			menus.add(new SisMenu("", "", "", "", "", "", false));
			return menus;
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public SisPeriodo getPeriodoPorFecha(final Date fecha, final String empresa) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("fecha", fecha);
			map.put("empresa", empresa);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return restTemplate.postForObject(conexionWS + "/getPeriodoPorFecha", UtilsJSON.objetoToJson(map),
					SisPeriodo.class);
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public SisLoginTO getPermisoInventarioTO(final SisInfoTO sisInfoTO) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("infEmpresa", sisInfoTO.getEmpresa());
			map.put("infUsuario", sisInfoTO.getUsuario());
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return restTemplate.postForObject(conexionWS + "/getPermisoInventarioTO", UtilsJSON.objetoToJson(map),
					SisLoginTO.class);
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public List<SisComboPeriodoTO> getSisComboPeriodoTO(final String empresa) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("empresa", empresa);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return Arrays.asList(restTemplate.postForObject(conexionWS + "/getListaSisPeriodosTO",
					UtilsJSON.objetoToJson(map), SisComboPeriodoTO[].class));

		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public List<SisGrupoTO> getSisGrupoTOs(final String empresa, String grupCodigo) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("empresa", empresa);
			map.put("grupCodigo", grupCodigo);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return Arrays.asList(restTemplate.postForObject(conexionWS + "/getSisGrupoTOs", UtilsJSON.objetoToJson(map),
					SisGrupoTO[].class));
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public SisPckeystoreTO getSisPckeystoreTO() {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return restTemplate.postForObject(conexionWS + "/getSisPckeystoreTO", UtilsJSON.objetoToJson(map),
					SisPckeystoreTO.class);
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public SisReporteTO getSisReporteTO(final SisInfoTO sisInfoTO) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("infMac", sisInfoTO.getMac());
			map.put("sisInfoTO", sisInfoTO);
			return restTemplate.postForObject(conexionWS + "/getSisReporteTO", UtilsJSON.objetoToJson(map),
					SisReporteTO.class);
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public String guardarImagenEmpresa(final byte[] imagen, final String nombre) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("imagen", imagen);
			map.put("nombre", nombre);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return restTemplate.postForObject(conexionWS + "/guardarImagenEmpresa", UtilsJSON.objetoToJson(map),
					String.class);
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public boolean insertarSisEmpresa(final SisEmpresaTO sisEmpresaTO) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("sisEmpresaTO", sisEmpresaTO);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return restTemplate.postForObject(conexionWS + "/insertarSisEmpresa", UtilsJSON.objetoToJson(map),
					boolean.class);
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return false;
	}

	public String insertarSisUsuarioTO(final SisUsuarioTO sisUsuarioTO, final boolean insertaDetalle) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("sisUsuarioTO", sisUsuarioTO);
			map.put("insertaDetalle", insertaDetalle);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return restTemplate.postForObject(conexionWS + "/insertarSisUsuarioTO", UtilsJSON.objetoToJson(map),
					String.class);
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public Boolean insertaSisPeriodoTO(final SisPeriodoTO sisPeriodoTO) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("sisPeriodoTO", sisPeriodoTO);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return restTemplate.postForObject(conexionWS + "/insertarSisPeriodoTO", UtilsJSON.objetoToJson(map),
					boolean.class);
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public String modificarPasswordSisUsuarioTO(final SisUsuarioTO sisUsuarioTO, final String pass) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("sisUsuarioTO", sisUsuarioTO);
			map.put("pass", pass);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return restTemplate.postForObject(conexionWS + "/modificarPasswordSisUsuarioTO",
					UtilsJSON.objetoToJson(map), String.class);
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public boolean modificarSisEmpresa(final SisEmpresaTO sisEmpresaTO) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("sisEmpresaTO", sisEmpresaTO);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return restTemplate.postForObject(conexionWS + "/modificarSisEmpresa", UtilsJSON.objetoToJson(map),
					boolean.class);
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return false;
	}

	public Boolean modificarSisPeriodoTO(final SisPeriodoTO sisPeriodoTO) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("sisPeriodoTO", sisPeriodoTO);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return restTemplate.postForObject(conexionWS + "/modificarSisPeriodoTO", UtilsJSON.objetoToJson(map),
					boolean.class);
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public boolean modificarSisPermiso(final List<SisPermisoTO> listaSisPermisoTO, final String usuario,
			final String codModifGrupo) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("listaSisPermisoTO", listaSisPermisoTO);
			map.put("usuario", usuario);
			map.put("codModifGrupo", codModifGrupo);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return restTemplate.postForObject(conexionWS + "/modificarSisPermiso", UtilsJSON.objetoToJson(map),
					boolean.class);

		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return false;
	}

	public String modificarSisUsuarioTO(final SisUsuarioTO sisUsuarioTO, final String pass) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("sisUsuarioTO", sisUsuarioTO);
			map.put("pass", pass);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return restTemplate.postForObject(conexionWS + "/modificarSisUsuarioTO", UtilsJSON.objetoToJson(map),
					String.class);
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public byte[] obtenerImagenEmpresa(final String nombre) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("nombre", nombre);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return restTemplate.postForObject(conexionWS + "/obtenerImagenEmpresa", UtilsJSON.objetoToJson(map),
					byte[].class);
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public SisGrupoTO sisGrupoUsuariosTO() {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("infEmpresa", UtilAplication.getSisInfoTO().getEmpresa());
			map.put("infUsuario", UtilAplication.getSisInfoTO().getUsuario());
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return restTemplate.postForObject(conexionWS + "/sisGrupoUsuariosTO", UtilsJSON.objetoToJson(map),
					SisGrupoTO.class);
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	/////////////////////////// Metodos WEB ///////////////////////////

	public SisGrupoTO sisGrupoUsuariosTO(final String empresa) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("infEmpresa", empresa);
			map.put("infUsuario", UtilAplication.getSisInfoTO().getUsuario());
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return restTemplate.postForObject(conexionWS + "/sisGrupoUsuariosTO", UtilsJSON.objetoToJson(map),
					SisGrupoTO.class);
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public void sisRegistrarEventosUsuario(final String suceso, final String mensaje, final String tabla) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("suceso", suceso);
			map.put("mensaje", mensaje);
			map.put("tabla", tabla);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			restTemplate.postForObject(conexionWS + "/sisRegistrarEventosUsuario", map, Void.class);
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
	}

	public String updateSisReporteTO(final SisReporteTO sisReporteTO) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("sisReporteTO", sisReporteTO);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return restTemplate.postForObject(conexionWS + "/updateSisReporteTO", UtilsJSON.objetoToJson(map),
					String.class);
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

	public String validarPeriodo(final Date fecha, final String empresa) {
		try {
			final Map<String, Object> map = new HashMap<>();
			map.put("fecha", fecha);
			map.put("empresa", empresa);
			map.put("sisInfoTO", UtilAplication.getSisInfoTO());
			return restTemplate.postForObject(conexionWS + "/validarPeriodo", UtilsJSON.objetoToJson(map),
					String.class);
		} catch (final RestClientException e) {
			UtilsExcepciones.enviarError(e, getClass().getName());
		}
		return null;
	}

}
