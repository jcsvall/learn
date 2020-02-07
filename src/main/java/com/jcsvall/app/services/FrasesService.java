package com.jcsvall.app.services;

import java.util.List;

import com.jcsvall.app.dtos.FraseDto;
import com.jcsvall.app.entities.Frases;
import com.jcsvall.app.entities.Usuarios;

public interface FrasesService {
	public abstract Frases save(Frases frases);

	public abstract List<Frases> findAll();

	public abstract List<Frases> findFirsTendByIdUsuarioAndFechaUpdate(Integer id);

	public abstract Frases save(FraseDto frase);

	public abstract Frases findById(Integer id);

	public abstract List<Frases> finDByIdUsuarioAndEstado(Integer id, String estado);

	public abstract List<Frases> save(List<Frases> frases);

	public abstract List<Frases> findByUsuariosDesc(Integer id);

	public abstract Integer findCountByIdUsuarioAndEstado(Integer id, String estado);

	public abstract Integer deleteById(Integer id);

	public abstract void delete(Frases frase);

	public abstract Frases update(FraseDto frase);

	public abstract List<Frases> findByIdUsuarioAndFraseDesc(Integer id, String frase);

}
