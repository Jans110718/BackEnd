package com.centroinformacion.service;

import java.util.List;

import com.centroinformacion.entity.Libro;

public interface LibroService {

	public abstract Libro insertaActualizaLibro(Libro obj);
	public abstract List<Libro> listaLibro();
 	
	
	//CRUD
		public abstract List<Libro> listaLibroPorTituloLike(String titulo);
		public abstract void eliminaLibro(int idLibro);
		
		//VALIDACION PARA REGISTRAR
		public abstract List<Libro> listaLibroPorTituloIgualRegistra(String titulo);

		//VALIDACION PARA ACTUALIZAR
		public abstract List<Libro> listaLibroPorTituloIgualActualiza(String titulo, int idLibro);
		
		//CONSULTA
		public abstract List<Libro> listaConsultaCompleja(
			    String titulo,
			    int anioDesde,
			    int anioHasta,
			    String serie,
			    int estado,
			    int idCategoriaLibro,
			    int idEstadoPrestamo,
			    int idTipoLibro,
			    int idEditorial
			);


}
