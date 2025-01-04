package com.centroinformacion.entity;

import java.io.Serializable;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class LibroHasAutorPK implements Serializable {

	private static final long serialVersionUID = 1L;
	private int idLibro;
	private int idAutor;

}
