package com.centroinformacion.service;

import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.centroinformacion.entity.Editorial;
import com.centroinformacion.repository.EditorialRepository;

@Service
public class EditorialServiceImp implements EditorialService {
	@Autowired
	private EditorialRepository repository;

	@Override
	public Editorial insertaActualizaEditorial(Editorial obj) {
		return repository.save(obj);
	}

	@Override
	public List<Editorial> listaTodos() {
		return repository.findByOrderByRazonSocialAsc();
	}

	@Override
	public List<Editorial> listaEditorialPorRazonSocialLike(String razonSocial) {
		return repository.listaPorRazonSocialLike(razonSocial);
	}

	@Override
	public void eliminaEditorial(int idEditorial) {
		repository.deleteById(idEditorial);
	}

	@Override
	public List<Editorial> listaEditorialPorRazonSocialIgualRegistra(String razonSocial) {
		return repository.listaPorRazonSocialIgualRegistra(razonSocial);
	}

	@Override
	public List<Editorial> listaEditorialPorRazonSocialIgualActualiza(String razonSocial, int idEditorial) {
		return repository.listaPorRazonSocialIgualActualiza(razonSocial, idEditorial);
	}
	@Override
	public List<Editorial> listaPorRuc(String ruc) {
		return repository.findByRuc(ruc);
	}

	@Override
	public List<Editorial> listaPorRazonSocial(String razonSocial) {
		return repository.findByRazonSocial(razonSocial);
	}

	@Override
	public List<Editorial> listaCompleja(String razonSocial, String direccion, String ruc, String gerente, Date fecIni,
			Date fecFin, int estado, int idPais) {
		return repository.listaCompleja(razonSocial, direccion, ruc, gerente, fecIni, fecFin, estado, idPais);
	}

}