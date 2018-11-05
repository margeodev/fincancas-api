package com.pessoal.financas.api.repository.lancamento;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.util.StringUtils;

import com.pessoal.financas.api.model.Lancamento;
import com.pessoal.financas.api.model.Lancamento_;
import com.pessoal.financas.api.repository.filter.LancamentoFilter;


public class LancamentosImpl implements LancamentosQuery {

	@PersistenceContext
	private EntityManager manager;
	
	@Override
	public Page<Lancamento> filtrar(LancamentoFilter lancamentoFilter, Pageable pageable) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Lancamento> criteria = builder.createQuery(Lancamento.class);
		Root<Lancamento> root = criteria.from(Lancamento.class);
		
		Predicate[] predicates = criarRestricoes(lancamentoFilter, builder, root);
		criteria.where(predicates);
		
		TypedQuery<Lancamento> query = manager.createQuery(criteria);
		adicionarRestricoesDePaginacao(query, pageable);
		
		return new PageImpl<> (query.getResultList(), pageable, total(lancamentoFilter));
		
	}

	private Predicate[] criarRestricoes(LancamentoFilter lancamentoFilter, CriteriaBuilder builder,
			Root<Lancamento> root) {
		
		List<Predicate> predicates = new ArrayList<>();
		
		if(!StringUtils.isEmpty(lancamentoFilter.getDescricao())) {
			predicates.add(builder.like(builder.lower(root.get(Lancamento_.descricao)), "%"+lancamentoFilter.getDescricao().toLowerCase()+"%"));
		}
		
		if(lancamentoFilter.getDataVencimentoDe() != null) {
			predicates.add(builder.greaterThanOrEqualTo(root.get(Lancamento_.dataVencimento), lancamentoFilter.getDataVencimentoDe()));			
		}
		
		if(lancamentoFilter.getDataVencimentoAte() != null) {	
			predicates.add(builder.lessThanOrEqualTo(root.get(Lancamento_.dataVencimento), lancamentoFilter.getDataVencimentoAte()));			
		}
		
		return predicates.toArray(new Predicate[predicates.size()]);
	}
	
	private void adicionarRestricoesDePaginacao(TypedQuery<Lancamento> query, Pageable pageable ){
		int pageAtual = pageable.getPageNumber();
		int totalRegistrosPorPagina = pageable.getPageSize();
		int primeiroRegistroDaPagina = pageAtual*totalRegistrosPorPagina;
		
		//Define a página inicial pra mostrar os registros
		query.setFirstResult(primeiroRegistroDaPagina);
		
		//Define o total de registros por página
		query.setMaxResults(totalRegistrosPorPagina);		
	}
	
	private Long total(LancamentoFilter lancamentoFilter) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		
		//Criando a query com o tipo de retorno
		CriteriaQuery<Long> criteria = builder.createQuery(Long.class);
		
		//Onde a consulta é feita
		Root<Lancamento> root = criteria.from(Lancamento.class);
		
		//Adicionando o filtro
		Predicate[] predicates = criarRestricoes(lancamentoFilter, builder, root);
		
		//Adicionando as restrições com filtro
		criteria.where(predicates);
		
		//Conta o TOTAL de registros
		criteria.select(builder.count(root));
		
		//retorna a quantidade de registros com os filtros
		return manager.createQuery(criteria).getSingleResult();
		
	}
	
	
	
	
	
	

}
