package com.jcsvall.app.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jcsvall.app.dtos.CategoriaDto;
import com.jcsvall.app.dtos.FraseDto;
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

	@PostMapping("/ajax/crear")
	public String crearUpdate(@RequestBody CategoriaDto categoriaDto, ModelMap model) {
		Categorias cat = new Categorias();
		cat.setCategoria(categoriaDto.getCategoria());
		cat.setIdUsuarios(us);
		String classCss = "alert alert-success";
		String msg = "Creado satisfactoriamente";
		if ("EDITAR".equalsIgnoreCase(categoriaDto.getAccion())) {
			cat.setId(categoriaDto.getId());
			msg = "Editado satisfactoriamente";
		}
		categoriasService.save(cat);
		model.addAttribute("message", msg);
		model.addAttribute("class", classCss);
		List<Categorias> catList = categoriasService.findAllByUsuario(us);
		catList.sort((c1, c2) -> c1.getId().compareTo(c2.getId()));
		model.put("categorias", catList);
		return "categorias/categorias :: formFragment";
	}

	@RequestMapping("/ajax/eliminar/{id}")
	public String eliminar(@PathVariable("id") Integer id, ModelMap model) {
		Categorias cat = categoriasService.findById(id);
		if (cat.getFrasesList().isEmpty()) {
			categoriasService.delete(cat);
			model.addAttribute("message", "Eliminado satisfactoriamente");
			model.addAttribute("class", "alert alert-success");
		} else {
			model.addAttribute("message", "No se puede eliminar porque contiene frases asociadas");
			model.addAttribute("class", "alert alert-danger");
		}
		List<Categorias> catList = categoriasService.findAllByUsuario(us);
		catList.sort((c1, c2) -> c1.getId().compareTo(c2.getId()));
		model.put("categorias", catList);
		return "categorias/categorias :: formFragment";
	}

}
