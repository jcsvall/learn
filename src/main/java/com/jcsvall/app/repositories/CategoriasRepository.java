package com.jcsvall.app.repositories;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.jcsvall.app.entities.Categorias;
import com.jcsvall.app.entities.Frases;
import com.jcsvall.app.entities.Usuarios;
import com.jcsvall.app.utils.Constantes;

@Repository("categoriasRepository")
public interface CategoriasRepository extends JpaRepository<Categorias, Serializable> {
	
	//public List<Categorias> findByIdUsuarios(Usuarios usuarios);
}
