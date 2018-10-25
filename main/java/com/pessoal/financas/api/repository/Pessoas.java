package com.pessoal.financas.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pessoal.financas.api.model.Pessoa;

public interface Pessoas extends JpaRepository<Pessoa, Long> {

}
