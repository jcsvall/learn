package com.jcsvall.app.controllers;

import java.text.DecimalFormat;
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
	private List<Frases> frasesListToReverse;

	private int totalElementosIniciales;
	private int totalFrasesPendientes;
	private Boolean esReverse;

	@RequestMapping("/principal")
	public String principal(ModelMap model) {
		model.addAttribute("test", "Testing");
		return "/learn/principal";
	}

	@RequestMapping("/frases")
	public String frases(ModelMap model) {
		esReverse = false;
		List<Frases> frasesPendientes = frasesService.finDByIdUsuarioAndEstado(1, "REVISANDO");
		totalFrasesPendientes = frasesPendientes.size();

		frasesList = frasesService.findFirsTendByIdUsuarioAndFechaUpdate(1);
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
		
		return "learn/frases";
	}

	@PostMapping(value = "/ajax/guardar")
	public String guardar(@ModelAttribute Frases form, BindingResult bindingResult, ModelMap model)
			throws ParseException {
		System.out.println(form);

		return "PAGO_AUTOMATICO_OIRSA_FORM_FRAGMENT";
	}

	@RequestMapping(value = "/ajax/guardar/{id}")
	public String updateSi(@PathVariable("id") Integer id, ModelMap model) {
		model.addAttribute("finalizado", false);
		Frases frase = frasesList.stream().filter(x -> x.getId().equals(id)).findFirst().orElse(null);
		if (frase != null) {
			frasesList.remove(frase);
			frase.setFechaUpdate(new Date());
			if (!"MARCADO-NO".equalsIgnoreCase(frase.getEstado())) {
				frase.setEstado("REVISADO");
			}
			frasesService.save(frase);
		}
		calculoBarraProgress(model, frasesList);
		List<Frases> fraseOne = new ArrayList<>();
		if (!frasesList.isEmpty()) {
			fraseOne.add(frasesList.get(0));
		} else {
			model.addAttribute("finalizado", true);
		}
		model.put("frasesList", fraseOne);
		
		if(esReverse) {
			return "learn/frasesReverse :: accordionFragment";
		}
		return "learn/frases :: accordionFragment";
	}

	@RequestMapping(value = "/ajax/guardar/no/{id}")
	public String updateNo(@PathVariable("id") Integer id, ModelMap model) {
		System.out.println(id);
		Frases frase = frasesList.stream().filter(x -> x.getId().equals(id)).findFirst().orElse(null);
		if (frase != null) {
			frasesList.remove(frase);
			frase.setEstado("MARCADO-NO");
			frasesList.add(frase);
		}
		calculoBarraProgress(model, frasesList);
		List<Frases> fraseOne = Arrays.asList(frasesList.get(0));
		model.put("frasesList", fraseOne);
		if(esReverse) {
			return "learn/frasesReverse :: accordionFragment";
		}
		return "learn/frases :: accordionFragment";
	}

	// add
	@RequestMapping("/add")
	public String add(ModelMap model) {
		frasesList = frasesService.findFirsTendByIdUsuarioAndFechaUpdate(1);
		model.put("frasesList", frasesList);
		return "learn/add";
	}

	@PostMapping("/ajax/crear")
	public String crear(@RequestBody FraseDto frase, ModelMap model) {
		Frases creado = frasesService.save(frase);
		return "learn/add :: formFragment";
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
	
	@RequestMapping("/frases_reverse")
	public String frasesReverse(ModelMap model) {
		esReverse = true;
		List<Frases> frasesPendientes = new ArrayList<>();
		if(frasesListToReverse != null && !frasesListToReverse.isEmpty()) {
			frasesPendientes = frasesService.save(frasesListToReverse);
		}else {
			frasesPendientes = frasesService.finDByIdUsuarioAndEstado(1, "REVISANDO");
		}		
		
		totalFrasesPendientes = frasesPendientes.size();

		frasesList = frasesService.findFirsTendByIdUsuarioAndFechaUpdate(1);
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
		
		return "learn/frasesReverse";
	}
	
	@RequestMapping("/frases_lista")
	public String frasesList(ModelMap model) {
		
		frasesList = frasesService.findByUsuariosDesc(1);		
		int total = frasesList.size();
				
		model.put("frasesList", frasesList);
		model.addAttribute("fraseTotal", total);
		calculoBarraProgress(model, frasesList);
		
		return "learn/frases_lista";
	}
}
