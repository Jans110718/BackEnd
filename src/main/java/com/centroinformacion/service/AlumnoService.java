package com.centroinformacion.service;

import java.util.Date;
import java.util.List;

import com.centroinformacion.entity.Alumno;

public interface AlumnoService {

	public abstract List<Alumno> listaTodos();
	public abstract Alumno registraAlumno(Alumno obj);
	

	//PARA EL CRUD (INSERT - DELETE - UPDATE - SELECT)
	public abstract Alumno insertaActualizaAlumno(Alumno obj);
	public abstract List<Alumno> listaAlumnoPorTituloLike(String titulo);
	public abstract void eliminaAlumno(int idAlumno);
	public abstract List<Alumno> listaAlumnoTodos();
	
	//VALIDACIONES - REGISTER 
	public abstract List<Alumno> listaAlumnoPorNombreIgualRegistro(String titulo);
	public abstract List<Alumno> listaAlumnoPorApellidoIgualRegistro(String apellido);
	
	//VALIDACIONES  - UPDATE
	public abstract List<Alumno> listaAlumnoPorTituloIgualActualiza(String titulo, int idAlumno);
	public abstract List<Alumno> validacionListaPorTitutloIgualRegistra(String titulo);
	
	public abstract List<Alumno> listaPorDni(String dni);
	public abstract List<Alumno> listaPorTelefono(String telefono);
	
	//Consulta
	public abstract List<Alumno> listaConsultaCompleja(String nombres, String apellidos, String telefono,String celular, 
			String dni,String correo,String tipoSangre,
			Date fechaNacimientoDesde,Date fechaNacimientoHasta,int estado,int idPais,int idModalidad);
	


}
