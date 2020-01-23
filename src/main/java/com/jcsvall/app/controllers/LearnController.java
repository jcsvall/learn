package com.jcsvall.app.controllers;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jcsvall.app.dtos.FraseDto;
import com.jcsvall.app.entities.Frases;
import com.jcsvall.app.entities.Traducciones;
import com.jcsvall.app.services.FrasesService;

@Controller
@RequestMapping("/learn")
public class LearnController {

	@Autowired
	@Qualifier("frasesService")
	private FrasesService frasesService;
	
	private List<Frases> frasesList;
	
	private int totalElementosIniciales;

	@RequestMapping("/principal")
	public String principal(ModelMap model) {
		model.addAttribute("test", "Testing");
		return "/learn/principal";
	}

	@RequestMapping("/frases")
	public String frases(ModelMap model) {
		frasesList = frasesService.findFirsTendByIdUsuarioAndFechaUpdate(1);

		List<Frases> fraseOne = new ArrayList<>();
		if (!frasesList.isEmpty()) {
			Frases firs = frasesList.get(0);
			fraseOne.add(firs);
		}
		model.put("frasesList", fraseOne);
		model.put("frasesListLazyInit", frasesList);
		model.addAttribute("cssBarra", "progress-bar w-0");
		model.addAttribute("porcentajeValue", "0");
		totalElementosIniciales = frasesList.size();
		return "learn/frases";
	}
	
	@PostMapping(value="/ajax/guardar")
	public String guardar(@ModelAttribute Frases form, BindingResult bindingResult,
													 ModelMap model) throws ParseException {
		System.out.println(form);
		
		return "PAGO_AUTOMATICO_OIRSA_FORM_FRAGMENT";
	}
	
	@RequestMapping(value="/ajax/guardar/{id}")
	public String updateSi(@PathVariable("id") Integer id, ModelMap model) {
		model.addAttribute("finalizado", false);
		Frases frase = frasesList.stream().filter(x->x.getId().equals(id)).findFirst().orElse(null);
		if(frase != null) {
			frasesList.remove(frase);
			frase.setFechaUpdate(new Date());
			frase.setEstado("REVISADO");
			frasesService.save(frase);			
		}
		calculoBarraProgress(model, frasesList);
		List<Frases> fraseOne = new ArrayList<>();
		if (!frasesList.isEmpty()) {
			fraseOne.add(frasesList.get(0));
		}else{
			model.addAttribute("finalizado", true);
		}
		model.put("frasesList", fraseOne);
		return "learn/frases :: accordionFragment";
	}
	
	@RequestMapping(value="/ajax/guardar/no/{id}")
	public String updateNo(@PathVariable("id") Integer id, ModelMap model) {
		System.out.println(id);
		Frases frase = frasesList.stream().filter(x->x.getId().equals(id)).findFirst().orElse(null);
		if(frase != null) {
			frasesList.remove(frase);
			frasesList.add(frase);
		}
		calculoBarraProgress(model, frasesList);
		List<Frases> fraseOne = Arrays.asList(frasesList.get(0));
		model.put("frasesList", fraseOne);
		return "learn/frases :: accordionFragment";
	}
	
	//add
	@RequestMapping("/add")
	public String add(ModelMap model) {
		frasesList = frasesService.findFirsTendByIdUsuarioAndFechaUpdate(1);		
		model.put("frasesList", frasesList);
		return "learn/add";
	}
	
	@PostMapping("/ajax/crear")
	public String crear(@RequestBody FraseDto frase,ModelMap model) {
		Frases creado = frasesService.save(frase);		
		return "learn/add :: formFragment";
	}
	
	private void calculoBarraProgress(ModelMap model,List<Frases> frasesList) {
		String valor="0";
		//int cuarto = (int) Math.ceil(totalElementosIniciales/4);
		int quitados=totalElementosIniciales-frasesList.size();
		if(quitados>=5) {
			valor="25";
		}
		if(quitados>=10) {
			valor="50";
		}
		if(quitados>=15) {
			valor="75";
		}
		if(quitados==20) {
			valor="100";
		}
		model.addAttribute("cssBarra", "progress-bar w-"+valor);
		model.addAttribute("porcentajeValue",valor);
	}
	
}
