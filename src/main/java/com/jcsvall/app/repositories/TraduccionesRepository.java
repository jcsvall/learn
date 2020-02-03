package com.jcsvall.app.repositories;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jcsvall.app.entities.Frases;
import com.jcsvall.app.entities.Traducciones;

@Repository("traduccionesRepository")
public interface TraduccionesRepository extends JpaRepository<Traducciones, Serializable> {
	public List<Traducciones> findByIdFrases(Frases frase);
}
