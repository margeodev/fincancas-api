package com.pessoal.financas.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pessoal.financas.api.model.Lancamento;
import com.pessoal.financas.api.model.Pessoa;
import com.pessoal.financas.api.repository.Lancamentos;
import com.pessoal.financas.api.repository.Pessoas;
import com.pessoal.financas.api.service.exception.PessoaInexistenteOuInativaException;


@Service
public class LancamentoService {

	@Autowired
	private Pessoas pessoas;
	
	@Autowired 
	private Lancamentos lancamentos;
	
	public Lancamento salvar(Lancamento lancamento) {
		Pessoa pessoa = pessoas.findOne(lancamento.getPessoa().getCodigo());
		if(pessoa == null || !pessoa.getAtivo()) {
			throw new PessoaInexistenteOuInativaException();
		}
		return lancamentos.save(lancamento);
	}

}
