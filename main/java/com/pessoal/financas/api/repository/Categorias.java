package com.pessoal.financas.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pessoal.financas.api.model.Categoria;

public interface Categorias extends JpaRepository<Categoria, Long> {

}
