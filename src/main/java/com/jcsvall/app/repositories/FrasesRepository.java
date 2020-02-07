package com.jcsvall.app.repositories;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.jcsvall.app.entities.Frases;
import com.jcsvall.app.entities.Usuarios;
import com.jcsvall.app.utils.Constantes;

@Repository("frasesRepository")
public interface FrasesRepository extends JpaRepository<Frases, Serializable>{
	public List<Frases> findByIdUsuarios(Usuarios usuarios);
	
	@Query("SELECT f FROM Frases f WHERE f.idUsuarios.id = :id and f.estado NOT IN('"+Constantes.NUEVO+"','"+Constantes.PERSONALIZADO+"') order by f.fechaUpdate asc")
	public List<Frases> findFirsTendByIdUsuarioAndFechaUpdate(Integer id);
	
	public Frases findById(Integer id);
	
	@Query("SELECT f FROM Frases f WHERE f.idUsuarios.id = :id AND f.estado=:estado order by f.id")
	public List<Frases> finDByIdUsuarioAndEstado(Integer id,String estado);
	
	@Query("SELECT f FROM Frases f WHERE f.idUsuarios.id = :id order by f.id desc")
	public List<Frases> findByIdUsuarioDesc(Integer id);
	
	@Query("SELECT count(f) FROM Frases f WHERE f.idUsuarios.id = :id AND f.estado = :estado")
	public Integer findCountByIdUsuarioAndEstado(Integer id, String estado);
	
	public Integer deleteById(Integer id);
	
	@Query("SELECT f FROM Frases f WHERE f.idUsuarios.id = :id and UPPER(f.frase) like %:frase% order by f.id desc")
	public List<Frases> findByIdUsuarioAndFraseDesc(Integer id, String frase);
}
