package com.pessoal.cobranca.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pessoal.cobranca.model.Titulo;

public interface TituloRepository extends JpaRepository<Titulo, Long> {

	public List<Titulo> findByDescricaoContaining(String descricao);

	// public List<Titulo> findByDataVencimentoBetween(Date dataVencimento, Date
	// dataFim);

	public List<Titulo> findByStartDateBetween(LocalDate dataInicial, LocalDate dataFinal);

}
