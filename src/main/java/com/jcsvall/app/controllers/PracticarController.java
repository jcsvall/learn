package com.jcsvall.app.controllers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jcsvall.app.dtos.ObjetoComunDto;
import com.jcsvall.app.entities.Categorias;
import com.jcsvall.app.entities.Frases;
import com.jcsvall.app.entities.Usuarios;
import com.jcsvall.app.services.CategoriasService;
import com.jcsvall.app.services.FrasesService;

@Controller
@RequestMapping("/practicar")
public class PracticarController {
	@Autowired
	@Qualifier("frasesService")
	private FrasesService frasesService;

	@Autowired
	@Qualifier("categoriasService")
	private CategoriasService categoriasService;

	private Usuarios us;
	private List<Frases> frasesList;

	@RequestMapping("/practicar")
	public String practicar(ModelMap model) {
		us = new Usuarios(1);
		List<Categorias> cat = categoriasService.findAllByUsuario(us);
		model.put("categorias", cat);
		return "practicar/practicar";
	}

	@PostMapping("/ajax/frases")
	public String getFrasesPractica(@RequestBody ObjetoComunDto objetoComunDto, ModelMap model) {
		frasesList = frasesService.findAllByIdUsuarioAndFechaUpdateAsc(us.getId());
		if (objetoComunDto.getValor() != null && !objetoComunDto.getValor().isEmpty()) {
			String[] catsId = objetoComunDto.getValor().split(",");
			List<Frases> frsList = new ArrayList<>();
			for (String id : catsId) {
				Integer idCat = Integer.parseInt(id);
				List<Frases> frs = frasesList.stream()
						.filter(f -> f.getIdCategorias().getId() != null && f.getIdCategorias().getId() == idCat)
						.collect(Collectors.toList());
				frsList.addAll(frs);
			}
			frasesList = frsList;
		}
		model.put("frases", frasesList);
		model.addAttribute("lista", "lista");
		return "practicar/practicar::dimanicContectFragment";
	}

	@RequestMapping("/ajax/practica")
	public String getPracticando(ModelMap model) {
		List<Frases> fraseOne = new ArrayList<>();
		if (!frasesList.isEmpty()) {
			Frases firs = frasesList.get(0);
			fraseOne.add(firs);
		}
		model.put("frases", fraseOne);
		model.addAttribute("practicaList", "practicaList");
		return "practicar/practicar::dimanicContectFragment";
	}

	@RequestMapping(value = "/ajax/guardar/{id}")
	public String updateSi(@PathVariable("id") Integer id, ModelMap model) {
		model.addAttribute("finalizado", false);
		Frases frase = frasesList.stream().filter(x -> x.getId().equals(id)).findFirst().orElse(null);
		if (frase != null) {
			frasesList.remove(frase);
			frase.setFechaUpdate(new Date());
			frasesService.save(frase);
		}
		// calculoBarraProgress(model, frasesList);
		List<Frases> fraseOne = new ArrayList<>();
		if (!frasesList.isEmpty()) {
			fraseOne.add(frasesList.get(0));
		} else {
			model.addAttribute("finalizado", true);
		}
		model.put("frases", fraseOne);
		model.addAttribute("practicaList", "practicaList");
		return "practicar/practicar::dimanicContectFragment";
	}
	
	@RequestMapping(value = "/ajax/guardar/no/{id}")
	public String updateNo(@PathVariable("id") Integer id, ModelMap model) {
		Frases frase = frasesList.stream().filter(x -> x.getId().equals(id)).findFirst().orElse(null);
		if (frase != null) {
			frasesList.remove(frase);
			frasesList.add(frase);
		}
		//calculoBarraProgress(model, frasesList);
		List<Frases> fraseOne = Arrays.asList(frasesList.get(0));
		model.put("frases", fraseOne);		
		model.addAttribute("practicaList", "practicaList");
		return "practicar/practicar::dimanicContectFragment";
	}

}
