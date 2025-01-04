package com.centroinformacion.entity;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.centroinformacion.util.FunctionUtil;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "tesis")
public class Tesis {
  

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int idTesis;
	private String titulo;
	
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd" , timezone = "America/Lima")
	private Date fechaCreacion;

	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss")
	private Date fechaRegistro;

	
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss")
	private Date fechaActualizacion;
	
	private int estado;

	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idTema")
	private DataCatalogo tema;
	
	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idIdioma")
	private DataCatalogo idioma;
	
	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idCentroEstudios")
	private DataCatalogo centroEstudios;
	
	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idUsuarioRegistro")
	private Usuario usuarioRegistro;
	
	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idUsuarioActualiza")
	private Usuario usuarioActualiza;
	
	
	
	
	//PARA EL REPORTE
	public String getReporteEstado() {
		return estado == 1 ? "Activo" : "Inactivo";
	}
	
	public String getReporteFecha() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return sdf.format(fechaRegistro);
	}
	public String getReporteTema() {
		return tema.getDescripcion();
	}
	public String getReporteIdioma() {
		return idioma.getDescripcion();
	}
	public String getReporteCentroEstudios() {
		return centroEstudios.getDescripcion();
	}
	public String getReporteFechaCreacion() {
		return FunctionUtil.getFechaString(fechaCreacion);
	}
   
}
