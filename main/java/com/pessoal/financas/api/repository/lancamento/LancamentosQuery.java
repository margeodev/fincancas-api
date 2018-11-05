package com.pessoal.financas.api.repository.lancamento;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.pessoal.financas.api.model.Lancamento;
import com.pessoal.financas.api.repository.filter.LancamentoFilter;

public interface LancamentosQuery {
	
	public Page<Lancamento> filtrar(LancamentoFilter lancamentoFilter, Pageable pageable);
	
}
