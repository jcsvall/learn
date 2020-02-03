package com.jcsvall.app.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.jcsvall.app.entities.Frases;
import com.jcsvall.app.entities.Traducciones;
import com.jcsvall.app.repositories.TraduccionesRepository;
import com.jcsvall.app.services.TraduccionesService;

@Service("traduccionesService")
public class TraduccionesServiceImpl implements TraduccionesService {
	@Autowired
	@Qualifier("traduccionesRepository")
	TraduccionesRepository traduccionesRepository;

	@Override
	public void delete(List<Traducciones> traduccionList) {
		traduccionesRepository.deleteAll(traduccionList);
	}

	@Override
	public List<Traducciones> findByFrase(Frases frase) {
		return traduccionesRepository.findByIdFrases(frase);
	}

}
