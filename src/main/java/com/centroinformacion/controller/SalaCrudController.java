package com.centroinformacion.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
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
import com.centroinformacion.entity.Sala;
import com.centroinformacion.service.SalaService;
import com.centroinformacion.util.AppSettings;

@RestController
@RequestMapping("/url/crudSala")
@CrossOrigin(origins = AppSettings.URL_CROSS_ORIGIN)

public class SalaCrudController {
	@Autowired
	private SalaService salaService;
	
	
	@GetMapping("/listaSalaPorNumeroLike/{var}")
	@ResponseBody
	public ResponseEntity<?> listaSalaPorNumeroLike(@PathVariable("var") String numero){
		List<Sala> lstSalida = null;
		if (numero.equals("todos")) {
			lstSalida = salaService.listaTodos();
		}else {
			lstSalida = salaService.listaSalaPorNumeroLike(numero +  "%");
		}
		return ResponseEntity.ok(lstSalida);
	}
	
	@PostMapping("/registraSala")
	@ResponseBody
	public ResponseEntity<?> insertaSala(@RequestBody Sala obj) {
		Map<String, Object> salida = new HashMap<>();
		try {
			obj.setIdSala(0);
			obj.setFechaActualizacion(new Date());
			obj.setFechaRegistro(new Date());
			obj.setEstado(AppSettings.ACTIVO);
			
			List<Sala> lstBusqueda = salaService.listaSalaPorNumeroIgualRegistra(obj.getNumero());
			if(!lstBusqueda.isEmpty()) {
				salida.put("mensaje", "El Número " + obj.getNumero() + " ya existe");
				return ResponseEntity.ok(salida);
			}
			
			Sala objSalida =  salaService.insertaActualizaSala(obj);
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
	
	

	@GetMapping("/buscaPorNumeroIgual")
	@ResponseBody
	public String validaNumero(String numero) {
		List<Sala> lst = salaService.listaSalaPorNumeroIgualRegistra(numero);
		if(CollectionUtils.isEmpty(lst)) {
			return "{\"valid\":true}";
		}else {
			return "{\"valid\":false}";
		}
	}
	
	
	
	@PutMapping("/actualizaSala")
	@ResponseBody
	public ResponseEntity<Map<String, Object>> actualizaSala(@RequestBody Sala obj) {
		Map<String, Object> salida = new HashMap<>();
		try {
			
			obj.setFechaActualizacion(new Date());
			
			List<Sala> lstBusqueda = salaService.listaSalaPorNombreIgualActualiza(obj.getNumero(), obj.getIdSala());
			if(!lstBusqueda.isEmpty()) {
				salida.put("mensaje", "El Número " + obj.getNumero() + " ya existe");
				return ResponseEntity.ok(salida);
			}
			
			Sala objSalida =  salaService.insertaActualizaSala(obj);
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
	
	
	@DeleteMapping("/eliminaSala/{id}")
	@ResponseBody
	public ResponseEntity<Map<String, Object>> eliminaSala(@PathVariable("id") int idSala) {
		Map<String, Object> salida = new HashMap<>();
		try {
			salaService.eliminaSala(idSala);
			salida.put("mensaje", AppSettings.MENSAJE_ELI_EXITOSO);
		} catch (Exception e) {
			e.printStackTrace();
			salida.put("mensaje", AppSettings.MENSAJE_ELI_ERROR);
		}
		return ResponseEntity.ok(salida);
	}

}
