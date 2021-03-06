package com.jcsvall.app.services.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.jcsvall.app.dtos.FraseDto;
import com.jcsvall.app.dtos.TraduccionDto;
import com.jcsvall.app.entities.Categorias;
import com.jcsvall.app.entities.Frases;
import com.jcsvall.app.entities.Traducciones;
import com.jcsvall.app.entities.Usuarios;
import com.jcsvall.app.repositories.FrasesRepository;
import com.jcsvall.app.services.CategoriasService;
import com.jcsvall.app.services.FrasesService;
import com.jcsvall.app.services.TraduccionesService;

@Service("frasesService")
public class FrasesSeviceImpl implements FrasesService {
	@Autowired
	@Qualifier("frasesRepository")
	FrasesRepository frasesRepository;

	@Autowired
	@Qualifier("traduccionesService")
	TraduccionesService traduccionesService;
	
	@Autowired
	@Qualifier("categoriasService")
	CategoriasService categoriasService;

	@Override
	public Frases save(Frases frases) {
		return frasesRepository.save(frases);
	}

	@Override
	public List<Frases> findAll() {
		return frasesRepository.findAll();
	}

	@Override
	public List<Frases> findFirsTendByIdUsuarioAndFechaUpdate(Integer id) {
		List<Frases> frases = new ArrayList<>();
		String REVISANDO = "REVISANDO";
		Boolean pendientes = false;
		frases = finDByIdUsuarioAndEstado(id, REVISANDO);
		if (!frases.isEmpty()) {
			pendientes = true;
			frases = new ArrayList<>();
		}

		List<Frases> frasesMarcadasNo = finDByIdUsuarioAndEstado(id, "MARCADO-NO");
		int cont = 0;
		for (Frases frase : frasesMarcadasNo) {
			if (cont == 20) {
				break;
			}
			if (!pendientes) {
				frase.setEstado(REVISANDO);
			}
			frases.add(frase);
			cont++;
		}

		List<Frases> frasesNuevas = finDByIdUsuarioAndEstado(id, "NUEVO");
		for (Frases frase : frasesNuevas) {
			if (cont == 20) {
				break;
			}
			if (!pendientes) {
				frase.setEstado(REVISANDO);
			}
			frases.add(frase);
			cont++;
		}

		if (cont < 20) {
			List<Frases> frasesList = frasesRepository.findFirsTendByIdUsuarioAndFechaUpdate(id);
			for (Frases frase : frasesList) {
				if (cont == 20) {
					break;
				}
				if (!pendientes) {
					frase.setEstado(REVISANDO);
				}
				frases.add(frase);
				cont++;
			}
		}

		frasesRepository.saveAll(frases);

		return frases;
	}

	@Override
	public Frases save(FraseDto frase) {
		Frases f = new Frases();
		f.setEstado("NUEVO");
		f.setFechaIngreso(new Date());
		f.setFechaUpdate(new Date());
		f.setFrase(frase.getFrase());
		f.setPronunciacion(frase.getPronunciacion());
		f.setIdCategorias(new Categorias(frase.getCategoriaId()));
		f.setIdUsuarios(new Usuarios(1));
		List<Traducciones> tradList = new ArrayList<>();
		for (TraduccionDto tr : frase.getTraduccionesList()) {
			Traducciones t = new Traducciones();
			t.setIdFrases(f);
			t.setTraduccion(tr.getTraduccion());
			tradList.add(t);
		}
		f.setTraduccionesList(tradList);
		return frasesRepository.save(f);
	}

	@Override
	public Frases findById(Integer id) {
		return frasesRepository.findById(id);
	}

	@Override
	public List<Frases> finDByIdUsuarioAndEstado(Integer id, String estado) {
		return frasesRepository.finDByIdUsuarioAndEstado(id, estado);
	}

	@Override
	public List<Frases> save(List<Frases> frases) {
		return frasesRepository.saveAll(frases);
	}

	@Override
	public List<Frases> findByUsuariosDesc(Integer id) {
		return frasesRepository.findByIdUsuarioDesc(id);
	}

	@Override
	public Integer findCountByIdUsuarioAndEstado(Integer id, String estado) {
		return frasesRepository.findCountByIdUsuarioAndEstado(id, estado);
	}

	@Override
	public Integer deleteById(Integer id) {
		return frasesRepository.deleteById(id);
	}

	@Override
	public void delete(Frases frase) {
		frasesRepository.delete(frase);
	}

	@Override
	public Frases update(FraseDto frase) {
		Frases f = findById(frase.getId());
		List<Traducciones> toDelete = f.getTraduccionesList();		
		f.setTraduccionesList(new ArrayList<>());
		f.setFechaUpdate(new Date());
		f.setFrase(frase.getFrase());
		f.setPronunciacion(frase.getPronunciacion());
		
		f.setIdCategorias(categoriasService.findById(frase.getCategoriaId()));
		List<Traducciones> tradList = new ArrayList<>();
		for (TraduccionDto tr : frase.getTraduccionesList()) {
			Traducciones t = new Traducciones();
			t.setIdFrases(f);
			t.setTraduccion(tr.getTraduccion());
			tradList.add(t);
		}
		f.setTraduccionesList(tradList);
		traduccionesService.delete(toDelete);
		return frasesRepository.save(f);
	}

	@Override
	public List<Frases> findByIdUsuarioAndFraseDesc(Integer id, String frase) {
		return frasesRepository.findByIdUsuarioAndFraseDesc(id, frase);
	}

	@Override
	public List<Frases> findAllByIdUsuarioAndFechaUpdateAsc(Integer id) {
		return frasesRepository.findAllByIdUsuarioAndFechaUpdateAsc(id);
	}

}
