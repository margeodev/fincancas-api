package com.pessoal.financas.api.resource;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pessoal.financas.api.model.Categoria;
import com.pessoal.financas.api.repository.Categorias;

@RestController
@RequestMapping("/categorias")
public class CategoriaResource {

	@Autowired
	private Categorias categorias;
	
	@GetMapping
	public List<Categoria> listar() {
		return categorias.findAll();
	}
}
