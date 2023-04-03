package com.pessoal.cobranca.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pessoal.cobranca.model.Receita;

public interface ReceitaRepository extends JpaRepository<Receita, Long>{

	List<Receita> findByDescricaoContaining(String descricao);
	
	

}
