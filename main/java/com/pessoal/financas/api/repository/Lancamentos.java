package com.pessoal.financas.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pessoal.financas.api.model.Lancamento;
import com.pessoal.financas.api.repository.lancamento.LancamentosQuery;

public interface Lancamentos extends JpaRepository<Lancamento, Long>, LancamentosQuery{

}
