package com.jcsvall.app.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.jcsvall.app.entities.Categorias;
import com.jcsvall.app.entities.Usuarios;
import com.jcsvall.app.repositories.CategoriasRepository;
import com.jcsvall.app.services.CategoriasService;

@Service("categoriasService")
public class CategoriasServiceImpl implements CategoriasService{
	@Autowired
	@Qualifier("categoriasRepository")
	CategoriasRepository categoriasRepository;
	@Override
	public List<Categorias> findAllByUsuario(Usuarios usuario) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public List<Categorias> findAll() {
		return categoriasRepository.findAll();
	}
	@Override
	public Categorias findById(Integer id) {
		// TODO Auto-generated method stub
		return categoriasRepository.findById(id);
	}

}
