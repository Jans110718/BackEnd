package com.centroinformacion.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.centroinformacion.entity.Tesis;

public interface TesisRepository extends JpaRepository<Tesis, Integer> {
	
	@Query("select e from Tesis e where e.titulo like ?1 ")
	public abstract List<Tesis> listaPorTituloLike(String nombres);
	
	@Query("select e from Tesis e where e.titulo = ?1 ")
	public abstract List<Tesis> listaPorTituloIgualRegistra(String nombres);
	
	@Query("select e from Tesis e where e.titulo = ?1 and e.idTesis != ?2 ")
	public abstract List<Tesis> listaPorTituloIgualActualiza(String nombres, int idAutor);
	
	@Query("select e from Tesis e where "
			+ " e.titulo like ?1 and "
			+ " e.fechaCreacion >=  ?2 and "
			+ " e.fechaCreacion <=  ?3 and "
			+ " e.estado = ?4 and "
			+ " (?5 = -1 or e.tema.idDataCatalogo = ?5) and "
			+ " (?6 = -1 or e.idioma.idDataCatalogo = ?6) and "
			+ " (?7 = -1 or e.centroEstudios.idDataCatalogo = ?7) ")
public abstract List<Tesis> listaConsultaCompleja(String titulo, Date fecIni, Date fecFin, int estado,	int idTema, int idIdioma, int idCentroEstudios);
}
