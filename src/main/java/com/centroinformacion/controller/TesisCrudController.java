package com.centroinformacion.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.centroinformacion.entity.Tesis;
import com.centroinformacion.service.TesisService;
import com.centroinformacion.util.AppSettings;

@RestController
@RequestMapping("/url/crudTesis")
@CrossOrigin(origins = AppSettings.URL_CROSS_ORIGIN)
public class TesisCrudController {
	
	@Autowired
	private TesisService tesisService;
	
	
	@GetMapping("/listaTesisPorTituloLike/{var}")
	@ResponseBody
	public ResponseEntity<?> listaTesisPorTituloLike(@PathVariable("var") String titulo){
		List<Tesis> lstSalida = null;
		if (titulo.equals("todos")) {
			lstSalida = tesisService.listaTesis();
		}else {
			lstSalida = tesisService.listaTesisPorTituloLike(titulo +  "%");
		}
		return ResponseEntity.ok(lstSalida);
	}
	
	@PostMapping("/registraTesis")
	@ResponseBody
	public ResponseEntity<?> insertaTesis(@RequestBody Tesis obj) {
		Map<String, Object> salida = new HashMap<>();
		try {
			obj.setIdTesis(0);
			obj.setFechaActualizacion(new Date());
			obj.setFechaRegistro(new Date());
			obj.setEstado(AppSettings.ACTIVO);
			
			List<Tesis> lstBusqueda = tesisService.listaTesisPorTituloIgualRegistra(obj.getTitulo());
			if(!lstBusqueda.isEmpty()) {
				salida.put("mensaje", "La Tesis " + obj.getTitulo() + " ya existe");
				return ResponseEntity.ok(salida);
			}
			
			Tesis objSalida =  tesisService.insertaActualizaTesis(obj);
			if (objSalida == null) {
				salida.put("mensaje", AppSettings.MENSAJE_REG_ERROR);
			} else {
				salida.put("mensaje", AppSettings.MENSAJE_REG_EXITOSO);
			}
		} catch (Exception e) {
			e.printStackTrace();
			salida.put("mensaje", AppSettings.MENSAJE_REG_ERROR);
		}
		return ResponseEntity.ok(salida);
	}

	@PutMapping("/actualizaTesis")
	@ResponseBody
	public ResponseEntity<Map<String, Object>> actualizaTesis(@RequestBody Tesis obj) {
		Map<String, Object> salida = new HashMap<>();
		try {
			
			obj.setFechaActualizacion(new Date());
			
			List<Tesis> lstBusqueda = tesisService.listaTesisPorTituloIgualActualiza(obj.getTitulo(), obj.getIdTesis());
			if(!lstBusqueda.isEmpty()) {
				salida.put("mensaje", "La Tesis " + obj.getTitulo() + " ya existe");
				return ResponseEntity.ok(salida);
			}
			
			Tesis objSalida =  tesisService.insertaActualizaTesis(obj);
			if (objSalida == null) {
				salida.put("mensaje", AppSettings.MENSAJE_ACT_ERROR);
			} else {
				salida.put("mensaje", AppSettings.MENSAJE_ACT_EXITOSO);
			}
		} catch (Exception e) {
			e.printStackTrace();
			salida.put("mensaje", AppSettings.MENSAJE_ACT_ERROR);
		}
		return ResponseEntity.ok(salida);
	}
	
	
	@DeleteMapping("/eliminaTesis/{id}")
	@ResponseBody
	public ResponseEntity<Map<String, Object>> eliminaTesis(@PathVariable("id") int idTesis) {
		Map<String, Object> salida = new HashMap<>();
		try {
			tesisService.eliminaTesis(idTesis);
			salida.put("mensaje", AppSettings.MENSAJE_ELI_EXITOSO);
		} catch (Exception e) {
			e.printStackTrace();
			salida.put("mensaje", AppSettings.MENSAJE_ELI_ERROR);
		}
		return ResponseEntity.ok(salida);
	}
	
}
