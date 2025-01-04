package com.centroinformacion.service;
import java.util.List;

import com.centroinformacion.entity.Proveedor;
public interface ProveedorService {
	public abstract List<Proveedor> listaTodos();
	public abstract Proveedor insertaActualizaProveedor(Proveedor obj); 
	public abstract List<Proveedor> listaPorRuc(String ruc);
	public abstract List<Proveedor> listaPorRazonSocial(String razonsocial);
	public abstract List<Proveedor> listaPorCelular(String celular);
	public abstract List<Proveedor> listaPorContacto(String contacto);
	public abstract  List<Proveedor>listaProveedorPorRazonSocialLike(String razonsocial); 
	public abstract void eliminaProveedor(int idEditorial); 
	public abstract List<Proveedor> listaProveedorPorRazonSocialIgualRegistra(String razonsocial); 
	public abstract List<Proveedor> listaProveedorPorRazonSocialIgualActualiza(String razonsocial,int idProveedor); 
 
	public abstract List<Proveedor> listaCompleja(String razonsocial, String direccion,String ruc,String telefono,String celular,String contacto, int estado,int idPais,int idTipoProveedor );



}
