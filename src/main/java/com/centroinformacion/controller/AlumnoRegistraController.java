package com.centroinformacion.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired; // Inyección automática de dependencias
import org.springframework.http.ResponseEntity; // Respuesta HTTP personalizada
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.CrossOrigin; // Permite solicitudes desde dominios diferentes
import org.springframework.web.bind.annotation.GetMapping; // Mapea las solicitudes HTTP GET a métodos de controlador
import org.springframework.web.bind.annotation.PostMapping; // Mapea las solicitudes HTTP POST a métodos de controlador
import org.springframework.web.bind.annotation.RequestBody; // Convierte el cuerpo de la solicitud HTTP en un objeto Java
import org.springframework.web.bind.annotation.RequestMapping; // Mapea solicitudes web a métodos de controlador
import org.springframework.web.bind.annotation.ResponseBody; // Convierte el valor de retorno de un método en una respuesta HTTP
import org.springframework.web.bind.annotation.RestController; // Marca la clase como un controlador REST

import com.centroinformacion.entity.Alumno; // Clase de entidad Alumno
import com.centroinformacion.service.AlumnoService; // Clase de servicio AlumnoService
import com.centroinformacion.util.AppSettings; // Configuración de la aplicación

@RestController
@RequestMapping("/url/alumno") // Ruta base para este controlador
@CrossOrigin(origins = AppSettings.URL_CROSS_ORIGIN) // Permite solicitudes desde dominios específicos
public class AlumnoRegistraController {
	
	
	@Autowired // Inyección de dependencias de AlumnoService
	private AlumnoService alumnoService;
	
	
	 
	@GetMapping // Mapea solicitudes GET a este método
	@ResponseBody // Convierte la lista de alumnos en una respuesta HTTP
	public ResponseEntity<List<Alumno>> listaAlumno(){
		List<Alumno> lista = alumnoService.listaTodos(); // Obtiene la lista de todos los alumnos
		return ResponseEntity.ok(lista); // Devuelve la lista como respuesta HTTP 200 OK
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
	
	
	@PostMapping // Mapea solicitudes POST a este método
	@ResponseBody // Convierte el objeto de alumno en una respuesta HTTP
	public ResponseEntity<?> inserta(@RequestBody Alumno obj){
		HashMap<String, Object> salida = new HashMap<>(); // HashMap para la respuesta
		
		
		// Se establecen las fechas de registro y actualización del alumno
		obj.setFechaActualizacion(new Date());
		obj.setFechaRegistro(new Date());
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
		
		
		// Inserta o actualiza el alumno en la base de datos
		Alumno objSalida = alumnoService.insertaActualizaAlumno(obj);
		if (objSalida == null) {
			salida.put("mensaje","Error en el registro"); // Mensaje de error
		}else {
			salida.put("mensaje","Se registró el Alumno con el ID ==> " + objSalida.getIdAlumno()); // Mensaje de éxito
		}
		return ResponseEntity.ok(salida); // Devuelve la respuesta con el mensaje
	}
}
