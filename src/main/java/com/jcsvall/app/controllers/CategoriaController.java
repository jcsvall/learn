package com.jcsvall.app.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jcsvall.app.entities.Categorias;
import com.jcsvall.app.entities.Usuarios;
import com.jcsvall.app.services.CategoriasService;

@Controller
@RequestMapping("/categorias")
public class CategoriaController {
	@Autowired
	@Qualifier("categoriasService")
	private CategoriasService categoriasService;

	private Usuarios us;

	@RequestMapping("/listado")
	public String frases(ModelMap model) {
		us = new Usuarios(1);
		List<Categorias> catList = categoriasService.findAllByUsuario(us);
		model.put("categorias", catList);
		return "categorias/categorias";
	}

	@RequestMapping("/ajax/crear/{id}/{valor}")
	public String crearUpdate(@PathVariable("id") Integer id,@PathVariable("valor") String valor, ModelMap model) {
		Categorias cat = new Categorias();
		if(id==-1) {
			cat.setCategoria(valor);
			cat.setIdUsuarios(us);
		}
		categoriasService.save(cat);
		List<Categorias> catList = categoriasService.findAllByUsuario(us);
		model.put("categorias", catList);
		return "categorias/categorias :: formFragment";
	}

}
