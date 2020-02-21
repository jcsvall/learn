package com.jcsvall.app.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jcsvall.app.entities.Frases;
import com.jcsvall.app.entities.Usuarios;
import com.jcsvall.app.services.FrasesService;
import com.jcsvall.app.services.UsuariosService;

@Controller
@RequestMapping("/list")
public class FrasesListController {
	@Autowired
	@Qualifier("frasesService")
	private FrasesService frasesService;
	@Autowired
	@Qualifier("usuariosService")
	private UsuariosService usuariosService;
	private Usuarios us;
	private List<Frases> frasesList;

	@RequestMapping("/frases")
	public String frases(ModelMap model) {
		return "listaFrases/frases_lista";
	}
}
