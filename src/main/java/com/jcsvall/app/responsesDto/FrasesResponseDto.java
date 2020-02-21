package com.jcsvall.app.responsesDto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.jcsvall.app.dtos.CategoriaDto;
import com.jcsvall.app.entities.Categorias;
import com.jcsvall.app.entities.Frases;
import com.jcsvall.app.entities.Traducciones;
import com.jcsvall.app.entities.Usos;
import com.jcsvall.app.entities.Usuarios;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class FrasesResponseDto {

	private Integer id;
	private String frase;
	private String pronunciacion;
	private String estado;
	private Integer ordenPersonal;
	private Date fechaIngreso;
	private Date fechaUpdate;
	private List<Traducciones> traduccionesList;
	private List<Usos> usosList;
	private CategoriaDto categoriaDto;

	public FrasesResponseDto(Frases frase) {
		this.id = frase.getId();
		this.frase = frase.getFrase();
		this.pronunciacion = frase.getPronunciacion();
		this.estado = frase.getEstado();
		this.ordenPersonal = frase.getOrdenPersonal();
		this.fechaIngreso = frase.getFechaIngreso();
		this.fechaUpdate = frase.getFechaUpdate();
		this.traduccionesList = new ArrayList<>();
		this.traduccionesList = frase.getTraduccionesList();
		this.usosList = frase.getUsosList();
		if (frase.getIdCategorias() != null) {
			CategoriaDto cat = new CategoriaDto();
			cat.setId(frase.getIdCategorias().getId());
			cat.setCategoria(frase.getIdCategorias().getCategoria());
			this.categoriaDto = cat;
		}
	}
}
