package com.centroinformacion.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.centroinformacion.entity.Alumno;
import com.centroinformacion.repository.AlumnoRepository;

@Service // Indica que esta clase es un servicio de Spring, gestionado por el contenedor
			// de Spring.
public class AlumnoServiceImp implements AlumnoService {

	@Autowired
	private AlumnoRepository repository;

	@Override
	public List<Alumno> listaTodos() {
		return repository.findByOrderByApellidosAsc();
	}

	@Override
	public Alumno registraAlumno(Alumno obj) {
		return repository.save(obj);
	}

	@Override
	public Alumno insertaActualizaAlumno(Alumno obj) {
		return repository.save(obj);
	}

	@Override
	public List<Alumno> listaAlumnoPorTituloLike(String titulo) {
		return repository.listaAlumnoPorTituloLike(titulo);
	}

	@Override
	public void eliminaAlumno(int idAlumno) {
		repository.deleteById(idAlumno);
	}

	@Override
	public List<Alumno> listaAlumnoTodos() {
		return repository.findAll();
	}

	@Override
	public List<Alumno> listaAlumnoPorNombreIgualRegistro(String titulo) {
		return repository.listaAlumnoPorTituloLike(titulo);
	}

	@Override
	public List<Alumno> listaAlumnoPorTituloIgualActualiza(String titulo, int idAlumno) {
		return repository.listaPorTituloIgualActualiza(titulo, idAlumno);
	}

	@Override
	public List<Alumno> validacionListaPorTitutloIgualRegistra(String titulo) {
		return repository.validacionListaPorNombreIgualRegistra(titulo);
	}

	@Override
	public List<Alumno> listaPorDni(String dni) {
		return repository.listaPorDniIgualRegistra(dni);
	}

	@Override
	public List<Alumno> listaPorTelefono(String telefono) {
		return repository.listaPorTelefonoIgualRegistra(telefono);
	}

	@Override
	public List<Alumno> listaAlumnoPorApellidoIgualRegistro(String apellido) {
		
		return repository.listaAlumnoPorApellidoLike(apellido);
	}

	@Override
	public List<Alumno> listaConsultaCompleja(String nombres, String apellidos, String telefono, String celular,
			String dni, String correo, String tipoSangre, Date fechaNacimientoDesde, Date fechaNacimientoHasta, int estado, int idPais,
			int idModalidad) {
		return repository.listaConsultaCompleja(nombres, apellidos, telefono, celular, dni, correo, tipoSangre, fechaNacimientoDesde, fechaNacimientoHasta, estado, idPais, idModalidad);
	}
}