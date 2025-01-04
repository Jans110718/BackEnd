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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.centroinformacion.entity.Proveedor;
import com.centroinformacion.service.ProveedorService;
import com.centroinformacion.util.AppSettings;

@RestController

@RequestMapping("/url/crudProveedor")

@CrossOrigin(origins = AppSettings.URL_CROSS_ORIGIN)
public class ProveedorCrudController {
	@Autowired

	private ProveedorService proveedorService;

	@GetMapping("/listaProveedorPorRazonSocialLike/{var}")
	@ResponseBody
	public ResponseEntity<?> listaProveedorPorRazonSocial(@PathVariable("var") String razonsocial) {
		List<Proveedor> lstSalida = null;
		if (razonsocial.equals("todos")) {
			lstSalida = proveedorService.listaTodos();
		} else {
			lstSalida = proveedorService.listaProveedorPorRazonSocialLike(razonsocial + "%");
		}
		return ResponseEntity.ok(lstSalida);
	}

	@PostMapping("/registraProveedor")
	@ResponseBody
	public ResponseEntity<?> insertaProveedor(@RequestBody Proveedor obj) {
	    Map<String, Object> salida = new HashMap<>();
	    try {
	        obj.setIdProveedor(0);
	        obj.setFechaActualizacion(new Date());
	        obj.setFechaRegistro(new Date());
	        obj.setEstado(AppSettings.ACTIVO);
	        
	        
	        // Verificar si la razón social del proveedor ya existe
	        List<Proveedor> lstBusqueda = proveedorService.listaProveedorPorRazonSocialIgualRegistra(obj.getRazonsocial());
	        if (!lstBusqueda.isEmpty()) {
	            salida.put("mensaje", "El proveedor " + obj.getRazonsocial() + " ya existe");
	            return ResponseEntity.ok(salida);
	        }
	        
	        // Insertar o actualizar el proveedor
	        Proveedor objSalida = proveedorService.insertaActualizaProveedor(obj);
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


	@PutMapping("/actualizaProveedor")
	@ResponseBody
	public ResponseEntity<Map<String, Object>> actualizaProveedor(@RequestBody Proveedor obj) {
	    Map<String, Object> salida = new HashMap<>();
	    try {
	        obj.setFechaActualizacion(new Date());
	        
	        // Verifica si la razón social ya existe
	        List<Proveedor> lstBusquedaRazonSocial = proveedorService
	                .listaProveedorPorRazonSocialIgualActualiza(obj.getRazonsocial(), obj.getIdProveedor());
	        if (!lstBusquedaRazonSocial.isEmpty()) {
	            salida.put("mensaje", "El proveedor " + obj.getRazonsocial() + " ya existe");
	            return ResponseEntity.ok(salida);
	        }

	        // Actualiza el proveedor
	        Proveedor objSalida = proveedorService.insertaActualizaProveedor(obj);
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

	@DeleteMapping("/eliminaProveedor/{id}")
	@ResponseBody
	public ResponseEntity<Map<String, Object>> eliminaProveedor(@PathVariable("id") int idProveedor) {
		Map<String, Object> salida = new HashMap<>();
		try {
			proveedorService.eliminaProveedor(idProveedor);
			salida.put("mensaje", AppSettings.MENSAJE_ELI_EXITOSO);
		} catch (Exception e) {
			e.printStackTrace();
			salida.put("mensaje", AppSettings.MENSAJE_ELI_ERROR);
		}
		return ResponseEntity.ok(salida);

	}
	@GetMapping("/buscarProveedorPorRucActualiza")
	@ResponseBody
	public String validaRucActualiza(String ruc) {
		List<Proveedor> lst = proveedorService.listaPorRuc(ruc);
		if(CollectionUtils.isEmpty(lst)) {
			return "{\"valid\":true}";
		}else {
			return "{\"valid\":false}";
		}
	}
	@GetMapping("/buscarProveedorPorRazonSocialActualiza")
	@ResponseBody
	public String validaRazonSocialActualiza(String razonsocial) {
	    List<Proveedor> lst = proveedorService.listaPorRazonSocial(razonsocial);
	    if (CollectionUtils.isEmpty(lst)) {
	        return "{\"valid\":true}";
	    } else {
	        return "{\"valid\":false}";
	    }
	}
	@GetMapping("/buscarProveedorPorCelularActualiza")
	@ResponseBody
	public String validaCelularActualiza(String celular) {
	    List<Proveedor> lst = proveedorService.listaPorCelular(celular);
	    if (CollectionUtils.isEmpty(lst)) {
	        return "{\"valid\":true}";
	    } else {
	        return "{\"valid\":false}";
	    }
	}
	@GetMapping("/buscarProveedorPorContactoActualiza")
	@ResponseBody
	public String validaContactoActualiza(String contacto) {
	    List<Proveedor> lst = proveedorService.listaPorContacto(contacto);
	    if (CollectionUtils.isEmpty(lst)) {
	        return "{\"valid\":true}";
	    } else {
	        return "{\"valid\":false}";
	    }
	}

}
