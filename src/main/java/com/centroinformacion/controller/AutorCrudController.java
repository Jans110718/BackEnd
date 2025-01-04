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

import com.centroinformacion.entity.Autor;
import com.centroinformacion.service.AutorService;
import com.centroinformacion.util.AppSettings;

@RestController
@RequestMapping("/url/crudAutor")
@CrossOrigin(origins = AppSettings.URL_CROSS_ORIGIN)
public class AutorCrudController {
	@Autowired
	private AutorService autorService;
	
	
	@GetMapping("/listaAutorPorNombresLike/{var}")
	@ResponseBody
	public ResponseEntity<?> listaAutorPorNombresLike(@PathVariable("var") String nombres){
		List<Autor> lstSalida = null;
		if (nombres.equals("todos")) {
			lstSalida = autorService.listaAutor();
		}else {
			lstSalida = autorService.listaAutorPorNombresLike(nombres +  "%");
		}
		return ResponseEntity.ok(lstSalida);
	}
	
	@PostMapping("/registraAutor")
	@ResponseBody
	public ResponseEntity<?> insertaAutor(@RequestBody Autor obj) {
	    Map<String, Object> salida = new HashMap<>();
	    try {
	        obj.setIdAutor(0);
	        obj.setFechaActualizacion(new Date());
	        obj.setFechaRegistro(new Date());
	        obj.setEstado(AppSettings.ACTIVO);

	        // Verificar nombres
	        List<Autor> lstNombres = autorService.listaPorNombresYApellidosIgual(obj.getNombres(), obj.getApellidos());
	        if (!lstNombres.isEmpty()) {
	        	salida.put("mensaje", "El Autor " + obj.getNombres() + " " +  obj.getApellidos() +  " ya existe");
	            return ResponseEntity.ok(salida);
	        }

	        /*// Verificar apellidos
	        List<Autor> lstApellidos = autorService.listaAutorPorApellidosIgualRegistra(obj.getApellidos());
	        if (!lstApellidos.isEmpty()) {
	            salida.put("mensaje", "El Autor con el apellido " + obj.getApellidos() + " ya existe");
	            return ResponseEntity.ok(salida);
	        }*/

	        // Verificar número de celular
	        List<Autor> lstCelular = autorService.listaAutorPorCelularIgualRegistra(obj.getCelular());
	        if (!lstCelular.isEmpty()) {
	            salida.put("mensaje", "El número de celular " + obj.getCelular() + " ya está en uso");
	            return ResponseEntity.ok(salida);
	        }
	        
	        // Verificar número de teléfono
	        List<Autor> lstTelefono = autorService.listaAutorPorTelefonoIgualRegistra(obj.getTelefono());
	        if (!lstTelefono.isEmpty()) {
	            salida.put("mensaje", "El teléfono " + obj.getTelefono() + " ya está en uso");
	            return ResponseEntity.ok(salida);
	        }

	        // Si pasa todas las verificaciones, se procede con el registro
	        Autor objSalida = autorService.insertaActualizaAutor(obj);
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


	@PutMapping("/actualizaAutor")
	@ResponseBody
	public ResponseEntity<Map<String, Object>> actualizaAutor(@RequestBody Autor obj) {
		Map<String, Object> salida = new HashMap<>();
		try {
			
			obj.setFechaActualizacion(new Date());
			
			List<Autor> lstBusqueda = autorService.listaPorNombresApellidosIgualActualiza(obj.getNombres(),obj.getApellidos(), obj.getIdAutor());
			if(!lstBusqueda.isEmpty()) {
				salida.put("mensaje", "El Autor " + obj.getNombres() + " " +  obj.getApellidos() +  " ya existe");
				return ResponseEntity.ok(salida);
			}

	        /*// Verificar apellidos
	        List<Autor> lstApellidos = autorService.listaAutorPorApellidosIgualActualiza(obj.getApellidos(),obj.getIdAutor());
	        if (!lstApellidos.isEmpty()) {
	            salida.put("mensaje", "El Autor con el apellido " + obj.getApellidos() + " ya existe");
	            return ResponseEntity.ok(salida);
	        }*/

	        // Verificar número de celular
	        List<Autor> lstCelular = autorService.listaAutorPorCelularIgualActualiza(obj.getCelular(),obj.getIdAutor());
	        if (!lstCelular.isEmpty()) {
	            salida.put("mensaje", "El número de celular " + obj.getCelular() + " ya está en uso");
	            return ResponseEntity.ok(salida);
	        }
	        
	        // Verificar número de teléfono
	        List<Autor> lstTelefono = autorService.listaAutorPorTelefonoIgualActualiza(obj.getTelefono(),obj.getIdAutor());
	        if (!lstTelefono.isEmpty()) {
	            salida.put("mensaje", "El teléfono " + obj.getTelefono() + " ya está en uso");
	            return ResponseEntity.ok(salida);
			
	        }
	        
			Autor objSalida =  autorService.insertaActualizaAutor(obj);
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
	
	
	@DeleteMapping("/eliminaAutor/{id}")
	@ResponseBody
	public ResponseEntity<Map<String, Object>> eliminaAutor(@PathVariable("id") int idAutor) {
		Map<String, Object> salida = new HashMap<>();
		try {
			autorService.eliminaAutor(idAutor);
			salida.put("mensaje", AppSettings.MENSAJE_ELI_EXITOSO);
		} catch (Exception e) {
			e.printStackTrace();
			salida.put("mensaje", AppSettings.MENSAJE_ELI_ERROR);
		}
		return ResponseEntity.ok(salida);
	}
}