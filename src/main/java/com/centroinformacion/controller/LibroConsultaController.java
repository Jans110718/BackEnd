package com.centroinformacion.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
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
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.centroinformacion.entity.Libro;
import com.centroinformacion.service.LibroService;
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
@RequestMapping("/url/consultaLibro")
@CrossOrigin(origins = AppSettings.URL_CROSS_ORIGIN)
public class LibroConsultaController {
    
    @Autowired
    private LibroService libroService;
    
    @GetMapping("/consultaLibroPorParametros")
    @ResponseBody
    public ResponseEntity<?> consultaLibroPorParametros(
            @RequestParam(name = "titulo", required = true, defaultValue = "")String titulo,
            @RequestParam(name = "anioDesde" , required = true , defaultValue = "") int anioDesde,
            @RequestParam(name = "anioHasta" , required = true , defaultValue = "")  int anioHasta,        
            @RequestParam(name = "serie", required = true, defaultValue = "")String serie,
            @RequestParam(name = "estado" , required = true , defaultValue = "") int estado,
            @RequestParam(name = "idCategoriaLibro", required = true, defaultValue = "")int idCategoriaLibro,
            @RequestParam(name = "idEstadoPrestamo", required = true, defaultValue = "")int idEstadoPrestamo,
            @RequestParam(name = "idTipoLibro", required = true, defaultValue = "")int idTipoLibro,
            @RequestParam(name = "idEditorial", required = true, defaultValue = "")int idEditorial
            ){
        List<Libro> lstSalida = libroService.listaConsultaCompleja(
                "%"+titulo+"%",anioDesde,anioHasta,"%"+serie+"%",estado,idCategoriaLibro,idEstadoPrestamo,idTipoLibro,idEditorial);
        
        return ResponseEntity.ok(lstSalida);
    }
        private static String[] HEADERs = {"CÓDIGO", "TITULO", "AÑO", "SERIE", "ESTADO", "CATEGORIA", "ESTADO PRESTAMO","TIPO DE LIBRO","EDITORIAL"};
        private static String SHEET = "Listado de Libro";
        private static String TITLE = "Listado de Libro - Autor: Jorge Jacinto";
        private static int[] HEADER_WITH = { 3000, 10000, 6000,6000, 6000, 6000, 6000, 10000, 10000 };
        
        @PostMapping("/reporteLibroExcel")
        public void reporteExcel(
                @RequestParam(name = "titulo", required = true, defaultValue = "")String titulo,
                @RequestParam(name = "anioDesde" , required = true , defaultValue = "") int anioDesde,
                @RequestParam(name = "anioHasta" , required = true , defaultValue = "")  int anioHasta,                
                @RequestParam(name = "serie", required = true, defaultValue = "")String serie,    
                @RequestParam(name = "estado" , required = true , defaultValue = "") int estado,
                @RequestParam(name = "idCategoriaLibro", required = true, defaultValue = "-1")int idCategoriaLibro,
                @RequestParam(name = "idEstadoPrestamo", required = true, defaultValue = "-1")int idEstadoPrestamo,
                @RequestParam(name = "idTipoLibro", required = true, defaultValue = "-1")int idTipoLibro,
                @RequestParam(name = "idEditorial", required = true, defaultValue = "-1")int idEditorial,
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

                

                // Fila 3....n

                List<Libro> lstSalida = libroService.listaConsultaCompleja(
                        "%"+titulo+"%",anioDesde,anioHasta,"%"+serie+"%",estado,idCategoriaLibro,idEstadoPrestamo,idTipoLibro,idEditorial);
                
                //List<Revista> lstSalida = revistaService.listaTodos();
                // Filas de datos
                int rowIdx = 3;
                for (Libro obj : lstSalida) {
                    Row row = hoja.createRow(rowIdx++);

                    Cell cel0 = row.createCell(0);
                    cel0.setCellValue(obj.getIdLibro());
                    cel0.setCellStyle(estiloDatosCentrado);
                    
                    Cell cel1 = row.createCell(1);
                    cel1.setCellValue(obj.getTitulo());
                    cel1.setCellStyle(estiloDatosIzquierdo);
                    
                    Cell cel2 = row.createCell(2);
                    cel2.setCellValue(obj.getAnio());
                    cel2.setCellStyle(estiloDatosIzquierdo);
                    
                    Cell cel3= row.createCell(3);
                    cel3.setCellValue(obj.getSerie());
                    cel3.setCellStyle(estiloDatosIzquierdo);
                    
                    Cell cel4 = row.createCell(4);
                    cel4.setCellValue(obj.getEstado()==1?AppSettings.ACTIVO_DESC:AppSettings.INACTIVO_DESC);
                    cel4.setCellStyle(estiloDatosCentrado);
                    
                    Cell cel5 = row.createCell(5);
                    cel5.setCellValue(obj.getCategoriaLibro().getDescripcion());
                    cel5.setCellStyle(estiloDatosCentrado);
                    
                    Cell cel6= row.createCell(6);
                    cel6.setCellValue(obj.getEstadoPrestamo().getDescripcion());
                    cel6.setCellStyle(estiloDatosCentrado);
                    
                    Cell cel7= row.createCell(7);
                    cel7.setCellValue(obj.getTipoLibro().getDescripcion());
                    cel7.setCellStyle(estiloDatosCentrado);
                    
                    Cell cel8= row.createCell(8);
                    cel8.setCellValue(obj.getEditorial().getRazonSocial());
                    cel8.setCellStyle(estiloDatosCentrado);
                    
                }

            // Tipo de archivo y nombre de archivo
                        response.setContentType("application/vnd.ms-excel");
                        response.addHeader("Content-disposition", "attachment; filename=ReporteLibro.xlsx");

                        OutputStream outStream = response.getOutputStream();
                        excel.write(outStream);
                        outStream.close();

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
            

                }

            
@PostMapping("/reporteLibroPDF")
public void reporteLibroPDF(
        @RequestParam(name = "titulo", required = true, defaultValue = "")String titulo,
        @RequestParam(name = "anioDesde" , required = true , defaultValue = "") int anioDesde,
        @RequestParam(name = "anioHasta" , required = true , defaultValue = "")  int anioHasta,                
        @RequestParam(name = "serie", required = true, defaultValue = "")String serie,    
        @RequestParam(name = "estado" , required = true , defaultValue = "") int estado,
        @RequestParam(name = "idCategoriaLibro", required = true, defaultValue = "")int idCategoriaLibro,
        @RequestParam(name = "idEstadoPrestamo", required = true, defaultValue = "")int idEstadoPrestamo,
        @RequestParam(name = "idTipoLibro", required = true, defaultValue = "")int idTipoLibro,
        @RequestParam(name = "idEditorial", required = true, defaultValue = "")int idEditorial,
        HttpServletRequest request, HttpServletResponse response) {
    try {
        List<Libro> lstSalida = libroService.listaConsultaCompleja(
                "%"+titulo+"%",anioDesde,anioHasta,"%"+serie+"%",estado,idCategoriaLibro,idEstadoPrestamo,idTipoLibro,idEditorial);
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(lstSalida);
        
        // PASO 2: Diseño de reporte
        String fileReporte = request.getServletContext().getRealPath("/reportLibro.jasper");

        // PASO3: parámetros adicionales
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("Titulo", titulo); // Ejemplo de parámetro adicional

        JasperReport jasperReport = (JasperReport) JRLoader.loadObject(new FileInputStream(new File(fileReporte)));
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, params, dataSource);

        // PASO 5: parametros en el Header del mensajes HTTP
        response.setContentType("application/pdf");
        response.addHeader("Content-disposition", "attachment; filename=ReporteLibro.pdf");

        // PASO 6: Se envia el pdf
        OutputStream outStream = response.getOutputStream();
        JasperExportManager.exportReportToPdfStream(jasperPrint, outStream);

    } catch (Exception e) {
        e.printStackTrace();
    }

}}
