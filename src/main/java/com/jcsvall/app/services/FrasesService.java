package com.jcsvall.app.services;

import java.util.List;

import com.jcsvall.app.dtos.FraseDto;
import com.jcsvall.app.entities.Frases;

public interface FrasesService {
	public abstract Frases save(Frases frases);

	public abstract List<Frases> findAll();

	public abstract List<Frases> findFirsTendByIdUsuarioAndFechaUpdate(Integer id);
	
	public abstract Frases save(FraseDto frase);
	
	public abstract Frases findById(Integer id);
	
	public abstract List<Frases> finDByIdUsuarioAndEstado(Integer id,String estado);
}
