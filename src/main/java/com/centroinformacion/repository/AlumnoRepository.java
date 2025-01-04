package com.centroinformacion.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.centroinformacion.entity.Alumno;

// Define el repositorio para la entidad Alumno, extendiendo JpaRepository para proporcionar métodos CRUD.
public interface AlumnoRepository extends JpaRepository<Alumno, Integer> {
	
public abstract List<Alumno> findByOrderByApellidosAsc();
	 
	@Query("select t from Alumno t where t.nombres like ?1")
	public abstract List<Alumno> listaAlumnoPorTituloLike(String titulo);
	
	@Query("select t from Alumno t where t.apellidos like ?1")
	public abstract List<Alumno> listaAlumnoPorApellidoLike(String apellido);
	
	@Query("select t from Alumno t where t.nombres like ?1")
	public abstract List<Alumno> listaPorTituloLikeIgualRegistra(String titulo);
	
	@Query("select t from Alumno t where t.nombres = ?1 and t.idAlumno != ?2")
	public abstract List<Alumno> listaPorTituloIgualActualiza(String titulo, int idAlumno);
	
	@Query("select t from Alumno t where t.nombres = ?1")
	public abstract List<Alumno> validacionListaPorNombreIgualRegistra(String titulo);
	
	@Query("select a from Alumno a where a.telefono = ?1 ")
	public abstract List<Alumno> listaPorTelefonoIgualRegistra(String telefono);
	
	@Query("select a from Alumno a where a.dni= ?1 ")
	public abstract List<Alumno> listaPorDniIgualRegistra(String dni);
	
	@Query("select a from Alumno a where "
			+ " a.nombres like ?1  and "
			+ " a.apellidos like ?2 and "
			+ " a.telefono like ?3 and "
			+ " a.celular like ?4 and "
			+ " a.dni like ?5 and "
			+ " a.correo like ?6 and "
			+ " a.tipoSangre like ?7 and "
			+ " a.fechaNacimiento >= ?8 and "
			+ " a.fechaNacimiento <= ?9 and "
			+ " a.estado =?10 and "
			+ " (?11 = -1 or a.pais.idPais = ?11) and "
			+ " (?12 = -1 or a.modalidad.idDataCatalogo = ?12) ")
	public abstract List<Alumno> listaConsultaCompleja(
			String nombres, 
			String apellidos, 
			String telefono,
			String celular,
			String dni, 
			String correo, 
			String tipoSangre, 
			Date fechaNacimientoDesde, 
			Date fechaNacimientoHasta, 
			int estado, 
			int idPais,
			int idModalidad);
}
