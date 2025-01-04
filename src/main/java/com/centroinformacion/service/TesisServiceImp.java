package com.centroinformacion.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.centroinformacion.entity.Tesis;
import com.centroinformacion.repository.TesisRepository;

@Service
public class TesisServiceImp implements TesisService{
	
	@Autowired	
	private TesisRepository repository;
	
	@Override
	public Tesis insertaActualizaTesis(Tesis obj) {
		return repository.save(obj);
	}
	
	@Override
	public List<Tesis> listaTesis() {
		return repository.findAll();
	}

	@Override
	public List<Tesis> listaTesisPorTituloLike(String titulo) {
		return repository.listaPorTituloLike(titulo);
	}

	@Override
	public void eliminaTesis(int idTesis) {
		repository.deleteById(idTesis);
		
	}

	@Override
	public List<Tesis> listaTesisPorTituloIgualRegistra(String titulo) {
		return repository.listaPorTituloIgualRegistra(titulo);
	}

	@Override
	public List<Tesis> listaTesisPorTituloIgualActualiza(String titulo, int idTesis) {
		return repository.listaPorTituloIgualActualiza(titulo, idTesis);
	}

	@Override
	public List<Tesis> listaConsultaCompleja(String titulo, Date fecIni, Date fecFin, int estado, int idTema,
			int idIdioma, int idCentroEstudios) {
		return repository.listaConsultaCompleja(titulo, fecIni, fecFin, estado, idTema, idIdioma, idCentroEstudios);
	}
}
