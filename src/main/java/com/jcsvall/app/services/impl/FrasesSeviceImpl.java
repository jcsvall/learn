package com.jcsvall.app.services.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.jcsvall.app.dtos.FraseDto;
import com.jcsvall.app.dtos.TraduccionDto;
import com.jcsvall.app.entities.Categorias;
import com.jcsvall.app.entities.Frases;
import com.jcsvall.app.entities.Traducciones;
import com.jcsvall.app.entities.Usuarios;
import com.jcsvall.app.repositories.FrasesRepository;
import com.jcsvall.app.services.FrasesService;

@Service("frasesService")
public class FrasesSeviceImpl implements FrasesService {
	@Autowired
	@Qualifier("frasesRepository")
	FrasesRepository frasesRepository;

	@Override
	public Frases save(Frases frases) {
		return frasesRepository.save(frases);
	}

	@Override
	public List<Frases> findAll() {
		return frasesRepository.findAll();
	}

	@Override
	public List<Frases> findFirsTendByIdUsuarioAndFechaUpdate(Integer id) {
		List<Frases> frases = new ArrayList<>();
		List<Frases> frasesList = frasesRepository.findFirsTendByIdUsuarioAndFechaUpdate(id);
		int cont = 0;
		for (Frases frase : frasesList) {
			frases.add(frase);
			cont++;
			if (cont > 21) {
				break;
			}
		}
		return frases;
	}

	@Override
	public Frases save(FraseDto frase) {
		Frases f = new Frases();
		f.setEstado("PENDIENTE");
		f.setFechaIngreso(new Date());
		f.setFechaUpdate(new Date());
		f.setFrase(frase.getFrase());
		f.setPronunciacion(frase.getPronunciacion());
		f.setIdCategorias(new Categorias(1));
		f.setIdUsuarios(new Usuarios(1));
		List<Traducciones> tradList = new ArrayList<>();
		for(TraduccionDto tr:frase.getTraduccionesList()) {
			Traducciones t = new Traducciones();
			t.setIdFrases(f);
			t.setTraduccion(tr.getTraduccion());
			tradList.add(t);
		}
		f.setTraduccionesList(tradList);
		return frasesRepository.save(f);
	}

	@Override
	public Frases findById(Integer id) {
		return frasesRepository.findById(id);
	}

}
