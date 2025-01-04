package com.centroinformacion.service;

import java.util.List;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.centroinformacion.entity.Proveedor;
import com.centroinformacion.repository.ProveedorRepository;

@Service
public class ProveedorServiceImp implements ProveedorService {

	@Autowired
	private ProveedorRepository repository;
	@Override
	public Proveedor insertaActualizaProveedor(Proveedor obj) {
		return repository.save(obj);
	}
	@Override
	public List<Proveedor> listaTodos() {
		return repository.findByOrderByRazonsocialAsc();
	}
	@Override
	public List<Proveedor> listaProveedorPorRazonSocialLike(String razonsocial) {
		return repository.listaPorRazonSocialLike(razonsocial);
	}
	@Override
	public void eliminaProveedor(int idProveedor) {
        repository.deleteById(idProveedor);		
	}
	@Override
	public List<Proveedor> listaProveedorPorRazonSocialIgualRegistra(String razonsocial) {
		return repository.listaPorRazonSocialIgualRegistra(razonsocial);
	}
	@Override
	public List<Proveedor> listaProveedorPorRazonSocialIgualActualiza(String razonsocial, int idProveedor) {
		return repository.listaPorRazonSocialIgualActualiza(razonsocial, idProveedor);
	}
	@Override
	public List<Proveedor> listaPorRuc(String ruc) {
		// TODO Auto-generated method stub
		return repository.findByRuc(ruc);
	}
	@Override
	public List<Proveedor> listaPorRazonSocial(String razonsocial) {
		// TODO Auto-generated method stub
	    return repository.findByRazonsocial(razonsocial);
	}
	@Override
	public List<Proveedor> listaPorCelular(String celular) {
		// TODO Auto-generated method stub
		return repository.findByCelular(celular);
	}
	@Override
	public List<Proveedor> listaPorContacto(String contacto) {
		// TODO Auto-generated method stub
		return repository.findByContacto(contacto);
	}
	@Override
	public List<Proveedor> listaCompleja(String razonsocial, String direccion, String ruc, String telefono,
			String celular, String contacto, int estado, int idPais, int idTipoProveedor) {
		// TODO Auto-generated method stub
		return repository.listaCompleja(razonsocial, direccion, ruc, telefono, celular, contacto, estado, idPais, idTipoProveedor);
	}
	
	
}