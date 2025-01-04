package com.centroinformacion.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.centroinformacion.entity.Libro;
import com.centroinformacion.service.LibroService;
import com.centroinformacion.util.AppSettings;

@RestController
@RequestMapping("/url/libro")
@CrossOrigin(origins = AppSettings.URL_CROSS_ORIGIN)
public class LibroRegistraController {

    @Autowired
    private LibroService libroService;

    @GetMapping
    @ResponseBody
    public ResponseEntity<List<Libro>> listaLibro() {
        List<Libro> lista = libroService.listaLibro();
        return ResponseEntity.ok(lista);
    }

    @PostMapping
    @ResponseBody
    public ResponseEntity<?> inserta(@RequestBody Libro obj) {
        HashMap<String, Object> salida = new HashMap<>();
        obj.setIdLibro(0);
        obj.setFechaActualizacion(new Date());
        obj.setFechaRegistro(new Date());
        obj.setEstado(AppSettings.ACTIVO);

        List<Libro> lstBusqueda = libroService.listaLibroPorTituloIgualRegistra(obj.getTitulo());
        if (!lstBusqueda.isEmpty()) {
            salida.put("mensaje", "El libro " + obj.getTitulo() + " ya existe");
            return ResponseEntity.ok(salida);
        }

        Libro objSalida = libroService.insertaActualizaLibro(obj);
        if (objSalida == null) {
            salida.put("mensaje", "Error en el registro");
        } else {
            salida.put("mensaje", "Se registró el Libro con el ID ==> " + objSalida.getIdLibro());
        }
        return ResponseEntity.ok(salida);
    }
}
