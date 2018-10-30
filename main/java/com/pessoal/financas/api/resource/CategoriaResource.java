package com.pessoal.financas.api.resource;

import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pessoal.financas.api.event.RecursoCriadoEvent;
import com.pessoal.financas.api.model.Categoria;
import com.pessoal.financas.api.repository.Categorias;

@RestController
@RequestMapping("/categorias")
public class CategoriaResource {

	@Autowired
	private Categorias categorias;
	
	@Autowired
	private ApplicationEventPublisher publisher;
	
	@GetMapping
	public List<Categoria> listar() {
		return categorias.findAll();
	}
	
	@PostMapping
	public ResponseEntity<Categoria> criar(@Valid @RequestBody Categoria categoria, HttpServletResponse response) {
		Categoria categoriaSalva = categorias.save(categoria);		
		publisher.publishEvent(new RecursoCriadoEvent(this, response, categoriaSalva.getCodigo()));	
		
		return ResponseEntity.status(HttpStatus.CREATED).body(categoriaSalva);		
	}
	
	@GetMapping("/{codigo}")
	public Categoria buscarPeloCodigo(@PathVariable Long codigo) {
		return categorias.findOne(codigo);
	}
}
