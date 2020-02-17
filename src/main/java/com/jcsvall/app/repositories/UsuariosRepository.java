package com.jcsvall.app.repositories;

import java.io.Serializable;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jcsvall.app.entities.Usuarios;

@Repository("usuariosRepository")
public interface UsuariosRepository extends JpaRepository<Usuarios, Serializable> {
	Optional<Usuarios> findByUsuario(String usuario);
}
