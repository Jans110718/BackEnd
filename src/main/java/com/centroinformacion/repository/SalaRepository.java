package com.centroinformacion.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
//AUTOR : SIFUENTES GAMBINI CESAR JAREN
import org.springframework.data.jpa.repository.Query;

import com.centroinformacion.entity.Sala;

public interface SalaRepository extends JpaRepository<Sala, Integer>{

	
	@Query("select s from Sala s where s.numero like ?1 ")
	public abstract List<Sala> listaPorNumeroLike(String numero);
	
	@Query("select s from Sala s where s.numero = ?1 ")
	public abstract List<Sala> listaPorNumeroIgualRegistra(String numero);
	
	@Query("select s from Sala s where s.numero = ?1 and s.idSala != ?2 ")
	public abstract List<Sala> listaPorNumeroIgualActualiza(String numero, int idSala);

	@Query("select e from Sala e where "
	        + " (e.numero like ?1) and "
	        + " (?2 = -1 or e.piso = ?2) and "
	        + " (?3 = -1 or e.numAlumnos = ?3) and "
	        + " (e.recursos like ?4) and "
	        + " (e.estado = ?5) and "
	        + " (?6 = -1 or e.tipoSala.idDataCatalogo = ?6) and "
	        + " (?7 = -1 or e.sede.idDataCatalogo = ?7) and "
	        + " (?8 = -1 or e.estadoReserva.idDataCatalogo = ?8) ")
	public abstract List<Sala> listaConsultaCompleja(String numero, int piso, int numAlumnos, String recursos, int estado, int idTipoSala, int idSede, int idEstadoReserva);
}