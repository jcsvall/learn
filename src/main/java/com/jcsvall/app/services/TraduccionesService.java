package com.jcsvall.app.services;

import java.util.List;

import com.jcsvall.app.entities.Frases;
import com.jcsvall.app.entities.Traducciones;

public interface TraduccionesService {
	public abstract void delete(List<Traducciones> traduccionList);

	public abstract List<Traducciones> findByFrase(Frases frase);
}
