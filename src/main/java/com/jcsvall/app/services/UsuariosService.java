package com.jcsvall.app.services;

import com.jcsvall.app.entities.Usuarios;

public interface UsuariosService {
	public abstract Usuarios getUsuarioLogeado();

	public abstract Usuarios getUsuarioByLogin(String login);
}
