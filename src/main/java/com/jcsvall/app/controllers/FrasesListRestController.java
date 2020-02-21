package com.jcsvall.app.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jcsvall.app.dtos.ObjetoComunDto;
import com.jcsvall.app.entities.Frases;
import com.jcsvall.app.entities.Usuarios;
import com.jcsvall.app.responsesDto.FrasesResponseDto;
import com.jcsvall.app.services.FrasesService;
import com.jcsvall.app.services.UsuariosService;
import com.jcsvall.app.utils.Constantes;

@RestController
@RequestMapping("/listRest")
public class FrasesListRestController {
	@Autowired
	@Qualifier("frasesService")
	private FrasesService frasesService;
	@Autowired
	@Qualifier("usuariosService")
	private UsuariosService usuariosService;
	private Usuarios us;
	private List<Frases> frasesList;

	@GetMapping("/ajax/frases")
	public Object getListFrases() {
		List<FrasesResponseDto> response = new ArrayList<>();
		us = usuariosService.getUsuarioLogeado();
	    frasesList = frasesService.findByUsuariosDesc(us.getId());
		for (Frases frase : frasesList) {
			response.add(new FrasesResponseDto(frase));
		}
		return response;
	}
	
	@PostMapping("/ajax/personalizar/{ids}")
	public Object personalizar(@PathVariable("ids") String ids) {
		ObjetoComunDto response = new ObjetoComunDto();
		String[] idObjects = ids.split(",");
		List<Frases> listFrasesToUpdate = new ArrayList<>();
		for(String id:idObjects) {
			Frases frase = frasesService.findById(Integer.parseInt(id));
			frase.setEstado(Constantes.PERSONALIZADO);
			listFrasesToUpdate.add(frase);
		}
		if(!listFrasesToUpdate.isEmpty()) {
			frasesService.save(listFrasesToUpdate);
			response.setTipo("Exito");
			response.setValor("Datos actualizados correctamente");
		}
		return response;
	}
}
