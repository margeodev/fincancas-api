package com.pessoal.financas.api.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.pessoal.financas.api.model.Pessoa;
import com.pessoal.financas.api.repository.Pessoas;

@Service
public class PessoaService {

	@Autowired
	private Pessoas pessoas;
	
	public Pessoa atualizar(Long codigo, Pessoa pessoa) {
		Pessoa pessoaSalva = buscarPessoaPorNome(codigo);
		BeanUtils.copyProperties(pessoa, pessoaSalva, "codigo");
		return pessoas.save(pessoaSalva);
	}	

	public void atualizarCampoAtivo(Long codigo, Boolean ativo) {
		Pessoa pessoaSalva = buscarPessoaPorNome(codigo);
		pessoaSalva.setAtivo(ativo);
		pessoas.save(pessoaSalva);
	}
	
	private Pessoa buscarPessoaPorNome(Long codigo) {
		Pessoa pessoaSalva = pessoas.findOne(codigo);
		if(pessoaSalva == null) {
			throw new EmptyResultDataAccessException(1);
		}
		return pessoaSalva;
	}
}
