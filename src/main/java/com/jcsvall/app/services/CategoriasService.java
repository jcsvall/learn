package com.jcsvall.app.services;

import java.util.List;

import com.jcsvall.app.entities.Categorias;
import com.jcsvall.app.entities.Frases;
import com.jcsvall.app.entities.Usuarios;

public interface CategoriasService {
	public abstract List<Categorias> findAllByUsuario(Usuarios usuario);
	public abstract List<Categorias> findAll();
	public abstract Categorias findById(Integer id);
	public abstract Categorias save(Categorias categoria);
}
