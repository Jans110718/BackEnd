package com.centroinformacion.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service; // Añade esta importación

import com.centroinformacion.entity.Libro;
import com.centroinformacion.repository.LibroRepository;


@Service // Añade esta anotación
public class LibroServiceImp implements LibroService{
	
	@Autowired	
	private LibroRepository repository;
	
	@Override
	public Libro insertaActualizaLibro(Libro obj) {
		return repository.save(obj);
	}
	
	@Override
	public List<Libro> listaLibro() {
		return repository.findAll();
	}
 

	
	@Override
	public List<Libro> listaLibroPorTituloLike(String titulo) {
		return repository.listaPorTituloLike(titulo);
	}

	@Override
	public void eliminaLibro(int idLibro) {
		repository.deleteById(idLibro);

	}

	@Override
	public List<Libro> listaLibroPorTituloIgualRegistra(String titulo) {
		return repository.listaPorTituloIgualRegistra(titulo);
	}

	@Override
	public List<Libro> listaLibroPorTituloIgualActualiza(String titulo, int idLibro) {
		return repository.listaPorTituloIgualActualiza(titulo, idLibro);
	}

	@Override
	public List<Libro> listaConsultaCompleja(String titulo, int anioDesde,int anioHasta, String serie, int estado, int idCategoriaLibro,
			int idEstadoPrestamo, int idTipoLibro, int idEditorial) {
		return repository.listaConsultaCompleja(titulo, anioDesde, anioHasta, serie, estado, idCategoriaLibro, idEstadoPrestamo, idTipoLibro, idEditorial);
	}
	

	
	
	
}
