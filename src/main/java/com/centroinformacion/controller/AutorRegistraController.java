package com.centroinformacion.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.centroinformacion.entity.Autor;
import com.centroinformacion.service.AutorService;
import com.centroinformacion.util.AppSettings;


@RestController
@RequestMapping("/url/autor")
@CrossOrigin(origins = AppSettings.URL_CROSS_ORIGIN)
public class AutorRegistraController {

	@Autowired
	private AutorService autorService;
	
	@GetMapping
	@ResponseBody
	public ResponseEntity<List<Autor>> listaAutor(){
		List<Autor> lista = autorService.listaAutor();
		return ResponseEntity.ok(lista);
	}
	
	@PostMapping
	@ResponseBody
	public ResponseEntity<?> inserta(@RequestBody Autor obj){
		HashMap<String, Object> salida = new HashMap<>();
		obj.setFechaActualizacion(new Date());
		obj.setFechaRegistro(new Date());
		obj.setEstado(AppSettings.ACTIVO);
		
		
		Autor objSalida = autorService.insertaActualizaAutor(obj);
		if (objSalida == null) {
			salida.put("mensaje","Error en el registro");
		}else {
			salida.put("mensaje","Se registró el Autor con el ID ==> " + objSalida.getIdAutor());
		}
		return ResponseEntity.ok(salida);
	}
	
	
}
