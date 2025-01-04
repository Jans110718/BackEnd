package com.centroinformacion.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.centroinformacion.entity.Libro;

public interface LibroRepository extends JpaRepository<Libro, Integer>{

 

	@Query("select e from Libro e where e.titulo like ?1 ")
	public abstract List<Libro> listaPorTituloLike(String nombres);

	@Query("select e from Libro e where e.titulo = ?1 ")
	public abstract List<Libro> listaPorTituloIgualRegistra(String nombres);

	@Query("select e from Libro e where e.titulo = ?1 and e.idLibro != ?2 ")
	public abstract List<Libro> listaPorTituloIgualActualiza(String nombres, int idAutor);
	
	
	@Query("select e from Libro e where "
	        + "e.titulo like %?1% and "
	        + "e.anio >= ?2 and e.anio <= ?3 and " 
	        + "e.serie like %?4% and "
	        + "e.estado = ?5 and "
	        + "(?6 = -1 or e.categoriaLibro.idDataCatalogo = ?6) and "
	        + "(?7 = -1 or e.estadoPrestamo.idDataCatalogo = ?7) and "
	        + "(?8 = -1 or e.tipoLibro.idDataCatalogo = ?8) and "
	        + "(?9 = -1 or e.editorial.idEditorial = ?9)")
	public abstract List<Libro> listaConsultaCompleja(
	        String titulo,
	        int anioInicio,
	        int anioFin, 
	        String serie, 
	        int estado,
	        int idCategoriaLibro, 
	        int idEstadoPrestamo,
	        int idTipoLibro, 
	        int idEditorial);

	
}
