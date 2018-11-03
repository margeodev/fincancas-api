package com.pessoal.financas.api.repository.lancamento;

import java.util.List;

import com.pessoal.financas.api.model.Lancamento;
import com.pessoal.financas.api.repository.filter.LancamentoFilter;

public interface LancamentosQuery {
	
	public List<Lancamento> filtrar(LancamentoFilter lancamentoFilter);
	
}
