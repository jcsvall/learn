package com.jcsvall.app.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.jcsvall.app.entities.Usuarios;
import com.jcsvall.app.repositories.UsuariosRepository;
import com.jcsvall.app.services.UsuariosService;

@Service("usuariosService")
public class UsuariosServiceImpl implements UsuariosService {
	@Autowired
	@Qualifier("usuariosRepository")
	UsuariosRepository usuariosRepository;

	@Override
	public Usuarios getUsuarioLogeado() {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		UserDetails userDetails = null;
		if (principal instanceof UserDetails) {
			userDetails = (UserDetails) principal;
		}
		String userName = userDetails.getUsername();
		return getUsuarioByLogin(userName);
	}

	@Override
	public Usuarios getUsuarioByLogin(String login) {
		return usuariosRepository.findByUsuario(login).get();
	}

}
