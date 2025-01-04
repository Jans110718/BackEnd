package com.centroinformacion.service;

import java.util.Date;
import java.util.List;
import com.centroinformacion.entity.Editorial;
import com.centroinformacion.entity.Revista;

public interface EditorialService {
//Para el crud  
	public abstract Editorial insertaActualizaEditorial(Editorial obj);

	public abstract List<Editorial> listaEditorialPorRazonSocialLike(String razonSocial);
	public abstract List<Editorial> listaPorRuc(String ruc);
	public abstract List<Editorial> listaPorRazonSocial(String razonSocial);

	public abstract void eliminaEditorial(int idEditorial);

	public abstract List<Editorial> listaTodos();

	public abstract List<Editorial> listaEditorialPorRazonSocialIgualRegistra(String razonSocial);
	public abstract List<Editorial> listaEditorialPorRazonSocialIgualActualiza(String razonSocial, int idEditorial);
	public abstract List<Editorial> listaCompleja(String razonSocial, String direccion,String ruc,String gerente, Date fecIni, Date fecFin, int estado,	int idPais);
}