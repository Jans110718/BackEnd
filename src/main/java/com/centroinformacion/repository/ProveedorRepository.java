package com.centroinformacion.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.centroinformacion.entity.Proveedor;

public interface ProveedorRepository extends JpaRepository<Proveedor, Integer>{
	public List<Proveedor> findByRuc(String ruc);
	public List<Proveedor> findByCelular(String celular);
	    public List<Proveedor> findByRazonsocial(String razonsocial);
		public List<Proveedor> findByContacto(String contacto);

	@Query("select e from Proveedor e where e.razonsocial like ?1 ")
	public abstract List<Proveedor> listaPorRazonSocialLike(String razonsocial);
	
	@Query("select e from Proveedor e where e.razonsocial = ?1 ")
	public abstract List<Proveedor> listaPorRazonSocialIgualRegistra(String razonsocial);
	@Query("select e from Proveedor e where e.razonsocial = ?1 and e.idProveedor != ?2 ")
	public abstract List<Proveedor> listaPorRazonSocialIgualActualiza(String razonsocial, int idProveedor);
	public abstract List<Proveedor> findByOrderByRazonsocialAsc();
	@Query("select p from Proveedor p where "
	        + " p.razonsocial like ?1 and "
	        + " p.direccion like ?2 and "
	        + " p.ruc like ?3 and "
	        + " p.telefono like ?4 and "
	        + " p.celular like ?5 and "
	        + " p.contacto like ?6 and "
	        + " p.estado = ?7 and "
	        + " (?8 = -1 or p.pais.idPais = ?8) and "
	        + " (?9 = -1 or p.tipoProveedor.idDataCatalogo = ?9) ")
	public abstract List<Proveedor> listaCompleja(String razonsocial, 
												  String direccion, 
	                                              String ruc, 
	                                              String telefono, 
	                                              String celular, 
	                                              String contacto, 
	                                              int estado, 
	                                              int idPais, 
	                                              int idTipoProveedor);

}
