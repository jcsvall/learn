package com.jcsvall.app.pojos;

import com.jcsvall.app.entities.Categorias;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CategoriaCheckedPojo {
	public Categorias categoria;
	public String checked;
}
