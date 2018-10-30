package com.pessoal.financas.api.resource;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

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
import com.pessoal.financas.api.model.Pessoa;
import com.pessoal.financas.api.repository.Pessoas;

@RestController
@RequestMapping("/pessoas")
public class PessoaResource {
	
	@Autowired
	private Pessoas pessoas;
	
	@Autowired
	private ApplicationEventPublisher publisher;

	@GetMapping
	public List<Pessoa> listar() {
		return pessoas.findAll();
	}
	
	@GetMapping("/{codigo}")
	public Pessoa buscarPeloCodigo(@PathVariable Long codigo) {
		return pessoas.findOne(codigo);
	}
	
	@PostMapping
	public ResponseEntity<Pessoa> criar(@RequestBody Pessoa pessoa, HttpServletResponse response) {
		Pessoa pessoaSalva = pessoas.save(pessoa);		
		publisher.publishEvent(new RecursoCriadoEvent(this, response, pessoaSalva.getCodigo()));
		
		return ResponseEntity.status(HttpStatus.CREATED).body(pessoaSalva);
		
	}
}
