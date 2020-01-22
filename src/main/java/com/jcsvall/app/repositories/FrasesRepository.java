package com.jcsvall.app.repositories;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.jcsvall.app.entities.Frases;
import com.jcsvall.app.entities.Usuarios;

@Repository("frasesRepository")
public interface FrasesRepository extends JpaRepository<Frases, Serializable>{
	public List<Frases> findByIdUsuarios(Usuarios usuarios);
	
	@Query("SELECT f FROM Frases f WHERE f.idUsuarios.id = :id order by f.fechaUpdate asc")
	public List<Frases> findFirsTendByIdUsuarioAndFechaUpdate(Integer id);
	
	public Frases findById(Integer id);
	
}
