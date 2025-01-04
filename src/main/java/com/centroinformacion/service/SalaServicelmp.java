package com.centroinformacion.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.centroinformacion.entity.Sala;
import com.centroinformacion.repository.SalaRepository;
//AUTOR : SIFUENTES GAMBINI CESAR JAREN

@Service
public class SalaServicelmp implements SalaService {
	@Autowired
	private SalaRepository repository;
	
	@Override
	public Sala insertaActualizaSala(Sala obj) {
		return repository.save(obj);
	}

	@Override
	public List<Sala> listaTodos() {
		return repository.findAll();
	}

	@Override
	public List<Sala> listaSalaPorNumeroLike(String numero) {
		return repository.listaPorNumeroLike(numero);

	}

	@Override
	public List<Sala> listaSalaPorNumeroIgualRegistra(String numero) {
		return repository.listaPorNumeroIgualRegistra(numero);

	}

	@Override
	public List<Sala> listaSalaPorNombreIgualActualiza(String numero, int idSala) {
		return repository.listaPorNumeroIgualActualiza(numero, idSala);
	}

	@Override
	public void eliminaSala(int idSala) {
		repository.deleteById(idSala);
		
	}

	@Override
	public List<Sala> listaConsultaCompleja(String numero, int piso, int numAlumnos, String recursos, int estado,
			int idTipoSala, int idSede, int idEstadoReserva) {

		return repository.listaConsultaCompleja(numero, piso, numAlumnos, recursos, estado, idTipoSala, idSede, idEstadoReserva);
	}
}