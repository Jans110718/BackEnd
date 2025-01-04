package com.centroinformacion.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.centroinformacion.entity.Alumno;
import com.centroinformacion.service.AlumnoService;
import com.centroinformacion.util.AppSettings;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;

@RestController
@RequestMapping("/url/consultaAlumno")
@CrossOrigin(origins = AppSettings.URL_CROSS_ORIGIN)
public class AlumnoConsultaController {

	@Autowired
	private AlumnoService alumnoService;
	
	@GetMapping("/consultaAlumnoPorParametros")
	@ResponseBody
	public ResponseEntity<?> consultaAlumnoPorParametros(
			@RequestParam(name = "nombres", required = true, defaultValue = "") String nombres,
	        @RequestParam(name = "apellidos", required = true, defaultValue = "") String apellidos,
	        @RequestParam(name = "telefono", required = true, defaultValue = "") String telefono,
	        @RequestParam(name = "celular", required = true, defaultValue = "") String celular,
	        @RequestParam(name = "dni", required = true, defaultValue = "") String dni,
	        @RequestParam(name = "correo", required = true, defaultValue = "") String correo,
	        @RequestParam(name = "tipoSangre", required = true, defaultValue = "") String tipoSangre,
	        @RequestParam(name = "fechaNacimientoDesde", required = true, defaultValue = "") @DateTimeFormat(pattern = "yyyy-MM-dd") Date fechaNacimientoDesde,
	        @RequestParam(name = "fechaNacimientoHasta", required = true, defaultValue = "") @DateTimeFormat(pattern = "yyyy-MM-dd") Date fechaNacimientoHasta,
	        @RequestParam(name = "estado", required = true, defaultValue = "") int estado,
	        @RequestParam(name = "idPais", required = false, defaultValue = "-1") int idPais,
	        @RequestParam(name = "idModalidad", required = false, defaultValue = "-1") int idModalidad
			){
		
		List<Alumno> lstSalida = alumnoService.listaConsultaCompleja(
				"%" + nombres + "%",
				"%" + apellidos+ "%",
				"%" + telefono+ "%",
				"%" + celular+ "%",
				"%" + dni+ "%",
				"%" + correo+ "%",
				"%" + tipoSangre+ "%",
				fechaNacimientoDesde,
				fechaNacimientoHasta,
				estado,
				idPais,
				idModalidad);
		
		return ResponseEntity.ok(lstSalida);
	}
	//Reporte excel.
		private static String[] HEADERs = {"CODIGO","NOMBRES","APELLIDOS","TELÉFONO","CELULAR","DNI","CORREO","TIPO DE SANGRE","FECHA DE NACIMIENTO","PAIS","MODALIDAD","ESTADO"};
		private static String SHEET = "Listado de Alumno";
		private static String TITLE = "Listado de Alumno - Autor: Lionel Orihuela";
		private static int[] HEADER_WITH = {3000,9000,10000,6000,6000,8000,10000,3000,10000,8000,8000,6000};
		
		@PostMapping("/reporteAlumnoExcel")
		public void reporteExcel(@RequestParam(name = "nombres", required = true, defaultValue = "") String nombres,
				@RequestParam(name = "apellidos", required = true, defaultValue = "") String apellidos,
				@RequestParam(name = "telefono", required = true, defaultValue = "") String telefono,
				@RequestParam(name = "celular", required = true, defaultValue = "") String celular,
				@RequestParam(name = "dni", required = true, defaultValue = "") String dni,
				@RequestParam(name = "correo", required = true, defaultValue = "") String correo,
				@RequestParam(name = "tipoSangre", required = true, defaultValue = "") String tipoSangre,
				@RequestParam(name = "fechaNacimientoDesde", required = true, defaultValue = "") @DateTimeFormat(pattern = "yyyy-MM-dd") Date fechaNacimientoDesde,
				@RequestParam(name = "fechaNacimientoHasta", required = true, defaultValue = "") @DateTimeFormat(pattern = "yyyy-MM-dd") Date fechaNacimientoHasta,
				@RequestParam(name = "estado", required = true, defaultValue = "") int estado,
				@RequestParam(name = "idPais", required = false, defaultValue = "-1") int idPais,
				@RequestParam(name = "idModalidad", required = false, defaultValue = "-1") int idModalidad,
				HttpServletRequest request, HttpServletResponse response) {
			try (Workbook excel = new XSSFWorkbook()) {
				// Se crear la hoja del Excel
				Sheet hoja = excel.createSheet(SHEET);

				// Agrupar
				hoja.addMergedRegion(new CellRangeAddress(0, 0, 0, HEADER_WITH.length - 1));

				// Se establece el ancho de las columnas
				for (int i = 0; i < HEADER_WITH.length; i++) {
					hoja.setColumnWidth(i, HEADER_WITH[i]);
				}

				// Fuenta
				Font fuente = excel.createFont();
				fuente.setFontHeightInPoints((short) 10);
				fuente.setFontName("Arial");
				fuente.setBold(true);
				fuente.setColor(IndexedColors.WHITE.getIndex());

				// Estilo
				CellStyle estiloCeldaCentrado = excel.createCellStyle();
				estiloCeldaCentrado.setWrapText(true);
				estiloCeldaCentrado.setAlignment(HorizontalAlignment.CENTER);
				estiloCeldaCentrado.setVerticalAlignment(VerticalAlignment.CENTER);
				estiloCeldaCentrado.setFont(fuente);
				estiloCeldaCentrado.setFillForegroundColor(IndexedColors.DARK_BLUE.getIndex());
				estiloCeldaCentrado.setFillPattern(FillPatternType.SOLID_FOREGROUND);

				// Estilo para datos
				CellStyle estiloDatosCentrado = excel.createCellStyle();
				estiloDatosCentrado.setAlignment(HorizontalAlignment.CENTER);
				estiloDatosCentrado.setVerticalAlignment(VerticalAlignment.CENTER);
				estiloDatosCentrado.setBorderBottom(BorderStyle.THIN);
				estiloDatosCentrado.setBorderTop(BorderStyle.THIN);
				estiloDatosCentrado.setBorderLeft(BorderStyle.THIN);
				estiloDatosCentrado.setBorderRight(BorderStyle.THIN);

				CellStyle estiloDatosIzquierdo = excel.createCellStyle();
				estiloDatosIzquierdo.setAlignment(HorizontalAlignment.LEFT);
				estiloDatosIzquierdo.setVerticalAlignment(VerticalAlignment.CENTER);
				estiloDatosIzquierdo.setBorderBottom(BorderStyle.THIN);
				estiloDatosIzquierdo.setBorderTop(BorderStyle.THIN);
				estiloDatosIzquierdo.setBorderLeft(BorderStyle.THIN);
				estiloDatosIzquierdo.setBorderRight(BorderStyle.THIN);

				// Fila 0
				Row fila1 = hoja.createRow(0);
				Cell celAuxs = fila1.createCell(0);
				celAuxs.setCellStyle(estiloCeldaCentrado);
				celAuxs.setCellValue(TITLE);

				// Fila 1
				Row fila2 = hoja.createRow(1);
				Cell celAuxs2 = fila2.createCell(0);
				celAuxs2.setCellValue("");

				// Fila 2
				Row fila3 = hoja.createRow(2);
				for (int i = 0; i < HEADERs.length; i++) {
					Cell celda1 = fila3.createCell(i);
					celda1.setCellStyle(estiloCeldaCentrado);
					celda1.setCellValue(HEADERs[i]);
				}

				// formato para fecha
				SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
				List<Alumno> lstSalida = alumnoService.listaConsultaCompleja("%" + nombres + "%", "%" + apellidos + "%",
						"%" + telefono + "%", "%" + celular + "%", "%" + dni + "%", "%" + correo + "%",
						"%" + tipoSangre + "%", fechaNacimientoDesde, fechaNacimientoHasta, estado, idPais,
						idModalidad);

				// Filas de datos
				int rowIdx = 3;
				for (Alumno obj : lstSalida) {
					Row row = hoja.createRow(rowIdx++);

					Cell cel0 = row.createCell(0);
					cel0.setCellValue(obj.getIdAlumno());
					cel0.setCellStyle(estiloDatosCentrado);

					Cell cel1 = row.createCell(1);
					cel1.setCellValue(obj.getNombres());
					cel1.setCellStyle(estiloDatosCentrado);

					Cell cel2 = row.createCell(2);
					cel2.setCellValue(obj.getApellidos());
					cel2.setCellStyle(estiloDatosCentrado);

					Cell cel3 = row.createCell(3);
					cel3.setCellValue(obj.getTelefono());
					cel3.setCellStyle(estiloDatosCentrado);

					Cell cel4 = row.createCell(4);
					cel4.setCellValue(obj.getCelular());
					cel4.setCellStyle(estiloDatosCentrado);

					Cell cel5 = row.createCell(5);
					cel5.setCellValue(obj.getDni());
					cel5.setCellStyle(estiloDatosCentrado);

					Cell cel6 = row.createCell(6);
					cel6.setCellValue(obj.getCorreo());
					cel6.setCellStyle(estiloDatosCentrado);

					Cell cel7 = row.createCell(7);
					cel7.setCellValue(obj.getTipoSangre());
					cel7.setCellStyle(estiloDatosCentrado);

					Cell cel8 = row.createCell(8);
					cel8.setCellValue(sdf.format(obj.getFechaNacimiento()));
					cel8.setCellStyle(estiloDatosCentrado);

					Cell cel9 = row.createCell(9);
				    cel9.setCellValue(obj.getPais().getNombre());
				    cel9.setCellStyle(estiloDatosCentrado);

				    Cell cel10 = row.createCell(10);
				    cel10.setCellValue(obj.getModalidad().getDescripcion());
				    cel10.setCellStyle(estiloDatosCentrado);

				    Cell cel11 = row.createCell(11);
				    cel11.setCellValue(obj.getEstado() == 1 ? AppSettings.ACTIVO_DESC : AppSettings.INACTIVO_DESC);
				    cel11.setCellStyle(estiloDatosCentrado);
				}
				//Tipo de archivo y nombre de archivp
				response.setContentType("aplication/vnd.ms-excel");
				response.addHeader("Content-disposition", "attachment; filename=ReporteAlumno.xlsx");
				
				OutputStream outStream= response.getOutputStream();
				excel.write(outStream);
				outStream.close();

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	
		@PostMapping("/reporteAlumnoPDF")
		public void reportePDF(@RequestParam(name = "nombres", required = true, defaultValue = "") String nombres,
				@RequestParam(name = "apellidos", required = true, defaultValue = "") String apellidos,
				@RequestParam(name = "telefono", required = true, defaultValue = "") String telefono,
				@RequestParam(name = "celular", required = true, defaultValue = "") String celular,
				@RequestParam(name = "dni", required = true, defaultValue = "") String dni,
				@RequestParam(name = "correo", required = true, defaultValue = "") String correo,
				@RequestParam(name = "tipoSangre", required = true, defaultValue = "") String tipoSangre,
				@RequestParam(name = "fechaNacimientoDesde", required = true, defaultValue = "") @DateTimeFormat(pattern = "yyyy-MM-dd") Date fechaNacimientoDesde,
				@RequestParam(name = "fechaNacimientoHasta", required = true, defaultValue = "") @DateTimeFormat(pattern = "yyyy-MM-dd") Date fechaNacimientoHasta,
				@RequestParam(name = "estado", required = true, defaultValue = "") int estado,
				@RequestParam(name = "idPais", required = false, defaultValue = "-1") int idPais,
				@RequestParam(name = "idModalidad", required = false, defaultValue = "-1") int idModalidad,
				HttpServletRequest request, HttpServletResponse response) {
			try {
				//PASO 1 Fuente de datos
				List<Alumno> lstSalida = alumnoService.listaConsultaCompleja("%" + nombres + "%", "%" + apellidos + "%",
						"%" + telefono + "%", "%" + celular + "%", "%" + dni + "%", "%" + correo + "%",
						"%" + tipoSangre + "%", fechaNacimientoDesde, fechaNacimientoHasta, estado, idPais,
						idModalidad);
				
				JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(lstSalida);

				//PASO 2 Diseño de reporte
				String fileReporte = request.getServletContext().getRealPath("/reporteAlumno.jasper");
				
				//PASO 3 parametros adicionales
				Map<String, Object> params = new HashMap<String, Object>();
				
				//PASO 4
				JasperReport jasperReport = (JasperReport) JRLoader.loadObject(new FileInputStream(new File(fileReporte)));
				JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, params, dataSource);
				
				// PASO 5 parametros en el Header del mensajes HTTP
				response.setContentType("application/pdf");
				response.addHeader("Content-disposition", "attachment; filename=ReporteAlumno.pdf");
				
				// PASO 6 Se envia el pdf
				OutputStream outStream = response.getOutputStream();
				JasperExportManager.exportReportToPdfStream(jasperPrint, outStream);
				
			} catch (Exception e) {
				e.printStackTrace();
			}	
		}
}
