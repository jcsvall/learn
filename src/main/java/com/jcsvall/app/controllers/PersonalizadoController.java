package com.jcsvall.app.controllers;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jcsvall.app.entities.Frases;
import com.jcsvall.app.services.FrasesService;
import com.jcsvall.app.utils.Constantes;

@Controller
@RequestMapping("/personalizado")
public class PersonalizadoController {
	@Autowired
	@Qualifier("frasesService")
	private FrasesService frasesService;
	private List<Frases> frasesList;
	private List<Frases> frasesListToReverse;
	private int totalElementosIniciales;
	private int totalFrasesPendientes;
	private Boolean esReverse;

	@RequestMapping("/personalizado/{idioma}")
	public String personalizado(ModelMap model,@PathVariable("idioma") String idioma) {
		if("i".equalsIgnoreCase(idioma)) {
		esReverse = false;
		}else {
			esReverse = true;
		}
		List<Frases> frasesPendientes = frasesService.finDByIdUsuarioAndEstado(1, Constantes.PERSONALIZADO);
		totalFrasesPendientes = frasesPendientes.size();
		frasesList = new ArrayList<>();
		frasesList.addAll(frasesPendientes);
		frasesListToReverse = new ArrayList<>();
		frasesListToReverse.addAll(frasesList);
		totalElementosIniciales = frasesList.size();

		if (totalFrasesPendientes > 0) {
			frasesList = frasesPendientes;
		}

		List<Frases> fraseOne = new ArrayList<>();
		if (!frasesList.isEmpty()) {
			Frases firs = frasesList.get(0);
			fraseOne.add(firs);
		}
		model.put("frasesList", fraseOne);
		model.put("frasesListLazyInit", frasesList);
		model.addAttribute("cssBarra", "progress-bar w-0");
		model.addAttribute("porcentajeValue", "0");
		
		calculoBarraProgress(model, frasesList);
		if (esReverse) {
			return "personalizado/personalizadoReverse";
		}
		return "personalizado/personalizado";
	}
	
	@RequestMapping(value = "/ajax/guardar/{id}")
	public String updateSi(@PathVariable("id") Integer id, ModelMap model) {
		model.addAttribute("finalizado", false);
		Frases frase = frasesList.stream().filter(x -> x.getId().equals(id)).findFirst().orElse(null);
		if (frase != null) {
			frasesList.remove(frase);			
		}
		calculoBarraProgress(model, frasesList);
		List<Frases> fraseOne = new ArrayList<>();
		if (!frasesList.isEmpty()) {
			fraseOne.add(frasesList.get(0));
		} else {
			model.addAttribute("finalizado", true);
		}
		model.put("frasesList", fraseOne);

		if (esReverse) {
			return "personalizado/personalizadoReverse :: accordionFragment";
		}
		return "personalizado/personalizado :: accordionFragment";
	}

	
	@RequestMapping(value = "/ajax/guardar/no/{id}")
	public String updateNo(@PathVariable("id") Integer id, ModelMap model) {
		System.out.println(id);
		Frases frase = frasesList.stream().filter(x -> x.getId().equals(id)).findFirst().orElse(null);
		if (frase != null) {
			frasesList.remove(frase);
			frasesList.add(frase);
		}
		calculoBarraProgress(model, frasesList);
		List<Frases> fraseOne = Arrays.asList(frasesList.get(0));
		model.put("frasesList", fraseOne);
		if (esReverse) {
			return "personalizado/personalizadoReverse :: accordionFragment";
		}
		return "personalizado/personalizado :: accordionFragment";
	}
	
	
	private void calculoBarraProgress(ModelMap model, List<Frases> frasesList) {
		String valor = "0";
		double recordsTotales = totalElementosIniciales;
		double quitados = recordsTotales - frasesList.size();

		double porcetaje25 = getWithTwoDecimals((recordsTotales / 4));
		double porcetaje50 = getWithTwoDecimals((porcetaje25 * 2));
		double porcetaje75 = getWithTwoDecimals((porcetaje25 * 3));
		double porcetaje100 = getWithTwoDecimals((porcetaje25 * 4));

		if (totalFrasesPendientes > 0) {
			quitados = recordsTotales - totalFrasesPendientes;
			totalFrasesPendientes = 0;
		}

		if (quitados >= porcetaje25) {
			valor = "25";
		}
		if (quitados >= porcetaje50) {
			valor = "50";
		}
		if (quitados >= porcetaje75) {
			valor = "75";
		}
		if (quitados >= porcetaje100) {
			valor = "100";
		}
		model.addAttribute("cssBarra", "progress-bar w-" + valor);
		model.addAttribute("porcentajeValue", valor);
	}
	
	private Double getWithTwoDecimals(Double value) {
		DecimalFormat df = new DecimalFormat("#.00");
		return Double.valueOf(df.format(value));
	}
}
