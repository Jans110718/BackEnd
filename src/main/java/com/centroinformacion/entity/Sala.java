package com.centroinformacion.entity;

import java.util.Date;

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
@Table(name = "sala")
public class Sala {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int idSala;
	private String numero;
	private int piso;
	private int numAlumnos;
	private String recursos;
	
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss")
	private Date fechaRegistro;

	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss")
	private Date fechaActualizacion;
	
	private int estado;
	
	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idTipoSala")
	private DataCatalogo tipoSala;

	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idSede")
	private DataCatalogo sede;
		
	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idUsuarioRegistro")
	private Usuario usuarioRegistro;
	
	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idUsuarioActualiza")
	private Usuario usuarioActualiza;
	
	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idEstadoReserva")
	private DataCatalogo estadoReserva;

	
	// Para el reportes
		public String getReporteEstado() {
			return estado == 1 ? "Activo" : "Inactivo";
		}
		public String getReporteTipoSala() {
			return tipoSala.getIdDataCatalogo()+"-"+tipoSala.getDescripcion();
		}
		public String getReporteSede() {
			return sede.getIdDataCatalogo()+"-"+sede.getDescripcion();
		}
		public String getReporteEstadoReserva() {
			return estadoReserva.getIdDataCatalogo()+"-"+estadoReserva.getDescripcion();
		}
	}