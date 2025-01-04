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
import com.centroinformacion.entity.Proveedor;
import com.centroinformacion.service.ProveedorService;
import com.centroinformacion.util.AppSettings;
@RestController
@RequestMapping("/url/Proveedor")
@CrossOrigin(origins = AppSettings.URL_CROSS_ORIGIN)
public class ProveedorRegistraController {
	@Autowired
	private ProveedorService ProveedorService;
	
	@GetMapping
	@ResponseBody
	public ResponseEntity<List<Proveedor>> listaeProveedor(){
		List<Proveedor> lista = ProveedorService.listaTodos();
		return ResponseEntity.ok(lista);
	}
	@PostMapping
	@ResponseBody
	public ResponseEntity<?> inserta(@RequestBody Proveedor obj){
		HashMap<String, Object> salida = new HashMap<>();
		
		obj.setFechaActualizacion(new Date());
		obj.setFechaRegistro(new Date());
		obj.setEstado(AppSettings.ACTIVO);	
		Proveedor objSalida = ProveedorService.insertaActualizaProveedor(obj);
		if (objSalida == null) {
			salida.put("mensaje","Error en el registro");
		}else {
			salida.put("mensaje","Se registró el Proveedor con el ID ==> " + objSalida.getIdProveedor());
		}
		return ResponseEntity.ok(salida);
	}
	
}