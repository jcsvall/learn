package com.jcsvall.app.services.impl;

import java.util.Collection;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.jcsvall.app.entities.Usuarios;
import com.jcsvall.app.repositories.UsuariosRepository;

@Service
@Transactional
public class CustomUserDetailsService implements UserDetailsService {

	@Autowired
	@Qualifier("usuariosRepository")
	private UsuariosRepository usuariosRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
		Optional<Usuarios> user = usuariosRepository.findByUsuario(userName);
		if (!user.isPresent()) {
			throw new UsernameNotFoundException("User not found by name: " + userName);
		}
		return toUserDetails(user.get());
	}

	private UserDetails toUserDetails(Usuarios usuarios) {
		return User.withUsername(usuarios.getUsuario()).password(passwordEncoder.encode(usuarios.getClave()))
				.roles("USER").build();
	}
}
