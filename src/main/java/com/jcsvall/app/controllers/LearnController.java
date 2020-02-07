package com.jcsvall.app.controllers;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jcsvall.app.dtos.FraseDto;
import com.jcsvall.app.dtos.ObjetoComunDto;
import com.jcsvall.app.entities.Categorias;
import com.jcsvall.app.entities.Frases;
import com.jcsvall.app.entities.Traducciones;
import com.jcsvall.app.services.CategoriasService;
import com.jcsvall.app.services.FrasesService;
import com.jcsvall.app.utils.Constantes;

@Controller
@RequestMapping("/learn")
public class LearnController {

	@Autowired
	@Qualifier("frasesService")
	private FrasesService frasesService;

	@Autowired
	@Qualifier("categoriasService")
	private CategoriasService categoriasService;

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
//		frasesListToReverse = new ArrayList<>();
//		frasesListToReverse.addAll(frasesList);
		totalElementosIniciales = frasesList.size();

		if (totalFrasesPendientes > 0) {
			frasesList = frasesPendientes;
		}

		frasesListToReverse = new ArrayList<>();
		frasesListToReverse.addAll(frasesList);

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

		if (esReverse) {
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
		if (esReverse) {
			return "learn/frasesReverse :: accordionFragment";
		}
		return "learn/frases :: accordionFragment";
	}

	// add
	@RequestMapping("/add")
	public String add(ModelMap model) {
//		frasesList = frasesService.findFirsTendByIdUsuarioAndFechaUpdate(1);
		// model.put("frasesList", frasesList);
		return "learn/add";
	}

	@PostMapping("/ajax/crear")
	public String crear(@RequestBody FraseDto frase, ModelMap model) {
		Frases creado = frasesService.save(frase);
		List<Categorias> cat = categoriasService.findAll();
		model.addAttribute("message", "Registro Creado");
		model.put("categorias", cat);
		return "learn/add :: formFragment";
	}

	@PostMapping("/ajax/update")
	public String update(@RequestBody FraseDto frase, ModelMap model) {
		frasesService.update(frase);

		frasesList = frasesService.findByUsuariosDesc(1);
		int total = frasesList.size();

		List<Categorias> cat = categoriasService.findAll();
		model.put("categorias", cat);

		model.put("frasesList", frasesList);
		model.addAttribute("fraseTotal", total);
		model.addAttribute("message", "Registro Actualizado");

		return "learn/frases_lista :: accordionFragment";
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
		frasesPendientes = frasesService.finDByIdUsuarioAndEstado(1, "REVISANDO");
		if (frasesListToReverse != null && !frasesListToReverse.isEmpty() && frasesPendientes.isEmpty()) {
			frasesPendientes = frasesService.save(frasesListToReverse);
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

		List<Categorias> cat = categoriasService.findAll();
		model.put("categorias", cat);

		model.put("frasesList", frasesList);
		model.addAttribute("fraseTotal", total);
		// calculoBarraProgress(model, frasesList);

		return "learn/frases_lista";
	}

	@RequestMapping(value = "/ajax/personalizar/{id}/{accion}")
	public String personalizar(@PathVariable("id") Integer id, @PathVariable("accion") Integer accion, ModelMap model) {
		String estado = Constantes.PERSONALIZADO;
		Integer cantidad = frasesService.findCountByIdUsuarioAndEstado(1, estado) + 1;

		Frases frase = frasesList.stream().filter(x -> x.getId().equals(id)).findFirst().orElse(null);

		List<Frases> fraseList = new ArrayList<>();
		if (accion == 0) {
			estado = Constantes.REVISADO;
			cantidad = 0;
			fraseList = frasesList.stream()
					.filter(f -> f.getEstado().equals(Constantes.PERSONALIZADO)
							&& f.getOrdenPersonal() > frase.getOrdenPersonal() && f.getOrdenPersonal() != null
							&& f.getOrdenPersonal() != 0)
					.collect(Collectors.toList());
			for (Frases f : fraseList) {
				f.setOrdenPersonal(f.getOrdenPersonal() - 1);
			}
		}

		if (frase != null) {
			frase.setEstado(estado);
			frase.setOrdenPersonal(cantidad);
			frasesService.save(frase);
			frasesService.save(fraseList);
		}

		int total = frasesList.size();
		model.put("frasesList", frasesList);
		model.addAttribute("fraseTotal", total);

		return "learn/frases_lista :: accordionFragment";
	}

	@RequestMapping(value = "/ajax/accion/{tipo}")
	public String addInit(@PathVariable("tipo") String tipo, ModelMap model) {
		List<Categorias> cat = categoriasService.findAll();
		model.put("categorias", cat);
		return "learn/add :: formFragment";
	}

	@RequestMapping(value = "/ajax/eliminar/{id}")
	public String actuEli(@PathVariable("id") Integer id, ModelMap model) {
		int total = frasesList.size();
		Frases frase = frasesList.stream().filter(x -> x.getId().equals(id)).findFirst().orElse(null);

		if (frase != null) {
			frasesService.delete(frase);
			frasesList.remove(frase);
			total = frasesList.size();
		}
		model.put("frasesList", frasesList);
		model.addAttribute("fraseTotal", total);

		return "learn/frases_lista :: accordionFragment";
	}

	@PostMapping("/ajax/buscar")
	public String buscar(@RequestBody ObjetoComunDto objectoComunDto, ModelMap model) {

		frasesList = frasesService.findByIdUsuarioAndFraseDesc(1, objectoComunDto.getValor().toUpperCase());
		int total = frasesList.size();

		List<Categorias> cat = categoriasService.findAll();
		model.put("categorias", cat);

		model.put("frasesList", frasesList);
		model.addAttribute("fraseTotal", total);
		model.addAttribute("buscando", objectoComunDto.getValor());

		return "learn/frases_lista :: accordionFragment";
	}

	@RequestMapping("/practicar")
	public String practicar(ModelMap model) {
		return "learn/practicar";
	}

}
