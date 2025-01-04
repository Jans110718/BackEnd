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

import com.centroinformacion.entity.Tesis;
import com.centroinformacion.service.TesisService;
import com.centroinformacion.util.AppSettings;


@RestController
@RequestMapping("/url/tesis")
@CrossOrigin(origins = AppSettings.URL_CROSS_ORIGIN)
public class TesisRegistraController {
	
	@Autowired
	private TesisService TesisService;
	
	@GetMapping
	@ResponseBody
	public ResponseEntity<List<Tesis>> listaeTesis(){
		List<Tesis> lista = TesisService.listaTesis();
		return ResponseEntity.ok(lista);
	}
	
	
	@PostMapping
	@ResponseBody
	public ResponseEntity<?> inserta(@RequestBody Tesis obj){
		HashMap<String, Object> salida = new HashMap<>();
		
		obj.setFechaActualizacion(new Date());
		obj.setFechaRegistro(new Date());
		obj.setEstado(AppSettings.ACTIVO);
		
		
		Tesis objSalida = TesisService.insertaActualizaTesis(obj);
		if (objSalida == null) {
			salida.put("mensaje","Error en el registro");
		}else {
			salida.put("mensaje","Se registró la Tesis con el ID ==> " + objSalida.getIdTesis());
		}
		return ResponseEntity.ok(salida);
	}
	
}