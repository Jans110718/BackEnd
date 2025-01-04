package com.centroinformacion.repository;

import java.util.Date;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.centroinformacion.entity.Autor;

public interface AutorRepository extends JpaRepository<Autor, Integer>{

    @Query("select e from Autor e where e.nombres like ?1 ")
    public abstract List<Autor> listaPorNombresLike(String nombres);

    @Query("select a from Autor a where a.nombres = ?1 and a.apellidos = ?2")
    List<Autor> listaPorNombresYApellidosIgual(String nombres, String apellidos);

    /*@Query("select a from Autor a where a.apellidos = ?1")
    List<Autor> listaPorApellidosIgualRegistra(String apellidos);*/

    @Query("select a from Autor a where a.celular = ?1")
    List<Autor> listaPorCelularIgualRegistra(String celular);

    @Query("select a from Autor a where a.telefono = ?1")
    List<Autor> listaAutorPorTelefonoIgualRegistra(String telefono);
    
    @Query("select a from Autor a where (a.nombres = ?1 or a.apellidos = ?1) and (a.nombres = ?2 or a.apellidos = ?2) and a.idAutor != ?3")
    List<Autor> listaPorNombresApellidosIgualActualiza(String nombres, String apellidos, int idAutor);

    /*@Query("select a from Autor a where a.nombres = ?1 and a.idAutor != ?2")
    List<Autor> listaPorNombresIgualActualiza(String nombres, int idAutor);

    @Query("select a from Autor a where a.apellidos = ?1 and a.idAutor != ?2")
    List<Autor> listaPorApellidosIgualActualiza(String apellidos, int idAutor);*/

    @Query("select a from Autor a where a.celular = ?1 and a.idAutor != ?2")
    List<Autor> listaPorCelularIgualActualiz(String celular, int idAutor);

    @Query("select a from Autor a where a.telefono = ?1 and a.idAutor != ?2")
    List<Autor> listaAutorPorTelefonoIgualActualiza(String telefono , int idAutor);

    @Query("select a from Autor a where "
			+ " a.nombres like ?1  and "
			+ " a.apellidos like ?2 and "
			+ " a.fechaNacimiento >= ?3 and "
			+ " a.fechaNacimiento <= ?4 and "
			+ " a.telefono like ?5 and "
			+ " a.celular like ?6 and "
			+ " a.orcid like ?7 and "
			+ " a.estado =?8 and "
			+ " (?9 = -1 or a.pais.idPais = ?9) and "
			+ " (?10 = -1 or a.grado.idDataCatalogo = ?10) ")
	public abstract List<Autor> listaConsultaCompleja(
			String nombres, 
			String apellidos, 
			Date fechaNacimientoDesde, 
			Date fechaNacimientoHasta, 
			String telefono,
			String celular,
			String orcid, 
			int estado, 
			int idPais,
			int idGrado);
}
