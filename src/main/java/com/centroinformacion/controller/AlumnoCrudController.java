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

import com.centroinformacion.entity.Alumno;
import com.centroinformacion.service.AlumnoService;
import com.centroinformacion.util.AppSettings;

@RestController
@RequestMapping("/url/crudAlumno")
@CrossOrigin(origins = AppSettings.URL_CROSS_ORIGIN)

public class AlumnoCrudController {
		
	@Autowired
		private AlumnoService alumnoService;
	
	
	@GetMapping("/listaAlumno")
	@ResponseBody
	public ResponseEntity<List<Alumno>> listaAlumno(){
		List<Alumno> lista = alumnoService.listaTodos();
		return ResponseEntity.ok(lista);
	}
	
	@GetMapping("/buscaPorNombreIgual")
	@ResponseBody
	public String validaTitulo(String titulo) {
		List<Alumno> lst = alumnoService.validacionListaPorTitutloIgualRegistra(titulo);
		if(CollectionUtils.isEmpty(lst)) {
			return "{\"valid\":true}";
		}else {
			return "{\"valid\":false}";
		}
	}
	
	@GetMapping("/listaAlumnoPorNombreLike/{var}")
	@ResponseBody
	public ResponseEntity<?> listaAlumnoPortituloLike(@PathVariable("var") String titulo){
		List<Alumno> lstSalida = null;
		
		if (titulo.equals("todos")) {
			lstSalida = alumnoService.listaAlumnoTodos();
		}else {
			lstSalida = alumnoService.listaAlumnoPorTituloLike(titulo +  "%");
		}
		return ResponseEntity.ok(lstSalida);
	}
	
	
	@PostMapping("/registraAlumno")
	@ResponseBody
	public ResponseEntity<?> insertaAlumno(@RequestBody Alumno obj){
		Map<String, Object> salida = new HashMap<>();
		try {
			
			obj.setFechaRegistro(new Date());
			obj.setFechaActualizacion(new Date());
			obj.setEstado(AppSettings.ACTIVO);
			
			List<Alumno> lstBusqueda = alumnoService.listaAlumnoPorNombreIgualRegistro(obj.getNombres());
			if(!lstBusqueda.isEmpty()) {
				salida.put("mensaje", "El nombre " + obj.getNombres() + " ya existe");
				return ResponseEntity.ok(salida);
			}
			
			List<Alumno> lstBusquedaApellido = alumnoService.listaAlumnoPorApellidoIgualRegistro(obj.getApellidos());
			if(!lstBusquedaApellido.isEmpty()) {
				salida.put("mensaje", "El apellido " + obj.getApellidos() + " ya existe");
				return ResponseEntity.ok(salida);
			}
			
			List<Alumno> lstBusquedaTelefono = alumnoService.listaPorTelefono(obj.getTelefono());
			if(!lstBusquedaTelefono.isEmpty()) {
				salida.put("mensaje", "El teléfono " + obj.getTelefono() + " ya existe");
				return ResponseEntity.ok(salida);
			}
			List<Alumno> lstBusquedaDni = alumnoService.listaPorDni(obj.getDni());
			if(!lstBusquedaDni.isEmpty()) {
				salida.put("mensaje", "El Dni " + obj.getDni() + " ya existe");
				return ResponseEntity.ok(salida);
			}
			
			Alumno objSalida = alumnoService.insertaActualizaAlumno(obj);
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
	
	@PutMapping("/actualizaAlumno")
	@ResponseBody
	public ResponseEntity<Map<String, Object>> actualizaAlumno(@RequestBody Alumno obj){
		Map<String, Object> salida = new HashMap<>();
		try {
			
			obj.setFechaActualizacion(new Date());
			
			List<Alumno> lstBusqueda = alumnoService.listaAlumnoPorTituloIgualActualiza(obj.getNombres(), obj.getIdAlumno());
			if(!lstBusqueda.isEmpty()) {
				salida.put("mensaje", "El Alumno " + obj.getNombres() + " ya existe");
				return ResponseEntity.ok(salida);
			}
			
			Alumno objSalida =  alumnoService.insertaActualizaAlumno(obj);
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
	
	@DeleteMapping("/eliminaAlumno/{id}")
	@ResponseBody
	public ResponseEntity<Map<String, Object>> eliminaAlumno(@PathVariable("id") int idAlumno) {
		Map<String, Object> salida = new HashMap<>();
		try {
			
			alumnoService.eliminaAlumno(idAlumno);
			salida.put("mensaje", AppSettings.MENSAJE_ELI_EXITOSO);
		} catch (Exception e) {
			e.printStackTrace();
			salida.put("mensaje", AppSettings.MENSAJE_ELI_ERROR);
		}
		return ResponseEntity.ok(salida);
	}
	
	
	
}
