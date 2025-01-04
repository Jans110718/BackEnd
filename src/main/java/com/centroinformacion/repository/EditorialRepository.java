package com.centroinformacion.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.centroinformacion.entity.Editorial;
import com.centroinformacion.entity.Revista;

public interface EditorialRepository extends JpaRepository<Editorial, Integer>{
	public List<Editorial> findByRuc(String ruc);
	public List<Editorial> findByRazonSocial(String razonSocial);

	@Query("select e from Editorial e where e.razonSocial like ?1 ")
	public abstract List<Editorial> listaPorRazonSocialLike(String razonSocial);
	
	@Query("select e from Editorial e where e.razonSocial = ?1 ")
	public abstract List<Editorial> listaPorRazonSocialIgualRegistra(String razonSocial);
	
	@Query("select e from Editorial e where e.razonSocial = ?1 and e.idEditorial != ?2 ")
	public abstract List<Editorial> listaPorRazonSocialIgualActualiza(String razonSocial, int idEditorial);
	public abstract List<Editorial> findByOrderByRazonSocialAsc();

	@Query("select e from Editorial e where "
			+ " e.razonSocial like ?1 and "
			+ " e.direccion like ?2 and "
			+ " e.ruc like ?3 and "
			+ " e.gerente like ?4 and "
			+ " e.fechaCreacion >=  ?5 and "
			+ " e.fechaCreacion <=  ?6 and "
			+ " e.estado = ?7 and "
			+ " (?8 = -1 or e.pais.idPais = ?8)  ")
	public abstract List<Editorial> listaCompleja(String razonSocial, String direccion, 
												String ruc,String gerente,
												Date fecIni, Date fecFin, 
												int estado,	int idPais);
		
	
}
