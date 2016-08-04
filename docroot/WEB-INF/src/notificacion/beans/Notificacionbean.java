package notificacion.beans;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import notificacion.controller.Controller;
import notificacion.entities.ListaNotificacion;
import notificacion.entities.selecItem;
import notificacion.entities.selecteme;

import com.liferay.util.bridges.jsf.common.FacesMessageUtil;

@ManagedBean(name = "Notificacionbean")
@ViewScoped
public class Notificacionbean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String idNotificacionCodigo;
	private String idNotificacionTipo;
	private String codigo;
	private String ciudadSeleccionada;
	private String idRelacionEntidad;
	private List<selecItem> idNotificacionTipos;
	private List<selecteme> idRelacionEntidades;
	private List<selecteme> idRelacionEntidadesOrg;
//	private List<selecItem> ciudades;
	private List<ListaNotificacion> listado;
	private ListaNotificacion actual;
	private SelectItem ciudad;
	private SelectItem ciudadActual;
	private String descripcion;
	private List<SelectItem> ciudadesItems;
	
	public void setIdRelacionEntidades(List<selecteme> idRelacionEntidades) {
		this.idRelacionEntidades = idRelacionEntidades;
	}

	public List<selecteme> getIdRelacionEntidadesOrg() {
		return idRelacionEntidadesOrg;
	}

	public void setIdRelacionEntidadesOrg(List<selecteme> idRelacionEntidadesOrg) {
		this.idRelacionEntidadesOrg = idRelacionEntidadesOrg;
	}

	public String getCiudadSeleccionada() {
		return ciudadSeleccionada;
	}

	public void setCiudadSeleccionada(String ciudadSeleccionada) {
		this.ciudadSeleccionada = ciudadSeleccionada;
	}
	
	public SelectItem getCiudadActual() {
		return ciudadActual;
	}

	public void setCiudadActual(SelectItem ciudadActual) {
		this.ciudadActual = ciudadActual;
	}

	public SelectItem getCiudad() {
		return ciudad;
	}

	public void setCiudad(SelectItem ciudad) {
		System.out.println("entrando");
		this.ciudad = ciudad;
	}

//	public List<selecItem> getCiudades() {
//		return ciudades;
//	}

	public void filtrarEntidades() {
		idRelacionEntidades = new ArrayList<selecteme>();
		if(ciudadSeleccionada!=null && !ciudadSeleccionada.equals("")){
			for(selecteme ent: idRelacionEntidadesOrg)
				if(ent.getIdCiudad().equals(ciudadSeleccionada))
					idRelacionEntidades.add(ent);
			
		}else{
			System.out.println("NADA");
		}
		System.out.println(idRelacionEntidades.size());
	}
	
	public void filtrarEntidadesEditar() {
		idRelacionEntidades = new ArrayList<selecteme>();
		ciudadSeleccionada = actual.getEntidad().getIdCiudad();
		actual.getEntidad().setIdCiudad("");
		if(ciudadSeleccionada!=null && !ciudadSeleccionada.equals("")){
			for(selecteme ent: idRelacionEntidadesOrg)
				if(ent.getIdCiudad().equals(ciudadSeleccionada))
					idRelacionEntidades.add(ent);
			
		}else{
			System.out.println("NADA");
		}
		System.out.println(idRelacionEntidades.size());
	}

//	public void setCiudades(List<selecItem> ciudades) {
//		this.ciudades = ciudades;
//	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public ListaNotificacion getActual() {
		return actual;
	}

	public void setActual(ListaNotificacion actual) {
		this.actual = getNotificacion(actual.getIdNotificacion());
		this.actual.setTipo(getTipoNombre(this.actual.getIdNotificacionTipo()));
		this.actual.setEntidad(getEntidad(this.actual.getIdRelacionEntidad()));
		ciudadSeleccionada = actual.getEntidad().getIdCiudad();
		ciudadActual = getCiudad(ciudadSeleccionada);
		filtrarEntidades();
		System.out.println(this.actual);
	}

	public List<ListaNotificacion> getListado() {
		return listado;
	}

	public void setListado(List<ListaNotificacion> listado) {
		this.listado = listado;
	}

	public List<selecItem> getIdNotificacionTipos() {
		return idNotificacionTipos;
	}

	public void setIdNotificacionTipos(List<selecItem> idNotificacionTipos) {
		this.idNotificacionTipos = idNotificacionTipos;
	}

	public List<selecteme> getIdRelacionEntidades() {
		return idRelacionEntidades;
	}

	public void setIdRelacionEntidas(List<selecteme> idRelacionEntidades) {
		this.idRelacionEntidades = idRelacionEntidades;
	}

	public String getIdNotificacionCodigo() {
		return idNotificacionCodigo;
	}

	public void setIdNotificacionCodigo(String idNotificacionCodigo) {
		this.idNotificacionCodigo = idNotificacionCodigo;
	}

	public String getIdNotificacionTipo() {
		return idNotificacionTipo;
	}

	public void setIdNotificacionTipo(String idNotificacionTipo) {
		this.idNotificacionTipo = idNotificacionTipo;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getIdRelacionEntidad() {
		return idRelacionEntidad;
	}

	public void setIdRelacionEntidad(String idRelacionEntidad) {
		this.idRelacionEntidad = idRelacionEntidad;
	}

	public Notificacionbean() throws SQLException {
		idNotificacionTipos = new ArrayList<selecItem>();
		ResultSet rs1 = Controller.selecNotificacionTipo();
		selecTipo(rs1);
		idRelacionEntidadesOrg = new ArrayList<selecteme>();
		ResultSet rs = Controller.selecRelacion();
		slectRela(rs);
//		ciudades = new ArrayList<selecItem>();
//		ResultSet rs2 =Controller.selecCiudad();
//		selecCiudad(rs2);
		ciudadActual = new SelectItem();

	}
	
	public void cargarRelacion(){
		System.out.println("entro en cargar relacion");
		idNotificacionTipos = new ArrayList<selecItem>();
		ResultSet rs1 = Controller.selecNotificacionTipo();
		selecTipo(rs1);
		idRelacionEntidadesOrg = new ArrayList<selecteme>();
		ResultSet rs = Controller.selecRelacion();
		slectRela(rs);
//		ciudades = new ArrayList<selecItem>();
		
		ciudadActual = new SelectItem();
	}
	
//	private void selecCiudad(ResultSet result) {
//		
//	}


	private void selecTipo(ResultSet result) {
		try {
			if (result != null) {
				for (; result.next(); idNotificacionTipos.add(new selecItem(
						result.getString(1), result.getString(2)))) {
				}
			} else {
				System.out.println("no ahy coincidencias de busqueda");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void slectRela(ResultSet result) {
		try {
			if (result != null) {
				for (; result.next(); idRelacionEntidadesOrg.add(new selecteme(
						result.getString(1), result.getString(5), result.getString(2)))) {
				}
			} else {
				System.out.println("no ahy coincidencias de busqueda");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void enviarNotificacion() throws SQLException {
		

		if (idNotificacionTipo == null || idNotificacionTipo.equals("")) {
			FacesMessageUtil.error(FacesContext.getCurrentInstance(),"Debe seleccionar una Descripcion");
			return;
		}

		if (codigo == null || codigo.equals("")) {
			FacesMessageUtil.error(FacesContext.getCurrentInstance(),"Debe escribir Codigo");
			return;

		}
		if (idRelacionEntidad == null || idRelacionEntidad.equals("")) {
			FacesMessageUtil.error(FacesContext.getCurrentInstance(),"Debe seleccionar una Entidad ");
			return;

		}
		if (idRelacionEntidad.equals("")) {
			limpiar();
			FacesContext.getCurrentInstance().addMessage("error",new FacesMessage(" Error"));
		} else {
			if(validarNotificacion()){
				System.out.println("codigo agregar"+codigo);
				Controller.enviarNotificacion(idNotificacionTipo, codigo,idRelacionEntidad);
				FacesMessageUtil.info(FacesContext.getCurrentInstance(),"Se agrego una nueva Notificacion");
			}else{
				FacesMessageUtil.error(FacesContext.getCurrentInstance(),"El tipo de Notificacion ya existe ");
			}
			}			
			limpiar();
			
		}
	


	public void limpiar() {
		idNotificacionCodigo = null;
		idNotificacionTipo = null;
		codigo = null;
		idRelacionEntidad = null;
	}

	public void cargar() throws SQLException {
		String filtros = "";
		System.out.println("entro en cargar");
		if (ciudadSeleccionada != null && ciudadSeleccionada != "") {
			filtros += " AND c.A065CODICIUD = " + ciudadSeleccionada;
		}
		if (idNotificacionTipo != null && idNotificacionTipo != "") {
			filtros += " AND nt.IDNOTIFICACIONTIPO=" + idNotificacionTipo;
		}
		if (codigo != null && codigo != "") {
			filtros += " AND nc.Codigo=" + codigo;
		}
		if (idRelacionEntidad != null && !idRelacionEntidad.equals(""))
			filtros += " AND t.IdRelacionEntidad = " + idRelacionEntidad;
		
		cargarRelacion();
		listarNotificacion(Controller.listado(filtros));
	}

	public void eliminar(ListaNotificacion row){
		Controller.eliminar(row);
		listado = null;
		try {
			cargar();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void editar(){
		System.out.println("codigo editar "+actual.getCodigo());
		Controller.editar(actual);
		listado = null;
		try {
			cargar();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public selecteme getEntidad(String idEntidad){
		for(selecteme s: idRelacionEntidadesOrg){
			if(s.getNombre().equals(idEntidad))
				return s;
		}
		for(selecteme s: idRelacionEntidadesOrg){
			if(s.getCodigo().equals(idEntidad))
				return s;
		}
		return new selecteme("", "", "");
	}
	
	public ListaNotificacion getNotificacion(String idNotificacion){
		for(ListaNotificacion s: listado){
			if(s.getIdNotificacion().equals(idNotificacion))
				return s;
		}
		return new ListaNotificacion("", "", "", "", "", new selecItem("", ""), new selecteme("", "", ""));
	}
	
	public selecItem getTipoNombre(String idTipo){
		for(selecItem s: idNotificacionTipos){
			if(s.getNombre().equals(idTipo)){
				return s;
			}
		}
		
		for(selecItem s: idNotificacionTipos){
			if(s.getCodigo().equals(idTipo)){
				return s;
			}
		}
		return new selecItem("", "");
	}
	
	public SelectItem getCiudad(String Ciudad){
		for(SelectItem s: ciudadesItems){
			if(s.getValue().equals(Ciudad)){
				return s;
			}
		}
		return ciudad;
	}
	
	public String getCiudadNombre(String idCiudad) {
		for(SelectItem s: ciudadesItems){
			if(s.getValue().equals(idCiudad))
				return s.getValue().toString(); 
		}
		return null;
	}
	
	public void listarNotificacion(ResultSet result) throws SQLException {
		System.out.println("entro en listar notificacion");
		listado = new ArrayList<ListaNotificacion>();
		if (result != null) {
			while (result.next()) {
				listado.add(new ListaNotificacion(	result.getString(3),
													result.getString(2),
													result.getString(5),
													result.getString(4),
													result.getString(1),
													getTipoNombre(result.getString(2)),
													getEntidad(result.getString(4))
													));
			}
		}
	}

	public void agregarActuacion(){
		if(descripcion == null || descripcion.equals("")){
			FacesMessageUtil.error(FacesContext.getCurrentInstance(),"Debe Agregar una Actuacion");
			return;
		}else{
			Controller.agregarActuacion(descripcion);
			FacesContext.getCurrentInstance().addMessage("Succesful",new FacesMessage("Actucion Agregada con exito"));
		}
		
	}
	
	public boolean validarNotificacion() throws SQLException {
		listarNotificacion(Controller.listado(""));
		actual = new ListaNotificacion("",idNotificacionTipo,codigo,idRelacionEntidad,getCiudadNombre(ciudadSeleccionada),getTipoNombre(idNotificacionTipo),getEntidad(idRelacionEntidad));
		System.out.println(actual);
		for(ListaNotificacion i:listado){
			if(	actual.getCodigo().trim().equals(i.getCodigo().trim()) &&
				actual.getEntidad().getCodigo().trim().equals(i.getEntidad().getCodigo().trim()) &&
				actual.getTipo().getCodigo().trim().equals(i.getTipo().getCodigo().trim()))
					return false;
		}
		return true;
	}

	public List<SelectItem> getciudadesItems() {
		try {
			System.out.println("    ciudades");
			ResultSet result =Controller.selecCiudad();			
			if (result != null) {
				while (result.next()){ 
					System.out.println(result.getString(2));
					ciudadesItems.add(new SelectItem(result.getString(1),result.getString(2)));
				}
				
			} else {
				System.out.println("no ahy coincidencias de busqueda");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return ciudadesItems;
	}

	public void setciudadesItems(
			List<SelectItem> ciudadesItems) {
		this.ciudadesItems = ciudadesItems;
	}
	
	
}
