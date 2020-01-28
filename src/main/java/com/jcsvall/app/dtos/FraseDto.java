package com.jcsvall.app.dtos;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class FraseDto {
	private String frase;
	private String pronunciacion;
	private Integer categoriaId;
	private List<TraduccionDto> traduccionesList;
}
